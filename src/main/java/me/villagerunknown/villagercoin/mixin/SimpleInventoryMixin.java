package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SimpleContainer.class)
public abstract class SimpleInventoryMixin implements Container {
	
	@Override
	public int getMaxStackSize() {
		return Villagercoin.MAX_STACK_COUNT;
	}
	
}
