package me.villagerunknown.villagercoin.client.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public abstract class DrawContextMixin {
	
	@Shadow
	@Final
	private Matrix3x2fStack pose;
	
	@Inject(method = "itemCount", at = @At("HEAD"), cancellable = true)
	private void drawStackCount(Font textRenderer, ItemStack stack, int x, int y, @Nullable String stackCountText, CallbackInfo ci) {
		if( !stack.isEmpty() && stack.getMaxStackSize() > 99 && stack.getCount() > 99 ) {
			stackCountText = CoinFeature.humanReadableNumber( stack.getCount(), false );
			float scale = CoinFeature.humanReadableNumberScale( stackCountText.length() );
			
			this.pose.popMatrix();
			this.pose.pushMatrix();
			
			this.pose.translate(x * (1 - scale) + (1 - scale) * 16, y * (1 - scale) + (1 - scale) * 16);
			this.pose.scale(scale, scale);

			GuiGraphicsExtractor drawContext = (GuiGraphicsExtractor) (Object) this;
			drawContext.text(textRenderer, stackCountText, x + 19 - 2 - textRenderer.width( stackCountText ), y + 6 + 3, -1, true);
			
			ci.cancel();
		} // if
	}

}
