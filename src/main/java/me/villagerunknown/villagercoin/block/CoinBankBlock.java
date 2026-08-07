package me.villagerunknown.villagercoin.block;

import com.mojang.serialization.MapCodec;
import me.villagerunknown.villagercoin.block.entity.CoinBankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class CoinBankBlock extends AbstractCoinBankBlock {
	
	public static final MapCodec<CoinBankBlock> CODEC = simpleCodec(CoinBankBlock::new);

	public CoinBankBlock(Properties settings) {
		super(settings);
	}
	
	@Override
	protected MapCodec<CoinBankBlock> codec() {
		return CODEC;
	}
	
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CoinBankBlockEntity(pos, state);
	}
	
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
		return (BlockState)this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));
	}
	
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(new Property[]{WATERLOGGED});
	}
	
}
