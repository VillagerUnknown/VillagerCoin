package me.villagerunknown.villagercoin.recipe;

import com.mojang.serialization.MapCodec;
import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.AccumulatingValueComponent;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.component.ReceiptValueComponent;
import me.villagerunknown.villagercoin.feature.LedgerCraftingFeature;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.HashSet;

import static me.villagerunknown.villagercoin.component.Components.*;

public class LedgerRecipe extends CustomRecipe {
	
	public static final LedgerRecipe INSTANCE = new LedgerRecipe();
	public static final MapCodec<LedgerRecipe> CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, LedgerRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
	
	public LedgerRecipe() {
		super();
	}
	
	@Override
	public boolean matches(CraftingInput craftingRecipeInput, Level world) {
		int containsReceipts = 0;
		int containsCarrier = 0;
		
		long receiptsAccumulatedValue = 0L;
		
		ItemStack existingLedger = null;
		
		for(int i = 0; i < craftingRecipeInput.height(); ++i) {
			for(int j = 0; j < craftingRecipeInput.width(); ++j) {
				ItemStack itemStack = craftingRecipeInput.getItem(j, i);
				if( !itemStack.isEmpty() ) {
					if( LedgerCraftingFeature.isCraftingResultLedger( itemStack.getItem() ) ) {
						existingLedger = itemStack;
					} else if( itemStack.is( LedgerCraftingFeature.RECIPE_CARRIER_ITEM ) ) {
						containsCarrier++;
					} else if( itemStack.is( Villagercoin.getItemTagKey( "receipt" ) ) ) {
						ReceiptValueComponent receiptValueComponent = itemStack.get( RECEIPT_VALUE_COMPONENT );
						
						if( null != receiptValueComponent ) {
							receiptsAccumulatedValue += receiptValueComponent.value();
							containsReceipts++;
						} // if
					} else if( !itemStack.is( Items.AIR ) ) {
						return false;
					} // if, else
				} // if
			} // for
		} // for
		
		if( null != existingLedger ) {
			WritableBookContent writableBookContentComponent = existingLedger.get( DataComponents.WRITABLE_BOOK_CONTENT );
			
			if( null != writableBookContentComponent ) {
				if( writableBookContentComponent.pages().size() < WritableBookContent.MAX_PAGES ) {
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
	public @NonNull ItemStack assemble(CraftingInput craftingRecipeInput) {
		long totalValue = 0;
		
		ItemStack carrierStack = null;
		
		for(int i = 0; i < craftingRecipeInput.size(); ++i) {
			ItemStack itemStack = craftingRecipeInput.getItem(i);
			
			if( !itemStack.isEmpty() ) {
				if( itemStack.is( LedgerCraftingFeature.RECIPE_CARRIER_ITEM ) ) {
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
			Item ledgerItem = ledgerResults.stream().toList().get((int) MathUtil.getRandomWithinRange( 0, ledgerResults.size() ));
			
			returnStack = new ItemStack(ledgerItem, 1);
			
			if( null != carrierStack ) {
				Component carrierCustomName = carrierStack.get( DataComponents.CUSTOM_NAME );
				
				if( null != carrierCustomName ) {
					returnStack.set(DataComponents.CUSTOM_NAME, carrierCustomName);
				} // if
			} // if
		} // if
		
		return returnStack;
	}
	
	public boolean fits(int width, int height) {
		return width * height >= 2;
	}
	
	@Override
	public RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return LedgerCraftingFeature.RECIPE_SERIALIZER;
	}
}
