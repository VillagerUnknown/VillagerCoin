package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CopyCountComponent;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.*;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static me.villagerunknown.villagercoin.component.Components.COPY_COUNT_COMPONENT;
import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

@Mixin(ResultSlot.class)
public class CraftingResultSlotMixin {
	
	@Final
	@Shadow
	private CraftingContainer craftSlots;
	
	@Final
	@Shadow
	private Player player;
	
	@Shadow
	private int removeCount;
	
	@Inject(method = "onTake", at = @At("HEAD"), cancellable = true)
	private void onTakeItem(Player player, ItemStack stack, CallbackInfo ci) {
		if( player.level().isClientSide() ) {
			return;
		} // if
		
		if( ReceiptCraftingFeature.isCraftingResultReceipt( stack.getItem() ) ) {
			// # Receipts - Remove the paper
			
			ReceiptCraftingFeature.subtractCarrierFromIngredients( this.craftSlots, 1 );
			ReceiptCraftingFeature.setCustomName( player, stack );
			
			this.checkTakeAchievements(stack);
			
			ci.cancel();
			
		} else {
			CraftingInput.Positioned positioned = this.craftSlots.asPositionedCraftInput();
			CraftingInput craftingRecipeInput = positioned.input();
			NonNullList<ItemStack> defaultedList;
			
			if( player.level() instanceof ServerLevel serverWorld ) {
				defaultedList = serverWorld.recipeAccess().getRecipeFor(RecipeType.CRAFTING, craftingRecipeInput, serverWorld).map((recipe) -> ((CraftingRecipe)recipe.value()).getRemainingItems(craftingRecipeInput)).orElseGet(() -> copyInput(craftingRecipeInput));
			} else {
				defaultedList = CraftingRecipe.defaultCraftingReminder(craftingRecipeInput);
			} // if, else
			
			if( LedgerCraftingFeature.isCraftingResultLedger( stack.getItem() ) ) {
				// # Ledgers - Remove the written book and receipts and add each receipt as a page in the ledger
				
				AtomicReference<TreeMap<String, HashSet<CoinCraftingFeature.CoinIngredient>>> ingredientsMap = new AtomicReference<>(new TreeMap<>());
				
				ItemStack existingLedger = null;
				
				int left = positioned.left();
				int top = positioned.top();
				
				int receipts = 0;
				
				for(int y = 0; y < craftingRecipeInput.height(); ++y) {
					for (int x = 0; x < craftingRecipeInput.width(); ++x) {
						int m = x + left + (y + top) * craftSlots.getWidth();
						
						ItemStack ingredient = this.craftSlots.getItem( m );
						
						if( ingredient.is( Villagercoin.getItemTagKey( "receipt" ) ) ) {
							receipts++;
							ingredientsMap.set( LedgerCraftingFeature.updateIngredientsMap( ingredientsMap, ingredient, m, x, y ) );
						}  else if( ingredient.is( Villagercoin.getItemTagKey( "ledger" ) ) ) {
							existingLedger = ingredient;
						} // if, else if
					} // for
				} // for
				
				LedgerCraftingFeature.updateLedgerFromSlot( stack, ingredientsMap.get(), existingLedger, (receipts == 0) );
				
				if( receipts > 0 ) {
					LedgerCraftingFeature.subtractLedgerFromIngredients( this.craftSlots, 1 );
				}  // if
				
				LedgerCraftingFeature.subtractCarrierFromIngredients( this.craftSlots, 1 );
				LedgerCraftingFeature.removeReceiptsFromIngredients( this.craftSlots.getItems() );
				
				this.checkTakeAchievements(stack);
				
				ci.cancel();
				
			} else if (CoinBankCraftingFeature.isCraftingResultCoinBank(stack)) {
				// # Coin Banks - Receive the currency value of the ingredients
				
				AtomicLong totalCost = new AtomicLong(0);
				TreeMap<Long, CoinCraftingFeature.CoinIngredient> ingredientsMap = CoinCraftingFeature.getCoinIngredientsMap(this.craftSlots);
				
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
						CoinStackCraftingFeature.subtractCarrierFromIngredients(this.craftSlots, 1);
					}
					
					AtomicLong totalCost = new AtomicLong((long) stack.getCount() * currencyComponent.value());
					TreeMap<Long, CoinCraftingFeature.CoinIngredient> ingredientsMap = CoinCraftingFeature.getCoinIngredientsMap(this.craftSlots);
					
					ingredientsMap.forEach((order, coinIngredient) -> {
						int ingredientSlot = coinIngredient.slot;
						ItemStack ingredient = coinIngredient.stack;
						ItemStack itemStack2 = (ItemStack) defaultedList.get(coinIngredient.x + coinIngredient.y * craftingRecipeInput.width());
						
						totalCost.set(CoinCraftingFeature.subtractCoinValueFromTotalCost(ingredient, totalCost, this.craftSlots, ingredientSlot));
						
						if (!itemStack2.isEmpty()) {
							if (ingredient.isEmpty()) {
								this.craftSlots.setItem(ingredientSlot, itemStack2);
							} else if (ItemStack.isSameItemSameComponents(ingredient, itemStack2)) {
								itemStack2.grow(ingredient.getCount());
								this.craftSlots.setItem(ingredientSlot, itemStack2);
							} else if (!this.player.getInventory().add(itemStack2)) {
								this.player.drop(itemStack2, false);
							}
						}
					});
					
					this.checkTakeAchievements(stack);
					
					ci.cancel();
					
				} // if
			} // if, else if
		} // if, else
	}
	
	@Shadow
	protected void checkTakeAchievements(ItemStack stack) {}
	
	@Unique
	private static NonNullList<ItemStack> copyInput(CraftingInput input) {
		NonNullList<ItemStack> defaultedList = NonNullList.withSize(input.size(), ItemStack.EMPTY);
		
		for(int i = 0; i < defaultedList.size(); ++i) {
			defaultedList.set(i, input.getItem(i));
		}
		
		return defaultedList;
	}
	
}
