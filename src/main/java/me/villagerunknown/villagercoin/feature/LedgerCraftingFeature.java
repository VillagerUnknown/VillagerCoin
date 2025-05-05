package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.AccumulatingValueComponent;
import me.villagerunknown.villagercoin.component.DateComponent;
import me.villagerunknown.villagercoin.component.ReceiptValueComponent;
import me.villagerunknown.villagercoin.component.UpdatedDateComponent;
import me.villagerunknown.villagercoin.item.CoinItems;
import me.villagerunknown.villagercoin.recipe.LedgerRecipe;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WritableBookContentComponent;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.RawFilteredPair;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static me.villagerunknown.villagercoin.Villagercoin.*;

public class LedgerCraftingFeature {
	
	public static Item RECIPE_CARRIER_ITEM = Items.WRITABLE_BOOK;
	
	private static HashSet<Item> CRAFTING_RESULT_LEDGERS = new HashSet<>();
	
	public static RecipeSerializer<LedgerRecipe> RECIPE_SERIALIZER;
	
	public static void execute(){}
	
	public static void registerCraftingResultLedger( Item item ) {
		CRAFTING_RESULT_LEDGERS.add( item );
	}
	
	public static boolean isCraftingResultLedger( Item item ) {
		return CRAFTING_RESULT_LEDGERS.contains( item );
	}
	
	public static HashSet<Item> getCraftingResultLedgers() {
		return CRAFTING_RESULT_LEDGERS;
	}
	
	public static void subtractCarrierFromIngredients(RecipeInputInventory craftingInput, long amount ) {
		CraftingRecipeInput.Positioned positioned = craftingInput.createPositionedRecipeInput();
		CraftingRecipeInput craftingRecipeInput = positioned.input();
		int left = positioned.left();
		int top = positioned.top();
		
		for(int y = 0; y < craftingRecipeInput.getHeight(); ++y) {
			for (int x = 0; x < craftingRecipeInput.getWidth(); ++x) {
				int m = x + left + (y + top) * craftingInput.getWidth();
				ItemStack ingredientStack = craftingInput.getStack(m);
				
				if( ingredientStack.isOf( RECIPE_CARRIER_ITEM ) || ingredientStack.isIn( Villagercoin.getItemTagKey( "ledger" ) ) ) {
					if( amount > Integer.MAX_VALUE ) {
						amount = Integer.MAX_VALUE;
					} // if
					
					craftingInput.removeStack( m, CoinCraftingFeature.toIntSafely(amount) );
					break;
				} // if
			} // for
		} // for
	}
	
	public static void removeCarrierFromIngredients(List<ItemStack> ingredients ) {
		for (ItemStack ingredientStack : ingredients) {
			if( ingredientStack.isOf( RECIPE_CARRIER_ITEM ) || ingredientStack.isIn( Villagercoin.getItemTagKey( "ledger" ) ) ) {
				ingredientStack.decrement( ingredientStack.getCount() );
				break;
			} // if
		} // for
	}
	
	public static void subtractCarrierFromIngredients(List<ItemStack> ingredients, int amount ) {
		for (ItemStack ingredientStack : ingredients) {
			if( ingredientStack.isOf( RECIPE_CARRIER_ITEM ) || ingredientStack.isIn( Villagercoin.getItemTagKey( "ledger" ) ) ) {
				ingredientStack.decrement( amount );
				break;
			} // if
		} // for
	}
	
	public static void removeReceiptsFromIngredients(List<ItemStack> ingredients ) {
		for (ItemStack ingredientStack : ingredients) {
			if( ingredientStack.isIn( Villagercoin.getItemTagKey("receipt") ) ) {
				ingredientStack.decrement( ingredientStack.getCount() );
			} // if
		} // for
	}
	
	public static void subtractReceiptsFromIngredients(List<ItemStack> ingredients, int amount ) {
		for (ItemStack ingredientStack : ingredients) {
			if( ingredientStack.isIn( Villagercoin.getItemTagKey("receipt") ) ) {
				ingredientStack.decrement( amount );
			} // if
		} // for
	}
	
	public static TreeMap<String, HashSet<CoinCraftingFeature.CoinIngredient>> updateIngredientsMap(AtomicReference<TreeMap<String, HashSet<CoinCraftingFeature.CoinIngredient>>> ingredientsMap, ItemStack ingredient, int slot, int x, int y ) {
		if(!ingredient.isEmpty()) {
			DateComponent dateComponent = ingredient.get( DATE_COMPONENT );
			
			if( null != dateComponent ) {
				String date = dateComponent.date();
				
				TreeMap<String, HashSet<CoinCraftingFeature.CoinIngredient>> map = ingredientsMap.get();
				
				HashSet<CoinCraftingFeature.CoinIngredient> items = map.getOrDefault( date, new HashSet<>() );
				
				items.add(new CoinCraftingFeature.CoinIngredient( slot, ingredient, y, x ));
				
				ingredientsMap.updateAndGet( (m) -> {
					m.put( date, items );
					return m;
				} );
			} // if
		} // if
		
		return ingredientsMap.get();
	}
	
	public static TreeMap<String, HashSet<ItemStack>> updateIngredientsMap(AtomicReference<TreeMap<String, HashSet<ItemStack>>> ingredientsMap, ItemStack ingredient) {
		if(!ingredient.isEmpty()) {
			DateComponent dateComponent = ingredient.get( DATE_COMPONENT );
			
			if( null != dateComponent ) {
				String date = dateComponent.date();
				
				TreeMap<String, HashSet<ItemStack>> map = ingredientsMap.get();
				
				HashSet<ItemStack> items = map.getOrDefault( date, new HashSet<>() );
				
				items.add(ingredient);
				
				ingredientsMap.updateAndGet( (m) -> {
					m.put( date, items );
					return m;
				} );
			} // if
		} // if
		
		return ingredientsMap.get();
	}
	
	public static ItemStack updateLedgerFromSlot(ItemStack itemStack, TreeMap<String, HashSet<CoinCraftingFeature.CoinIngredient>> ingredientsMap, @Nullable ItemStack existingLedger) {
		Item item = itemStack.getItem();
		
		DateComponent dateComponent = itemStack.get( DATE_COMPONENT );
		
		if( null == dateComponent ) {
			itemStack.set(DATE_COMPONENT, new DateComponent(LocalDate.now().toString()));
		} // if
		
		itemStack.set( UPDATED_DATE_COMPONENT, new UpdatedDateComponent( LocalDate.now().toString() ) );
		
		AtomicInteger pageCount = new AtomicInteger();
		
		WritableBookContentComponent writableBookContentComponent = item.getComponents().get( DataComponentTypes.WRITABLE_BOOK_CONTENT );
		
		if( null != existingLedger ) {
			writableBookContentComponent = existingLedger.get( DataComponentTypes.WRITABLE_BOOK_CONTENT );
			if( null != writableBookContentComponent ) {
				pageCount.set(writableBookContentComponent.pages().size());
			} // if
		} // if
		
		if( null == writableBookContentComponent ) {
			pageCount.set(0);
		} // if, else
		
		Set<RawFilteredPair<String>> newPages = new HashSet<>();
		
		AtomicLong totalAmount = new AtomicLong();
		totalAmount.set(0);
		
		ingredientsMap.forEach(( date, itemStacks ) -> {
			for (CoinCraftingFeature.CoinIngredient ingredient : itemStacks) {
				if( pageCount.get() < WritableBookContentComponent.MAX_PAGE_COUNT ) {
					ItemStack stack = ingredient.stack;
					
					ReceiptValueComponent receiptValueComponent = stack.get( RECEIPT_VALUE_COMPONENT );
					
					if( null != receiptValueComponent ) {
						NumberFormat numberFormat = NumberFormat.getIntegerInstance();
						
						Text receiptText = stack.get( DataComponentTypes.ITEM_NAME );
						
						if( null == receiptText ) {
							receiptText = stack.getName();
						} // if
						
						Text dateText = Text.translatable( "item.villagerunknown-villagercoin.ledger.content.date", date );
						
						long value = receiptValueComponent.value() * stack.getCount();
						
						totalAmount.addAndGet(value);
						
						Text valueText = Text.translatable(
								"item.villagerunknown-villagercoin.ledger.content.total",
								numberFormat.format(value ),
								CoinItems.COPPER_COIN.getName().getString()
						);
						
						Text pageText = Text.empty()
								.append( dateText ).append("\n\n")
								.append( receiptText ).append("\n\n")
								.append( valueText );
						
						RawFilteredPair<String> pair = new RawFilteredPair<>( pageText.getString(), Optional.of( pageText.getString() ) );
						
						newPages.add( pair );
						pageCount.getAndIncrement();
					} // if
				} // if
			} // for
		});
		
		List<RawFilteredPair<String>> newWrittenPages = new ArrayList<>();
		
		if( null != writableBookContentComponent ) {
			newWrittenPages.addAll(writableBookContentComponent.pages());
		} // if
		
		newWrittenPages.addAll( newPages );
		
		WritableBookContentComponent newWritableBookContentComponent = new WritableBookContentComponent(
				newWrittenPages
		);
		
		itemStack.set( DataComponentTypes.WRITABLE_BOOK_CONTENT, newWritableBookContentComponent );
		
		if( null != existingLedger ) {
			AccumulatingValueComponent accumulatingValueComponent = existingLedger.get(ACCUMULATING_VALUE_COMPONENT);
			
			if (null != accumulatingValueComponent) {
				totalAmount.addAndGet(accumulatingValueComponent.value());
			} // if
		} // if
		
		itemStack.set( ACCUMULATING_VALUE_COMPONENT, new AccumulatingValueComponent(totalAmount.longValue()) );
		
		return itemStack;
	}
	
	public static ItemStack updateLedger(ItemStack itemStack, AtomicReference<TreeMap<String, HashSet<ItemStack>>> ingredientsMap, @Nullable ItemStack existingLedger) {
		Item item = itemStack.getItem();
		
		DateComponent dateComponent = itemStack.get( DATE_COMPONENT );
		
		if( null == dateComponent ) {
			itemStack.set(DATE_COMPONENT, new DateComponent(LocalDate.now().toString()));
		} // if
		
		itemStack.set( UPDATED_DATE_COMPONENT, new UpdatedDateComponent( LocalDate.now().toString() ) );
		
		AtomicInteger pageCount = new AtomicInteger();
		
		WritableBookContentComponent writableBookContentComponent = item.getComponents().get( DataComponentTypes.WRITABLE_BOOK_CONTENT );
		
		if( null != existingLedger ) {
			writableBookContentComponent = existingLedger.get( DataComponentTypes.WRITABLE_BOOK_CONTENT );
			if( null != writableBookContentComponent ) {
				pageCount.set(writableBookContentComponent.pages().size());
			} // if
		} // if
		
		if( null == writableBookContentComponent ) {
			pageCount.set(0);
		} // if, else
		
		Set<RawFilteredPair<String>> newPages = new HashSet<>();
		
		AtomicLong totalAmount = new AtomicLong();
		totalAmount.set(0);
		
		ingredientsMap.get().forEach(( date, itemStacks ) -> {
			for (ItemStack stack : itemStacks) {
				if( pageCount.get() < WritableBookContentComponent.MAX_PAGE_COUNT ) {
					ReceiptValueComponent receiptValueComponent = stack.get( RECEIPT_VALUE_COMPONENT );
					
					if( null != receiptValueComponent ) {
						NumberFormat numberFormat = NumberFormat.getIntegerInstance();
						
						Text receiptText = stack.get( DataComponentTypes.ITEM_NAME );
						
						if( null == receiptText ) {
							receiptText = stack.getName();
						} // if
						
						Text dateText = Text.translatable( "item.villagerunknown-villagercoin.ledger.content.date", date );
						
						long value = receiptValueComponent.value() * stack.getCount();
						
						totalAmount.addAndGet(value);
						
						Text valueText = Text.translatable(
								"item.villagerunknown-villagercoin.ledger.content.total",
								numberFormat.format(value ),
								CoinItems.COPPER_COIN.getName().getString()
						);
						
						Text pageText = Text.empty()
								.append( dateText ).append("\n\n")
								.append( receiptText ).append("\n\n")
								.append( valueText );
						
						RawFilteredPair<String> pair = new RawFilteredPair<>( pageText.getString(), Optional.of( pageText.getString() ) );
						
						newPages.add( pair );
						pageCount.getAndIncrement();
					} // if
				} // if
			} // for
		});
		
		List<RawFilteredPair<String>> newWrittenPages = new ArrayList<>();
		
		if( null != writableBookContentComponent ) {
			newWrittenPages.addAll(writableBookContentComponent.pages());
		} // if
		
		newWrittenPages.addAll( newPages );
		
		WritableBookContentComponent newWritableBookContentComponent = new WritableBookContentComponent(
				newWrittenPages
		);
		
		itemStack.set( DataComponentTypes.WRITABLE_BOOK_CONTENT, newWritableBookContentComponent );
		
		if( null != existingLedger ) {
			AccumulatingValueComponent accumulatingValueComponent = existingLedger.get(ACCUMULATING_VALUE_COMPONENT);
			
			if (null != accumulatingValueComponent) {
				totalAmount.addAndGet(accumulatingValueComponent.value());
			} // if
		} // if
		
		itemStack.set( ACCUMULATING_VALUE_COMPONENT, new AccumulatingValueComponent(totalAmount.longValue()) );
		
		return itemStack;
	}
	
	static {
		RECIPE_SERIALIZER = (RecipeSerializer) Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of( MOD_ID, "crafting_special_ledger" ), new SpecialRecipeSerializer(LedgerRecipe::new));
	}
	
}
