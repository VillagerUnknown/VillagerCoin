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
	public boolean addEdibleCoinsToStructureLootTables = true;
	
	@ConfigEntry.Category("General")
	public boolean addCoinsToMobDrops = true;
	
	@ConfigEntry.Category("General")
	public boolean enablePigCoinDrops = false;
	
	@ConfigEntry.Category("General")
	public boolean addEdibleCoinsToMobDrops = true;
	
	@ConfigEntry.Category("General")
	public boolean enableCoinFlipping = true;
	
	@ConfigEntry.Category("General")
	public boolean enableTradeModifications = true;
	
	@ConfigEntry.Category("General")
	public float lootingBonusPerLevel = 0.1F;
	
}
