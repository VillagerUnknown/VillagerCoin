package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.inventory.Inventory;
import net.minecraft.village.MerchantInventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MerchantInventory.class)
public abstract class MerchantInventoryMixin implements Inventory {
	
	@Override
	public int getMaxCountPerStack() {
		return Villagercoin.MAX_STACK_COUNT;
	}
	
}
