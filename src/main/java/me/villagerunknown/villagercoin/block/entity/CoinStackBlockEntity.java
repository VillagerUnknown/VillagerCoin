package me.villagerunknown.villagercoin.block.entity;

import me.villagerunknown.villagercoin.feature.CoinStackBlockEntityFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class CoinStackBlockEntity extends AbstractCurrencyValueBlockEntity {

	public CoinStackBlockEntity(BlockEntityType<CoinStackBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	public CoinStackBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(CoinStackBlockEntityFeature.COIN_STACK_ENTITY_TYPE, blockPos, blockState);
	}
	
}
