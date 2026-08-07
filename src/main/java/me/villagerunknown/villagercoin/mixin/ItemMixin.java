package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.class)
public abstract class ItemMixin {
	
	@Shadow
	public static final int ABSOLUTE_MAX_STACK_SIZE = Villagercoin.MAX_STACK_COUNT;
	
}
