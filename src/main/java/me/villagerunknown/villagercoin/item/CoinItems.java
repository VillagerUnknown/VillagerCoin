package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.feature.CoinFeature;
import me.villagerunknown.villagercoin.feature.MobsDropCoinsFeature;
import me.villagerunknown.villagercoin.feature.StructuresIncludeCoinsFeature;
import net.minecraft.item.Item;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;
import static me.villagerunknown.villagercoin.feature.CoinFeature.*;

public class CoinItems {
	
	public static final Item COPPER_COIN;
	public static final Item IRON_COIN;
	public static final Item GOLD_COIN;
	public static final Item EMERALD_COIN;
	public static final Item NETHERITE_COIN;
	
	public CoinItems() {}
	
	static {
		COPPER_COIN = CoinFeature.registerCraftableCoinItem( MOD_ID, "copper_" + CoinFeature.COIN_STRING, COPPER_VALUE, COPPER_RARITY, MobsDropCoinsFeature.COPPER_DROP_MINIMUM, MobsDropCoinsFeature.COPPER_DROP_MAXIMUM, MobsDropCoinsFeature.COPPER_DROP_CHANCE, MobsDropCoinsFeature.COPPER_DROP_MULTIPLIER, StructuresIncludeCoinsFeature.COPPER_LOOT_TABLE_WEIGHT, StructuresIncludeCoinsFeature.COPPER_LOOT_TABLE_ROLLS, COPPER_FLIP_CHANCE, StructuresIncludeCoinsFeature.COPPER_LOOT_TABLES );
		IRON_COIN = CoinFeature.registerCraftableCoinItem( MOD_ID, "iron_" + CoinFeature.COIN_STRING, IRON_VALUE, IRON_RARITY, MobsDropCoinsFeature.IRON_DROP_MINIMUM, MobsDropCoinsFeature.IRON_DROP_MAXIMUM, MobsDropCoinsFeature.IRON_DROP_CHANCE, MobsDropCoinsFeature.IRON_DROP_MULTIPLIER, StructuresIncludeCoinsFeature.IRON_LOOT_TABLE_WEIGHT, StructuresIncludeCoinsFeature.IRON_LOOT_TABLE_ROLLS, IRON_FLIP_CHANCE, StructuresIncludeCoinsFeature.IRON_LOOT_TABLES );
		GOLD_COIN = CoinFeature.registerCraftableCoinItem( MOD_ID, "gold_" + CoinFeature.COIN_STRING, GOLD_VALUE, GOLD_RARITY, MobsDropCoinsFeature.GOLD_DROP_MINIMUM, MobsDropCoinsFeature.GOLD_DROP_MAXIMUM,MobsDropCoinsFeature. GOLD_DROP_CHANCE, MobsDropCoinsFeature.GOLD_DROP_MULTIPLIER, StructuresIncludeCoinsFeature.GOLD_LOOT_TABLE_WEIGHT, StructuresIncludeCoinsFeature.GOLD_LOOT_TABLE_ROLLS, GOLD_FLIP_CHANCE, StructuresIncludeCoinsFeature.GOLD_LOOT_TABLES );
		EMERALD_COIN = CoinFeature.registerCraftableCoinItem( MOD_ID, "emerald_" + CoinFeature.COIN_STRING, EMERALD_VALUE, EMERALD_RARITY, MobsDropCoinsFeature.EMERALD_DROP_MINIMUM, MobsDropCoinsFeature.EMERALD_DROP_MAXIMUM, MobsDropCoinsFeature.EMERALD_DROP_CHANCE, MobsDropCoinsFeature.EMERALD_DROP_MULTIPLIER, StructuresIncludeCoinsFeature.EMERALD_LOOT_TABLE_WEIGHT, StructuresIncludeCoinsFeature.EMERALD_LOOT_TABLE_ROLLS, EMERALD_FLIP_CHANCE, StructuresIncludeCoinsFeature.EMERALD_LOOT_TABLES );
		NETHERITE_COIN = CoinFeature.registerCraftableCoinItem( MOD_ID, "netherite_" + CoinFeature.COIN_STRING, NETHERITE_VALUE, NETHERITE_RARITY, MobsDropCoinsFeature.NETHERITE_DROP_MINIMUM, MobsDropCoinsFeature.NETHERITE_DROP_MAXIMUM, MobsDropCoinsFeature.NETHERITE_DROP_CHANCE, MobsDropCoinsFeature.NETHERITE_DROP_MULTIPLIER, StructuresIncludeCoinsFeature.NETHERITE_LOOT_TABLE_WEIGHT, StructuresIncludeCoinsFeature.NETHERITE_LOOT_TABLE_ROLLS, NETHERITE_FLIP_CHANCE, StructuresIncludeCoinsFeature.NETHERITE_LOOT_TABLES, new Item.Settings().fireproof() );
	}
	
}
