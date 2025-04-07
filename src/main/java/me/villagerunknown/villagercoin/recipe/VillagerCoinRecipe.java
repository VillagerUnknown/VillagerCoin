package me.villagerunknown.villagercoin.recipe;

import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class VillagerCoinRecipe extends SpecialCraftingRecipe {
	
	private static final Ingredient COIN = Ingredient.ofItems(
			coinFeature.COPPER_COIN,
			coinFeature.IRON_COIN,
			coinFeature.GOLD_COIN,
			coinFeature.EMERALD_COIN,
			coinFeature.NETHERITE_COIN
	);
	
	public VillagerCoinRecipe(CraftingRecipeCategory category) {
		super(category);
	}
	
	@Override
	public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
		boolean containsCoin = false;
		
		for(int i = 0; i < craftingRecipeInput.getHeight(); ++i) {
			for(int j = 0; j < craftingRecipeInput.getWidth(); ++j) {
				ItemStack itemStack = craftingRecipeInput.getStackInSlot(j, i);
				if( !itemStack.isEmpty() ) {
					if( COIN.test( itemStack ) ) {
						containsCoin = true;
					} else {
						return false;
					} // if, else
				} // if
			} // for
		} // for
		
		return containsCoin;
	}
	
	@Override
	public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup lookup) {
		int totalStacks = craftingRecipeInput.getStackCount();
		int totalValue = 0;
		
		ItemStack returnStack = ItemStack.EMPTY;
		
		if( totalStacks > 1 ) {
			// # Combine Coins to the Highest Coin Value
			
			for(int i = 0; i < craftingRecipeInput.getSize(); ++i) {
				ItemStack itemStack = craftingRecipeInput.getStackInSlot(i);
				
				if( !itemStack.isEmpty() ) {
					
					if( itemStack.isOf( coinFeature.COPPER_COIN ) ) {
						totalValue += itemStack.getCount();
					}
					else if( itemStack.isOf( coinFeature.IRON_COIN ) ) {
						totalValue += itemStack.getCount() * coinFeature.getCoinValue( coinFeature.IRON_COIN );
					}
					else if( itemStack.isOf( coinFeature.GOLD_COIN ) ) {
						totalValue += itemStack.getCount() * coinFeature.getCoinValue( coinFeature.GOLD_COIN );
					}
					else if( itemStack.isOf( coinFeature.EMERALD_COIN ) ) {
						totalValue += itemStack.getCount() * coinFeature.getCoinValue( coinFeature.EMERALD_COIN );
					}
					else if( itemStack.isOf( coinFeature.NETHERITE_COIN ) ) {
						totalValue += itemStack.getCount() * coinFeature.getCoinValue( coinFeature.NETHERITE_COIN );
					} // if, else if ...
					
				} // if
			} // for
			
			if( totalValue >= coinFeature.getCoinValue( coinFeature.NETHERITE_COIN ) ) {
				returnStack = new ItemStack( coinFeature.NETHERITE_COIN, 1 );
			}
			else if( totalValue >= coinFeature.getCoinValue( coinFeature.EMERALD_COIN ) ) {
				returnStack = new ItemStack( coinFeature.EMERALD_COIN, 1 );
			}
			else if( totalValue >= coinFeature.getCoinValue( coinFeature.GOLD_COIN ) ) {
				returnStack = new ItemStack( coinFeature.GOLD_COIN, 1 );
			}
			else if( totalValue >= coinFeature.getCoinValue( coinFeature.IRON_COIN ) ) {
				returnStack = new ItemStack( coinFeature.IRON_COIN, 1 );
			} // if, else if ...
			
		} else {
			// # Convert Single Coin to a Higher or Lower Valued Coin
			
			for(int i = 0; i < craftingRecipeInput.getSize(); ++i) {
				ItemStack itemStack = craftingRecipeInput.getStackInSlot(i);
				
				if( !itemStack.isEmpty() ) {
					
					int itemCount = itemStack.getCount();
					
					if( itemStack.isOf( coinFeature.COPPER_COIN ) ) {
						if( itemCount >= coinFeature.getConversionValue( coinFeature.COPPER_COIN, coinFeature.IRON_COIN ) ) {
							returnStack = new ItemStack( coinFeature.IRON_COIN, 1 );
						} // if
					}
					else if( itemStack.isOf( coinFeature.IRON_COIN ) ) {
						if( itemCount >= coinFeature.getConversionValue( coinFeature.IRON_COIN, coinFeature.GOLD_COIN ) ) {
							returnStack = new ItemStack( coinFeature.GOLD_COIN, 1 );
						} else {
							returnStack = new ItemStack( coinFeature.COPPER_COIN, coinFeature.getConversionValue( coinFeature.IRON_COIN, coinFeature.COPPER_COIN ) );
						} // if, else
					}
					else if( itemStack.isOf( coinFeature.GOLD_COIN ) ) {
						if( itemCount >= coinFeature.getConversionValue( coinFeature.GOLD_COIN, coinFeature.EMERALD_COIN ) ) {
							returnStack = new ItemStack( coinFeature.EMERALD_COIN, 1 );
						} else {
							returnStack = new ItemStack( coinFeature.IRON_COIN, coinFeature.getConversionValue( coinFeature.GOLD_COIN, coinFeature.IRON_COIN ) );
						} // if, else
					}
					else if( itemStack.isOf( coinFeature.EMERALD_COIN ) ) {
						if( itemCount >= coinFeature.getConversionValue( coinFeature.EMERALD_COIN, coinFeature.NETHERITE_COIN ) ) {
							returnStack = new ItemStack( coinFeature.NETHERITE_COIN, 1 );
						} else {
							returnStack = new ItemStack( coinFeature.GOLD_COIN, coinFeature.getConversionValue( coinFeature.EMERALD_COIN, coinFeature.GOLD_COIN ) );
						} // if, else
					}
					else if( itemStack.isOf( coinFeature.NETHERITE_COIN ) ) {
						returnStack = new ItemStack( coinFeature.EMERALD_COIN, coinFeature.getConversionValue( coinFeature.NETHERITE_COIN, coinFeature.EMERALD_COIN ) );
					} // if, else if ...
					
				} // if
			} // for
			
		} // if, else
		
		return returnStack;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return width * height >= 2;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return coinFeature.RECIPE_SERIALIZER;
	}
}
