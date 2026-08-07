package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Slot.class)
public abstract class SlotMixin implements Container {
	
	@Override
	public int getMaxStackSize() {
		return Villagercoin.MAX_STACK_COUNT;
	}
	
}
