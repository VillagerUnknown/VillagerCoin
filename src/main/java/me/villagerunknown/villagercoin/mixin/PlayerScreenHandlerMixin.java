package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinCraftingFeature;
import me.villagerunknown.villagercoin.feature.CoinStackCraftingFeature;
import me.villagerunknown.villagercoin.feature.LedgerCraftingFeature;
import me.villagerunknown.villagercoin.feature.ReceiptCraftingFeature;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
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

import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends ScreenHandler {
	
	@Final
	@Shadow
	private RecipeInputInventory craftingInput;
	
	@Final
	@Shadow
	private CraftingResultInventory craftingResult;
	
	protected PlayerScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
		super(type, syncId);
	}
	
	@Inject(method = "quickMove", at = @At("HEAD"), cancellable = true)
	public void quickMove(PlayerEntity player, int slot, CallbackInfoReturnable<ItemStack> cir) {
		Slot slot2 = (Slot) this.slots.get(slot);
		if( 0 == slot && slot2.hasStack() ) {
			ItemStack craftedItemStack = this.craftingResult.getStack( slot );
			
			if( !craftedItemStack.isEmpty() ) {
				if( ReceiptCraftingFeature.isCraftingResultReceipt( craftedItemStack.getItem() ) ) {
					// # Receipts - Remove the paper
					
					ReceiptCraftingFeature.subtractCarrierFromIngredients( this.craftingInput, 1 );
					ReceiptCraftingFeature.setCustomName( player, craftedItemStack );
					
					craftedItemStack.getItem().onCraftByPlayer(craftedItemStack, player.getWorld(), player);
					
					if (!this.insertItem(craftedItemStack, 10, 46, true)) {
						player.dropItem(craftedItemStack, true);
					} // if
					
					slot2.onQuickTransfer(craftedItemStack, this.craftingResult.getStack( slot ));
					
					slot2.markDirty();
					
					// This can allow quick-crafting but requires extra work
					//slot2.onTakeItem(player, craftedItemStack);
					
					cir.setReturnValue( craftedItemStack );
					
				} else if( LedgerCraftingFeature.isCraftingResultLedger( craftedItemStack.getItem() ) ) {
					// # Ledgers - Remove the written book and receipts and add each receipt as a page in the ledger
					
					AtomicReference<TreeMap<String, HashSet<ItemStack>>> ingredientsMap = new AtomicReference<>(new TreeMap<>());
					
					ItemStack existingLedger = null;
					
					for(ItemStack ingredient : this.craftingInput.getHeldStacks()) {
						if( ingredient.isIn( Villagercoin.getItemTagKey( "receipt" ) ) ) {
							ingredientsMap.set( LedgerCraftingFeature.updateIngredientsMap( ingredientsMap, ingredient ) );
						} else if( ingredient.isIn( Villagercoin.getItemTagKey( "ledger" ) ) ) {
							existingLedger = ingredient;
						} // if, else if
					} // for
					
					LedgerCraftingFeature.updateLedger( craftedItemStack, ingredientsMap, existingLedger );
					
					LedgerCraftingFeature.subtractCarrierFromIngredients( this.craftingInput, 1 );
					LedgerCraftingFeature.removeReceiptsFromIngredients( this.craftingInput.getHeldStacks() );
					
					craftedItemStack.getItem().onCraftByPlayer(craftedItemStack, player.getWorld(), player);
					
					if (!this.insertItem(craftedItemStack, 10, 46, true)) {
						player.dropItem(craftedItemStack, true);
					} // if
					
					slot2.onQuickTransfer(craftedItemStack, this.craftingInput.getStack( slot ));
					
					slot2.markDirty();
					
					cir.setReturnValue( craftedItemStack );
					
				} else if( CoinCraftingFeature.isCraftingResultCoin( craftedItemStack.getItem() ) ) {
					// # Coins - Convert up and down between the currencies
					
					CurrencyComponent currencyComponent = craftedItemStack.get( CURRENCY_COMPONENT );
					
					if( null != currencyComponent ) {
						AtomicLong totalCost = new AtomicLong((long) craftedItemStack.getCount() * currencyComponent.value());
						TreeMap<Long, CoinCraftingFeature.CoinIngredient> ingredientsMap = CoinCraftingFeature.getCoinIngredientsMap( this.craftingInput );
						
						ingredientsMap.forEach( ( order, coinIngredient ) -> {
							int ingredientSlot = coinIngredient.slot;
							ItemStack ingredient = coinIngredient.stack;
							
							totalCost.set( CoinCraftingFeature.subtractCoinValueFromTotalCost( ingredient, totalCost, this.craftingInput, ingredientSlot ) );
						} );
						
						craftedItemStack.getItem().onCraftByPlayer(craftedItemStack, player.getWorld(), player);
						
						if (!this.insertItem(craftedItemStack, 9, 45, true)) {
							player.dropItem(craftedItemStack, true);
						}
						
						slot2.onQuickTransfer(craftedItemStack, this.craftingResult.getStack( slot ));
						
						slot2.markDirty();
						
						// This can allow the quick-crafting but requires extra work
						// slot2.onTakeItem(player, craftedItemStack);
						
						cir.setReturnValue( craftedItemStack );
						
					} // if
				} // if
			} // if
		} // if
	}
	
}
