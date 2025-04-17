package me.villagerunknown.villagercoin;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "villagerunknown-villagercoin")
public class VillagercoinConfigData implements me.shedaniel.autoconfig.ConfigData {
	
	/**
	 * Coins
	 */
	
	@ConfigEntry.Category("Coins")
	public int currencyConversionMultiplier = 100;
	
	@ConfigEntry.Category("Coins")
	public int maximumCoinStackSize = 5000;
	
	@ConfigEntry.Category("Coins")
	public boolean enableCoinFlipping = true;
	
	/**
	 * Loot Tables
	 */
	
	@ConfigEntry.Category("LootTables")
	public boolean addCoinsToStructureLootTables = true;
	
	@ConfigEntry.Category("LootTables")
	public int copperLootTableRolls = 10;
	
	@ConfigEntry.Category("LootTables")
	public int ironLootTableRolls = 15;
	
	@ConfigEntry.Category("LootTables")
	public int goldLootTableRolls = 20;
	
	@ConfigEntry.Category("LootTables")
	public int emeraldLootTableRolls = 0;
	
	@ConfigEntry.Category("LootTables")
	public int netheriteLootTableRolls = 0;
	
	@ConfigEntry.Category("LootTables")
	public int copperLootTableWeight = 20;
	
	@ConfigEntry.Category("LootTables")
	public int ironLootTableWeight = 15;
	
	@ConfigEntry.Category("LootTables")
	public int goldLootTableWeight = 10;
	
	@ConfigEntry.Category("LootTables")
	public int emeraldLootTableWeight = 0;
	
	@ConfigEntry.Category("LootTables")
	public int netheriteLootTableWeight = 0;
	
	/**
	 * Mob Drops
	 */
	
	@ConfigEntry.Category("MobDrops")
	public boolean addCoinsToMobDrops = true;
	
	@ConfigEntry.Category("MobDrops")
	public boolean enableBreedableMobDrops = false;
	
	@ConfigEntry.Category("MobDrops")
	public float lootingBonusPerLevel = 0.1F;
	
	@ConfigEntry.Category("MobDrops")
	public int copperDropMinimum = 1;
	
	@ConfigEntry.Category("MobDrops")
	public int ironDropMinimum = 0;
	
	@ConfigEntry.Category("MobDrops")
	public int goldDropMinimum = 0;
	
	@ConfigEntry.Category("MobDrops")
	public int emeraldDropMinimum = 0;
	
	@ConfigEntry.Category("MobDrops")
	public int netheriteDropMinimum = 0;
	
	@ConfigEntry.Category("MobDrops")
	public int copperDropMaximum = 10;
	
	@ConfigEntry.Category("MobDrops")
	public int ironDropMaximum = 5;
	
	@ConfigEntry.Category("MobDrops")
	public int goldDropMaximum = 3;
	
	@ConfigEntry.Category("MobDrops")
	public int emeraldDropMaximum = 0;
	
	@ConfigEntry.Category("MobDrops")
	public int netheriteDropMaximum = 0;
	
	@ConfigEntry.Category("MobDrops")
	public float copperDropChance = 0.5F;
	
	@ConfigEntry.Category("MobDrops")
	public float ironDropChance = 0.25F;
	
	@ConfigEntry.Category("MobDrops")
	public float goldDropChance = 0.1F;
	
	@ConfigEntry.Category("MobDrops")
	public float emeraldDropChance = 0F;
	
	@ConfigEntry.Category("MobDrops")
	public float netheriteDropChance = 0F;
	
	@ConfigEntry.Category("MobDrops")
	public int copperDropMultiplier = 1;
	
	@ConfigEntry.Category("MobDrops")
	public int ironDropMultiplier = 2;
	
	@ConfigEntry.Category("MobDrops")
	public int goldDropMultiplier = 3;
	
	@ConfigEntry.Category("MobDrops")
	public int emeraldDropMultiplier = 4;
	
	@ConfigEntry.Category("MobDrops")
	public int netheriteDropMultiplier = 5;
	
	/**
	 * Trades
	 */
	
	@ConfigEntry.Category("Trades")
	public boolean enableTradeModifications = true;
	
	@ConfigEntry.Category("Trades")
	public int goldCoinSellItemDivisor = 2;
	
	@ConfigEntry.Category("Trades")
	public int goldCoinSellItemMaximum = 32;
	
	@ConfigEntry.Category("Trades")
	public int goldForDiamond = 16;
	
	@ConfigEntry.Category("Trades")
	public int goldForEmerald = 8;
	
	@ConfigEntry.Category("Trades")
	public float chanceDiamondBecomesEmeraldTrade = 0.5F;
	
	/**
	 * Collectable Coins
	 */
	
	@ConfigEntry.Category("CollectableCoins")
	public int copperMaximumCollectables = 10;
	
	@ConfigEntry.Category("CollectableCoins")
	public int ironMaximumCollectables = 5;
	
	@ConfigEntry.Category("CollectableCoins")
	public int goldMaximumCollectables = 3;
	
	@ConfigEntry.Category("CollectableCoins")
	public int emeraldMaximumCollectables = 2;
	
	@ConfigEntry.Category("CollectableCoins")
	public int netheriteMaximumCollectables = 1;
	
	@ConfigEntry.Category("CollectableCoins")
	public int copperCollectableValue = 100;
	
	@ConfigEntry.Category("CollectableCoins")
	public int ironCollectableValue = 500;
	
	@ConfigEntry.Category("CollectableCoins")
	public int goldCollectableValue = 500000;
	
	@ConfigEntry.Category("CollectableCoins")
	public int emeraldCollectableValue = 2000000;
	
	@ConfigEntry.Category("CollectableCoins")
	public int netheriteCollectableValue = 200000000;
	
	@ConfigEntry.Category("CollectableCoins")
	public float copperCollectableDropChance = 0.001F;
	
	@ConfigEntry.Category("CollectableCoins")
	public float ironCollectableDropChance = 0.0005F;
	
	@ConfigEntry.Category("CollectableCoins")
	public float goldCollectableDropChance = 0.0001F;
	
	@ConfigEntry.Category("CollectableCoins")
	public float emeraldCollectableDropChance = 0.00005F;
	
	@ConfigEntry.Category("CollectableCoins")
	public float netheriteCollectableDropChance = 0.00001F;
	
	/**
	 * Effect Coins
	 */
	
	@ConfigEntry.Category("EffectCoins")
	public float inventoryEffectChancePerTick = 1F;
	
	/**
	 * Edible Coins
	 */
	
	@ConfigEntry.Category("EdibleCoins")
	public int copperEdibleDropMaximum = 2;
	
	@ConfigEntry.Category("EdibleCoins")
	public int ironEdibleDropMaximum = 1;
	
	@ConfigEntry.Category("EdibleCoins")
	public int goldEdibleDropMaximum = 1;
	
	@ConfigEntry.Category("EdibleCoins")
	public int emeraldEdibleDropMaximum = 1;
	
	@ConfigEntry.Category("EdibleCoins")
	public int netheriteEdibleDropMaximum = 1;
	
	@ConfigEntry.Category("EdibleCoins")
	public float copperEdibleDropChance = 0.125F;
	
	@ConfigEntry.Category("EdibleCoins")
	public float ironEdibleDropChance = 0.0625F;
	
	@ConfigEntry.Category("EdibleCoins")
	public float goldEdibleDropChance = 0.025F;
	
	@ConfigEntry.Category("EdibleCoins")
	public float emeraldEdibleDropChance = 0.00625F;
	
	@ConfigEntry.Category("EdibleCoins")
	public float netheriteEdibleDropChance = 0.0015625F;
	
}
