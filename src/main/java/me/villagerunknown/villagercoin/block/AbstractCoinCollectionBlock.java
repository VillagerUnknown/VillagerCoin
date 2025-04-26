package me.villagerunknown.villagercoin.block;

import me.villagerunknown.villagercoin.block.entity.AbstractCurrencyValueBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinBankBlocksFeature;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

public abstract class AbstractCoinCollectionBlock extends BlockWithEntity {
	
	protected AbstractCoinCollectionBlock(Settings settings) {
		super(
				settings
						.breakInstantly()
						.pistonBehavior(PistonBehavior.DESTROY)
						.sounds( BlockSoundGroup.CHAIN )
		);
	}
	
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}
	
	@Override
	public boolean canMobSpawnInside(BlockState state) { return false; }
	
	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		CoinFeature.playCoinSound( player );
		return super.onUse(state, world, pos, player, hit);
	}
	
	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
		
		if( null != currencyComponent ) {
			BlockEntity blockEntity = world.getBlockEntity( pos );
			
			if( blockEntity instanceof AbstractCurrencyValueBlockEntity currencyValueBlockEntity ) {
				currencyValueBlockEntity.setBlockEntityCurrencyValue(blockEntity, itemStack, currencyComponent);
			} // if
		} // if
		
		super.onPlaced(world, pos, state, placer, itemStack);
	}
	
	@Override
	protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
		BlockPos blockPos = hit.getBlockPos();
		if (!world.isClient && projectile.canModifyAt(world, blockPos) && projectile.canBreakBlocks(world)) {
			world.breakBlock(blockPos, true, projectile);
		}
	}
	
	@Override
	protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
		BlockEntity blockEntity = world.getBlockEntity( pos );
		
		if( blockEntity instanceof AbstractCurrencyValueBlockEntity currencyValueBlockEntity ) {
			currencyValueBlockEntity.dropTotalValueAsCoins();
		} // if
		
		super.onStacksDropped(state, world, pos, tool, dropExperience);
	}
	
	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
		CurrencyComponent currencyComponent = stack.get( CURRENCY_COMPONENT );
		
		if( null != currencyComponent ) {
			tooltip.add(
					Text.translatable(
							"block.villagerunknown-villagercoin.coin_bank.tooltip",
							currencyComponent.value(),
							CoinItems.COPPER_COIN.getName().getString()
					).formatted(Formatting.ITALIC, Formatting.GRAY)
			);
		} // if
		
		super.appendTooltip(stack, context, tooltip, options);
	}
	
	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return CoinBankBlocksFeature.getComparatorOutput(state, world, pos);
	}
	
	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}
	
}
