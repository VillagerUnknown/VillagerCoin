package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Inventory.class)
public abstract class PlayerInventoryMixin implements Container {
	
	@Override
	public int getMaxStackSize() {
		return Villagercoin.MAX_STACK_COUNT;
	}
	
}
