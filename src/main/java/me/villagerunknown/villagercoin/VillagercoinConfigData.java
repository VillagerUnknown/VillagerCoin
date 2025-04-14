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
	
	@ConfigEntry.Category("Coins")
	public int copperDropMinimum = 1;
	
	@ConfigEntry.Category("Coins")
	public int ironDropMinimum = 0;
	
	@ConfigEntry.Category("Coins")
	public int goldDropMinimum = 0;
	
	@ConfigEntry.Category("Coins")
	public int emeraldDropMinimum = 0;
	
	@ConfigEntry.Category("Coins")
	public int netheriteDropMinimum = 0;
	
	@ConfigEntry.Category("Coins")
	public int copperDropMaximum = 10;
	
	@ConfigEntry.Category("Coins")
	public int ironDropMaximum = 5;
	
	@ConfigEntry.Category("Coins")
	public int goldDropMaximum = 3;
	
	@ConfigEntry.Category("Coins")
	public int emeraldDropMaximum = 0;
	
	@ConfigEntry.Category("Coins")
	public int netheriteDropMaximum = 0;
	
	@ConfigEntry.Category("Coins")
	public float copperDropChance = 0.5F;
	
	@ConfigEntry.Category("Coins")
	public float ironDropChance = 0.25F;
	
	@ConfigEntry.Category("Coins")
	public float goldDropChance = 0.1F;
	
	@ConfigEntry.Category("Coins")
	public float emeraldDropChance = 0F;
	
	@ConfigEntry.Category("Coins")
	public float netheriteDropChance = 0F;
	
	/**
	 * Effect Coins
	 */
	
	@ConfigEntry.Category("EffectCoins")
	public float inventoryEffectChancePerTick = 1F;
	
	/**
	 * Loot Tables
	 */
	
	@ConfigEntry.Category("LootTables")
	public boolean addCoinsToStructureLootTables = true;
	
	/**
	 * Mob Drops
	 */
	
	@ConfigEntry.Category("MobDrops")
	public boolean addCoinsToMobDrops = true;
	
	@ConfigEntry.Category("MobDrops")
	public boolean enableBreedableMobDrops = false;
	
	@ConfigEntry.Category("MobDrops")
	public float lootingBonusPerLevel = 0.1F;
	
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
	
}
