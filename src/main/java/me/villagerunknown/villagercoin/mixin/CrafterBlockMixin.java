package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinCraftingFeature;
import me.villagerunknown.villagercoin.feature.CoinStackCraftingFeature;
import me.villagerunknown.villagercoin.feature.ReceiptCraftingFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.CrafterBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CrafterBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;
import static net.minecraft.block.CrafterBlock.CRAFTING;
import static net.minecraft.block.CrafterBlock.getCraftingRecipe;

@Mixin(CrafterBlock.class)
public class CrafterBlockMixin {
	
	@Shadow
	private void transferOrSpawnStack(ServerWorld world, BlockPos pos, CrafterBlockEntity blockEntity, ItemStack stack, BlockState state, RecipeEntry<CraftingRecipe> recipe) {}
	
	@Inject(method = "craft", at = @At("HEAD"), cancellable = true)
	protected void craft(BlockState state, ServerWorld world, BlockPos pos, CallbackInfo ci) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if(blockEntity instanceof CrafterBlockEntity crafterBlockEntity) {
			CraftingRecipeInput craftingRecipeInput = crafterBlockEntity.createRecipeInput();
			
			Optional optional = getCraftingRecipe(world, craftingRecipeInput);
			if( optional.isPresent() ) {
				RecipeEntry<CraftingRecipe> recipeEntry = (RecipeEntry)optional.get();
				ItemStack itemStack = ((CraftingRecipe)recipeEntry.value()).craft(craftingRecipeInput, world.getRegistryManager());
				if (itemStack.isEmpty()) {
					world.syncWorldEvent(1050, pos, 0);
				} else {
					if( ReceiptCraftingFeature.isCraftingResultReceipt( itemStack.getItem() ) ) {
						ReceiptCraftingFeature.subtractCarrierFromIngredients( crafterBlockEntity.getHeldStacks(), 1 );
						
						this.transferOrSpawnStack(world, pos, crafterBlockEntity, itemStack, state, recipeEntry);
						
						crafterBlockEntity.markDirty();
						
						ci.cancel();
					} else if(
							CoinCraftingFeature.isCraftingResultCoin( itemStack.getItem() )
							|| CoinStackCraftingFeature.isCraftingResultCoinStack( itemStack.getItem() )
					) {
						CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
						
						if( null != currencyComponent ) {
							if( CoinStackCraftingFeature.isCraftingResultCoinStack( itemStack.getItem() ) ) {
								CoinStackCraftingFeature.subtractCarrierFromIngredients( crafterBlockEntity.getHeldStacks(), 1 );
							} // if
							
							crafterBlockEntity.setCraftingTicksRemaining(6);
							world.setBlockState(pos, (BlockState)state.with(CRAFTING, true), 2);
							itemStack.onCraftByCrafter(world);
							this.transferOrSpawnStack(world, pos, crafterBlockEntity, itemStack, state, recipeEntry);
							Iterator var9 = ((CraftingRecipe)recipeEntry.value()).getRemainder(craftingRecipeInput).iterator();
							
							while(var9.hasNext()) {
								ItemStack itemStack2 = (ItemStack)var9.next();
								if (!itemStack2.isEmpty()) {
									this.transferOrSpawnStack(world, pos, crafterBlockEntity, itemStack2, state, recipeEntry);
								}
							}
							
							// This ingredients map doesn't require as much information as the others.
							// ItemStack is used instead of coinFeature.CoinIngredient
							AtomicReference<TreeMap<Long, ItemStack>> ingredientsMap = new AtomicReference<>(new TreeMap<>(Villagercoin.reverseSortLong));
							
							crafterBlockEntity.getHeldStacks().forEach((stack) -> {
								ingredientsMap.set(CoinCraftingFeature.updateCoinIngredientsMap(ingredientsMap.get(), stack));
							});
							
							AtomicLong totalCost = new AtomicLong((long) itemStack.getCount() * currencyComponent.value());
							
							ingredientsMap.get().forEach((order, ingredient) -> {
								totalCost.set(CoinCraftingFeature.subtractCoinValueFromTotalCost(ingredient, totalCost, crafterBlockEntity.getHeldStacks()));
							});
							
							crafterBlockEntity.markDirty();
							
							ci.cancel();
						} // if
					} // if
				} // if, else
			} // if
		} // if
	}
	
}
