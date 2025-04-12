package me.villagerunknown.villagercoin.item;

import me.villagerunknown.platform.util.EntityUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import net.minecraft.world.World;

public abstract class AbstractCoinItem extends Item {
	
	public static SoundEvent SOUND = SoundEvents.BLOCK_CHAIN_STEP;
	
	public AbstractCoinItem(Settings settings) {
		super(settings);
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
