package me.villagerunknown.villagercoin.mixin;

import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HopperBlockEntity.class)
public interface HopperBlockEntityAccessor {
	
	@Invoker("isOnCustomCooldown")
	boolean invokeIsDisabled();
	
	@Invoker("setCooldown")
	void invokeSetTransferCooldown(int transferCooldown);

}
