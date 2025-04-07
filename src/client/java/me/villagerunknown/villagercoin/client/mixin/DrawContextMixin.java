package me.villagerunknown.villagercoin.client.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
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
	private MatrixStack matrices;
	
	@Inject(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At("HEAD"), cancellable = true)
	private void drawItemInSlot(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
		if( null == countOverride && !stack.isEmpty() && stack.getMaxCount() > 99 && stack.getCount() > 99 ) {
			int digits = Integer.toString( stack.getCount() ).length();
			float scale = 0.8F;
			
			if( digits > 3 ) {
				scale = 0.6F;
			} // if
			
			this.matrices.push();
			
			this.matrices.translate(x * (1 - scale) + (1 - scale) * 16, y * (1 - scale) + (1 - scale) * 16, 0);
			this.matrices.scale(scale, scale, 1.0F);
			
			this.matrices.translate(0.0F, 0.0F, 200.0F);
			DrawContext drawContext = (DrawContext) (Object) this;
			drawContext.drawText(textRenderer, Integer.toString( stack.getCount() ), x + 19 - 2 - textRenderer.getWidth( Integer.toString( stack.getCount() ) ), y + 6 + 3, 16777215, true);
			
			this.matrices.pop();
			
			ci.cancel();
		} // if
	}

}
