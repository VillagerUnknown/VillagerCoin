package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class CustomItemGroupFeature {
	
	public static void execute(){
		registerItemGroup();
	}
	
	private static void registerItemGroup() {
		Registry.register(Registries.ITEM_GROUP, Villagercoin.ITEM_GROUP_KEY, Villagercoin.ITEM_GROUP);
	}
	
}
