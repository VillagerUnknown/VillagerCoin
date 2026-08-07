package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.component.*;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import me.villagerunknown.villagercoin.feature.CollectableCoinFeature;
import me.villagerunknown.villagercoin.feature.MobsDropCoinsFeature;
import me.villagerunknown.villagercoin.feature.StructuresIncludeCoinsFeature;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import java.util.List;

import static me.villagerunknown.villagercoin.component.Components.*;

public class InventoryEffectCoinItem extends AbstractInventoryEffectCoinItem {
	
	public InventoryEffectCoinItem(Properties settings) {
		super(
				settings
						.stacksTo( 1 )
						.component( COIN_COMPONENT, new CoinComponent( CoinFeature.COPPER_RARITY, CoinFeature.COPPER_FLIP_CHANCE ) )
						.component( DROP_COMPONENT, new DropComponent( MobsDropCoinsFeature.COPPER_DROP_MINIMUM, MobsDropCoinsFeature.COPPER_DROP_MAXIMUM, MobsDropCoinsFeature.COPPER_DROP_CHANCE, MobsDropCoinsFeature.COPPER_DROP_MULTIPLIER) )
						.component( LOOT_TABLE_COMPONENT, new LootTableComponent( StructuresIncludeCoinsFeature.COPPER_LOOT_TABLE_WEIGHT, StructuresIncludeCoinsFeature.COPPER_LOOT_TABLE_ROLLS ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( CoinFeature.COPPER_VALUE ) )
						.component( COLLECTABLE_COMPONENT, new CollectableComponent( CollectableCoinFeature.COPPER_MAXIMUM_IN_CIRCULATION ) )
						.component( DataComponents.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffects( List.of( new SuspiciousStewEffects.Entry( MobEffects.LUCK, 1 ) ) ) )
		);
	}
	
	public InventoryEffectCoinItem(Properties settings, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffects.Entry> statusEffects) {
		super(
				settings
						.stacksTo( maximumAllowedInServer )
						.component( COIN_COMPONENT, new CoinComponent( rarity, flipChance ) )
						.component( DROP_COMPONENT, new DropComponent( dropMinimum, dropMaximum, dropChance, dropChanceMultiplier ) )
						.component( LOOT_TABLE_COMPONENT, new LootTableComponent( lootTableWeight, lootTableRolls ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( value ) )
						.component( COLLECTABLE_COMPONENT, new CollectableComponent( maximumAllowedInServer ) )
						.component( DataComponents.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffects( statusEffects ) )
		);
	}
	
}
