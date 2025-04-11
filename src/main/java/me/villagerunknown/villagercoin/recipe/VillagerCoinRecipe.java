package me.villagerunknown.villagercoin.recipe;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.type.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

public class VillagerCoinRecipe extends SpecialCraftingRecipe {
	
	public VillagerCoinRecipe(CraftingRecipeCategory category) {
		super(category);
	}
	
	@Override
	public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
		boolean containsOnlyCoins = false;
		
		for(int i = 0; i < craftingRecipeInput.getHeight(); ++i) {
			for(int j = 0; j < craftingRecipeInput.getWidth(); ++j) {
				ItemStack itemStack = craftingRecipeInput.getStackInSlot(j, i);
				if( !itemStack.isEmpty() ) {
					if( coinFeature.COINS.containsValue( itemStack.getItem() ) ) {
						containsOnlyCoins = true;
					} else {
						return false;
					} // if, else
				} // if
			} // for
		} // for
		
		return containsOnlyCoins;
	}
	
	@Override
	public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup lookup) {
		int totalValue = 0;
		ItemStack returnStack = ItemStack.EMPTY;
		
		if( craftingRecipeInput.getStackCount() > 1 ) {
			// # Combine Multiple Coins to the Highest Coin Value
			
			for(int i = 0; i < craftingRecipeInput.getSize(); ++i) {
				ItemStack itemStack = craftingRecipeInput.getStackInSlot(i);
				
				if( !itemStack.isEmpty() ) {
					CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
					
					if( null != currencyComponent ) {
						totalValue += itemStack.getCount() * currencyComponent.value();
					} // if
				} // if
			} // for
			
			ItemStack largestCoin = coinFeature.getLargestCoin( totalValue );
			CurrencyComponent largestComponent = largestCoin.get( CURRENCY_COMPONENT );
			
			if( null != largestComponent && largestComponent.value() > 1 ) {
				returnStack = largestCoin;
			} // if
		} else {
			// # Convert a Single Coin to a Higher or Lower Valued Coin
			
			for(int i = 0; i < craftingRecipeInput.getSize(); ++i) {
				ItemStack itemStack = craftingRecipeInput.getStackInSlot(i);
				
				if( !itemStack.isEmpty() ) {
					CurrencyComponent currencyComponent = itemStack.get(CURRENCY_COMPONENT);
					
					if (null != currencyComponent) {
						totalValue = itemStack.getCount() * currencyComponent.value();
						
						ItemStack smallerCoin = coinFeature.getSmallerCoin( currencyComponent.value() );
						CurrencyComponent smallerComponent = smallerCoin.get( CURRENCY_COMPONENT );
						
						ItemStack largestCoin = coinFeature.getLargestCoin( totalValue );
						CurrencyComponent largestComponent = largestCoin.get( CURRENCY_COMPONENT );
						
						if( null != smallerComponent && itemStack.getCount() < currencyComponent.getConversionValue( currencyComponent.value(), smallerComponent.value() ) ) {
							// Convert to Lower
							returnStack = smallerCoin;
						} else if( largestCoin.getItem() != itemStack.getItem() && null != largestComponent && itemStack.getCount() >= currencyComponent.getConversionValue( currencyComponent.value(), largestComponent.value() ) ){
							// Convert to Higher
							returnStack = largestCoin;
						} // if
					} // if
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
