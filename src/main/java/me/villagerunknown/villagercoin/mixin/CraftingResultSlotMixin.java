package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinCraftingFeature;
import me.villagerunknown.villagercoin.feature.CoinStackCraftingFeature;
import me.villagerunknown.villagercoin.feature.ReceiptCraftingFeature;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
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

import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.LOGGER;

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
		
		if( ReceiptCraftingFeature.isCraftingResultReceipt( stack.getItem() ) ) {
			ReceiptCraftingFeature.subtractCarrierFromIngredients( this.input, 1 );
			ReceiptCraftingFeature.setCustomName( player, stack );
			ci.cancel();
		} else if(
				CoinCraftingFeature.isCraftingResultCoin( stack.getItem() )
				|| CoinStackCraftingFeature.isCraftingResultCoinStack( stack.getItem() )
		) {
			CraftingRecipeInput.Positioned positioned = this.input.createPositionedRecipeInput();
			CraftingRecipeInput craftingRecipeInput = positioned.input();
			DefaultedList<ItemStack> defaultedList = player.getWorld().getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, craftingRecipeInput, player.getWorld());
			
			CurrencyComponent currencyComponent = stack.get( CURRENCY_COMPONENT );
			
			if( null != currencyComponent ) {
				if( CoinStackCraftingFeature.isCraftingResultCoinStack( stack.getItem() ) ) {
					CoinStackCraftingFeature.subtractCarrierFromIngredients( this.input, 1 );
				}
				
				AtomicLong totalCost = new AtomicLong((long) stack.getCount() * currencyComponent.value());
				TreeMap<Long, CoinCraftingFeature.CoinIngredient> ingredientsMap = CoinCraftingFeature.getCoinIngredientsMap( this.input );
				
				ingredientsMap.forEach(( order, coinIngredient ) -> {
					int ingredientSlot = coinIngredient.slot;
					ItemStack ingredient = coinIngredient.stack;
					ItemStack itemStack2 = (ItemStack)defaultedList.get(coinIngredient.x + coinIngredient.y * craftingRecipeInput.getWidth());
					
					totalCost.set( CoinCraftingFeature.subtractCoinValueFromTotalCost( ingredient, totalCost, this.input, ingredientSlot ) );
					
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
