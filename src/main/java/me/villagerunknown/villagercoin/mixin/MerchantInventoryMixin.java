package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.inventory.Inventory;
import net.minecraft.village.MerchantInventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MerchantInventory.class)
public abstract class MerchantInventoryMixin implements Inventory {
	
	@Override
	public int getMaxCountPerStack() {
		return coinFeature.MAX_COUNT;
	}
	
}
