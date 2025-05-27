package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.block.entity.CoinBankBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;

public class CoinBankBlockEntityFeature extends AbstractBlockEntityFeature {
	
	public static final String COIN_BANK_ENTITY_STRING = "coin_bank";
	
	public static BlockEntityType<CoinBankBlockEntity> COIN_BANK_ENTITY_TYPE = register();
	
	public static void execute() {}
	
	public static BlockEntityType<CoinBankBlockEntity> register() {
		Block[] blocks = CoinBankBlocksFeature.blocks.getBlocksArray();
		
		if( blocks.length > 0 ) {
			BlockEntityType.Builder<CoinBankBlockEntity> builder = BlockEntityType.Builder.create(
					CoinBankBlockEntity::new,
					blocks
			);
			
			return (BlockEntityType<CoinBankBlockEntity>) registerBlockEntities( builder, COIN_BANK_ENTITY_STRING );
		} // if
		
		return null;
	}
	
}
