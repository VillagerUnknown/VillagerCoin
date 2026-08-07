package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
	
	public ItemEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}
	
	@Shadow public abstract ItemStack getItem();
	
	@Inject(method = "setUnderwaterMovement", at = @At("HEAD"), cancellable = true)
	private void applyWaterBuoyancy(CallbackInfo ci) {
		if( Villagercoin.CONFIG.coinsSinkInLiquids && this.getItem().is( Villagercoin.getItemTagKey( "currency_coin" ) ) ) {
			this.applyGravity();
			ci.cancel();
		} // if
	}
	
	@Inject(method = "setUnderLavaMovement", at = @At("HEAD"), cancellable = true)
	private void applyLavaBuoyancy(CallbackInfo ci) {
		if( Villagercoin.CONFIG.coinsSinkInLiquids && this.getItem().is( Villagercoin.getItemTagKey( "currency_coin" ) ) ) {
			this.applyGravity();
			ci.cancel();
		} // if
	}
	
}
