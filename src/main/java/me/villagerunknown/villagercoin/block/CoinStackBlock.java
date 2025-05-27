package me.villagerunknown.villagercoin.block;

import com.mojang.serialization.MapCodec;
import me.villagerunknown.villagercoin.block.entity.CoinStackBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;

public class CoinStackBlock extends AbstractCoinStackBlock {
	
	public static final DirectionProperty FACING;
	
	public static final MapCodec<CoinStackBlock> CODEC = createCodec(CoinStackBlock::new);

	public CoinStackBlock(Settings settings) {
		super(
				settings
						.nonOpaque()
						.breakInstantly()
		);
	}
	
	@Override
	protected MapCodec<CoinStackBlock> getCodec() {
		return CODEC;
	}
	
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CoinStackBlockEntity(pos, state);
	}
	
	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}
	
	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}
	
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()).with(WATERLOGGED, Boolean.valueOf(fluidState.getFluid() == Fluids.WATER));
	}
	
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(new Property[]{FACING,WATERLOGGED});
	}
	
	static {
		FACING = Properties.HORIZONTAL_FACING;
	}
	
}
