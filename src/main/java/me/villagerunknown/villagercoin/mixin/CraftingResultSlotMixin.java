package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.*;
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

import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

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
		
		if( ReceiptCraftingFeature.isCraftingResultReceipt( stack.getItem() ) ) {
			// # Receipts - Remove the paper
			
			ReceiptCraftingFeature.subtractCarrierFromIngredients( this.input, 1 );
			ReceiptCraftingFeature.setCustomName( player, stack );
			
			this.onCrafted(stack);
			
			ci.cancel();
			
		} else {
			CraftingRecipeInput.Positioned positioned = this.input.createPositionedRecipeInput();
			CraftingRecipeInput craftingRecipeInput = positioned.input();
			DefaultedList<ItemStack> defaultedList = player.getWorld().getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, craftingRecipeInput, player.getWorld());
			
			if( LedgerCraftingFeature.isCraftingResultLedger( stack.getItem() ) ) {
				// # Ledgers - Remove the written book and receipts and add each receipt as a page in the ledger
				
				AtomicReference<TreeMap<String, HashSet<CoinCraftingFeature.CoinIngredient>>> ingredientsMap = new AtomicReference<>(new TreeMap<>());
				
				ItemStack existingLedger = null;
				
				int left = positioned.left();
				int top = positioned.top();
				
				for(int y = 0; y < craftingRecipeInput.getHeight(); ++y) {
					for (int x = 0; x < craftingRecipeInput.getWidth(); ++x) {
						int m = x + left + (y + top) * input.getWidth();
						
						ItemStack ingredient = this.input.getStack( m );
						
						if( ingredient.isIn( Villagercoin.getItemTagKey( "receipt" ) ) ) {
							ingredientsMap.set( LedgerCraftingFeature.updateIngredientsMap( ingredientsMap, ingredient, m, x, y ) );
						}  else if( ingredient.isIn( Villagercoin.getItemTagKey( "ledger" ) ) ) {
							existingLedger = ingredient;
						} // if, else if
					} // for
				} // for
				
				LedgerCraftingFeature.updateLedgerFromSlot( stack, ingredientsMap.get(), existingLedger );
				
				LedgerCraftingFeature.subtractCarrierFromIngredients( this.input, 1 );
				LedgerCraftingFeature.removeReceiptsFromIngredients( this.input.getHeldStacks() );
				
				this.onCrafted(stack);
				
				ci.cancel();
				
			} else if (CoinBankCraftingFeature.isCraftingResultCoinBank(stack)) {
				// # Coin Banks - Receive the currency value of the ingredients
				
				AtomicLong totalCost = new AtomicLong(0);
				TreeMap<Long, CoinCraftingFeature.CoinIngredient> ingredientsMap = CoinCraftingFeature.getCoinIngredientsMap(this.input);
				
				ingredientsMap.forEach((order, coinIngredient) -> {
					ItemStack ingredient = coinIngredient.stack;
					
					CurrencyComponent currencyComponent = ingredient.get(CURRENCY_COMPONENT);
					
					if (null != currencyComponent) {
						totalCost.addAndGet(currencyComponent.value());
					} // if
				});
				
				stack.set(CURRENCY_COMPONENT, new CurrencyComponent(totalCost.get()));
				
			} else if (
					CoinCraftingFeature.isCraftingResultCoin(stack.getItem())
					|| CoinStackCraftingFeature.isCraftingResultCoinStack(stack.getItem())
			) {
				// # Coins - Convert up and down between the currencies
				
				CurrencyComponent currencyComponent = stack.get(CURRENCY_COMPONENT);
				
				if (null != currencyComponent) {
					if (CoinStackCraftingFeature.isCraftingResultCoinStack(stack.getItem())) {
						CoinStackCraftingFeature.subtractCarrierFromIngredients(this.input, 1);
					}
					
					AtomicLong totalCost = new AtomicLong((long) stack.getCount() * currencyComponent.value());
					TreeMap<Long, CoinCraftingFeature.CoinIngredient> ingredientsMap = CoinCraftingFeature.getCoinIngredientsMap(this.input);
					
					ingredientsMap.forEach((order, coinIngredient) -> {
						int ingredientSlot = coinIngredient.slot;
						ItemStack ingredient = coinIngredient.stack;
						ItemStack itemStack2 = (ItemStack) defaultedList.get(coinIngredient.x + coinIngredient.y * craftingRecipeInput.getWidth());
						
						totalCost.set(CoinCraftingFeature.subtractCoinValueFromTotalCost(ingredient, totalCost, this.input, ingredientSlot));
						
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
					
					this.onCrafted(stack);
					
					ci.cancel();
					
				} // if
			} // if, else if
		} // if, else
	}
	
	@Shadow
	protected void onCrafted(ItemStack stack) {}
	
}
