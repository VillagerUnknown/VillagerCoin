package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.component.CurrencyComponent;
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

import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static me.villagerunknown.villagercoin.Villagercoin.*;

public class CoinCraftingFeature {
	
	public static TreeMap<Integer, Item> CRAFTABLE_COINS = new TreeMap<>();
	
	public static RecipeSerializer<VillagerCoinRecipe> RECIPE_SERIALIZER;
	
	public static void execute() {}
	
	public static void registerCoin( Item item, int value ) {
		CRAFTABLE_COINS.put( value, item );
	}
	
	public static boolean craftingAllowed( Item item ) {
		return item.getComponents().contains( CURRENCY_COMPONENT );
	}
	
	public static ItemStack getLargestCoin(int coinValue ) {
		ItemStack returnStack = ItemStack.EMPTY;
		Item coin = null;
		
		for (Integer value : CRAFTABLE_COINS.keySet()) {
			if( coinValue >= value ) {
				Item item = CRAFTABLE_COINS.get( value );
				
				if( !item.getComponents().contains( COLLECTABLE_COMPONENT ) ) {
					coin = item;
				} // if
			} // if
		} // for
		
		if( null != coin ) {
			CurrencyComponent currencyComponent = coin.getComponents().get( CURRENCY_COMPONENT );
			
			if( null != currencyComponent ) {
				returnStack = new ItemStack(coin, getConversionValue(coinValue, currencyComponent.value()));
			} else {
				returnStack = new ItemStack(coin, getConversionValue(coinValue, 1));
			} // if, else
		} // if
		
		return returnStack;
	}
	
	public static ItemStack getLargerCoin( int coinValue ) {
		ItemStack returnStack = ItemStack.EMPTY;
		Item coin = null;
		
		for (Integer value : CRAFTABLE_COINS.keySet()) {
			if( value > coinValue ) {
				Item item = CRAFTABLE_COINS.get( value );
				
				if( !item.getComponents().contains( COLLECTABLE_COMPONENT ) ) {
					coin = item;
					break;
				} // if
			} // if
		} // for
		
		if( null != coin ) {
			CurrencyComponent currencyComponent = coin.getComponents().get( CURRENCY_COMPONENT );
			
			if( null != currencyComponent ) {
				returnStack = new ItemStack(coin, getConversionValue(coinValue, currencyComponent.value()));
			} else {
				returnStack = new ItemStack(coin, getConversionValue(coinValue, 1));
			} // if, else
		} // if
		
		return returnStack;
	}
	
	public static ItemStack getSmallerCoin( int coinValue ) {
		ItemStack returnStack = ItemStack.EMPTY;
		Item coin = null;
		
		for (Integer value : CRAFTABLE_COINS.keySet()) {
			if( value < coinValue ) {
				Item item = CRAFTABLE_COINS.get( value );
				
				if( !item.getComponents().contains( COLLECTABLE_COMPONENT ) ) {
					coin = item;
				} // if
			} // if
		} // for
		
		if( null != coin ) {
			CurrencyComponent currencyComponent = coin.getComponents().get( CURRENCY_COMPONENT );
			
			if( null != currencyComponent ) {
				if( getConversionValue( coinValue, currencyComponent.value() ) > 1 ) {
					returnStack = new ItemStack(coin, getConversionValue(coinValue, currencyComponent.value()));
				} // if
			} // if
		} // if
		
		return returnStack;
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
	
	public static TreeMap<Integer, CoinIngredient> getCoinIngredientsMap( RecipeInputInventory input ) {
		CraftingRecipeInput.Positioned positioned = input.createPositionedRecipeInput();
		CraftingRecipeInput craftingRecipeInput = positioned.input();
		int left = positioned.left();
		int top = positioned.top();
		
		TreeMap<Integer, CoinIngredient> ingredientsMap = new TreeMap<>(Villagercoin.reverseSort);
		
		for(int y = 0; y < craftingRecipeInput.getHeight(); ++y) {
			for (int x = 0; x < craftingRecipeInput.getWidth(); ++x) {
				int m = x + left + (y + top) * input.getWidth();
				
				ingredientsMap = updateCoinIngredientsMap( ingredientsMap, input.getStack(m), m, x, y );
			} // for
		} // for
		
		return ingredientsMap;
	}
	
	public static TreeMap<Integer, CoinIngredient> updateCoinIngredientsMap( TreeMap<Integer, CoinIngredient> ingredientsMap, ItemStack itemStack, int slot, int x, int y ) {
		CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
		
		if( null != currencyComponent ) {
			int valueKey = currencyComponent.value();
			
			if( ingredientsMap.containsKey( valueKey ) ) {
				valueKey = valueKey + ingredientsMap.size();
			} // if, else
			
			ingredientsMap.put(valueKey, new CoinIngredient(slot, itemStack, y, x));
		} // if
		
		return ingredientsMap;
	}
	
	public static TreeMap<Integer, ItemStack> updateCoinIngredientsMap(TreeMap<Integer, ItemStack> ingredientsMap, ItemStack coinItemStack ) {
		if(!coinItemStack.isEmpty() && CRAFTABLE_COINS.containsValue(coinItemStack.getItem())) {
			CurrencyComponent currencyComponent = coinItemStack.get( CURRENCY_COMPONENT );
			
			if( null != currencyComponent ) {
				int valueKey = currencyComponent.value();
				
				if (ingredientsMap.containsKey(valueKey)) {
					valueKey = valueKey + ingredientsMap.size();
				} // if, else
				
				ingredientsMap.put(valueKey, coinItemStack);
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
	 * @return int - Total Cost
	 */
	public static int subtractCoinValueFromTotalCost(ItemStack ingredient, AtomicInteger totalCost, RecipeInputInventory craftingInput, int ingredientSlot ) {
		CurrencyComponent currencyComponent = ingredient.get( CURRENCY_COMPONENT );
		
		if( null != currencyComponent ) {
			int ingredientCoinValue = currencyComponent.value();
			int ingredientCoinStackValue = ingredient.getCount() * ingredientCoinValue;
			
			if( ingredientCoinValue == totalCost.get()) {
				craftingInput.removeStack(ingredientSlot, 1 );
				totalCost.addAndGet(-ingredientCoinValue);
			} else if( ingredientCoinStackValue <= totalCost.get()) {
				craftingInput.removeStack(ingredientSlot, ingredient.getCount());
				totalCost.addAndGet(-ingredientCoinStackValue);
			} else if( ingredientCoinValue < totalCost.get()) {
				int amount = totalCost.get() / ingredientCoinValue;
				
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
	 * @return int - Total Cost
	 */
	public static int subtractCoinValueFromTotalCost(ItemStack ingredient, AtomicInteger totalCost, DefaultedList<ItemStack> ingredients ) {
		for( ItemStack stack : ingredients ) {
			if( stack.equals(ingredient) ) {
				CurrencyComponent currencyComponent = ingredient.get( CURRENCY_COMPONENT );
				
				if( null != currencyComponent ) {
					int ingredientCoinValue = currencyComponent.value();
					int ingredientCoinStackValue = ingredient.getCount() * ingredientCoinValue;
					
					if (ingredientCoinValue == totalCost.get()) {
						stack.decrement(1);
						totalCost.addAndGet(-ingredientCoinValue);
					} else if (ingredientCoinStackValue <= totalCost.get()) {
						stack.decrement(ingredient.getCount());
						totalCost.addAndGet(-ingredientCoinStackValue);
					} else if (ingredientCoinValue < totalCost.get()) {
						int amount = totalCost.get() / ingredientCoinValue;
						
						if (amount >= ingredient.getCount()) {
							stack.decrement(ingredient.getCount());
							totalCost.addAndGet(-(ingredientCoinValue * ingredient.getCount()));
						} else {
							stack.decrement(amount);
							totalCost.addAndGet(-(ingredientCoinValue * amount));
						} // if, else
					} // if, else
					break;
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
