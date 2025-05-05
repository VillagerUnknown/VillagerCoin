package me.villagerunknown.villagercoin.mixin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.item.ItemStack.ITEM_CODEC;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	
	@Mutable
	@Final
	@Shadow
	public static final Codec<ItemStack> CODEC;
	
	@Inject(method = "capCount", at = @At("HEAD"), cancellable = true)
	private void capCount(int maxCount, CallbackInfo ci) {
		ItemStack stack = (ItemStack) (Object) this;
		
		if (!stack.isEmpty() && stack.getCount() > maxCount) {
			stack.setCount( Villagercoin.MAX_STACK_COUNT );
		}
		
		ci.cancel();
	}
	
	static{
		CODEC = Codec.lazyInitialized(() -> {
			return RecordCodecBuilder.create((instance) -> {
				return instance.group(ITEM_CODEC.fieldOf("id").forGetter(ItemStack::getRegistryEntry), Codecs.rangedInt(1, Integer.MAX_VALUE).fieldOf("count").orElse(1).forGetter(ItemStack::getCount), ComponentChanges.CODEC.optionalFieldOf("components", ComponentChanges.EMPTY).forGetter((stack) -> {
					return stack.getComponentChanges();
				})).apply(instance, ItemStack::new);
			});
		});
	}
	
}
