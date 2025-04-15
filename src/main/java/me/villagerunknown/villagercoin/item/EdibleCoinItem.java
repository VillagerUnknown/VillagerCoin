package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.component.CoinComponent;
import me.villagerunknown.villagercoin.data.component.CollectableComponent;
import me.villagerunknown.villagercoin.data.component.CurrencyComponent;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.util.Rarity;

import java.util.List;

import static me.villagerunknown.villagercoin.Villagercoin.*;

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
	
	public EdibleCoinItem(Settings settings, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects) {
		super(
				settings
						.maxCount( maximumAllowedInServer )
						.component( COIN_COMPONENT, new CoinComponent( rarity, dropMinimum, dropMaximum, dropChance, flipChance ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( value ) )
						.component( DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffectsComponent( statusEffects ) )
		);
	}
	
}
