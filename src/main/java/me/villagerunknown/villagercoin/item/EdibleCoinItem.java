package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.type.CoinComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.util.Rarity;

import static me.villagerunknown.villagercoin.Villagercoin.COIN_COMPONENT;

public class EdibleCoinItem extends AbstractEdibleCoinItem {
	
	public EdibleCoinItem(Settings settings) {
		super(
				settings
						.food(FoodComponents.COOKIE)
						.maxCount( Villagercoin.MAX_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( Rarity.COMMON, 0, 5, 0.1F, 0 ) )
		);
	}
	
	public EdibleCoinItem(Settings settings, FoodComponent foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance) {
		super(
				settings
						.food(foodComponent)
						.maxCount( Villagercoin.MAX_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( rarity, dropMinimum, dropMaximum, dropChance, 0 ) )
		);
	}
	
}
