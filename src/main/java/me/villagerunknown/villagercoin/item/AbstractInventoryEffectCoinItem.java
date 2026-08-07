package me.villagerunknown.villagercoin.item;

import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.feature.InventoryEffectCoinFeature;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbstractInventoryEffectCoinItem extends AbstractCollectableCoinItem {
	
	public AbstractInventoryEffectCoinItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
		if( MathUtil.hasChance( Villagercoin.CONFIG.inventoryEffectChancePerTick) ) {
			SuspiciousStewEffects effectsComponent = stack.get(DataComponents.SUSPICIOUS_STEW_EFFECTS);
			if (null != effectsComponent) {
				List<SuspiciousStewEffects.Entry> effects = effectsComponent.effects();
				if (!effects.isEmpty()) {
					for (SuspiciousStewEffects.Entry effect : effects) {
						if (InventoryEffectCoinFeature.canApplyEffect((LivingEntity) entity, effect)) {
							InventoryEffectCoinFeature.applyStatusEffect((LivingEntity) entity, effect, stack.getItem());
						} // if
					} // for
				} // if
			} // if
		} // if
		
		super.inventoryTick(stack, world, entity, slot);
	}
	
}
