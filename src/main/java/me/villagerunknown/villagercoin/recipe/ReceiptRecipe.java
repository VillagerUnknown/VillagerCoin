package me.villagerunknown.villagercoin.recipe;

import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.component.ReceiptValueComponent;
import me.villagerunknown.villagercoin.feature.CoinStackCraftingFeature;
import me.villagerunknown.villagercoin.feature.ReceiptCraftingFeature;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.HashSet;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.RECEIPT_VALUE_COMPONENT;

public class ReceiptRecipe extends SpecialCraftingRecipe {
	
	public ReceiptRecipe(CraftingRecipeCategory category) {
		super(category);
	}
	
	@Override
	public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
		int containsCurrencyComponent = 0;
		int containsCarrier = 0;
		
		for(int i = 0; i < craftingRecipeInput.getHeight(); ++i) {
			for(int j = 0; j < craftingRecipeInput.getWidth(); ++j) {
				ItemStack itemStack = craftingRecipeInput.getStackInSlot(j, i);
				if( !itemStack.isEmpty() ) {
					CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
					
					if( null != currencyComponent ) {
						containsCurrencyComponent++;
					} else if( itemStack.isOf( ReceiptCraftingFeature.RECIPE_CARRIER_ITEM) ) {
						containsCarrier++;
					} else if( !itemStack.isOf( Items.AIR ) ) {
						return false;
					} // if, else
				} // if
			} // for
		} // for
		
		return ( containsCurrencyComponent > 0 && 1 == containsCarrier );
	}
	
	@Override
	public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup lookup) {
		long totalValue = 0;
		ItemStack returnStack = ItemStack.EMPTY;

		for(int i = 0; i < craftingRecipeInput.getSize(); ++i) {
			ItemStack itemStack = craftingRecipeInput.getStackInSlot(i);
			
			if( !itemStack.isEmpty() ) {
				CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
				
				if( null != currencyComponent ) {
					totalValue += itemStack.getCount() * currencyComponent.value();
				} // if
			} // if
		} // for
		
		HashSet<Item> receiptResults = ReceiptCraftingFeature.getCraftingResultReceipts();
		returnStack = new ItemStack(receiptResults.stream().toList().get((int) MathUtil.getRandomWithinRange( 0, receiptResults.size() )), 1);
		
		ReceiptCraftingFeature.setReceiptValue( returnStack, totalValue );
		ReceiptCraftingFeature.setCraftedDate( returnStack );
		
		return returnStack;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return width * height >= 2;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return CoinStackCraftingFeature.RECIPE_SERIALIZER;
	}
}
