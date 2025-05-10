package me.villagerunknown.villagercoin.item;

import me.villagerunknown.platform.util.MathUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

public abstract class AbstractEdibleCoinItem extends AbstractCoinItem {
	
	public static SoundEvent SOUND = SoundEvents.ENTITY_GENERIC_EAT;
	
	public AbstractEdibleCoinItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if( !world.isClient() ) {
			SuspiciousStewEffectsComponent stewEffectsComponent = stack.get(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);
			
			if (null != stewEffectsComponent) {
				List<SuspiciousStewEffectsComponent.StewEffect> stewEffects = stewEffectsComponent.effects();
				
				SuspiciousStewEffectsComponent.StewEffect stewEffect = stewEffects.get((int) MathUtil.getRandomWithinRange(0, stewEffects.size()));
				
				user.addStatusEffect(stewEffect.createStatusEffectInstance());
			} // if
		} // if
		
		return super.finishUsing(stack, world, user);
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
