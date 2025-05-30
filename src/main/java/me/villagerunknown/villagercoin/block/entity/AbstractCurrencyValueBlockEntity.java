package me.villagerunknown.villagercoin.block.entity;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinCraftingFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.swing.text.NumberFormatter;
import java.util.ArrayList;
import java.util.List;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

public abstract class AbstractCurrencyValueBlockEntity extends BlockEntity {
	
	private Long totalCurrencyValue = 0L;
	
	public AbstractCurrencyValueBlockEntity(BlockEntityType<? extends AbstractCurrencyValueBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	public void setTotalCurrencyValue( long value ) {
		this.totalCurrencyValue = value;
	}
	
	public long getTotalCurrencyValue() {
		return this.totalCurrencyValue;
	}
	
	public boolean canIncrementCurrencyValue( long increment ) {
		return (this.totalCurrencyValue + increment) <= Villagercoin.CONFIG.maximumCoinBankCurrencyValue;
	}
	
	public void incrementCurrencyValue( long value ) {
		this.totalCurrencyValue += value;
		markDirty();
	}
	
	public void incrementCurrencyValueAndSetComponent( long value ) {
		incrementCurrencyValue( value );
		this.setComponents(ComponentMap.builder().add(CURRENCY_COMPONENT, new CurrencyComponent( getTotalCurrencyValue() )).build());
	}
	
	public boolean canDecrementCurrencyValue( long decrement ) {
		return (this.totalCurrencyValue - decrement) >= 0;
	}
	
	public void decrementCurrencyValue( long value ) {
		this.totalCurrencyValue -= value;
		markDirty();
	}
	
	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		
		this.totalCurrencyValue = nbt.getLong("totalCurrencyValue");
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		nbt.putLong("totalCurrencyValue", this.totalCurrencyValue);
		super.writeNbt(nbt, registryLookup);
	}
	
	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		return createNbt(registryLookup);
	}
	
	public List<ItemStack> getTotalValueAsCoins() {
		List<ItemStack> coinItemStacks = new ArrayList<>();
		
		World world = this.getWorld();
		
		long totalValue = this.totalCurrencyValue;
		
		if( null != world ) {
			while( totalValue > 0 ) {
				ItemStack coinStack = CoinCraftingFeature.getLargestCoin(totalValue, false);
				
				CurrencyComponent currencyComponent = coinStack.get(CURRENCY_COMPONENT);
				
				if (null != currencyComponent) {
					coinItemStacks.add( coinStack );
					
					totalValue -= coinStack.getCount() * currencyComponent.value();
				} else {
					break;
				} // if, else
			} // while
		} // if
		
		return coinItemStacks;
	}
	
	public void dropTotalValueAsCoins() {
		List<ItemStack> coinStacks = getTotalValueAsCoins();
		
		World world = this.getWorld();
		
		if( null != world ) {
			for (ItemStack coinStack : coinStacks) {
				world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), coinStack));
			} // for
		} // if
	}
	
	public boolean setBlockEntityCurrencyValue( BlockEntity blockEntity, ItemStack itemStack, CurrencyComponent currencyComponent ) {
		if( blockEntity instanceof AbstractCurrencyValueBlockEntity coinBankBlockEntity ) {
			coinBankBlockEntity.setComponents( itemStack.getComponents() );
			coinBankBlockEntity.setTotalCurrencyValue( currencyComponent.value() );
			
			return true;
		} // if
		
		return false;
	}
	
	public boolean incrementBlockEntityCurrencyValue( BlockEntity blockEntity, CurrencyComponent currencyComponent ) {
		if( blockEntity instanceof AbstractCurrencyValueBlockEntity coinBankBlockEntity ) {
			if( coinBankBlockEntity.canIncrementCurrencyValue( currencyComponent.value() ) ) {
				coinBankBlockEntity.incrementCurrencyValueAndSetComponent(currencyComponent.value());
				
				return true;
			} // if
		} // if
		
		return false;
	}
	
}
