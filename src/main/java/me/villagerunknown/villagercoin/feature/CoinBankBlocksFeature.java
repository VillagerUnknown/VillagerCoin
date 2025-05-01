package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.block.entity.AbstractCurrencyValueBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

public class CoinBankBlocksFeature {
	
	public static final String COIN_BANK_STRING = "coin_bank";
	
	public static void execute(){}
	
	public static BlockEntityType<? extends AbstractCurrencyValueBlockEntity> registerCoinBankBlockEntities(BlockEntityType.Builder<? extends AbstractCurrencyValueBlockEntity> builder) {
		// # Register the Block Entity Types
		return Registry.register(
				Registries.BLOCK_ENTITY_TYPE,
				Identifier.of(Villagercoin.MOD_ID, COIN_BANK_STRING),
				builder.build()
		);
	}
	
	public static Block registerCoinBankBlock(String id, Block block) {
		Block registeredBlock = RegistryUtil.registerBlock( id, block, Villagercoin.MOD_ID );
		
		Item item = RegistryUtil.registerItem(id, new BlockItem(registeredBlock, new Item.Settings().component( CURRENCY_COMPONENT, new CurrencyComponent(0))), Villagercoin.MOD_ID);
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );

		return block;
	}
	
	public static Block registerFireproofCoinBankBlock(String id, Block block) {
		Block registeredBlock = RegistryUtil.registerBlock( id, block, Villagercoin.MOD_ID );
		
		Item item = RegistryUtil.registerItem(id, new BlockItem(registeredBlock, new Item.Settings().fireproof().component( CURRENCY_COMPONENT, new CurrencyComponent(0))), Villagercoin.MOD_ID);
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		return block;
	}
	
	public static int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity( pos );
		
		if( blockEntity instanceof AbstractCurrencyValueBlockEntity coinBankBlockEntity ) {
			long value = coinBankBlockEntity.getTotalCurrencyValue();
			if( value > 0 ) {
				if( Long.MAX_VALUE == value ) {
					return 15;
				} else {
					return Math.max(1, (int) Math.ceil(Math.log(value) / Math.log(Long.MAX_VALUE) * 14));
				} // if, else
			} // if
		} // if
		
		return 0;
	}
	
}
