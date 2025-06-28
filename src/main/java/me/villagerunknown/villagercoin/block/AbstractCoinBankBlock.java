package me.villagerunknown.villagercoin.block;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.block.entity.AbstractCurrencyValueBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinBankBlocksFeature;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Optional;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

public abstract class AbstractCoinBankBlock extends AbstractCoinCollectionBlock {
	
	public AbstractCoinBankBlock(Settings settings) {
		super(settings);
	}
	
	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if( world.isClient() ) {
			return ItemActionResult.SUCCESS;
		} // if
		
		world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
		
		CurrencyComponent currencyComponent = stack.get(CURRENCY_COMPONENT);
		
		if( null != currencyComponent && stack.isIn( Villagercoin.getItemTagKey( "currency_coin" ) ) ) {
			BlockEntity blockEntity = world.getBlockEntity( pos );
			
			if( blockEntity instanceof AbstractCurrencyValueBlockEntity currencyValueBlockEntity && currencyValueBlockEntity.incrementBlockEntityCurrencyValue( blockEntity, currencyComponent ) ) {
				CoinFeature.playCoinSound( player );
				stack.decrementUnlessCreative(1, player);
				
				player.incrementStat( CoinBankBlocksFeature.COINS_INSERTED_STAT_ID );
				
				return ItemActionResult.CONSUME;
			} // if
		} // if
		
		return ItemActionResult.FAIL;
	}
	
	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		MinecraftServer server = world.getServer();

		if( null != server ) {
			ServerWorld serverWorld = server.getWorld( world.getRegistryKey() );

			if( null != serverWorld ) {
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
