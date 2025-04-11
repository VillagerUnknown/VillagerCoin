package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.data.type.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

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
		
		if( coinFeature.COINS.containsValue( stack.getItem() ) ) {
			CraftingRecipeInput.Positioned positioned = this.input.createPositionedRecipeInput();
			CraftingRecipeInput craftingRecipeInput = positioned.input();
			DefaultedList<ItemStack> defaultedList = player.getWorld().getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, craftingRecipeInput, player.getWorld());
			
			CurrencyComponent currencyComponent = stack.get( CURRENCY_COMPONENT );
			
			if( null != currencyComponent ) {
				AtomicInteger totalCost = new AtomicInteger(stack.getCount() * currencyComponent.value());
				TreeMap<Integer, coinFeature.CoinIngredient> ingredientsMap = coinFeature.getCoinIngredientsMap( this.input );
				
				ingredientsMap.forEach(( order, coinIngredient ) -> {
					int ingredientSlot = coinIngredient.slot;
					ItemStack ingredient = coinIngredient.stack;
					ItemStack itemStack2 = (ItemStack)defaultedList.get(coinIngredient.x + coinIngredient.y * craftingRecipeInput.getWidth());
					
					totalCost.set( coinFeature.subtractCoinValueFromTotalCost( ingredient, totalCost, this.input, ingredientSlot ) );
					
					if (!itemStack2.isEmpty()) {
						if (ingredient.isEmpty()) {
							this.input.setStack(ingredientSlot, itemStack2);
						} else if (ItemStack.areItemsAndComponentsEqual(ingredient, itemStack2)) {
							itemStack2.increment(ingredient.getCount());
							this.input.setStack(ingredientSlot, itemStack2);
						} else if (!this.player.getInventory().insertStack(itemStack2)) {
							this.player.dropItem(itemStack2, false);
						}
					}
				});
				
				ci.cancel();
			} // if
		} // if
	}
	
}
