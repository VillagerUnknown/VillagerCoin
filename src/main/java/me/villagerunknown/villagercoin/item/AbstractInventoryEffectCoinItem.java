package me.villagerunknown.villagercoin.item;

import me.villagerunknown.platform.util.EntityUtil;
import me.villagerunknown.villagercoin.feature.InventoryEffectCoinFeature;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
					if( InventoryEffectCoinFeature.canApplyEffect( (LivingEntity) entity, effect ) ) {
						InventoryEffectCoinFeature.applyStatusEffect( (LivingEntity) entity, effect, stack.getItem() );
					} // if
				} // for
			} // if
		} // if
		
		super.inventoryTick(stack, world, entity, slot, selected);
	}
	
}
