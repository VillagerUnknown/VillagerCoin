package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CopyCountComponent;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinCraftingFeature;
import me.villagerunknown.villagercoin.feature.CoinStackCraftingFeature;
import me.villagerunknown.villagercoin.feature.LedgerCraftingFeature;
import me.villagerunknown.villagercoin.feature.ReceiptCraftingFeature;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static me.villagerunknown.villagercoin.component.Components.COPY_COUNT_COMPONENT;
import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

@Mixin(InventoryMenu.class)
public abstract class PlayerScreenHandlerMixin extends AbstractContainerMenu {
	
	@Unique
	protected CraftingContainer craftingInventory = ((CraftingScreenHandlerAccessor) (Object) this).getCraftingInventory();
	
	@Unique
	protected ResultContainer craftingResultInventory = ((CraftingScreenHandlerAccessor) (Object) this).getCraftingResultInventory();
	
	protected PlayerScreenHandlerMixin(@Nullable MenuType<?> type, int syncId) {
		super(type, syncId);
	}
	
	@Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true)
	public void quickMove(Player player, int slot, CallbackInfoReturnable<ItemStack> cir) {
		Slot slot2 = (Slot) this.slots.get(slot);
		if( 0 == slot && slot2.hasItem() ) {
			ItemStack craftedItemStack = this.craftingResultInventory.getItem( slot );
			
			if( !craftedItemStack.isEmpty() ) {
				if( ReceiptCraftingFeature.isCraftingResultReceipt( craftedItemStack.getItem() ) ) {
					// # Receipts - Remove the paper
					
					ReceiptCraftingFeature.subtractCarrierFromIngredients( this.craftingInventory, 1 );
					ReceiptCraftingFeature.setCustomName( player, craftedItemStack );
					
					craftedItemStack.getItem().onCraftedBy(craftedItemStack, player);
					
					if (!this.moveItemStackTo(craftedItemStack, 10, 46, true)) {
						player.drop(craftedItemStack, true);
					} // if
					
					slot2.onQuickCraft(craftedItemStack, this.craftingResultInventory.getItem( slot ));
					
					slot2.setChanged();
					
					// This can allow quick-crafting but requires extra work
					//slot2.onTakeItem(player, craftedItemStack);
					
					cir.setReturnValue( craftedItemStack );
					
				} else if( LedgerCraftingFeature.isCraftingResultLedger( craftedItemStack.getItem() ) ) {
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
					
					craftedItemStack.getItem().onCraftedBy(craftedItemStack, player);
					
					if (!this.moveItemStackTo(craftedItemStack, 10, 46, true)) {
						player.drop(craftedItemStack, true);
					} // if
					
					slot2.onQuickCraft(craftedItemStack, this.craftingInventory.getItem( slot ));
					
					slot2.setChanged();
					
					cir.setReturnValue( craftedItemStack );
					
				} else if( CoinCraftingFeature.isCraftingResultCoin( craftedItemStack.getItem() ) ) {
					// # Coins - Convert up and down between the currencies
					
					CurrencyComponent currencyComponent = craftedItemStack.get( CURRENCY_COMPONENT );
					
					if( null != currencyComponent ) {
						AtomicLong totalCost = new AtomicLong((long) craftedItemStack.getCount() * currencyComponent.value());
						TreeMap<Long, CoinCraftingFeature.CoinIngredient> ingredientsMap = CoinCraftingFeature.getCoinIngredientsMap( this.craftingInventory );
						
						ingredientsMap.forEach( ( order, coinIngredient ) -> {
							int ingredientSlot = coinIngredient.slot;
							ItemStack ingredient = coinIngredient.stack;
							
							totalCost.set( CoinCraftingFeature.subtractCoinValueFromTotalCost( ingredient, totalCost, this.craftingInventory, ingredientSlot ) );
						} );
						
						craftedItemStack.getItem().onCraftedBy(craftedItemStack, player);
						
						if (!this.moveItemStackTo(craftedItemStack, 9, 46, true)) {
							player.drop(craftedItemStack, true);
						}
						
						slot2.onQuickCraft(craftedItemStack, this.craftingResultInventory.getItem( slot ));
						
						slot2.setChanged();
						
						// This can allow the quick-crafting but requires extra work
						// slot2.onTakeItem(player, craftedItemStack);
						
						cir.setReturnValue( craftedItemStack );
						
					} // if
				} // if
			} // if
		} // if
	}
	
}
