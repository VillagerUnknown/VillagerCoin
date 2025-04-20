package me.villagerunknown.villagercoin.block.entity;

import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinCraftingFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

public abstract class AbstractCoinBankBlockEntity extends BlockEntity {
	
	private Integer totalCurrencyValue = 0;
	
	public AbstractCoinBankBlockEntity(BlockEntityType<? extends AbstractCoinBankBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	public void setTotalCurrencyValue( int value ) {
		totalCurrencyValue = value;
	}
	
	public int getTotalCurrencyValue() {
		return totalCurrencyValue;
	}
	
	public boolean canIncrementCurrencyValue( int increment ) {
		return ( totalCurrencyValue + increment < Integer.MAX_VALUE );
	}
	
	public void incrementCurrencyValue(int value ) {
		totalCurrencyValue += value;
		markDirty();
	}
	
	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.totalCurrencyValue = nbt.getInt("totalCurrencyValue");
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		nbt.putInt("totalCurrencyValue", this.totalCurrencyValue);
		super.writeNbt(nbt, registryLookup);
	}
	
	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		return createNbt(registryLookup);
	}
	
	public void dropTotalValueAsCoins() {
		World world = this.getWorld();
		
		if( null != world ) {
			while (totalCurrencyValue > 0) {
				ItemStack coinStack = CoinCraftingFeature.getLargestCoin(totalCurrencyValue, false);
				
				CurrencyComponent currencyComponent = coinStack.get(CURRENCY_COMPONENT);
				
				if (null != currencyComponent) {
					world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), coinStack));
					
					totalCurrencyValue -= coinStack.getCount() * currencyComponent.value();
				} else {
					break;
				} // if, else
			} // while
		} // if
	}
	
}
