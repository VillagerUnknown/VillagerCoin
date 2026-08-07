package me.villagerunknown.villagercoin.block;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.block.entity.AbstractCurrencyValueBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Optional;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

public abstract class AbstractCoinStackBlock extends AbstractCoinCollectionBlock {
	
	public AbstractCoinStackBlock(Properties settings) {
		super(
				( Villagercoin.CONFIG.enableCoinStacksBreakOnCollision ) ? settings.noCollision() : settings
		);
	}
	
	@Override
	public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
		if( !world.isClientSide() && Villagercoin.CONFIG.enableCoinStacksBreakOnCollision && entity instanceof Player playerEntity && !playerEntity.hasInfiniteMaterials() && !playerEntity.isShiftKeyDown() ) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			
			if (blockEntity instanceof AbstractCurrencyValueBlockEntity currencyValueBlockEntity) {
				CurrencyComponent currencyComponent = currencyValueBlockEntity.components().get(CURRENCY_COMPONENT);
				
				if (null != currencyComponent) {
					world.destroyBlock(pos, true);
					CoinFeature.playCoinSound(playerEntity);
				} // if
			} // if
		} // if
		
		super.stepOn(world, pos, state, entity);
	}
	
	@Override
	public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		MinecraftServer server = world.getServer();
		
		if( null != server ) {
			ServerLevel serverWorld = server.getLevel( world.dimension() );
			
			if( null != serverWorld && !player.hasInfiniteMaterials() ) {
				RegistryAccess drm =serverWorld.registryAccess();
				Registry<Enchantment> reg = drm.lookupOrThrow(Registries.ENCHANTMENT);
				
				Enchantment silkTouchEnchantmentEntry = reg.getValue( Enchantments.SILK_TOUCH );
				Holder<Enchantment> regEntry = reg.wrapAsHolder( silkTouchEnchantmentEntry );
				
				if( !player.getItemInHand( player.getUsedItemHand() ).getEnchantments().keySet().contains( regEntry ) ) {
					BlockEntity blockEntity = world.getBlockEntity( pos );
					
					if( blockEntity instanceof AbstractCurrencyValueBlockEntity currencyValueBlockEntity ) {
						currencyValueBlockEntity.dropTotalValueAsCoins();
					} // if
				} // if
			} // if
		} // if
		
		return super.playerWillDestroy(world, pos, state, player);
	}
	
}
