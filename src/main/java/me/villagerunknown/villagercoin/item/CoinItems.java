package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.feature.CoinFeature;
import me.villagerunknown.villagercoin.feature.MobsDropCoinsFeature;
import me.villagerunknown.villagercoin.feature.StructuresIncludeCoinsFeature;
import net.minecraft.item.Item;

import static me.villagerunknown.villagercoin.feature.CoinFeature.*;

public class CoinItems {
	
	public static final Item COPPER_COIN;
	public static final Item IRON_COIN;
	public static final Item GOLD_COIN;
	public static final Item EMERALD_COIN;
	public static final Item NETHERITE_COIN;
	
	public CoinItems() {}
	
	static {
		COPPER_COIN = CoinFeature.registerCraftableCoinItem( "copper_" + CoinFeature.COIN_STRING, COPPER_VALUE, COPPER_RARITY, COPPER_DROP_MINIMUM, COPPER_DROP_MAXIMUM, COPPER_DROP_CHANCE, COPPER_FLIP_CHANCE, StructuresIncludeCoinsFeature.COMMON_LOOT_TABLES, MobsDropCoinsFeature.COMMON_MOB_DROPS);
		IRON_COIN = CoinFeature.registerCraftableCoinItem( "iron_" + CoinFeature.COIN_STRING, IRON_VALUE, IRON_RARITY, IRON_DROP_MINIMUM, IRON_DROP_MAXIMUM, IRON_DROP_CHANCE, IRON_FLIP_CHANCE, StructuresIncludeCoinsFeature.UNCOMMON_LOOT_TABLES, MobsDropCoinsFeature.COMMON_MOB_DROPS );
		GOLD_COIN = CoinFeature.registerCraftableCoinItem( "gold_" + CoinFeature.COIN_STRING, GOLD_VALUE, GOLD_RARITY, GOLD_DROP_MINIMUM, GOLD_DROP_MAXIMUM, GOLD_DROP_CHANCE, GOLD_FLIP_CHANCE, StructuresIncludeCoinsFeature.RARE_LOOT_TABLES, MobsDropCoinsFeature.RARE_MOB_DROPS );
		EMERALD_COIN = CoinFeature.registerCraftableCoinItem( "emerald_" + CoinFeature.COIN_STRING, EMERALD_VALUE, EMERALD_RARITY, EMERALD_DROP_MINIMUM, EMERALD_DROP_MAXIMUM, EMERALD_DROP_CHANCE, EMERALD_FLIP_CHANCE, StructuresIncludeCoinsFeature.EPIC_LOOT_TABLES, MobsDropCoinsFeature.EPIC_MOB_DROPS );
		NETHERITE_COIN = CoinFeature.registerCraftableCoinItem( "netherite_" + CoinFeature.COIN_STRING, NETHERITE_VALUE, NETHERITE_RARITY, NETHERITE_DROP_MINIMUM, NETHERITE_DROP_MAXIMUM, NETHERITE_DROP_CHANCE, NETHERITE_FLIP_CHANCE, new Item.Settings().fireproof() );
	}
	
}
