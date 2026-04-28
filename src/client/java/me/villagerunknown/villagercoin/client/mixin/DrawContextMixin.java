package me.villagerunknown.villagercoin.client.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {
	
	@Shadow
	@Final
	private Matrix3x2fStack matrices;
	
	@Inject(method = "drawStackCount", at = @At("HEAD"), cancellable = true)
	private void drawStackCount(TextRenderer textRenderer, ItemStack stack, int x, int y, @Nullable String stackCountText, CallbackInfo ci) {
		if( !stack.isEmpty() && stack.getMaxCount() > 99 && stack.getCount() > 99 ) {
			stackCountText = CoinFeature.humanReadableNumber( stack.getCount(), false );
			float scale = CoinFeature.humanReadableNumberScale( stackCountText.length() );
			
			this.matrices.popMatrix();
			this.matrices.pushMatrix();
			
			this.matrices.translate(x * (1 - scale) + (1 - scale) * 16, y * (1 - scale) + (1 - scale) * 16);
			this.matrices.scale(scale, scale);

			DrawContext drawContext = (DrawContext) (Object) this;
			drawContext.drawText(textRenderer, stackCountText, x + 19 - 2 - textRenderer.getWidth( stackCountText ), y + 6 + 3, -1, true);

			ci.cancel();
		} // if
	}

}
