package me.villagerunknown.villagercoin.block.entity;

import me.villagerunknown.villagercoin.feature.CoinBankBlockEntityFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CoinBankBlockEntity extends AbstractCurrencyValueBlockEntity {

	public CoinBankBlockEntity(BlockEntityType<CoinBankBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	public CoinBankBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(CoinBankBlockEntityFeature.COIN_BANK_ENTITY_TYPE, blockPos, blockState);
	}
	
}
