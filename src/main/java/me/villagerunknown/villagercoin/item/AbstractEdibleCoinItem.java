package me.villagerunknown.villagercoin.item;

import me.villagerunknown.platform.util.MathUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.List;

public abstract class AbstractEdibleCoinItem extends AbstractCoinItem {
	
	public static SoundEvent SOUND = SoundEvents.ENTITY_GENERIC_EAT.value();
	
	public AbstractEdibleCoinItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		SuspiciousStewEffectsComponent stewEffectsComponent = stack.get(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);
		
		if (null != stewEffectsComponent) {
			List<SuspiciousStewEffectsComponent.StewEffect> stewEffects = stewEffectsComponent.effects();
			
			if (!stewEffects.isEmpty() && stewEffects.size() > 1) {
				SuspiciousStewEffectsComponent.StewEffect stewEffect = stewEffects.get((int) MathUtil.getRandomWithinRange(0, stewEffects.size() - 1));
				
				stack.remove(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);
				stack.set(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffectsComponent(List.of(stewEffect)));
			} // if
			
		} // if
		
		if( world.isClient() ) {
			stack.remove(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);
		} // if
		
		return super.finishUsing(stack, world, user);
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.EAT;
	}
	
	public SoundEvent getEatSound() {
		return SOUND;
	}
	
}
