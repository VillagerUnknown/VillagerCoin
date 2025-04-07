package me.villagerunknown.villagercoin;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.PlatformMod;
import me.villagerunknown.platform.manager.featureManager;
import me.villagerunknown.villagercoin.feature.coinFeature;
import me.villagerunknown.villagercoin.feature.edibleCoinFeature;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

public class Villagercoin implements ModInitializer {
	
	public static PlatformMod<VillagercoinConfigData> MOD = null;
	public static String MOD_ID = null;
	public static Logger LOGGER = null;
	public static VillagercoinConfigData CONFIG = null;
	
	@Override
	public void onInitialize() {
		// # Register Mod w/ Platform
		MOD = Platform.register( "villagercoin", Villagercoin.class, VillagercoinConfigData.class );
		
		MOD_ID = MOD.getModId();
		LOGGER = MOD.getLogger();
		CONFIG = MOD.getConfig();
		
		// # Initialize Mod
		init();
	}
	
	private static void init() {
		Platform.init_mod( MOD );
		
		// # Activate Features
		featureManager.addFeature( "edibleCoin", edibleCoinFeature::execute );
		featureManager.addFeature( "coin", coinFeature::execute );
	}
	
}
