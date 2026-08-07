package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.MerchantContainer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MerchantContainer.class)
public abstract class MerchantInventoryMixin implements Container {
	
	@Override
	public int getMaxStackSize() {
		return Villagercoin.MAX_STACK_COUNT;
	}
	
}
