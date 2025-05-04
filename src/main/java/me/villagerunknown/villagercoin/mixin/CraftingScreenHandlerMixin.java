package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

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
			
			if( !craftedItemStack.isEmpty() ) {
				if( ReceiptCraftingFeature.isCraftingResultReceipt( craftedItemStack.getItem() ) ) {
					// # Receipts - Remove the paper
					
					ReceiptCraftingFeature.subtractCarrierFromIngredients( this.input, 1 );
					ReceiptCraftingFeature.setCustomName( player, craftedItemStack );
					
					this.context.run((world, pos) -> craftedItemStack.getItem().onCraftByPlayer(craftedItemStack, world, player));
					
					if (!this.insertItem(craftedItemStack, 10, 46, true)) {
						player.dropItem(craftedItemStack, true);
					} // if
					
					Slot slot2 = (Slot) this.slots.get(slot);
					slot2.onQuickTransfer(craftedItemStack, this.result.getStack( slot ));
					
					slot2.markDirty();
					
					cir.setReturnValue( craftedItemStack );
					
				} else {
					if( LedgerCraftingFeature.isCraftingResultLedger( craftedItemStack.getItem() ) ) {
						// # Ledgers - Remove the written book and receipts and add each receipt as a page in the ledger
						
						AtomicReference<TreeMap<String, HashSet<ItemStack>>> ingredientsMap = new AtomicReference<>(new TreeMap<>());
						
						ItemStack existingLedger = null;
						
						for(ItemStack ingredient : this.input.getHeldStacks()) {
							if( ingredient.isIn( Villagercoin.getItemTagKey( "receipt" ) ) ) {
								ingredientsMap.set( LedgerCraftingFeature.updateIngredientsMap( ingredientsMap, ingredient ) );
							} else if( ingredient.isIn( Villagercoin.getItemTagKey( "ledger" ) ) ) {
								existingLedger = ingredient;
							} // if, else if
						} // for
						
						LedgerCraftingFeature.updateLedger( craftedItemStack, ingredientsMap, existingLedger );
						
						LedgerCraftingFeature.subtractCarrierFromIngredients( this.input, 1 );
						LedgerCraftingFeature.removeReceiptsFromIngredients( this.input.getHeldStacks() );
						
						this.context.run((world, pos) -> craftedItemStack.getItem().onCraftByPlayer(craftedItemStack, world, player));
						
						if (!this.insertItem(craftedItemStack, 10, 46, true)) {
							player.dropItem(craftedItemStack, true);
						} // if
						
						Slot slot2 = (Slot) this.slots.get(slot);
						slot2.onQuickTransfer(craftedItemStack, this.result.getStack( slot ));
						
						slot2.markDirty();
						
						cir.setReturnValue( craftedItemStack );
						
					} else if( CoinBankCraftingFeature.isCraftingResultCoinBank( craftedItemStack ) ) {
						// # Coin Banks - Receive the currency value of the ingredients
						
						AtomicLong totalCost = new AtomicLong(0);
						TreeMap<Long, CoinCraftingFeature.CoinIngredient> ingredientsMap = CoinCraftingFeature.getCoinIngredientsMap( this.input );
						
						ingredientsMap.forEach(( order, coinIngredient ) -> {
							ItemStack ingredient = coinIngredient.stack;
							
							CurrencyComponent currencyComponent = ingredient.get( CURRENCY_COMPONENT );
							
							if( null != currencyComponent ) {
								totalCost.addAndGet( currencyComponent.value() );
							} // if
						});
						
						craftedItemStack.set( CURRENCY_COMPONENT, new CurrencyComponent( totalCost.get() ) );
						
					} else if(
							CoinCraftingFeature.isCraftingResultCoin( craftedItemStack.getItem() )
									|| CoinStackCraftingFeature.isCraftingResultCoinStack( craftedItemStack.getItem() )
					) {
						// # Coins - Convert up and down between the currencies
						
						CurrencyComponent currencyComponent = craftedItemStack.get( CURRENCY_COMPONENT );
						
						if( null != currencyComponent ) {
							if( CoinStackCraftingFeature.isCraftingResultCoinStack( craftedItemStack.getItem() ) ) {
								CoinStackCraftingFeature.subtractCarrierFromIngredients( this.input, 1 );
							} // if
							
							AtomicLong totalCost = new AtomicLong((long) craftedItemStack.getCount() * currencyComponent.value());
							TreeMap<Long, CoinCraftingFeature.CoinIngredient> ingredientsMap = CoinCraftingFeature.getCoinIngredientsMap( this.input );
							
							ingredientsMap.forEach(( order, coinIngredient ) -> {
								int ingredientSlot = coinIngredient.slot;
								ItemStack ingredient = coinIngredient.stack;
								
								totalCost.set( CoinCraftingFeature.subtractCoinValueFromTotalCost( ingredient, totalCost, this.input, ingredientSlot ) );
							});
							
							this.context.run((world, pos) -> craftedItemStack.getItem().onCraftByPlayer(craftedItemStack, world, player));
							
							if (!this.insertItem(craftedItemStack, 10, 46, true)) {
								player.dropItem(craftedItemStack, true);
							}
							
							Slot slot2 = (Slot) this.slots.get(slot);
							slot2.onQuickTransfer(craftedItemStack, this.result.getStack( slot ));
							
							slot2.markDirty();
							
							cir.setReturnValue( craftedItemStack );
						} // if
						
					} // if, else if
				} // if, else
			} // if
		} // if
	}
	
}
