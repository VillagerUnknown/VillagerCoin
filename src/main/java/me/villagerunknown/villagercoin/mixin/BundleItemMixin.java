package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BundleItem.class)
public class BundleItemMixin {
	
	@Final
	@Shadow
	private final static int field_51352 = coinFeature.MAX_COUNT;
	
	@Inject( method = "appendTooltip", at = @At("HEAD"), cancellable = true)
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
		BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)stack.get(DataComponentTypes.BUNDLE_CONTENTS);
		if (bundleContentsComponent != null) {
			int i = MathHelper.multiplyFraction(bundleContentsComponent.getOccupancy(), coinFeature.MAX_COUNT);
			tooltip.add(Text.translatable("item.minecraft.bundle.fullness", new Object[]{i, coinFeature.MAX_COUNT}).formatted(Formatting.GRAY));
		}
	}
	
}
