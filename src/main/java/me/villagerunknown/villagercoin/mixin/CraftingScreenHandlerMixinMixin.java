package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

@Mixin(CraftingMenu.class)
public abstract class CraftingScreenHandlerMixinMixin extends AbstractContainerMenu {
	
	@Unique
	protected CraftingContainer craftingInventory = ((CraftingScreenHandlerAccessor) (Object) this).getCraftingInventory();
	
	@Unique
	protected ResultContainer craftingResultInventory = ((CraftingScreenHandlerAccessor) (Object) this).getCraftingResultInventory();
	
	@Final
	@Shadow
	private ContainerLevelAccess access;
	
	@Final
	@Shadow
	private Player player;
	
	protected CraftingScreenHandlerMixinMixin(MenuType<?> type, int syncId) {
		super(type, syncId);
	}
	
	@Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true)
	public void quickMove(Player player, int slot, CallbackInfoReturnable<ItemStack> cir) {
		if( 0 == slot ) {
			Slot slot2 = (Slot)this.slots.get(slot);
			
			if( slot2 != null && slot2.hasItem() ) {
				
				ItemStack craftedItemStack = slot2.getItem();
				
				if( !craftedItemStack.isEmpty() ) {
					if( ReceiptCraftingFeature.isCraftingResultReceipt( craftedItemStack.getItem() ) ) {
						// # Receipts - Remove the paper
						
						ReceiptCraftingFeature.subtractCarrierFromIngredients( this.craftingInventory, 1 );
						ReceiptCraftingFeature.setCustomName( player, craftedItemStack );
						
						this.access.execute((world, pos) -> craftedItemStack.getItem().onCraftedBy(craftedItemStack, player));
						
						if (!this.moveItemStackTo(craftedItemStack, 10, 46, true)) {
							player.drop(craftedItemStack, true);
						} // if
						
						slot2.onQuickCraft(craftedItemStack, this.craftingResultInventory.getItem( slot ));
						
						slot2.setChanged();
						
						cir.setReturnValue( craftedItemStack );
						
					} else {
						if( LedgerCraftingFeature.isCraftingResultLedger( craftedItemStack.getItem() ) ) {
							// # Ledgers - Remove the written book and receipts and add each receipt as a page in the ledger
							
							AtomicReference<TreeMap<String, HashSet<ItemStack>>> ingredientsMap = new AtomicReference<>(new TreeMap<>());
							
							ItemStack existingLedger = null;
							
							int receipts = 0;
							
							for(ItemStack ingredient : this.craftingInventory.getItems()) {
								if( ingredient.is( Villagercoin.getItemTagKey( "receipt" ) ) ) {
									receipts++;
									ingredientsMap.set( LedgerCraftingFeature.updateIngredientsMap( ingredientsMap, ingredient ) );
								} else if( ingredient.is( Villagercoin.getItemTagKey( "ledger" ) ) ) {
									existingLedger = ingredient;
								} // if, else if
							} // for
							
							LedgerCraftingFeature.updateLedger( craftedItemStack, ingredientsMap, existingLedger, (receipts == 0) );
							
							if( receipts > 0 ) {
								LedgerCraftingFeature.subtractLedgerFromIngredients( this.craftingInventory, 1 );
							}  // if
							
							LedgerCraftingFeature.subtractCarrierFromIngredients( this.craftingInventory, 1 );
							LedgerCraftingFeature.removeReceiptsFromIngredients( this.craftingInventory.getItems() );
							
							this.access.execute((world, pos) -> craftedItemStack.getItem().onCraftedBy(craftedItemStack, player));
							
							if (!this.moveItemStackTo(craftedItemStack, 10, 46, true)) {
								player.drop(craftedItemStack, true);
							} // if
							
							slot2.onQuickCraft(craftedItemStack, this.craftingResultInventory.getItem( slot ));
							
							slot2.setChanged();
							
							cir.setReturnValue( craftedItemStack );
							
						} else if( CoinBankCraftingFeature.isCraftingResultCoinBank( craftedItemStack ) ) {
							// # Coin Banks - Receive the currency value of the ingredients
							
							AtomicLong totalCost = new AtomicLong(0);
							TreeMap<Long, CoinCraftingFeature.CoinIngredient> ingredientsMap = CoinCraftingFeature.getCoinIngredientsMap( this.craftingInventory );
							
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
									CoinStackCraftingFeature.subtractCarrierFromIngredients( this.craftingInventory, 1 );
								} // if
								
								AtomicLong totalCost = new AtomicLong((long) craftedItemStack.getCount() * currencyComponent.value());
								TreeMap<Long, CoinCraftingFeature.CoinIngredient> ingredientsMap = CoinCraftingFeature.getCoinIngredientsMap( this.craftingInventory );
								
								ingredientsMap.forEach(( order, coinIngredient ) -> {
									int ingredientSlot = coinIngredient.slot;
									ItemStack ingredient = coinIngredient.stack;
									
									totalCost.set( CoinCraftingFeature.subtractCoinValueFromTotalCost( ingredient, totalCost, this.craftingInventory, ingredientSlot ) );
								});
								
								this.access.execute((world, pos) -> craftedItemStack.getItem().onCraftedBy(craftedItemStack, player));
								
								if (!this.moveItemStackTo(craftedItemStack, 10, 46, true)) {
									player.drop(craftedItemStack, true);
								}
								
								slot2.onQuickCraft(craftedItemStack, this.craftingResultInventory.getItem( slot ));
								
								slot2.setChanged();
								
								cir.setReturnValue( craftedItemStack );
							} // if
							
						} // if, else if
					} // if, else
				} // if
				
			} // if
			
		} // if
	}
	
}
