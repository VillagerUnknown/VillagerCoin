package me.villagerunknown.villagercoin.item;

import me.villagerunknown.platform.util.EntityUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class AbstractInventoryEffectCoinItem extends AbstractCollectableCoinItem {
	
	public AbstractInventoryEffectCoinItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		SuspiciousStewEffectsComponent effectsComponent = stack.get(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS );
		if( null != effectsComponent ) {
			List<SuspiciousStewEffectsComponent.StewEffect> effects = effectsComponent.effects();
			
			if( !effects.isEmpty() ) {
				for( SuspiciousStewEffectsComponent.StewEffect effect : effects ) {
					EntityUtil.addStatusEffect( (LivingEntity) entity, effect.effect(), effect.duration(), 0, true, true, true );
				} // for
			} // if
		} // if
		
		super.inventoryTick(stack, world, entity, slot, selected);
	}
	
}
