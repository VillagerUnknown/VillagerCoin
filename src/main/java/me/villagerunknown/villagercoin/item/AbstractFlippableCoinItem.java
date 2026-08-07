package me.villagerunknown.villagercoin.item;

import me.villagerunknown.platform.util.EntityUtil;
import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.platform.util.MessageUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CoinComponent;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import java.util.List;

import static me.villagerunknown.villagercoin.component.Components.COIN_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public abstract class AbstractFlippableCoinItem extends AbstractCoinItem {
	
	public static int COOLDOWN_TIME = 100;
	
	public AbstractFlippableCoinItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public InteractionResult use(Level world, Player user, InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand( hand );
		
		if( !world.isClientSide() && !user.isSpectator() && Villagercoin.CONFIG.enableCoinFlipping ) {
			if( null != itemStack && 1 == itemStack.getCount() ) {
				CoinFeature.playCoinFlipSound( user );
				
				world.gameEvent(user, GameEvent.ENTITY_ACTION, user.blockPosition());
				
				CoinComponent coinComponent = itemStack.get( COIN_COMPONENT );
				
				float flipChance = 0.5F;
				
				if( null != coinComponent ) {
					flipChance = coinComponent.flipChance();
				} // if
				
				boolean flip = MathUtil.hasChance( flipChance );
				Component result;
				SoundEvent sound;
				
				if( flip ) {
					result = Component.translatable( "item." + MOD_ID + ".villager_coin.flip.heads", user.getScoreboardName() );
					
					sound = SoundEvents.VILLAGER_YES;
				} else {
					result = Component.translatable( "item." + MOD_ID + ".villager_coin.flip.tails", user.getScoreboardName() );
					
					sound = SoundEvents.VILLAGER_NO;
				} // if, else
				
				EntityUtil.playSound( user, sound, SoundSource.PLAYERS, 0.33F, 1F, false );
				
				MessageUtil.sendChatMessage( user, result.getString());
				
				user.getCooldowns().addCooldown( itemStack, COOLDOWN_TIME );
				user.awardStat(Stats.ITEM_USED.get(this));
				
				List<Entity> nearbyEntities = world.getEntities(user, user.getBoundingBox().inflate(16));
				
				if( !nearbyEntities.isEmpty() ) {
					for (Entity nearbyEntity : nearbyEntities) {
						if (nearbyEntity instanceof Player nearbyPlayer) {
							MessageUtil.sendChatMessage(nearbyPlayer, result.getString());
						} // if
					} // for
				} // if
				
				return InteractionResult.SUCCESS;
			} // if
		} // if
		
		return InteractionResult.PASS;
	}
	
}
