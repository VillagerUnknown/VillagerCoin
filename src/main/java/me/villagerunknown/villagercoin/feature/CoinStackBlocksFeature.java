package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.block.entity.AbstractCurrencyValueBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.type.CoinType;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

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
	
	public static void execute(){}
	
	public static BlockEntityType<? extends AbstractCurrencyValueBlockEntity> registerCoinStackBlockEntities(BlockEntityType.Builder<? extends AbstractCurrencyValueBlockEntity> builder) {
		// # Register the Block Entity Types
		return Registry.register(
				Registries.BLOCK_ENTITY_TYPE,
				Identifier.of(Villagercoin.MOD_ID, COIN_STACK_STRING),
				builder.build()
		);
	}
	
	public static Block registerCoinStackBlock( String id, Block block, long value ) {
		Block registeredBlock = RegistryUtil.registerBlock( id, block, Villagercoin.MOD_ID );
		
		Item item = RegistryUtil.registerItem(id, new BlockItem(registeredBlock, new Item.Settings().component( CURRENCY_COMPONENT, new CurrencyComponent( value ))), Villagercoin.MOD_ID);
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		return block;
	}
	
	public static Block registerFireproofCoinStackBlock( String id, Block block, long value ) {
		Block registeredBlock = RegistryUtil.registerBlock( id, block, Villagercoin.MOD_ID );
		
		Item item = RegistryUtil.registerItem(id, new BlockItem(registeredBlock, new Item.Settings().fireproof().component( CURRENCY_COMPONENT, new CurrencyComponent( value ))), Villagercoin.MOD_ID);
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		return block;
	}
	
	public static Block registerCraftableCoinStackBlock( CoinType type, String id, Block block, long value ) {
		Block registeredBlock = registerCoinStackBlock( id, block, value );
		
		CoinStackCraftingFeature.registerCraftingResultCoinStack( type, registeredBlock, value );
		
		return block;
	}
	
	public static Block registerCraftableFireproofCoinStackBlock( CoinType type, String id, Block block, long value ) {
		Block registeredBlock = registerFireproofCoinStackBlock( id, block, value );
		
		CoinStackCraftingFeature.registerCraftingResultCoinStack( type, registeredBlock, value );
		
		return block;
	}
	
}
