package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(CraftingResultSlot.class)
public class CraftingResultSlotMixin {
	
	@Final
	@Shadow
	private RecipeInputInventory input;
	
	@Final
	@Shadow
	private PlayerEntity player;
	
	@Shadow
	private int amount;
	
	@Inject(method = "onTakeItem", at = @At("HEAD"), cancellable = true)
	private void onTakeItem(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
		if( player.getWorld().isClient() ) {
			return;
		} // if
		
		Collection<Item> coins = coinFeature.COIN_ITEMS.values();
		
		if( coins.contains( stack.getItem() ) ) {
			CraftingRecipeInput.Positioned positioned = this.input.createPositionedRecipeInput();
			CraftingRecipeInput craftingRecipeInput = positioned.input();
			int i = positioned.left();
			int j = positioned.top();
			DefaultedList<ItemStack> defaultedList = player.getWorld().getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, craftingRecipeInput, player.getWorld());
			
			AtomicInteger totalCost = new AtomicInteger(stack.getCount() * coinFeature.getCoinValue(stack.getItem()));
			
			TreeMap<Integer, coinFeature.CoinIngredient> ingredientsMap = coinFeature.getCoinIngredientsMap( this.input );
			
			ingredientsMap.forEach( ( order, coinIngredient ) -> {
				int slot = coinIngredient.slot;
				ItemStack ingredient = coinIngredient.stack;
				ItemStack itemStack2 = (ItemStack)defaultedList.get(coinIngredient.x + coinIngredient.y * craftingRecipeInput.getWidth());
				
				if( coins.contains( ingredient.getItem() ) ) {
					int ingredientCoinValue = coinFeature.getCoinValue(ingredient.getItem());
					int ingredientCoinStackValue = ingredient.getCount() * ingredientCoinValue;
					
					if( ingredientCoinValue == totalCost.get()) {
						this.input.removeStack( slot, 1 );
						totalCost.addAndGet(-ingredientCoinValue);
					} else if( ingredientCoinStackValue <= totalCost.get()) {
						this.input.removeStack(slot, ingredient.getCount());
						totalCost.addAndGet(-ingredientCoinStackValue);
					} else if( ingredientCoinValue < totalCost.get()) {
						int amount = totalCost.get() / ingredientCoinValue;
						
						if( amount >= ingredient.getCount() ) {
							this.input.removeStack(slot, ingredient.getCount());
							totalCost.addAndGet(-(ingredientCoinValue * ingredient.getCount()));
						} else {
							this.input.removeStack(slot, amount);
							totalCost.addAndGet(-(ingredientCoinValue * amount));
						} // if, else
					} // if, else
					ingredient = this.input.getStack(slot);
				} // if
				
				if (!itemStack2.isEmpty()) {
					if (ingredient.isEmpty()) {
						this.input.setStack(slot, itemStack2);
					} else if (ItemStack.areItemsAndComponentsEqual(ingredient, itemStack2)) {
						itemStack2.increment(ingredient.getCount());
						this.input.setStack(slot, itemStack2);
					} else if (!this.player.getInventory().insertStack(itemStack2)) {
						this.player.dropItem(itemStack2, false);
					}
				}
			} );
			
			ci.cancel();
		} // if
	}
	
}
