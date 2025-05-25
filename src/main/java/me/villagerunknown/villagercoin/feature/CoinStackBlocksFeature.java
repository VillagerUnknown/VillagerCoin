package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.list.BlocksList;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.type.CoinType;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

public class CoinStackBlocksFeature {
	
	public static final String COIN_STACK_STRING = "coin_stack";
	
	public static final long SMALL_VALUE_MULTIPLIER = 1;
	public static final long MEDIUM_VALUE_MULTIPLIER = CoinFeature.CURRENCY_CONVERSION_MULTIPLIER / 2;
	public static final long LARGE_VALUE_MULTIPLIER = CoinFeature.CURRENCY_CONVERSION_MULTIPLIER;
	public static final long SLAB_VALUE_MULTIPLIER = CoinFeature.CURRENCY_CONVERSION_MULTIPLIER * 5L;
	public static final long BLOCK_VALUE_MULTIPLIER = CoinFeature.CURRENCY_CONVERSION_MULTIPLIER * 10L;
	
	public static final long COPPER_VALUE = CoinFeature.COPPER_VALUE;
	public static final long IRON_VALUE = CoinFeature.IRON_VALUE;
	public static final long GOLD_VALUE = CoinFeature.GOLD_VALUE;
	public static final long EMERALD_VALUE = CoinFeature.EMERALD_VALUE;
	public static final long NETHERITE_VALUE = CoinFeature.NETHERITE_VALUE;
	
	public static BlocksList blocks = new BlocksList();
	
	public static void execute(){}
	
	public static void addBlock( Block block ) {
		blocks.addBlock( block );
	}
	
	public static Block registerCoinStackBlock( String namespace, String id, Block block, long value ) {
		Block registeredBlock = RegistryUtil.registerBlock( id, block, namespace );
		
		Item item = RegistryUtil.registerItem(id, new BlockItem(registeredBlock, new Item.Settings().component( CURRENCY_COMPONENT, new CurrencyComponent( value ))), namespace);
		
		Villagercoin.addItemToGroup( item );
		
		addBlock( registeredBlock );
		
		return block;
	}
	
	public static Block registerFireproofCoinStackBlock( String namespace, String id, Block block, long value ) {
		Block registeredBlock = RegistryUtil.registerBlock( id, block, namespace );
		
		Item item = RegistryUtil.registerItem(id, new BlockItem(registeredBlock, new Item.Settings().fireproof().component( CURRENCY_COMPONENT, new CurrencyComponent( value ))), namespace);
		
		Villagercoin.addItemToGroup( item );
		
		addBlock( registeredBlock );
		
		return block;
	}
	
	public static Block registerCraftableCoinStackBlock( CoinType type, String namespace, String id, Block block, long value ) {
		Block registeredBlock = registerCoinStackBlock( namespace, id, block, value );
		
		CoinStackCraftingFeature.registerCraftingResultCoinStack( type, registeredBlock, value );
		
		return block;
	}
	
	public static Block registerCraftableFireproofCoinStackBlock( CoinType type, String namespace, String id, Block block, long value ) {
		Block registeredBlock = registerFireproofCoinStackBlock( namespace, id, block, value );
		
		CoinStackCraftingFeature.registerCraftingResultCoinStack( type, registeredBlock, value );
		
		return block;
	}
	
}
