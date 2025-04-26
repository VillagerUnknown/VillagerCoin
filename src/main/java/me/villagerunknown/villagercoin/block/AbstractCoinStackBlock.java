package me.villagerunknown.villagercoin.block;

import me.villagerunknown.villagercoin.block.entity.AbstractCurrencyValueBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

public abstract class AbstractCoinStackBlock extends AbstractCoinCollectionBlock {
	
	public AbstractCoinStackBlock(Settings settings) {
		super(
				settings
						.noCollision()
		);
	}
	
	@Override
	protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if( !world.isClient && entity instanceof PlayerEntity playerEntity ) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			
			if (blockEntity instanceof AbstractCurrencyValueBlockEntity currencyValueBlockEntity) {
				CurrencyComponent currencyComponent = currencyValueBlockEntity.getComponents().get(CURRENCY_COMPONENT);
				
				if (null != currencyComponent) {
					world.breakBlock(pos, true);
					CoinFeature.playCoinSound(playerEntity);
				} // if
			} // if
		} // if
		
		super.onEntityCollision(state, world, pos, entity);
	}
	
	
	
	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (!world.isClient && !player.isInCreativeMode()) {
			world.breakBlock(pos, true);
			return ActionResult.SUCCESS_NO_ITEM_USED;
		} // if
		return super.onUse(state, world, pos, player, hit);
	}
	
	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if( !world.isClient && !player.isInCreativeMode() ) {
			BlockEntity blockEntity = world.getBlockEntity( pos );
			
			if( blockEntity instanceof AbstractCurrencyValueBlockEntity currencyValueBlockEntity ) {
				currencyValueBlockEntity.dropTotalValueAsCoins();
			} // if
		} // if
		
		return super.onBreak(world, pos, state, player);
	}
	
}
