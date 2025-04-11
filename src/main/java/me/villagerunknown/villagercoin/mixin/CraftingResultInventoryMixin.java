package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CraftingResultInventory.class)
public abstract class CraftingResultInventoryMixin implements Inventory {
	
	@Override
	public int getMaxCountPerStack() {
		return Villagercoin.MAX_COUNT;
	}
	
}
