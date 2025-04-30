package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LootableContainerBlockEntity.class)
public abstract class LootableContainerBlockEntityMixin implements Inventory {
	
	@Override
	public int getMaxCountPerStack() {
		return Villagercoin.MAX_STACK_COUNT;
	}
	
}
