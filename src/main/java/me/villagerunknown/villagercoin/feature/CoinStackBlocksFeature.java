package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.list.BlocksList;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.Components;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.type.CoinType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;
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
		
		Item.Properties settings = new Item.Properties()
				.useBlockDescriptionPrefix()
				.component(CURRENCY_COMPONENT, new CurrencyComponent(value))
				.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID,id)));
		
		Item item = RegistryUtil.registerItem(id, new BlockItem(registeredBlock, settings), namespace);
		
		Villagercoin.addItemToGroup( item );
		
		addBlock( registeredBlock );
		
		return block;
	}
	
	public static Block registerFireproofCoinStackBlock( String namespace, String id, Block block, long value ) {
		Block registeredBlock = RegistryUtil.registerBlock( id, block, namespace );
		
		Item.Properties settings = new Item.Properties()
				.useBlockDescriptionPrefix()
				.component(CURRENCY_COMPONENT, new CurrencyComponent(value))
				.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID,id)))
				.fireResistant();
		
		Item item = RegistryUtil.registerItem(id, new BlockItem(registeredBlock, settings), namespace);
		
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
