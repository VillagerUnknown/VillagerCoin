package me.villagerunknown.villagercoin.block;

import me.villagerunknown.villagercoin.Villagercoin;
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
				( Villagercoin.CONFIG.enableCoinStacksBreakOnCollision ) ? settings.breakInstantly().noCollision() : settings.breakInstantly()
		);
	}
	
	@Override
	protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if( !world.isClient && Villagercoin.CONFIG.enableCoinStacksBreakOnCollision && entity instanceof PlayerEntity playerEntity && !playerEntity.isInCreativeMode() && !playerEntity.isSneaking() ) {
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
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		MinecraftServer server = world.getServer();
		
		if( null != server ) {
			ServerWorld serverWorld = server.getWorld( world.getRegistryKey() );
			
			if( null != serverWorld && !player.isInCreativeMode() ) {
				DynamicRegistryManager drm =serverWorld.getRegistryManager();
				Registry<Enchantment> reg = drm.get(RegistryKeys.ENCHANTMENT);
				
				Optional<RegistryEntry.Reference<Enchantment>> optional = reg.getEntry( Enchantments.SILK_TOUCH );
				RegistryEntry<Enchantment> silkTouchEnchantmentEntry = optional.orElseThrow();
				
				if( !player.getStackInHand( player.getActiveHand() ).getEnchantments().getEnchantments().contains( silkTouchEnchantmentEntry ) ) {
					BlockEntity blockEntity = world.getBlockEntity( pos );
					
					if( blockEntity instanceof AbstractCurrencyValueBlockEntity currencyValueBlockEntity ) {
						currencyValueBlockEntity.dropTotalValueAsCoins();
					} // if
				} // if
			} // if
		} // if
		
		return super.onBreak(world, pos, state, player);
	}
	
}
