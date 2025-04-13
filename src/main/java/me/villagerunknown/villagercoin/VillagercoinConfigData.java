package me.villagerunknown.villagercoin;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "villagerunknown-villagercoin")
public class VillagercoinConfigData implements me.shedaniel.autoconfig.ConfigData {
	
	/**
	 * General
	 */
	
	@ConfigEntry.Category("General")
	public boolean addCoinsToStructureLootTables = true;
	
	@ConfigEntry.Category("General")
	public boolean addCoinsToMobDrops = true;
	
	@ConfigEntry.Category("General")
	public boolean enablePigCoinDrops = false;
	
	@ConfigEntry.Category("General")
	public boolean enableCoinFlipping = true;
	
	@ConfigEntry.Category("General")
	public boolean enableTradeModifications = true;
	
	@ConfigEntry.Category("General")
	public float lootingBonusPerLevel = 0.1F;
	
	@ConfigEntry.Category("General")
	public int maximumCoinStackSize = 5000;
	
	@ConfigEntry.Category("General")
	public int goldCoinSellItemDivisor = 2;
	
	@ConfigEntry.Category("General")
	public int goldCoinSellItemMaximum = 32;
	
	@ConfigEntry.Category("General")
	public int goldForDiamond = 16;
	
	@ConfigEntry.Category("General")
	public int goldForEmerald = 8;
	
	@ConfigEntry.Category("General")
	public float chanceDiamondBecomesEmeraldTrade = 0.5F;
	
}
