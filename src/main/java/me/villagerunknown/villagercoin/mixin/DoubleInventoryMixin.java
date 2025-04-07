package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DoubleInventory.class)
public abstract class DoubleInventoryMixin implements Inventory {
	
	@Override
	public int getMaxCountPerStack() {
		return coinFeature.MAX_COUNT;
	}
	
}
