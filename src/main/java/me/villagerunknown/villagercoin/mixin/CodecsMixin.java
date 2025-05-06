package me.villagerunknown.villagercoin.mixin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(Codecs.class)
public class CodecsMixin {
	
	@Inject(method = "rangedInt(IILjava/util/function/Function;)Lcom/mojang/serialization/Codec;", at = @At("HEAD"), cancellable = true)
	private static void rangedInt(int min, int max, Function<Integer, String> messageFactory, CallbackInfoReturnable<Codec<Integer>> cir) {
		if( max >= 99 ) {
			max = Integer.MAX_VALUE;
		} // if
		
		int finalMax = max;
		cir.setReturnValue( Codec.INT.validate((value) -> value.compareTo(min) >= 0 && value.compareTo(finalMax) <= 0 ? DataResult.success(value) : DataResult.error(() -> (String)messageFactory.apply(value))) );
	}
	
}
