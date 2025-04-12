package me.villagerunknown.villagercoin.item;

import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.UseAction;

public abstract class AbstractEdibleCoinItem extends AbstractCoinItem {
	
	public static SoundEvent SOUND = SoundEvents.ENTITY_GENERIC_EAT;
	
	public AbstractEdibleCoinItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.EAT;
	}
	
	@Override
	public SoundEvent getEatSound() {
		return SOUND;
	}
	
}
