package me.villagerunknown.villagercoin.recipe;

import com.mojang.serialization.MapCodec;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinStackCraftingFeature;
import me.villagerunknown.villagercoin.item.CoinItems;
import me.villagerunknown.villagercoin.type.CoinType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

public class CoinStackRecipe extends CustomRecipe {
	
	public static final CoinStackRecipe INSTANCE = new CoinStackRecipe();
	public static final MapCodec<CoinStackRecipe> CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, CoinStackRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
	
	public CoinStackRecipe() {
		super();
	}
	
	@Override
	public boolean matches(CraftingInput craftingRecipeInput, Level world) {
		boolean containsCoinInCenter = false;
		int containsCarrier = 0;
		
		for(int i = 0; i < craftingRecipeInput.height(); ++i) {
			for(int j = 0; j < craftingRecipeInput.width(); ++j) {
				ItemStack itemStack = craftingRecipeInput.getItem(j, i);
				if( !itemStack.isEmpty() ) {
					if( 1 == i && 1 == j && CoinStackCraftingFeature.canCraftResult( itemStack.getItem() ) ) {
						containsCoinInCenter = true;
					} else if( itemStack.is( CoinStackCraftingFeature.RECIPE_CARRIER_ITEM ) && ( 0 == i && 1 == j || 1 == i && 0 == j || 1 == i && 2 == j || 2 == i && 1 == j ) ) {
						containsCarrier++;
					} else if( !itemStack.is( Items.AIR ) ) {
						return false;
					} // if, else
				} // if
			} // for
		} // for
		
		return ( containsCoinInCenter && 4 == containsCarrier );
	}
	
	@Override
	public @NonNull ItemStack assemble(CraftingInput craftingRecipeInput) {
		int totalValue = 0;
		ItemStack returnStack = ItemStack.EMPTY;
		
		// Get Coin from Center of Crafting Table
		ItemStack itemStack = craftingRecipeInput.getItem(4);
		
		if( !itemStack.isEmpty() ) {
			CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
			
			if( null != currencyComponent ) {
				totalValue += itemStack.getCount() * currencyComponent.value();
			} // if
			
			ItemStack largestCoinStack = ItemStack.EMPTY;
			
			if( itemStack.is( CoinItems.COPPER_COIN ) ) {
				largestCoinStack = CoinStackCraftingFeature.getLargestCoinStack( CoinType.COPPER, totalValue );
			} else if( itemStack.is( CoinItems.IRON_COIN ) ) {
				largestCoinStack = CoinStackCraftingFeature.getLargestCoinStack( CoinType.IRON, totalValue );
			} else if( itemStack.is( CoinItems.GOLD_COIN ) ) {
				largestCoinStack = CoinStackCraftingFeature.getLargestCoinStack( CoinType.GOLD, totalValue );
			} else if( itemStack.is( CoinItems.EMERALD_COIN ) ) {
				largestCoinStack = CoinStackCraftingFeature.getLargestCoinStack( CoinType.EMERALD, totalValue );
			} else if( itemStack.is( CoinItems.NETHERITE_COIN ) ) {
				largestCoinStack = CoinStackCraftingFeature.getLargestCoinStack( CoinType.NETHERITE, totalValue );
			} // if, else if ...
			
			CurrencyComponent largestComponent = largestCoinStack.get( CURRENCY_COMPONENT );
			
			if( null != largestComponent && largestComponent.value() > 0 ) {
				returnStack = largestCoinStack;
			} // if
		} // if
		
		return returnStack;
	}
	
	public boolean fits(int width, int height) {
		return width * height >= 9;
	}
	
	@Override
	public RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return CoinStackCraftingFeature.RECIPE_SERIALIZER;
	}
}
