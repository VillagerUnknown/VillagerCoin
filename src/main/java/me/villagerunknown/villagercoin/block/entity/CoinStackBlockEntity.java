package me.villagerunknown.villagercoin.block.entity;

import me.villagerunknown.villagercoin.feature.CoinStackBlockEntityFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CoinStackBlockEntity extends AbstractCurrencyValueBlockEntity {

	public CoinStackBlockEntity(BlockEntityType<CoinStackBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	public CoinStackBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(CoinStackBlockEntityFeature.COIN_STACK_ENTITY_TYPE, blockPos, blockState);
	}
	
}
