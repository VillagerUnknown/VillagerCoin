package me.villagerunknown.villagercoin.mixin;

import net.minecraft.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HopperBlockEntity.class)
public interface HopperBlockEntityAccessor {
	
	@Invoker("isDisabled")
	boolean invokeIsDisabled();
	
	@Invoker("setTransferCooldown")
	void setTransferCooldown(int transferCooldown);

}
