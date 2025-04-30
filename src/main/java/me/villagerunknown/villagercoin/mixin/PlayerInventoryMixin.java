package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements Inventory {
	
	@Override
	public int getMaxCountPerStack() {
		return Villagercoin.MAX_STACK_COUNT;
	}
	
}
