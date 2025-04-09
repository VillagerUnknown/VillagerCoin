package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(CraftingScreenHandler.class)
public abstract class CraftingScreenHandlerMixin extends ScreenHandler {
	
	@Final
	@Shadow
	private RecipeInputInventory input;
	
	@Final
	@Shadow
	private CraftingResultInventory result;
	
	@Final
	@Shadow
	private ScreenHandlerContext context;
	
	@Final
	@Shadow
	private PlayerEntity player;
	
	protected CraftingScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
		super(type, syncId);
	}
	
	@Inject(method = "quickMove", at = @At("HEAD"), cancellable = true)
	public void quickMove(PlayerEntity player, int slot, CallbackInfoReturnable<ItemStack> cir) {
		if( 0 == slot ) {
			
			ItemStack craftedItemStack = this.result.getStack( slot );
			
			if( coinFeature.COIN_ITEMS.containsValue( craftedItemStack.getItem() ) ) {
				Collection<Item> coins = coinFeature.COIN_ITEMS.values();
				
				CraftingRecipeInput.Positioned positioned = this.input.createPositionedRecipeInput();
				CraftingRecipeInput craftingRecipeInput = positioned.input();
				int i = positioned.left();
				int j = positioned.top();
				DefaultedList<ItemStack> defaultedList = player.getWorld().getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, craftingRecipeInput, player.getWorld());
				
				AtomicInteger totalCost = new AtomicInteger(craftedItemStack.getCount() * coinFeature.getCoinValue(craftedItemStack.getItem()));
				
				TreeMap<Integer, coinFeature.CoinIngredient> ingredientsMap = coinFeature.getCoinIngredientsMap( this.input );
				
				ingredientsMap.forEach( ( order, coinIngredient ) -> {
					int ingredientSlot = coinIngredient.slot;
					ItemStack ingredient = coinIngredient.stack;
					ItemStack itemStack2 = (ItemStack)defaultedList.get(coinIngredient.x + coinIngredient.y * craftingRecipeInput.getWidth());
					
					if( coins.contains( ingredient.getItem() ) ) {
						int ingredientCoinValue = coinFeature.getCoinValue(ingredient.getItem());
						int ingredientCoinStackValue = ingredient.getCount() * ingredientCoinValue;
						
						if( ingredientCoinValue == totalCost.get()) {
							this.input.removeStack(ingredientSlot, 1 );
							totalCost.addAndGet(-ingredientCoinValue);
						} else if( ingredientCoinStackValue <= totalCost.get()) {
							this.input.removeStack(ingredientSlot, ingredient.getCount());
							totalCost.addAndGet(-ingredientCoinStackValue);
						} else if( ingredientCoinValue < totalCost.get()) {
							int amount = totalCost.get() / ingredientCoinValue;
							
							if( amount >= ingredient.getCount() ) {
								this.input.removeStack(ingredientSlot, ingredient.getCount());
								totalCost.addAndGet(-(ingredientCoinValue * ingredient.getCount()));
							} else {
								this.input.removeStack(ingredientSlot, amount);
								totalCost.addAndGet(-(ingredientCoinValue * amount));
							} // if, else
						} // if, else
						ingredient = this.input.getStack(ingredientSlot);
					} // if
					
					if (!craftedItemStack.isEmpty()) {
						this.context.run((world, pos) -> {
							craftedItemStack.getItem().onCraftByPlayer(craftedItemStack, world, player);
						});
						if (!this.insertItem(craftedItemStack, 10, 46, true)) {
							player.dropItem(craftedItemStack, false);
						}
						
						Slot slot2 = (Slot) this.slots.get(slot);
						slot2.onQuickTransfer(craftedItemStack, craftedItemStack);
						
						slot2.markDirty();
					} // if
				} );
				
				cir.setReturnValue( ItemStack.EMPTY );
			} // if
			
		} // if
	}
	
}
