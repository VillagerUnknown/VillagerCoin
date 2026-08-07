package me.villagerunknown.villagercoin.block;

import com.mojang.serialization.MapCodec;
import me.villagerunknown.villagercoin.block.entity.CoinStackBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class CoinStackBlock extends AbstractCoinStackBlock {
	
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	
	public static final MapCodec<CoinStackBlock> CODEC = simpleCodec(CoinStackBlock::new);

	public CoinStackBlock(Properties settings) {
		super(
				settings
						.noOcclusion()
						.instabreak()
		);
	}
	
	@Override
	protected MapCodec<CoinStackBlock> codec() {
		return CODEC;
	}
	
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CoinStackBlockEntity(pos, state);
	}
	
	@Override
	protected BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}
	
	@Override
	protected BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
	
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
		return (BlockState)this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection()).setValue(WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));
	}
	
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(new Property[]{FACING,WATERLOGGED});
	}
	
}
