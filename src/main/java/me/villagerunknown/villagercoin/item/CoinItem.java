package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.type.CoinComponent;
import me.villagerunknown.villagercoin.data.type.CurrencyComponent;
import net.minecraft.util.Rarity;

import static me.villagerunknown.villagercoin.Villagercoin.*;

public class CoinItem extends AbstractCoinItem {
	
	public CoinItem(Settings settings) {
		super(
				settings
						.maxCount( Villagercoin.MAX_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( Rarity.COMMON, 0, 10, 0.5F, 0.5F ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( 1 ) )
		);
	}
	
	public CoinItem(Settings settings, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, float flipChance) {
		super(
				settings
						.maxCount( Villagercoin.MAX_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( rarity, dropMinimum, dropMaximum, dropChance, flipChance ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( value ) )
		);
	}
	
}
