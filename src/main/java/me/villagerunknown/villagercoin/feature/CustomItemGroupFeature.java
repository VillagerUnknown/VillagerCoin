package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class CustomItemGroupFeature {
	
	public static void execute(){
		registerItemGroup();
	}
	
	private static void registerItemGroup() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Villagercoin.ITEM_GROUP_KEY, Villagercoin.ITEM_GROUP);
	}
	
}
