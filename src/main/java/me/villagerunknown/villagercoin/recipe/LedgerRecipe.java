package me.villagerunknown.villagercoin.recipe;

import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.AccumulatingValueComponent;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.component.ReceiptValueComponent;
import me.villagerunknown.villagercoin.feature.CoinStackCraftingFeature;
import me.villagerunknown.villagercoin.feature.LedgerCraftingFeature;
import me.villagerunknown.villagercoin.feature.ReceiptCraftingFeature;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WritableBookContentComponent;
import net.minecraft.component.type.WrittenBookContentComponent;
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

import static me.villagerunknown.villagercoin.Villagercoin.*;

public class LedgerRecipe extends SpecialCraftingRecipe {
	
	public LedgerRecipe(CraftingRecipeCategory category) {
		super(category);
	}
	
	@Override
	public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
		int containsReceipts = 0;
		int containsCarrier = 0;
		
		long receiptsAccumulatedValue = 0L;
		
		ItemStack existingLedger = null;
		
		for(int i = 0; i < craftingRecipeInput.getHeight(); ++i) {
			for(int j = 0; j < craftingRecipeInput.getWidth(); ++j) {
				ItemStack itemStack = craftingRecipeInput.getStackInSlot(j, i);
				if( !itemStack.isEmpty() ) {
					if( LedgerCraftingFeature.isCraftingResultLedger( itemStack.getItem() ) ) {
						existingLedger = itemStack;
					} else if( itemStack.isOf( LedgerCraftingFeature.RECIPE_CARRIER_ITEM ) ) {
						containsCarrier++;
					} else if( itemStack.isIn( Villagercoin.getItemTagKey( "receipt" ) ) ) {
						ReceiptValueComponent receiptValueComponent = itemStack.get( RECEIPT_VALUE_COMPONENT );
						
						if( null != receiptValueComponent ) {
							receiptsAccumulatedValue += receiptValueComponent.value();
							containsReceipts++;
						} // if
					} else if( !itemStack.isOf( Items.AIR ) ) {
						return false;
					} // if, else
				} // if
			} // for
		} // for
		
		if( null != existingLedger ) {
			WritableBookContentComponent writableBookContentComponent = existingLedger.get( DataComponentTypes.WRITABLE_BOOK_CONTENT );
			
			if( null != writableBookContentComponent ) {
				if( writableBookContentComponent.pages().size() < WritableBookContentComponent.MAX_PAGE_COUNT ) {
					AccumulatingValueComponent accumulatingValueComponent = existingLedger.get( ACCUMULATING_VALUE_COMPONENT );
					
					if( null != accumulatingValueComponent && accumulatingValueComponent.value() + receiptsAccumulatedValue < Long.MAX_VALUE ) {
						containsCarrier++;
					} // if
				} // if
			} // if
		} // if
		
		return ( containsReceipts > 0 && 1 == containsCarrier );
	}
	
	@Override
	public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup lookup) {
		long totalValue = 0;
		
		ItemStack carrierStack = null;
		
		for(int i = 0; i < craftingRecipeInput.getSize(); ++i) {
			ItemStack itemStack = craftingRecipeInput.getStackInSlot(i);
			
			if( !itemStack.isEmpty() ) {
				if( itemStack.isOf( LedgerCraftingFeature.RECIPE_CARRIER_ITEM ) ) {
					carrierStack = itemStack;
				} else {
					CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
					
					if( null != currencyComponent ) {
						totalValue += itemStack.getCount() * currencyComponent.value();
					} // if
				} // if, else
			} // if
		} // for
		
		HashSet<Item> ledgerResults = LedgerCraftingFeature.getCraftingResultLedgers();
		ItemStack returnStack = ItemStack.EMPTY;
		
		if( !ledgerResults.isEmpty() ) {
			returnStack = new ItemStack(ledgerResults.stream().toList().get((int) MathUtil.getRandomWithinRange( 0, ledgerResults.size() )), 1);
		} // if
		
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
