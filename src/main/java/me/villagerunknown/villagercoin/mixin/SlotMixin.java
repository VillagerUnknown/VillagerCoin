package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Slot.class)
public abstract class SlotMixin implements Inventory {
	
	@Override
	public int getMaxCountPerStack() {
		return coinFeature.MAX_COUNT;
	}
	
}
