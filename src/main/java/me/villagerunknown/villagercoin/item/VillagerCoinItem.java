package me.villagerunknown.villagercoin.item;

import me.villagerunknown.platform.util.EntityUtil;
import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.platform.util.MessageUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.type.CoinComponent;
import me.villagerunknown.villagercoin.data.type.CurrencyComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

import static me.villagerunknown.villagercoin.Villagercoin.*;

public class VillagerCoinItem extends Item {
	
	public static int COOLDOWN_TIME = 100;
	
	public static SoundEvent SOUND = SoundEvents.BLOCK_CHAIN_STEP;
	
	public VillagerCoinItem(Settings settings) {
		super(
				settings
						.maxCount( Villagercoin.MAX_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( Rarity.COMMON, 0, 10, 0.5F, 0.5F ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( 1 ) )
		);
	}
	
	public VillagerCoinItem(Settings settings, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, float flipChance) {
		super(
				settings
						.maxCount( Villagercoin.MAX_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( rarity, dropMinimum, dropMaximum, dropChance, flipChance ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( value ) )
		);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand( hand );
		
		if( !world.isClient() && !user.isSpectator() && Villagercoin.CONFIG.enableCoinFlipping ) {
			if( null != itemStack && 1 == itemStack.getCount() ) {
				playCoinSound( user );
				
				CoinComponent coinComponent = itemStack.get( COIN_COMPONENT );
				
				float flipChance = 0.5F;
				
				if( null != coinComponent ) {
					flipChance = coinComponent.flipChance();
				} // if
				
				boolean flip = MathUtil.hasChance( flipChance );
				Text result;
				SoundEvent sound;
				
				if( flip ) {
					result = Text.translatable( "item." + MOD_ID + ".villager_coin.flip.heads", user.getNameForScoreboard() );
					
					sound = SoundEvents.ENTITY_VILLAGER_YES;
				} else {
					result = Text.translatable( "item." + MOD_ID + ".villager_coin.flip.tails", user.getNameForScoreboard() );
					
					sound = SoundEvents.ENTITY_VILLAGER_NO;
				} // if, else
				
				EntityUtil.playSound( user, sound, SoundCategory.PLAYERS, 0.33F, 1F, false );
				
				MessageUtil.sendChatMessage( user, result.getString());
				
				user.getItemCooldownManager().set( this, COOLDOWN_TIME );
				user.incrementStat(Stats.USED.getOrCreateStat(this));
				
				List<Entity> nearbyEntities = world.getOtherEntities(user, user.getBoundingBox().expand(16));
				
				if( !nearbyEntities.isEmpty() ) {
					for (Entity nearbyEntity : nearbyEntities) {
						if (nearbyEntity instanceof PlayerEntity nearbyPlayer) {
							MessageUtil.sendChatMessage(nearbyPlayer, result.getString());
						} // if
					} // for
				} // if
				
				return TypedActionResult.success(itemStack);
			} // if
		} // if
		
		return TypedActionResult.pass(itemStack);
	}
	
	@Override
	public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
		if( !player.getWorld().isClient() ) {
			playCoinSound(player);
		} // if
		
		return super.onStackClicked(stack, slot, clickType, player);
	}
	
	@Override
	public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
		if( !player.getWorld().isClient() ) {
			playCoinSound(player);
		} // if
		
		super.onCraftByPlayer(stack, world, player);
	}
	
	public static void playCoinSound( PlayerEntity player ) {
		if( player.getWorld().isClient() ) {
			return;
		} // if
		
		EntityUtil.playSound(player, SOUND, SoundCategory.PLAYERS, 0.5F, 1F, false);
	}
	
}
