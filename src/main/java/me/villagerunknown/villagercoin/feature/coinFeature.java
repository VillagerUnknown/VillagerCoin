package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.type.CurrencyComponent;
import me.villagerunknown.villagercoin.item.VillagerCoinItem;
import me.villagerunknown.villagercoin.recipe.VillagerCoinRecipe;
import me.villagerunknown.platform.util.RegistryUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.*;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.collection.DefaultedList;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class coinFeature {
	
	public static String COIN_STRING = "villager_coin";
	
	public static Item COPPER_COIN;
	public static Item IRON_COIN;
	public static Item GOLD_COIN;
	public static Item EMERALD_COIN;
	public static Item NETHERITE_COIN;
	
	public static TreeMap<Integer, Item> COINS = new TreeMap<>();
	
	public static RecipeSerializer<VillagerCoinRecipe> RECIPE_SERIALIZER;
	
	public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "item_group"));
	public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(EMERALD_COIN))
			.displayName(Text.translatable("itemGroup." + MOD_ID))
			.build();
	
	public static void execute() {
		registerItemGroup();
	}
	
	private static void registerItemGroup() {
		Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);
	}
	
	public static Item registerVillagerCoinItem( String id, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, float flipChance ) {
		return registerVillagerCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, flipChance, new Item.Settings() );
	}
	
	public static Item registerVillagerCoinItem( String id, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, float flipChance, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new VillagerCoinItem( settings, value, rarity, dropMinimum, dropMaximum, dropChance, flipChance ), MOD_ID );
		
		COINS.put( value, item );
		
		RegistryUtil.addItemToGroup( CUSTOM_ITEM_GROUP_KEY, item );
		
		return item;
	}
	
	public static ItemStack getLargestCoin( int coinValue ) {
		ItemStack returnStack = ItemStack.EMPTY;
		Item coin = null;
		
		for (Integer value : COINS.keySet()) {
			if( coinValue >= value ) {
				coin = COINS.get( value );
			} // if
		} // for
		
		if( null != coin ) {
			returnStack = new ItemStack( coin, 1 );
		} // if
		
		return returnStack;
	}
	
	public static ItemStack getLargerCoin( int coinValue ) {
		ItemStack returnStack = ItemStack.EMPTY;
		Item coin = null;
		
		for (Integer value : COINS.keySet()) {
			if( value > coinValue ) {
				coin = COINS.get( value );
				break;
			} // if
		} // for
		
		if( null != coin ) {
			returnStack = new ItemStack( coin, 1 );
		} // if
		
		return returnStack;
	}
	
	public static ItemStack getSmallerCoin( int coinValue ) {
		ItemStack returnStack = ItemStack.EMPTY;
		Item coin = null;
		
		for (Integer value : COINS.keySet()) {
			if( value < coinValue ) {
				coin = COINS.get( value );
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
		if(!coinItemStack.isEmpty() && COINS.containsValue(coinItemStack.getItem())) {
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
	
	public static int subtractCoinValueFromTotalCost(ItemStack ingredient, AtomicInteger totalCost, RecipeInputInventory craftingInput, int ingredientSlot ) {
		if( COINS.containsValue( ingredient.getItem() ) ) {
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
		} // if
		
		return totalCost.get();
	}
	
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
		COPPER_COIN = registerVillagerCoinItem( "copper_" + coinFeature.COIN_STRING, 1, Rarity.COMMON, 0, 10, 0.5F, 0.4F );
		IRON_COIN = registerVillagerCoinItem( "iron_" + coinFeature.COIN_STRING, 100, Rarity.UNCOMMON, 0, 5, 0.25F, 0.5F );
		GOLD_COIN = registerVillagerCoinItem( "gold_" + coinFeature.COIN_STRING, 10000, Rarity.RARE, 0, 3, 0.1F, 0.6F );
		EMERALD_COIN = registerVillagerCoinItem( "emerald_" + coinFeature.COIN_STRING, 1000000, Rarity.EPIC, 0, 0, 0, 0.75F );
		NETHERITE_COIN = registerVillagerCoinItem( "netherite_" + coinFeature.COIN_STRING, 100000000, Rarity.EPIC, 0, 0, 0, 0.25F, new Item.Settings().fireproof() );
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
