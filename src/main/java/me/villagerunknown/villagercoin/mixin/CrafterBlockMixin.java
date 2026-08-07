package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CopyCountComponent;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.CrafterBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CrafterBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static me.villagerunknown.villagercoin.component.Components.COPY_COUNT_COMPONENT;
import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;
import static net.minecraft.world.level.block.CrafterBlock.CRAFTING;
import static net.minecraft.world.level.block.CrafterBlock.getPotentialResults;

@Mixin(CrafterBlock.class)
public class CrafterBlockMixin {
	
	@Shadow
	private void dispenseItem(ServerLevel world, BlockPos pos, CrafterBlockEntity blockEntity, ItemStack stack, BlockState state, RecipeHolder<CraftingRecipe> recipe) {}
	
	@Inject(method = "dispenseFrom", at = @At("HEAD"), cancellable = true)
	protected void craft(BlockState state, ServerLevel world, BlockPos pos, CallbackInfo ci) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if(blockEntity instanceof CrafterBlockEntity crafterBlockEntity) {
			CraftingInput craftingRecipeInput = crafterBlockEntity.asCraftInput();
			
			Optional optional = getPotentialResults(world, craftingRecipeInput);
			if( optional.isPresent() ) {
				RecipeHolder<CraftingRecipe> recipeEntry = (RecipeHolder)optional.get();
				ItemStack itemStack = ((CraftingRecipe)recipeEntry.value()).assemble(craftingRecipeInput);
				if (itemStack.isEmpty()) {
					world.levelEvent(1050, pos, 0);
				} else {
					if( ReceiptCraftingFeature.isCraftingResultReceipt( itemStack.getItem() ) ) {
						// # Receipts - Remove the paper
						
						ReceiptCraftingFeature.subtractCarrierFromIngredients( crafterBlockEntity.getItems(), 1 );
						
						crafterBlockEntity.setCraftingTicksRemaining(6);
						world.setBlock(pos, (BlockState)state.setValue(CRAFTING, true), 2);
						itemStack.onCraftedBySystem(world);
						this.dispenseItem(world, pos, crafterBlockEntity, itemStack, state, recipeEntry);
						Iterator var9 = ((CraftingRecipe)recipeEntry.value()).getRemainingItems(craftingRecipeInput).iterator();
						
						while(var9.hasNext()) {
							ItemStack itemStack2 = (ItemStack)var9.next();
							if (!itemStack2.isEmpty()) {
								this.dispenseItem(world, pos, crafterBlockEntity, itemStack2, state, recipeEntry);
							}
						} // while
						
						crafterBlockEntity.setChanged();
						
						ci.cancel();
						
					} else if( LedgerCraftingFeature.isCraftingResultLedger( itemStack.getItem() ) ) {
						// # Ledgers - Remove the written book and receipts and add each receipt as a page in the ledger
						
						AtomicReference<TreeMap<String, HashSet<ItemStack>>> ingredientsMap = new AtomicReference<>(new TreeMap<>());
						
						ItemStack existingLedger = null;
						
						int receipts = 0;
						
						for(ItemStack ingredient : crafterBlockEntity.getItems()) {
							if( ingredient.is( Villagercoin.getItemTagKey( "receipt" ) ) ) {
								receipts++;
								ingredientsMap.set( LedgerCraftingFeature.updateIngredientsMap( ingredientsMap, ingredient ) );
							}  else if( ingredient.is( Villagercoin.getItemTagKey( "ledger" ) ) ) {
								existingLedger = ingredient;
							} // if, else if
						} // for
						
						LedgerCraftingFeature.updateLedger( itemStack, ingredientsMap, existingLedger, (receipts == 0) );
						
						if( receipts > 0 ) {
							LedgerCraftingFeature.subtractLedgerFromIngredients( crafterBlockEntity.getItems(), 1 );
						} // if
						
						LedgerCraftingFeature.subtractCarrierFromIngredients( crafterBlockEntity.getItems(), 1 );
						LedgerCraftingFeature.removeReceiptsFromIngredients( crafterBlockEntity.getItems() );
						
						crafterBlockEntity.setCraftingTicksRemaining(6);
						world.setBlock(pos, (BlockState)state.setValue(CRAFTING, true), 2);
						itemStack.onCraftedBySystem(world);
						this.dispenseItem(world, pos, crafterBlockEntity, itemStack, state, recipeEntry);
						Iterator var9 = ((CraftingRecipe)recipeEntry.value()).getRemainingItems(craftingRecipeInput).iterator();
						
						while(var9.hasNext()) {
							ItemStack itemStack2 = (ItemStack)var9.next();
							if (!itemStack2.isEmpty()) {
								this.dispenseItem(world, pos, crafterBlockEntity, itemStack2, state, recipeEntry);
							}
						} // while
					} else if( CoinBankCraftingFeature.isCraftingResultCoinBank( itemStack ) ) {
						// # Coin Banks - Receive the currency value of the ingredients
						
						AtomicLong totalCost = new AtomicLong(0);
						
						crafterBlockEntity.getItems().forEach((ingredient) -> {
							CurrencyComponent currencyComponent = ingredient.get( CURRENCY_COMPONENT );
							
							if( null != currencyComponent ) {
								totalCost.addAndGet( currencyComponent.value() );
							} // if
						});
						
						itemStack.set( CURRENCY_COMPONENT, new CurrencyComponent( totalCost.get() ) );
						
					} else if(
							CoinCraftingFeature.isCraftingResultCoin( itemStack.getItem() )
							|| CoinStackCraftingFeature.isCraftingResultCoinStack( itemStack.getItem() )
					) {
						// # Coins - Convert up and down between the currencies
						
						CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
						
						if( null != currencyComponent ) {
							if( CoinStackCraftingFeature.isCraftingResultCoinStack( itemStack.getItem() ) ) {
								CoinStackCraftingFeature.subtractCarrierFromIngredients( crafterBlockEntity.getItems(), 1 );
							} // if
							
							crafterBlockEntity.setCraftingTicksRemaining(6);
							world.setBlock(pos, (BlockState)state.setValue(CRAFTING, true), 2);
							itemStack.onCraftedBySystem(world);
							this.dispenseItem(world, pos, crafterBlockEntity, itemStack, state, recipeEntry);
							Iterator var9 = ((CraftingRecipe)recipeEntry.value()).getRemainingItems(craftingRecipeInput).iterator();
							
							while(var9.hasNext()) {
								ItemStack itemStack2 = (ItemStack)var9.next();
								if (!itemStack2.isEmpty()) {
									this.dispenseItem(world, pos, crafterBlockEntity, itemStack2, state, recipeEntry);
								}
							}
							
							// This ingredients map doesn't require as much information as the others.
							// ItemStack is used instead of coinFeature.CoinIngredient
							AtomicReference<TreeMap<Long, ItemStack>> ingredientsMap = new AtomicReference<>(new TreeMap<>(Villagercoin.reverseSortLong));
							
							crafterBlockEntity.getItems().forEach((stack) -> {
								ingredientsMap.set(CoinCraftingFeature.updateCoinIngredientsMap(ingredientsMap.get(), stack));
							});
							
							AtomicLong totalCost = new AtomicLong((long) itemStack.getCount() * currencyComponent.value());
							
							ingredientsMap.get().forEach((order, ingredient) -> {
								totalCost.set(CoinCraftingFeature.subtractCoinValueFromTotalCost(ingredient, totalCost, crafterBlockEntity.getItems()));
							});
							
							crafterBlockEntity.setChanged();
							
							ci.cancel();
							
						} // if
					} // if, else if ...
				} // if, else
			} // if
		} // if
	}
	
}
