package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.feature.coinFeature;
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
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
					Collection<Item> coins = coinFeature.COIN_ITEMS.values();
					
					if( coins.contains( itemStack.getItem() ) ) {
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
						
						TreeMap<Integer, ItemStack> ingredientsMap = new TreeMap<>(coinFeature.reverseSort);
						
						crafterBlockEntity.getHeldStacks().forEach((stack) -> {
							if(!stack.isEmpty() && coins.contains(stack.getItem())) {
								int valueKey = coinFeature.getCoinValue(stack.getItem());
								
								if( ingredientsMap.containsKey( valueKey ) ) {
									valueKey = valueKey + ingredientsMap.size();
								} // if, else
								
								ingredientsMap.put(valueKey, stack);
							} // if
						});
						
						AtomicInteger totalCost = new AtomicInteger(itemStack.getCount() * coinFeature.getCoinValue(itemStack.getItem()));
						
						ingredientsMap.forEach( ( order, ingredient ) -> {
							for( ItemStack stack : crafterBlockEntity.getHeldStacks() ) {
								if( stack.equals(ingredient) ) {
									int ingredientCoinValue = coinFeature.getCoinValue(ingredient.getItem());
									int ingredientCoinStackValue = ingredient.getCount() * ingredientCoinValue;
									
									if( ingredientCoinValue == totalCost.get()) {
										stack.decrement(1);
										totalCost.addAndGet(-ingredientCoinValue);
									} else if( ingredientCoinStackValue <= totalCost.get()) {
										stack.decrement(ingredient.getCount());
										totalCost.addAndGet(-ingredientCoinStackValue);
									} else if( ingredientCoinValue < totalCost.get()) {
										int amount = totalCost.get() / ingredientCoinValue;
										
										if( amount >= ingredient.getCount() ) {
											stack.decrement(ingredient.getCount());
											totalCost.addAndGet(-(ingredientCoinValue * ingredient.getCount()));
										} else {
											stack.decrement(amount);
											totalCost.addAndGet(-(ingredientCoinValue * amount));
										} // if, else
									} // if, else
									break;
								} // if
							} // for
						} );
						
						crafterBlockEntity.markDirty();
						
						ci.cancel();
					} // if
				} // if, else
			} // if
		} // if
	}
	
}
