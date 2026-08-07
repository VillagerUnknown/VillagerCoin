package me.villagerunknown.villagercoin.block.entity;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinCraftingFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import javax.swing.text.NumberFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		setChanged();
	}
	
	public void incrementCurrencyValueAndSetComponent( long value ) {
		incrementCurrencyValue( value );
		this.setComponents(DataComponentMap.builder().set(CURRENCY_COMPONENT, new CurrencyComponent( getTotalCurrencyValue() )).build());
	}
	
	public boolean canDecrementCurrencyValue( long decrement ) {
		return (this.totalCurrencyValue - decrement) >= 0;
	}
	
	public void decrementCurrencyValue( long value ) {
		this.totalCurrencyValue -= value;
		setChanged();
	}
	
	@Override
	protected void loadAdditional(ValueInput view) {
		super.loadAdditional(view);
		
		Optional<Long> totalCurrencyValue = Optional.of(view.getLongOr("totalCurrencyValue", 0L));
		
		totalCurrencyValue.ifPresent(value -> this.totalCurrencyValue = value);
	}
	
	@Override
	protected void saveAdditional(ValueOutput view) {
		view.putLong("totalCurrencyValue", this.totalCurrencyValue);
		super.saveAdditional(view);
	}
	
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
		return saveWithoutMetadata(registryLookup);
	}
	
	public List<ItemStack> getTotalValueAsCoins() {
		List<ItemStack> coinItemStacks = new ArrayList<>();
		
		Level world = this.getLevel();
		
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
		
		Level world = this.getLevel();
		
		if( null != world ) {
			for (ItemStack coinStack : coinStacks) {
				world.addFreshEntity(new ItemEntity(world, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), coinStack));
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
