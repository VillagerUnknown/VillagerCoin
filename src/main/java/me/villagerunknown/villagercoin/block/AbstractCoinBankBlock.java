package me.villagerunknown.villagercoin.block;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.block.entity.AbstractCurrencyValueBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinBankBlocksFeature;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import java.util.Optional;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

public abstract class AbstractCoinBankBlock extends AbstractCoinCollectionBlock {
	
	public AbstractCoinBankBlock(Properties settings) {
		super(settings);
	}
	
	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if( world.isClientSide() ) {
			return InteractionResult.SUCCESS;
		} // if
		
		world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
		
		CurrencyComponent currencyComponent = stack.get(CURRENCY_COMPONENT);
		
		if( null != currencyComponent && stack.is( Villagercoin.getItemTagKey( "currency_coin" ) ) ) {
			BlockEntity blockEntity = world.getBlockEntity( pos );
			
			if( blockEntity instanceof AbstractCurrencyValueBlockEntity currencyValueBlockEntity && currencyValueBlockEntity.incrementBlockEntityCurrencyValue( blockEntity, currencyComponent ) ) {
				CoinFeature.playCoinSound( player );
				stack.consume(1, player);
				
				player.awardStat( CoinBankBlocksFeature.COINS_INSERTED_STAT_ID );
				
				return InteractionResult.CONSUME;
			} // if
		} // if
		
		return InteractionResult.FAIL;
	}
	
	@Override
	public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		MinecraftServer server = world.getServer();

		if( null != server ) {
			ServerLevel serverWorld = server.getLevel( world.dimension() );

			if( null != serverWorld ) {
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
