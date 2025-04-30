package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.item.CoinItems;
import me.villagerunknown.villagercoin.recipe.VillagerCoinRecipe;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.Collection;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static me.villagerunknown.villagercoin.Villagercoin.*;

public class CoinCraftingFeature {
	
	private static TreeMap<Long, Item> CRAFTING_RESULT_COINS = new TreeMap<>();
	
	public static RecipeSerializer<VillagerCoinRecipe> RECIPE_SERIALIZER;
	
	public static void execute() {}
	
	public static void registerCraftingResultCoin(Item item, long value ) {
		CRAFTING_RESULT_COINS.put( value, item );
	}
	
	public static boolean isCraftingResultCoin( Item item ) {
		return CRAFTING_RESULT_COINS.containsValue( item );
	}
	
	public static boolean canCraftResult(Item item ) {
		return item.getComponents().contains( CURRENCY_COMPONENT );
	}
	
	public static Collection<Item> getCraftingResultCoins() {
		return CRAFTING_RESULT_COINS.values();
	}
	
	public static ItemStack getLargestCoin( long coinValue, boolean singleCount ) {
		ItemStack returnStack = ItemStack.EMPTY;
		Item coin = null;
		
		for (long value : CRAFTING_RESULT_COINS.keySet()) {
			if( coinValue >= value ) {
				Item item = CRAFTING_RESULT_COINS.get( value );
				
				if( !item.getComponents().contains( COLLECTABLE_COMPONENT ) ) {
					coin = item;
				} // if
			} // if
		} // for
		
		if( null != coin ) {
			CurrencyComponent currencyComponent = coin.getComponents().get( CURRENCY_COMPONENT );
			
			returnStack = new ItemStack(coin, 1);
			
			if( !singleCount && null != currencyComponent ) {
				returnStack = new ItemStack(coin, getConversionValueSafelyFromLong(coinValue, currencyComponent.value()));
			} // if
		} // if
		
		return returnStack;
	}
	
	public static ItemStack getLargerCoin( long coinValue, boolean singleCount ) {
		ItemStack returnStack = ItemStack.EMPTY;
		Item coin = null;
		
		for (long value : CRAFTING_RESULT_COINS.keySet()) {
			if( value > coinValue ) {
				Item item = CRAFTING_RESULT_COINS.get( value );
				
				if( !item.getComponents().contains( COLLECTABLE_COMPONENT ) ) {
					coin = item;
					break;
				} // if
			} // if
		} // for
		
		if( null != coin ) {
			CurrencyComponent currencyComponent = coin.getComponents().get( CURRENCY_COMPONENT );
			
			returnStack = new ItemStack(coin, getConversionValueSafelyFromLong(coinValue, 1));
			
			if( !singleCount && null != currencyComponent ) {
				returnStack = new ItemStack(coin, getConversionValueSafelyFromLong(coinValue, currencyComponent.value()));
			} // if
		} // if
		
		return returnStack;
	}
	
	public static ItemStack getSmallerCoin( long coinValue ) {
		ItemStack returnStack = ItemStack.EMPTY;
		Item coin = null;
		
		for (Long value : CRAFTING_RESULT_COINS.keySet()) {
			if( value < coinValue ) {
				Item item = CRAFTING_RESULT_COINS.get( value );
				
				if( !item.getComponents().contains( COLLECTABLE_COMPONENT ) ) {
					coin = item;
				} // if
			} // if
		} // for
		
		if( null != coin ) {
			CurrencyComponent currencyComponent = coin.getComponents().get( CURRENCY_COMPONENT );
			
			if( null != currencyComponent ) {
				if( getConversionValue( coinValue, currencyComponent.value() ) > 1 ) {
					returnStack = new ItemStack(coin, getConversionValueSafelyFromLong( coinValue, currencyComponent.value()));
				} // if
			} // if
		} // if
		
		return returnStack;
	}
	
	public static ItemStack getSmallestCoin( int coinValue ) {
		if( coinValue > 0 ) {
			return new ItemStack( CoinItems.COPPER_COIN, coinValue );
		} // if
		
		return ItemStack.EMPTY;
	}
	
	public static int toIntSafely( long number ) {
		try {
			if( number > Integer.MAX_VALUE ) {
				number = Integer.MAX_VALUE;
			} // if
			
			return Math.toIntExact( number );
		} catch(ArithmeticException e) {
			LOGGER.warn( e.getMessage() );
		} // try, catch
		
		return 0;
	}
	
	public static int getConversionValue(int fromValue, int toValue) {
		int result;
		
		if( fromValue > toValue ) {
			result = fromValue / toValue;
		} else {
			result = toValue / fromValue;
		} // if, else
		
		return result;
	}
	
	public static long getConversionValue(long fromValue, long toValue) {
		long result;
		
		if( fromValue > toValue ) {
			result = fromValue / toValue;
		} else {
			result = toValue / fromValue;
		} // if, else
		
		return result;
	}
	
	public static int getConversionValueSafelyFromLong(long fromValue, long toValue) {
		long result;
		
		if( fromValue > toValue ) {
			result = fromValue / toValue;
		} else {
			result = toValue / fromValue;
		} // if, else
		
		return toIntSafely( result );
	}
	
	public static TreeMap<Long, CoinIngredient> getCoinIngredientsMap( RecipeInputInventory input ) {
		CraftingRecipeInput.Positioned positioned = input.createPositionedRecipeInput();
		CraftingRecipeInput craftingRecipeInput = positioned.input();
		int left = positioned.left();
		int top = positioned.top();
		
		TreeMap<Long, CoinIngredient> ingredientsMap = new TreeMap<>(Villagercoin.reverseSortLong);
		
		for(int y = 0; y < craftingRecipeInput.getHeight(); ++y) {
			for (int x = 0; x < craftingRecipeInput.getWidth(); ++x) {
				int m = x + left + (y + top) * input.getWidth();
				
				ingredientsMap = updateCoinIngredientsMap( ingredientsMap, input.getStack(m), m, x, y );
			} // for
		} // for
		
		return ingredientsMap;
	}
	
	public static TreeMap<Long, CoinIngredient> updateCoinIngredientsMap( TreeMap<Long, CoinIngredient> ingredientsMap, ItemStack itemStack, int slot, int x, int y ) {
		CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
		
		if( null != currencyComponent ) {
			long currencyValue = currencyComponent.value();
			
			if( ingredientsMap.containsKey( currencyValue ) ) {
				currencyValue = currencyValue + ingredientsMap.size();
			} // if, else
			
			ingredientsMap.put(currencyValue, new CoinIngredient(slot, itemStack, y, x));
		} // if
		
		return ingredientsMap;
	}
	
	public static TreeMap<Long, ItemStack> updateCoinIngredientsMap(TreeMap<Long, ItemStack> ingredientsMap, ItemStack coinItemStack ) {
		if(!coinItemStack.isEmpty()) {
			CurrencyComponent currencyComponent = coinItemStack.get( CURRENCY_COMPONENT );
			
			if( null != currencyComponent ) {
				long currencyValue = currencyComponent.value();
				
				if (ingredientsMap.containsKey(currencyValue)) {
					currencyValue = currencyValue + ingredientsMap.size();
				} // if, else
				
				ingredientsMap.put(currencyValue, coinItemStack);
			} // if
		} // if
		
		return ingredientsMap;
	}
	
	/**
	 * Subtracts Coin Value From Total Cost
	 * Used by:
	 *  - mixin.CraftingResultSlotMixin
	 *  - mixin.CraftingScreenHandlerMixin
	 *  - mixin.PlayerScreenHandlerMixin
	 *
	 * @param ingredient
	 * @param totalCost
	 * @param craftingInput
	 * @param ingredientSlot
	 * @return long - Total Cost
	 */
	public static long subtractCoinValueFromTotalCost(ItemStack ingredient, AtomicLong totalCost, RecipeInputInventory craftingInput, int ingredientSlot ) {
		CurrencyComponent currencyComponent = ingredient.get( CURRENCY_COMPONENT );
		
		if( null != currencyComponent ) {
			long ingredientCoinValue = currencyComponent.value();
			long ingredientCoinStackValue = ingredient.getCount() * ingredientCoinValue;
			
			if( ingredientCoinValue == totalCost.get()) {
				craftingInput.removeStack(ingredientSlot, 1 );
				totalCost.addAndGet(-ingredientCoinValue);
			} else if( ingredientCoinStackValue <= totalCost.get()) {
				craftingInput.removeStack(ingredientSlot, ingredient.getCount());
				totalCost.addAndGet(-ingredientCoinStackValue);
			} else if( ingredientCoinValue < totalCost.get()) {
				int amount = CoinCraftingFeature.getConversionValueSafelyFromLong( totalCost.get(), ingredientCoinValue );
				
				if( amount >= ingredient.getCount() ) {
					craftingInput.removeStack(ingredientSlot, ingredient.getCount());
					totalCost.addAndGet(-(ingredientCoinValue * ingredient.getCount()));
				} else {
					craftingInput.removeStack(ingredientSlot, amount);
					totalCost.addAndGet(-(ingredientCoinValue * amount));
				} // if, else
			} // if, else
		} // if
		
		return totalCost.get();
	}
	
	/**
	 * Subtracts Coin Value From Total Cost
	 * 	 * Used by:
	 * 	 *  - mixin.CrafterBlockMixin
	 *
	 * @param ingredient
	 * @param totalCost
	 * @param ingredients
	 * @return long - Total Cost
	 */
	public static long subtractCoinValueFromTotalCost(ItemStack ingredient, AtomicLong totalCost, DefaultedList<ItemStack> ingredients ) {
		for( ItemStack stack : ingredients ) {
			if( stack.equals(ingredient) ) {
				CurrencyComponent currencyComponent = ingredient.get( CURRENCY_COMPONENT );
				
				if( null != currencyComponent ) {
					long ingredientCoinValue = currencyComponent.value();
					long ingredientCoinStackValue = ingredient.getCount() * ingredientCoinValue;
					
					if (ingredientCoinValue == totalCost.get()) {
						stack.decrement(1);
						totalCost.addAndGet(-ingredientCoinValue);
					} else if (ingredientCoinStackValue <= totalCost.get()) {
						stack.decrement(ingredient.getCount());
						totalCost.addAndGet(-ingredientCoinStackValue);
					} else if (ingredientCoinValue < totalCost.get()) {
						int amount = CoinCraftingFeature.getConversionValueSafelyFromLong( totalCost.get(), ingredientCoinValue );
						
						if (amount >= ingredient.getCount()) {
							stack.decrement(ingredient.getCount());
							totalCost.addAndGet(-(ingredientCoinValue * ingredient.getCount()));
						} else {
							stack.decrement(amount);
							totalCost.addAndGet(-(ingredientCoinValue * amount));
						} // if, else
					} // if, else
				} // if
			} // if
		} // for
		
		return totalCost.get();
	}
	
	static {
		RECIPE_SERIALIZER = (RecipeSerializer) Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of( MOD_ID, "crafting_special_villager_coin" ), new SpecialRecipeSerializer(VillagerCoinRecipe::new));
	}
	
	public static class CoinIngredient {
		
		public int slot;
		public int y;
		public int x;
		public ItemStack stack;
		
		public CoinIngredient(int slot, ItemStack stack, int y, int x) {
			this.slot = slot;
			this.y = y;
			this.x = x;
			this.stack = stack;
		}
		
	}
	
}
