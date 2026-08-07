package me.villagerunknown.villagercoin.item;

import me.villagerunknown.platform.util.MathUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import java.util.List;

public abstract class AbstractEdibleCoinItem extends AbstractCoinItem {
	
	public static SoundEvent SOUND = SoundEvents.GENERIC_EAT.value();
	
	public AbstractEdibleCoinItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
		SuspiciousStewEffects stewEffectsComponent = stack.get(DataComponents.SUSPICIOUS_STEW_EFFECTS);
		
		if (null != stewEffectsComponent) {
			List<SuspiciousStewEffects.Entry> stewEffects = stewEffectsComponent.effects();
			
			if (!stewEffects.isEmpty() && stewEffects.size() > 1) {
				SuspiciousStewEffects.Entry stewEffect = stewEffects.get((int) MathUtil.getRandomWithinRange(0, stewEffects.size() - 1));
				
				stack.remove(DataComponents.SUSPICIOUS_STEW_EFFECTS);
				stack.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffects(List.of(stewEffect)));
			} // if
			
		} // if
		
		if( world.isClientSide() ) {
			stack.remove(DataComponents.SUSPICIOUS_STEW_EFFECTS);
		} // if
		
		return super.finishUsingItem(stack, world, user);
	}
	
	@Override
	public ItemUseAnimation getUseAnimation(ItemStack stack) {
		return ItemUseAnimation.EAT;
	}
	
	public SoundEvent getEatSound() {
		return SOUND;
	}
	
}
