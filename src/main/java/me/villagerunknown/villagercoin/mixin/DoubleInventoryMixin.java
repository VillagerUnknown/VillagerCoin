package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CompoundContainer.class)
public abstract class DoubleInventoryMixin implements Container {
	
	@Override
	public int getMaxStackSize() {
		return Villagercoin.MAX_STACK_COUNT;
	}
	
}
