package me.villagerunknown.villagercoin.recipe;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinCraftingFeature;
import me.villagerunknown.villagercoin.feature.CoinStackCraftingFeature;
import me.villagerunknown.villagercoin.item.CoinItems;
import me.villagerunknown.villagercoin.type.CoinType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

public class CoinStackRecipe extends SpecialCraftingRecipe {
	
	public CoinStackRecipe(CraftingRecipeCategory category) {
		super(category);
	}
	
	@Override
	public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
		boolean containsCoinInCenter = false;
		int containsString = 0;
		
		for(int i = 0; i < craftingRecipeInput.getHeight(); ++i) {
			for(int j = 0; j < craftingRecipeInput.getWidth(); ++j) {
				ItemStack itemStack = craftingRecipeInput.getStackInSlot(j, i);
				if( !itemStack.isEmpty() ) {
					if( 1 == i && 1 == j && CoinStackCraftingFeature.canCraftResult( itemStack.getItem() ) ) {
						containsCoinInCenter = true;
					} else if( itemStack.isOf( Items.STRING ) && ( 0 == i && 1 == j || 1 == i && 0 == j || 1 == i && 2 == j || 2 == i && 1 == j ) ) {
						containsString++;
					} else if( !itemStack.isOf( Items.AIR ) ) {
						return false;
					} // if, else
				} // if
			} // for
		} // for
		
		return ( containsCoinInCenter && 4 == containsString );
	}
	
	@Override
	public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup lookup) {
		int totalValue = 0;
		ItemStack returnStack = ItemStack.EMPTY;
		
		// Get Coin from Center of Crafting Table
		ItemStack itemStack = craftingRecipeInput.getStackInSlot(4);
		
		if( !itemStack.isEmpty() ) {
			CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
			
			if( null != currencyComponent ) {
				totalValue += itemStack.getCount() * currencyComponent.value();
			} // if
			
			ItemStack largestCoinStack = ItemStack.EMPTY;
			
			if( itemStack.isOf( CoinItems.COPPER_COIN ) ) {
				largestCoinStack = CoinStackCraftingFeature.getLargestCoinStack( CoinType.COPPER, totalValue );
			} else if( itemStack.isOf( CoinItems.IRON_COIN ) ) {
				largestCoinStack = CoinStackCraftingFeature.getLargestCoinStack( CoinType.IRON, totalValue );
			} else if( itemStack.isOf( CoinItems.GOLD_COIN ) ) {
				largestCoinStack = CoinStackCraftingFeature.getLargestCoinStack( CoinType.GOLD, totalValue );
			} else if( itemStack.isOf( CoinItems.EMERALD_COIN ) ) {
				largestCoinStack = CoinStackCraftingFeature.getLargestCoinStack( CoinType.EMERALD, totalValue );
			} else if( itemStack.isOf( CoinItems.NETHERITE_COIN ) ) {
				largestCoinStack = CoinStackCraftingFeature.getLargestCoinStack( CoinType.NETHERITE, totalValue );
			} // if, else if ...
			
			CurrencyComponent largestComponent = largestCoinStack.get( CURRENCY_COMPONENT );
			
			if( null != largestComponent && largestComponent.value() > 0 ) {
				returnStack = largestCoinStack;
			} // if
		} // if
		
		return returnStack;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return width * height >= 9;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return CoinStackCraftingFeature.RECIPE_SERIALIZER;
	}
}
