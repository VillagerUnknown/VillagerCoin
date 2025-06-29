package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.block.entity.AbstractCurrencyValueBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {
	
	@Inject(method = "insert", at = @At("HEAD"), cancellable = true)
	private static void insert(World world, BlockPos pos, HopperBlockEntity hopperBlockEntity, CallbackInfoReturnable<Boolean> cir) {
		Direction direction = Direction.getFacing( pos.getX(), pos.getY(), pos.getZ() );
		
		if( Direction.DOWN == direction ) {
			BlockEntity be = world.getBlockEntity( pos.down() );
			
			if( be instanceof AbstractCurrencyValueBlockEntity coinBankBlockEntity ) {
				if( coinBankBlockEntity.canIncrementCurrencyValue( 1 ) ) {
					for (int i = 0; i < hopperBlockEntity.size(); i++) {
						if( !((HopperBlockEntityAccessor) hopperBlockEntity).invokeIsDisabled() ) {
							ItemStack itemStack = hopperBlockEntity.getStack(i);
							
							if (!itemStack.isEmpty() && itemStack.isIn(Villagercoin.getItemTagKey( "currency_coin" ))) {
								CurrencyComponent currencyComponent = itemStack.get(CURRENCY_COMPONENT);
								
								if (null != currencyComponent) {
									long currencyValue = currencyComponent.value();
									
									if (coinBankBlockEntity.canIncrementCurrencyValue(currencyValue)) {
										itemStack.decrement(1);
										coinBankBlockEntity.incrementCurrencyValueAndSetComponent(currencyValue);
										
										((HopperBlockEntityAccessor) hopperBlockEntity).invokeSetTransferCooldown(8);
										
										cir.setReturnValue(true);
										break;
									} // if
								} // if
							} // if
						} // if
					} // for
				} // if
			} // if
		} // if
	}
	
}
