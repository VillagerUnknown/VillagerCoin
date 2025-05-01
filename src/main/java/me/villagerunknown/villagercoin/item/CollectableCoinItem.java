package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.component.*;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import me.villagerunknown.villagercoin.feature.CollectableCoinFeature;
import me.villagerunknown.villagercoin.feature.MobsDropCoinsFeature;
import me.villagerunknown.villagercoin.feature.StructuresIncludeCoinsFeature;
import net.minecraft.util.Rarity;

import static me.villagerunknown.villagercoin.Villagercoin.*;
import static me.villagerunknown.villagercoin.Villagercoin.LOOT_TABLE_COMPONENT;

public class CollectableCoinItem extends AbstractCollectableCoinItem {
	
	public CollectableCoinItem(Settings settings) {
		super(
				settings
						.maxCount( 1 )
						.component( COIN_COMPONENT, new CoinComponent( Rarity.RARE, 0.5F ) )
						.component( DROP_COMPONENT, new DropComponent( MobsDropCoinsFeature.COPPER_DROP_MINIMUM, MobsDropCoinsFeature.COPPER_DROP_MAXIMUM, MobsDropCoinsFeature.COPPER_DROP_CHANCE, MobsDropCoinsFeature.COPPER_DROP_MULTIPLIER) )
						.component( LOOT_TABLE_COMPONENT, new LootTableComponent( StructuresIncludeCoinsFeature.COPPER_LOOT_TABLE_WEIGHT, StructuresIncludeCoinsFeature.COPPER_LOOT_TABLE_ROLLS ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( CoinFeature.COPPER_VALUE ) )
						.component( COLLECTABLE_COMPONENT, new CollectableComponent( CollectableCoinFeature.COPPER_MAXIMUM_IN_CIRCULATION ) )
		);
	}
	
	public CollectableCoinItem(Settings settings, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer) {
		super(
				settings
						.maxCount( maximumAllowedInServer )
						.component( COIN_COMPONENT, new CoinComponent( rarity, flipChance ) )
						.component( DROP_COMPONENT, new DropComponent( dropMinimum, dropMaximum, dropChance, dropChanceMultiplier ) )
						.component( LOOT_TABLE_COMPONENT, new LootTableComponent( lootTableWeight, lootTableRolls ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( value ) )
						.component( COLLECTABLE_COMPONENT, new CollectableComponent( maximumAllowedInServer ) )
		);
	}
	
}
