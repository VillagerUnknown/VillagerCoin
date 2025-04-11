package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.class)
public abstract class ItemMixin {
	
	@Shadow
	public static final int MAX_MAX_COUNT = Villagercoin.MAX_COUNT;
	
}
