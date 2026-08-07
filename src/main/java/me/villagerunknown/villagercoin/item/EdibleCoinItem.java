package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CoinComponent;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.component.DropComponent;
import me.villagerunknown.villagercoin.component.LootTableComponent;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import me.villagerunknown.villagercoin.feature.MobsDropCoinsFeature;
import me.villagerunknown.villagercoin.feature.StructuresIncludeCoinsFeature;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import java.util.List;

import static me.villagerunknown.villagercoin.component.Components.*;

public class EdibleCoinItem extends AbstractEdibleCoinItem {
	
	public EdibleCoinItem(Properties settings) {
		super(
				settings
						.food(Foods.COOKIE)
						.stacksTo( Villagercoin.MAX_STACK_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( CoinFeature.COPPER_RARITY, CoinFeature.COPPER_FLIP_CHANCE ) )
						.component( DROP_COMPONENT, new DropComponent( MobsDropCoinsFeature.COPPER_DROP_MINIMUM, MobsDropCoinsFeature.COPPER_DROP_MAXIMUM, MobsDropCoinsFeature.COPPER_DROP_CHANCE, MobsDropCoinsFeature.COPPER_DROP_MULTIPLIER) )
						.component( LOOT_TABLE_COMPONENT, new LootTableComponent( StructuresIncludeCoinsFeature.COPPER_LOOT_TABLE_WEIGHT, StructuresIncludeCoinsFeature.COPPER_LOOT_TABLE_ROLLS ) )
				
		);
	}
	
	public EdibleCoinItem(Properties settings, FoodProperties foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls) {
		super(
				settings
						.food(foodComponent)
						.stacksTo( Villagercoin.MAX_STACK_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( rarity, CoinFeature.COPPER_FLIP_CHANCE ) )
						.component( DROP_COMPONENT, new DropComponent( dropMinimum, dropMaximum, dropChance, dropChanceMultiplier ) )
						.component( LOOT_TABLE_COMPONENT, new LootTableComponent( lootTableWeight, lootTableRolls ) )
		);
	}
	
	public EdibleCoinItem(Properties settings, FoodProperties foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, List<SuspiciousStewEffects.Entry> statusEffects) {
		super(
				settings
						.food(foodComponent)
						.stacksTo( Villagercoin.MAX_STACK_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( rarity, CoinFeature.COPPER_FLIP_CHANCE ) )
						.component( DROP_COMPONENT, new DropComponent( dropMinimum, dropMaximum, dropChance, dropChanceMultiplier ) )
						.component( LOOT_TABLE_COMPONENT, new LootTableComponent( lootTableWeight, lootTableRolls ) )
						.component( DataComponents.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffects( statusEffects ) )
		);
	}
	
}
