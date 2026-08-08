package me.villagerunknown.villagercoin.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.AutoConfigClient;
import me.villagerunknown.platform.PlatformConfigData;
import me.villagerunknown.villagercoin.VillagercoinConfigData;

public class ModMenuIntegration implements ModMenuApi {
	
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> AutoConfigClient.getConfigScreen( VillagercoinConfigData.class, parent ).get();
	}
	
}
