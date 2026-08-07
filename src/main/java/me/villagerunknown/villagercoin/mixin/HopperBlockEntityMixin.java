package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.block.entity.AbstractCurrencyValueBlockEntity;
import me.villagerunknown.villagercoin.block.entity.CoinBankBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {
	
	@Inject(method = "ejectItems", at = @At("HEAD"), cancellable = true)
	private static void insert(Level world, BlockPos pos, HopperBlockEntity hopperBlockEntity, CallbackInfoReturnable<Boolean> cir) {
		Direction direction = world.getBlockState( pos ).getValue(HopperBlock.FACING);
		
		if( Direction.DOWN == direction || Direction.UP == direction ) {
			BlockEntity be = world.getBlockEntity( pos.below() );
			if( be instanceof CoinBankBlockEntity coinBankBlockEntity ) {
				if( coinBankBlockEntity.canIncrementCurrencyValue( 1 ) ) {
					for (int i = 0; i < hopperBlockEntity.getContainerSize(); i++) {
						if( !((HopperBlockEntityAccessor) hopperBlockEntity).invokeIsDisabled() ) {
							ItemStack itemStack = hopperBlockEntity.getItem(i);
							if (!itemStack.isEmpty() && itemStack.is(Villagercoin.getItemTagKey( "currency_coin" ))) {
								CurrencyComponent currencyComponent = itemStack.get(CURRENCY_COMPONENT);
								
								if (null != currencyComponent) {
									long currencyValue = currencyComponent.value();
									
									if (coinBankBlockEntity.canIncrementCurrencyValue(currencyValue)) {
										itemStack.shrink(1);
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
