package me.villagerunknown.villagercoin.recipe;

import com.mojang.serialization.MapCodec;
import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.ReceiptCraftingFeature;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.HashSet;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

public class ReceiptRecipe extends CustomRecipe {
	
	public static final ReceiptRecipe INSTANCE = new ReceiptRecipe();
	public static final MapCodec<ReceiptRecipe> CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, ReceiptRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
	
	public ReceiptRecipe() {
		super();
	}
	
	@Override
	public boolean matches(CraftingInput craftingRecipeInput, Level world) {
		int containsCurrencyComponent = 0;
		int containsCarrier = 0;
		
		for(int i = 0; i < craftingRecipeInput.height(); ++i) {
			for(int j = 0; j < craftingRecipeInput.width(); ++j) {
				ItemStack itemStack = craftingRecipeInput.getItem(j, i);
				if( !itemStack.isEmpty() ) {
					CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
					
					if( null != currencyComponent ) {
						containsCurrencyComponent++;
					} else if( itemStack.is( ReceiptCraftingFeature.RECIPE_CARRIER_ITEM ) ) {
						containsCarrier++;
					} else if( !itemStack.is( Items.AIR ) ) {
						return false;
					} // if, else
				} // if
			} // for
		} // for
		
		return ( containsCurrencyComponent > 0 && 1 == containsCarrier );
	}
	
	@Override
	public @NonNull ItemStack assemble(CraftingInput craftingRecipeInput) {
		long totalValue = 0;
		
		ItemStack carrierStack = null;
		
		for(int i = 0; i < craftingRecipeInput.size(); ++i) {
			ItemStack itemStack = craftingRecipeInput.getItem(i);
			
			if( !itemStack.isEmpty() ) {
				if( itemStack.is( ReceiptCraftingFeature.RECIPE_CARRIER_ITEM ) ) {
					carrierStack = itemStack;
				} else {
					CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
					
					if( null != currencyComponent ) {
						totalValue += itemStack.getCount() * currencyComponent.value();
					} // if
				} // if, else
			} // if
		} // for
		
		HashSet<Item> receiptResults = ReceiptCraftingFeature.getCraftingResultReceipts();
		ItemStack returnStack = ItemStack.EMPTY;
		
		if( !receiptResults.isEmpty() ) {
			returnStack = new ItemStack(receiptResults.stream().toList().get((int) MathUtil.getRandomWithinRange( 0, receiptResults.size() )), 1);
			
			ReceiptCraftingFeature.setReceiptValue( returnStack, totalValue );
			ReceiptCraftingFeature.setCraftedDate( returnStack );
			
			if( null != carrierStack) {
				ReceiptCraftingFeature.setReceiptMessage( returnStack, carrierStack);
			} // if
		} // if
		
		return returnStack;
	}
	
	public boolean fits(int width, int height) {
		return width * height >= 2;
	}
	
	@Override
	public RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return ReceiptCraftingFeature.RECIPE_SERIALIZER;
	}
}
