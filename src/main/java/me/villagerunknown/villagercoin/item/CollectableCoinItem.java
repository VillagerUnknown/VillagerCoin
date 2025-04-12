package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.data.type.CoinComponent;
import me.villagerunknown.villagercoin.data.type.CollectableComponent;
import me.villagerunknown.villagercoin.data.type.CurrencyComponent;
import net.minecraft.util.Rarity;

import static me.villagerunknown.villagercoin.Villagercoin.COLLECTABLE_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.COIN_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

public class CollectableCoinItem extends AbstractCollectableCoinItem {
	
	public CollectableCoinItem(Settings settings) {
		super(
				settings
						.maxCount( 1 )
						.component( COIN_COMPONENT, new CoinComponent( Rarity.RARE, 0, 1, 0.001F, 0.5F ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( 1 ) )
						.component( COLLECTABLE_COMPONENT, new CollectableComponent(1) )
		);
	}
	
	public CollectableCoinItem(Settings settings, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, float flipChance, int maximumAllowedInServer) {
		super(
				settings
						.maxCount( maximumAllowedInServer )
						.component( COIN_COMPONENT, new CoinComponent( rarity, dropMinimum, dropMaximum, dropChance, flipChance ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( value ) )
						.component( COLLECTABLE_COMPONENT, new CollectableComponent( maximumAllowedInServer ) )
		);
	}
	
}
