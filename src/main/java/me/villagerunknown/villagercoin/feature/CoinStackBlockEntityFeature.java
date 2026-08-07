package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.block.entity.CoinStackBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CoinStackBlockEntityFeature extends AbstractBlockEntityFeature {
	
	public static final String COIN_STACK_ENTITY_STRING = "coin_stack";
	
	public static BlockEntityType<CoinStackBlockEntity> COIN_STACK_ENTITY_TYPE = register();
	
	public static void execute() {}
	
	public static BlockEntityType<CoinStackBlockEntity> register() {
		Block[] blocks = CoinStackBlocksFeature.blocks.getBlocksArray();
		
		if( blocks.length > 0 ) {
			FabricBlockEntityTypeBuilder<CoinStackBlockEntity> builder = FabricBlockEntityTypeBuilder.create(
					CoinStackBlockEntity::new,
					blocks
			);
			
			return (BlockEntityType<CoinStackBlockEntity>) registerBlockEntities( builder, COIN_STACK_ENTITY_STRING );
		} // if
		
		return null;
	}
	
}
