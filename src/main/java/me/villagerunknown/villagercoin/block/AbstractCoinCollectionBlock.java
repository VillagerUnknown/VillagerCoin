package me.villagerunknown.villagercoin.block;

import me.villagerunknown.villagercoin.block.entity.AbstractCurrencyValueBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinBankBlocksFeature;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.util.List;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

public abstract class AbstractCoinCollectionBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
	
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	protected AbstractCoinCollectionBlock(Properties settings) {
		super(
				settings
						.pushReaction(PushReaction.DESTROY)
						.sound( CoinFeature.COIN )
		);
	}
	
	protected RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
	
	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}
	
	@Override
	public boolean isPossibleToRespawnInThis(BlockState state) { return false; }
	
	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		CoinFeature.playCoinSound( player );
		return super.useWithoutItem(state, world, pos, player, hit);
	}
	
	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
		
		if( null != currencyComponent ) {
			BlockEntity blockEntity = world.getBlockEntity( pos );
			
			if( blockEntity instanceof AbstractCurrencyValueBlockEntity currencyValueBlockEntity ) {
				currencyValueBlockEntity.setBlockEntityCurrencyValue(blockEntity, itemStack, currencyComponent);
			} // if
		} // if
		
		super.setPlacedBy(world, pos, state, placer, itemStack);
	}
	
	@Override
	protected void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
		BlockPos blockPos = hit.getBlockPos();
		MinecraftServer server = world.getServer();
		if( null != server ) {
			ServerLevel serverWorld = server.getLevel(world.dimension());
			if( null != serverWorld ) {
				if (!world.isClientSide() && projectile.mayInteract(serverWorld, blockPos) && projectile.mayBreak(serverWorld)) {
					world.destroyBlock(blockPos, true, projectile);
				}
			}
		}
	}
	
	@Override
	protected void spawnAfterBreak(BlockState state, ServerLevel world, BlockPos pos, ItemStack tool, boolean dropExperience) {
		BlockEntity blockEntity = world.getBlockEntity( pos );
		
		if( blockEntity instanceof AbstractCurrencyValueBlockEntity currencyValueBlockEntity ) {
			currencyValueBlockEntity.dropTotalValueAsCoins();
		} // if
		
		super.spawnAfterBreak(state, world, pos, tool, dropExperience);
	}
	
	protected int getComparatorOutput(BlockState state, Level world, BlockPos pos) {
		return CoinBankBlocksFeature.getComparatorOutput(state, world, pos);
	}
	
	@Override
	protected boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}
	
	protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
		if (state.getValue(WATERLOGGED)) {
			tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		
		return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
		
		return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));
	}
	
	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}
	
}
