package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.type.CoinComponent;
import me.villagerunknown.villagercoin.data.type.CollectableComponent;
import me.villagerunknown.villagercoin.data.type.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CollectableCoinFeature;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.Rarity;

import static me.villagerunknown.villagercoin.Villagercoin.COLLECTABLE_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.COIN_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

public class CollectableCoinItem extends AbstractCoinItem {
	
	public CollectableCoinItem(Settings settings) {
		super(
				settings
						.maxCount( Villagercoin.MAX_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( Rarity.RARE, 0, 1, 0.001F, 0.5F ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( 1 ) )
						.component( COLLECTABLE_COMPONENT, new CollectableComponent(1) )
		);
	}
	
	public CollectableCoinItem(Settings settings, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, float flipChance, int maximumAllowedInServer) {
		super(
				settings
						.maxCount( Villagercoin.MAX_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( rarity, dropMinimum, dropMaximum, dropChance, flipChance ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( value ) )
						.component( COLLECTABLE_COMPONENT, new CollectableComponent(maximumAllowedInServer) )
		);
	}
	
	public void onItemEntityDestroyed(ItemEntity entity) {
		ItemStack itemStack = entity.getStack();
		CollectableComponent collectableComponent = itemStack.get( COLLECTABLE_COMPONENT );
		
		if( null != collectableComponent ) {
			Item item = itemStack.getItem();
			
			if( collectableComponent.isInCirculation( item ) ) {
				collectableComponent.removeFromCirculation( item, itemStack.getCount() );
			} // if
		} // if
		
		super.onItemEntityDestroyed(entity);
	}
	
	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if( !player.isInCreativeMode() ) {
			Item item = stack.getItem();
			CollectableComponent collectableComponent = stack.get(COLLECTABLE_COMPONENT);
			
			if( null != collectableComponent && !collectableComponent.canAddToCirculation( item ) ) {
				collectableComponent.addToCirculation( item );
			} // if
		} // if
		
		return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
	}
}
