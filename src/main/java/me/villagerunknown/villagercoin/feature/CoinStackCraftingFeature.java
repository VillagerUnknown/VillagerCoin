package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.item.CoinItems;
import me.villagerunknown.villagercoin.recipe.CoinStackRecipe;
import me.villagerunknown.villagercoin.recipe.VillagerCoinRecipe;
import me.villagerunknown.villagercoin.type.CoinType;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import java.util.*;

import static java.util.Map.entry;
import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;
import static me.villagerunknown.villagercoin.component.Components.*;

public class CoinStackCraftingFeature {
	
	public static Item RECIPE_CARRIER_ITEM = Items.STRING;
	
	private static HashMap<CoinType, TreeMap<Long, Item>> CRAFTING_RESULT_COIN_STACKS = new HashMap<>(Map.ofEntries(
			entry( CoinType.COPPER, new TreeMap<>() ),
			entry( CoinType.IRON, new TreeMap<>() ),
			entry( CoinType.GOLD, new TreeMap<>() ),
			entry( CoinType.EMERALD, new TreeMap<>() ),
			entry( CoinType.NETHERITE, new TreeMap<>() )
	));
	
	private static HashSet<Item> FLAT_CRAFTING_RESULT_COIN_STACKS = new HashSet<>();
	
	public static RecipeSerializer RECIPE_SERIALIZER;
	
	public static void execute() {}
	
	public static void registerCraftingResultCoinStack( CoinType type, Block block, long value ) {
		if( CRAFTING_RESULT_COIN_STACKS.containsKey( type ) ) {
			TreeMap<Long, Item> typeMap = CRAFTING_RESULT_COIN_STACKS.get( type );
			
			typeMap.put( value, block.asItem() );
			
			FLAT_CRAFTING_RESULT_COIN_STACKS.add( block.asItem() );
			CRAFTING_RESULT_COIN_STACKS.replace( type, typeMap );
		} else {
			Villagercoin.LOGGER.warn( "Attempted to register a Coin Stack crafting result with an invalid type provided." );
		} // if, else
	}
	
	public static boolean isCraftingResultCoinStack( Item item ) {
		return FLAT_CRAFTING_RESULT_COIN_STACKS.contains( item );
	}
	
	public static boolean canCraftResult( Item item ) {
		return (
				item.equals( CoinItems.COPPER_COIN )
				|| item.equals( CoinItems.IRON_COIN )
				|| item.equals( CoinItems.GOLD_COIN )
				|| item.equals( CoinItems.EMERALD_COIN )
				|| item.equals( CoinItems.NETHERITE_COIN )
		);
	}
	
	public static Collection<Item> getCraftingResultCoinStacks( CoinType type ) {
		if( CRAFTING_RESULT_COIN_STACKS.containsKey( type ) ) {
			TreeMap<Long, Item> typeMap = CRAFTING_RESULT_COIN_STACKS.get(type);
			
			return typeMap.values();
		} // if
		
		return null;
	}
	
	public static ItemStack getLargestCoinStack( CoinType type, long coinStackValue) {
		ItemStack returnStack = ItemStack.EMPTY;
		Item coinStack = null;
		
		if( CRAFTING_RESULT_COIN_STACKS.containsKey( type ) ) {
			TreeMap<Long, Item> typeMap = CRAFTING_RESULT_COIN_STACKS.get(type);
			
			for (Long value : typeMap.keySet()) {
				if( coinStackValue >= value ) {
					Item item = typeMap.get( value );
					
					if( !item.components().has( COLLECTABLE_COMPONENT ) ) {
						coinStack = item;
					} // if
				} // if
			} // for
			
			if( null != coinStack) {
				returnStack = new ItemStack(coinStack, 1);
			} // if
		} // if
		
		return returnStack;
	}
	
	public static ItemStack getSmallestCoinStack( CoinType type, long coinStackValue) {
		if( coinStackValue > 0 ) {
			if( CRAFTING_RESULT_COIN_STACKS.containsKey( type ) ) {
				TreeMap<Long, Item> typeMap = CRAFTING_RESULT_COIN_STACKS.get(type);
				
				if( coinStackValue > Integer.MAX_VALUE ) {
					coinStackValue = Integer.MAX_VALUE;
				} // if
				
				return new ItemStack( typeMap.get( 1L ), CoinCraftingFeature.toIntSafely(coinStackValue) );
			} // if
		} // if
		
		return ItemStack.EMPTY;
	}
	
	public static void subtractCarrierFromIngredients( CraftingContainer craftingInput, long amount ) {
		CraftingInput.Positioned positioned = craftingInput.asPositionedCraftInput();
		CraftingInput craftingRecipeInput = positioned.input();
		int left = positioned.left();
		int top = positioned.top();
		
		for(int y = 0; y < craftingRecipeInput.height(); ++y) {
			for (int x = 0; x < craftingRecipeInput.width(); ++x) {
				int m = x + left + (y + top) * craftingInput.getWidth();
				ItemStack ingredientStack = craftingInput.getItem(m);
				
				if( ingredientStack.is( RECIPE_CARRIER_ITEM ) ) {
					if( amount > Integer.MAX_VALUE ) {
						amount = Integer.MAX_VALUE;
					} // if
					
					craftingInput.removeItem( m, CoinCraftingFeature.toIntSafely(amount) );
				} // if
			} // for
		} // for
	}
	
	public static void subtractCarrierFromIngredients( NonNullList<ItemStack> ingredients, int amount ) {
		for (ItemStack ingredientStack : ingredients) {
			if( ingredientStack.is( RECIPE_CARRIER_ITEM ) ) {
				ingredientStack.shrink( amount );
			} // if
		} // for
	}
	
	static {
		RECIPE_SERIALIZER = Registry.register(
				BuiltInRegistries.RECIPE_SERIALIZER,
				Identifier.fromNamespaceAndPath( MOD_ID, "crafting_special_coin_stack" ),
				new RecipeSerializer<>(CoinStackRecipe.CODEC, CoinStackRecipe.STREAM_CODEC)
		);
	}
	
}
