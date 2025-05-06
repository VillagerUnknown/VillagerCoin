package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.*;
import me.villagerunknown.villagercoin.item.CoinItems;
import me.villagerunknown.villagercoin.recipe.LedgerCloningRecipe;
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

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;
import static me.villagerunknown.villagercoin.component.Components.*;

public class LedgerCraftingFeature {
	
	public static Item RECIPE_CARRIER_ITEM = Items.WRITABLE_BOOK;
	
	private static HashSet<Item> CRAFTING_RESULT_LEDGERS = new HashSet<>();
	
	public static RecipeSerializer<LedgerRecipe> RECIPE_SERIALIZER;
	public static RecipeSerializer<LedgerCloningRecipe> CLONING_RECIPE_SERIALIZER;
	
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
				
				if( ingredientStack.isOf( RECIPE_CARRIER_ITEM ) ) {
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
			subtractCarrierFromIngredients( List.of(ingredientStack), ingredientStack.getCount() );
		} // for
	}
	
	public static void subtractCarrierFromIngredients(List<ItemStack> ingredients, int amount ) {
		for (ItemStack ingredientStack : ingredients) {
			if( ingredientStack.isOf( RECIPE_CARRIER_ITEM ) ) {
				ingredientStack.decrement( amount );
				break;
			} // if
		} // for
	}
	
	public static void subtractLedgerFromIngredients(RecipeInputInventory craftingInput, long amount ) {
		CraftingRecipeInput.Positioned positioned = craftingInput.createPositionedRecipeInput();
		CraftingRecipeInput craftingRecipeInput = positioned.input();
		int left = positioned.left();
		int top = positioned.top();
		
		for(int y = 0; y < craftingRecipeInput.getHeight(); ++y) {
			for (int x = 0; x < craftingRecipeInput.getWidth(); ++x) {
				int m = x + left + (y + top) * craftingInput.getWidth();
				ItemStack ingredientStack = craftingInput.getStack(m);
				
				if( ingredientStack.isIn( Villagercoin.getItemTagKey( "ledger" ) ) ) {
					if( amount > Integer.MAX_VALUE ) {
						amount = Integer.MAX_VALUE;
					} // if
					
					craftingInput.removeStack( m, CoinCraftingFeature.toIntSafely(amount) );
					break;
				} // if
			} // for
		} // for
	}
	
	public static void removeLedgerFromIngredients(List<ItemStack> ingredients ) {
		for (ItemStack ingredientStack : ingredients) {
			subtractLedgerFromIngredients( List.of(ingredientStack), ingredientStack.getCount() );
		} // for
	}
	
	public static void subtractLedgerFromIngredients(List<ItemStack> ingredients, int amount ) {
		for (ItemStack ingredientStack : ingredients) {
			if( ingredientStack.isIn( Villagercoin.getItemTagKey( "ledger" ) ) ) {
				ingredientStack.decrement( amount );
				break;
			} // if
		} // for
	}
	
	public static void removeReceiptsFromIngredients(List<ItemStack> ingredients ) {
		for (ItemStack ingredientStack : ingredients) {
			subtractReceiptsFromIngredients( List.of(ingredientStack), ingredientStack.getCount() );
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
	
	public static ItemStack updateLedgerFromSlot(ItemStack itemStack, TreeMap<String, HashSet<CoinCraftingFeature.CoinIngredient>> ingredientsMap, @Nullable ItemStack existingLedger,boolean copying) {
		// Get page count from existing ledger item
		AtomicInteger pageCount = new AtomicInteger();
		
		WritableBookContentComponent writableBookContentComponent = itemStack.get(DataComponentTypes.WRITABLE_BOOK_CONTENT);
		
		List<RawFilteredPair<String>> newPages = new ArrayList<>();
		
		// Add pages from existing writable component
		if( null != existingLedger ) {
			writableBookContentComponent = existingLedger.get(DataComponentTypes.WRITABLE_BOOK_CONTENT);
			
			if( null != writableBookContentComponent ) {
				newPages.addAll(writableBookContentComponent.pages());
			} // if
		} // if
		
		setPageCount( writableBookContentComponent, existingLedger, pageCount );
		
		// Create new ledger object
		Ledger ledger = new Ledger( newPages, new AtomicLong(0) );
		
		// Add pages to ledger object
		ingredientsMap.forEach(( date, ingredients ) -> {
			for( CoinCraftingFeature.CoinIngredient ingredient : ingredients ) {
				if( pageCount.get() < WritableBookContentComponent.MAX_PAGE_COUNT ) {
					ledger.addPage( pageCount, ingredient.stack, date );
				} // if
			} // for
		});
		
		return writeLedger( ledger, itemStack, existingLedger, copying );
	}
	
	public static ItemStack updateLedger(ItemStack itemStack, AtomicReference<TreeMap<String, HashSet<ItemStack>>> ingredientsMap, @Nullable ItemStack existingLedger,boolean copying) {
		// Get page count from existing ledger item
		AtomicInteger pageCount = new AtomicInteger();
		
		WritableBookContentComponent writableBookContentComponent = itemStack.get(DataComponentTypes.WRITABLE_BOOK_CONTENT);
		
		List<RawFilteredPair<String>> newPages = new ArrayList<>();
		
		// Add pages from existing writable component
		if( null != existingLedger ) {
			writableBookContentComponent = existingLedger.get(DataComponentTypes.WRITABLE_BOOK_CONTENT);
			
			if( null != writableBookContentComponent ) {
				newPages.addAll(writableBookContentComponent.pages());
			} // if
		} // if
		
		setPageCount( writableBookContentComponent, existingLedger, pageCount );
		
		// Create new ledger object
		Ledger ledger = new Ledger( newPages, new AtomicLong(0) );
		
		// Add pages to ledger object
		ingredientsMap.get().forEach(( date, itemStacks ) -> {
			for( ItemStack stack : itemStacks ) {
				if( pageCount.get() < WritableBookContentComponent.MAX_PAGE_COUNT ) {
					ledger.addPage( pageCount, stack, date );
				} // if
			} // for
		});
		
		return writeLedger( ledger, itemStack, existingLedger, copying );
	}
	
	protected static void setPageCount(WritableBookContentComponent writableBookContentComponent, ItemStack existingLedger, AtomicInteger pageCount ) {
		if( null != existingLedger ) {
			writableBookContentComponent = existingLedger.get( DataComponentTypes.WRITABLE_BOOK_CONTENT );
			if( null != writableBookContentComponent ) {
				pageCount.set(writableBookContentComponent.pages().size());
			} // if
		} // if
		
		// Page count is 0 if we don't have a writable component
		if( null == writableBookContentComponent ) {
			pageCount.set(0);
		} // if, else
	}
	
	protected static ItemStack writeLedger( Ledger ledger, ItemStack itemStack, @Nullable ItemStack existingLedger, boolean copying ) {
		// Add pages from ledger object to writable book
		itemStack.set( DataComponentTypes.WRITABLE_BOOK_CONTENT, new WritableBookContentComponent(
				ledger.pages.stream().toList()
		) );
		
		String date = LocalDate.now().toString();
		
		if( null != existingLedger ) {
			// # Instructions for existing ledgers (including copying)
			
			DateComponent dateComponent = existingLedger.get( DATE_COMPONENT );
			
			if( dateComponent != null ) {
				itemStack.set( DATE_COMPONENT, dateComponent );
			} // if
			
			if( !copying ) {
				itemStack.set( UPDATED_DATE_COMPONENT, new UpdatedDateComponent( date ) );
			} else {
				UpdatedDateComponent updatedDateComponent = existingLedger.get( UPDATED_DATE_COMPONENT );
				
				if( null != updatedDateComponent ) {
					itemStack.set( UPDATED_DATE_COMPONENT, updatedDateComponent );
				} // if
			} // if, else
			
			// Add total value of receipts to ledger total
			AccumulatingValueComponent accumulatingValueComponent = existingLedger.get(ACCUMULATING_VALUE_COMPONENT);
			
			if (null != accumulatingValueComponent) {
				ledger.totalAmount.addAndGet(accumulatingValueComponent.value());
			} // if
			
			// Copy or set the Copy Count
			CopyCountComponent copyCountComponent = existingLedger.get(COPY_COUNT_COMPONENT);
			
			if( null != copyCountComponent ) {
				itemStack.set( COPY_COUNT_COMPONENT, copyCountComponent );
			} // if
			
			// Copy custom name
			Text customNameComponent = existingLedger.get(DataComponentTypes.CUSTOM_NAME);
			
			if (null != customNameComponent) {
				itemStack.set( DataComponentTypes.CUSTOM_NAME, customNameComponent );
			} else if( copying ) {
				itemStack.set(
						DataComponentTypes.CUSTOM_NAME,
						Text.translatable(
								"item.villagerunknown-villagercoin.ledger.copiedName",
								itemStack.getName().getString()
						)
				);
			} // if, else
		} else {
			// # Instructions for new ledgers
			
			// Set a date component
			itemStack.set( DATE_COMPONENT, new DateComponent( date ) );
		} // if, else
		
		itemStack.set( ACCUMULATING_VALUE_COMPONENT, new AccumulatingValueComponent(ledger.totalAmount.longValue()) );
		
		return itemStack;
	}
	
	static {
		RECIPE_SERIALIZER = (RecipeSerializer) Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of( MOD_ID, "crafting_special_ledger" ), new SpecialRecipeSerializer(LedgerRecipe::new));
		CLONING_RECIPE_SERIALIZER = (RecipeSerializer) Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of( MOD_ID, "crafting_special_ledgercloning" ), new SpecialRecipeSerializer(LedgerCloningRecipe::new));
	}
	
	public static class Ledger {
		
		public List<RawFilteredPair<String>> pages;
		
		public AtomicLong totalAmount;
		
		Ledger( List<RawFilteredPair<String>> pages, AtomicLong totalAmount ) {
			this.pages = pages;
			this.totalAmount = totalAmount;
		}
		
		protected void addPage( AtomicInteger pageCount, ItemStack ingredient, String date ) {
			ReceiptValueComponent receiptValueComponent = ingredient.get( RECEIPT_VALUE_COMPONENT );
			
			if( null != receiptValueComponent ) {
				NumberFormat numberFormat = NumberFormat.getIntegerInstance();
				
				Text receiptText = ingredient.get( DataComponentTypes.CUSTOM_NAME );
				
				if( null == receiptText ) {
					receiptText = ingredient.get( DataComponentTypes.ITEM_NAME );
					
					if( null == receiptText ) {
						receiptText = ingredient.getName();
					} // if
				} // if
				
				Text dateText = Text.translatable( "item.villagerunknown-villagercoin.ledger.content.date", date );
				
				long value = receiptValueComponent.value() * ingredient.getCount();
				
				this.totalAmount.addAndGet( value );
				
				Text valueText = Text.translatable(
						"item.villagerunknown-villagercoin.ledger.content.total",
						numberFormat.format( value ),
						CoinItems.COPPER_COIN.getName().getString()
				);
				
				ReceiptMessageComponent receiptMessageComponent = ingredient.get( RECEIPT_MESSAGE_COMPONENT );
				
				Text receiptMessage = Text.empty();
				
				if( null != receiptMessageComponent && !Objects.equals(receiptMessageComponent.message(), Villagercoin.CONFIG.defaultReceiptThankYouMessage) ) {
					receiptMessage = Text.empty().append( "\n\n" + receiptMessageComponent.message() );
				} // if
				
				Text pageText = Text.empty()
						.append( dateText ).append("\n\n")
						.append( receiptText ).append("\n\n")
						.append( valueText )
						.append( receiptMessage );
				
				String truncatedPageText = pageText.asTruncatedString(WritableBookContentComponent.MAX_PAGE_LENGTH);
				RawFilteredPair<String> pair = new RawFilteredPair<>( truncatedPageText, Optional.of( truncatedPageText ) );
				
				this.pages.add( pair );
				pageCount.getAndIncrement();
			} // if
		}
		
	}
	
}
