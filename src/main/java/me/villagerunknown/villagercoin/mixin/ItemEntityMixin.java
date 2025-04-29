package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
	
	public ItemEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	
	@Shadow public abstract ItemStack getStack();
	
	@Inject(method = "applyWaterBuoyancy", at = @At("HEAD"), cancellable = true)
	private void applyWaterBuoyancy(CallbackInfo ci) {
		if( Villagercoin.CONFIG.enableCoinsSinkingInWater && this.getStack().isIn( Villagercoin.getItemTagKey( "villager_coin" ) ) ) {
			this.applyGravity();
			ci.cancel();
		} // if
	}
	
	@Inject(method = "applyLavaBuoyancy", at = @At("HEAD"), cancellable = true)
	private void applyLavaBuoyancy(CallbackInfo ci) {
		if( Villagercoin.CONFIG.enableCoinsSinkingInWater && this.getStack().isIn( Villagercoin.getItemTagKey( "villager_coin" ) ) ) {
			this.applyGravity();
			ci.cancel();
		} // if
	}
	
}
