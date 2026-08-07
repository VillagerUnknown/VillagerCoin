package me.villagerunknown.villagercoin.recipe;

import com.mojang.serialization.MapCodec;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinCraftingFeature;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

public class VillagerCoinRecipe extends CustomRecipe {
	
	public static final VillagerCoinRecipe INSTANCE = new VillagerCoinRecipe();
	public static final MapCodec<VillagerCoinRecipe> CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, VillagerCoinRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
	
	public VillagerCoinRecipe() {
		super();
	}
	
	@Override
	public boolean matches(CraftingInput craftingRecipeInput, Level world) {
		boolean containsOnlyCoins = false;
		
		for(int i = 0; i < craftingRecipeInput.height(); ++i) {
			for(int j = 0; j < craftingRecipeInput.width(); ++j) {
				ItemStack itemStack = craftingRecipeInput.getItem(j, i);
				if( !itemStack.isEmpty() ) {
					if( CoinCraftingFeature.canCraftResult( itemStack.getItem() ) ) {
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
	public @NonNull ItemStack assemble(CraftingInput craftingRecipeInput) {
		long totalValue = 0;
		ItemStack returnStack = ItemStack.EMPTY;
		
		if( craftingRecipeInput.ingredientCount() > 1 ) {
			// # Combine Multiple Coins to the Highest Coin Value
			
			for(int i = 0; i < craftingRecipeInput.size(); ++i) {
				ItemStack itemStack = craftingRecipeInput.getItem(i);
				
				if( !itemStack.isEmpty() ) {
					CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
					
					if( null != currencyComponent ) {
						totalValue += itemStack.getCount() * currencyComponent.value();
					} // if
				} // if
			} // for
			
			ItemStack largestCoin = CoinCraftingFeature.getLargestCoin( totalValue, false );
			CurrencyComponent largestComponent = largestCoin.get( CURRENCY_COMPONENT );
			
			if( null != largestComponent && largestComponent.value() > 1 ) {
				returnStack = largestCoin;
			} // if
		} else {
			// # Convert a Single Coin to a Higher or Lower Valued Coin
			
			for(int i = 0; i < craftingRecipeInput.size(); ++i) {
				ItemStack itemStack = craftingRecipeInput.getItem(i);
				
				if( !itemStack.isEmpty() ) {
					CurrencyComponent currencyComponent = itemStack.get(CURRENCY_COMPONENT);
					
					if (null != currencyComponent) {
						totalValue = itemStack.getCount() * currencyComponent.value();
						
						ItemStack smallerCoin = CoinCraftingFeature.getSmallerCoin( currencyComponent.value() );
						CurrencyComponent smallerComponent = smallerCoin.get( CURRENCY_COMPONENT );
						
						ItemStack largestCoin = CoinCraftingFeature.getLargestCoin( totalValue, true );
						CurrencyComponent largestComponent = largestCoin.get( CURRENCY_COMPONENT );
						
						if(
							null != smallerComponent
							&& itemStack.getCount() < currencyComponent.getConversionValue( currencyComponent.value(), smallerComponent.value() )
						) {
							// Convert to Lower
							returnStack = smallerCoin;
						} else if(
								largestCoin.getItem() != itemStack.getItem()
								&& null != largestComponent
								&& itemStack.getCount() >= currencyComponent.getConversionValue( currencyComponent.value(), largestComponent.value() )
						){
							// Convert to Higher
							returnStack = largestCoin;
						} // if
					} // if
				} // if
			} // for
			
		} // if, else
		
		return returnStack;
	}
	
	public boolean fits(int width, int height) {
		return width * height >= 2;
	}
	
	@Override
	public @NonNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return CoinCraftingFeature.RECIPE_SERIALIZER;
	}
}
