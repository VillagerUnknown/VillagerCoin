package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.block.entity.AbstractCurrencyValueBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

public class CoinStackBlocksFeature {
	
	public static final String COIN_STACK_STRING = "coin_stack";
	
	public static final int SMALL_VALUE_MULTIPLIER = 1;
	public static final int MEDIUM_VALUE_MULTIPLIER = CoinFeature.CURRENCY_CONVERSION_MULTIPLIER / 2;
	public static final int LARGE_VALUE_MULTIPLIER = CoinFeature.CURRENCY_CONVERSION_MULTIPLIER;
	
	public static final int MEDIUM_HIGH_VALUE_MULTIPLIER = 2;
	public static final int LARGE_HIGH_VALUE_MULTIPLIER = 3;
	
	public static final int COPPER_VALUE = CoinFeature.COPPER_VALUE * SMALL_VALUE_MULTIPLIER;
	public static final int IRON_VALUE = CoinFeature.IRON_VALUE * SMALL_VALUE_MULTIPLIER;
	public static final int GOLD_VALUE = CoinFeature.GOLD_VALUE * SMALL_VALUE_MULTIPLIER;
	public static final int EMERALD_VALUE = CoinFeature.EMERALD_VALUE;
	public static final int NETHERITE_VALUE = CoinFeature.NETHERITE_VALUE;
	
	public static void execute(){}
	
	public static BlockEntityType<? extends AbstractCurrencyValueBlockEntity> registerCoinStackBlockEntities(BlockEntityType.Builder<? extends AbstractCurrencyValueBlockEntity> builder) {
		// # Register the Block Entity Types
		return Registry.register(
				Registries.BLOCK_ENTITY_TYPE,
				Identifier.of(Villagercoin.MOD_ID, COIN_STACK_STRING),
				builder.build()
		);
	}
	
	public static Block registerCoinStackBlock(String id, Block block, int value) {
		Block registeredBlock = RegistryUtil.registerBlock( id, block, Villagercoin.MOD_ID );
		
		Item item = RegistryUtil.registerItem(id, new BlockItem(registeredBlock, new Item.Settings().component( CURRENCY_COMPONENT, new CurrencyComponent( value ))), Villagercoin.MOD_ID);
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		return block;
	}
	
}
