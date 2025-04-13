package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.data.component.CoinComponent;
import me.villagerunknown.villagercoin.data.component.CollectableComponent;
import me.villagerunknown.villagercoin.data.component.CurrencyComponent;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Rarity;

import java.util.List;

import static me.villagerunknown.villagercoin.Villagercoin.*;
import static me.villagerunknown.villagercoin.Villagercoin.COLLECTABLE_COMPONENT;

public class InventoryEffectCoinItem extends AbstractInventoryEffectCoinItem {
	
	public InventoryEffectCoinItem(Settings settings) {
		super(
				settings
						.maxCount( 1 )
						.component( COIN_COMPONENT, new CoinComponent( Rarity.RARE, 0, 1, 0.001F, 0.5F ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( 1 ) )
						.component( COLLECTABLE_COMPONENT, new CollectableComponent(1) )
						.component( DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffectsComponent( List.of( new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.LUCK, 1 ) ) ) )
		);
	}
	
	public InventoryEffectCoinItem(Settings settings, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects) {
		super(
				settings
						.maxCount( maximumAllowedInServer )
						.component( COIN_COMPONENT, new CoinComponent( rarity, dropMinimum, dropMaximum, dropChance, flipChance ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( value ) )
						.component( COLLECTABLE_COMPONENT, new CollectableComponent( maximumAllowedInServer ) )
						.component( DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffectsComponent( statusEffects ) )
		);
	}
	
}
