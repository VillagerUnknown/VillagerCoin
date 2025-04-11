package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CraftingInventory.class)
public abstract class CraftingInventoryMixin implements Inventory {
	
	@Override
	public int getMaxCountPerStack() {
		return Villagercoin.MAX_COUNT;
	}
	
}
