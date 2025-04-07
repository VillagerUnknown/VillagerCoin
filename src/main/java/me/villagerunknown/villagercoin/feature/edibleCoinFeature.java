package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.item.EdibleVillagerCoinItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import java.util.HashMap;
import java.util.Set;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class edibleCoinFeature {
	
	public static Set<String> EDIBLE_COIN_TYPES = Set.of(
			"gold",
			"emerald",
			"netherite"
	);
	
	public static HashMap<String, Item> EDIBLE_COIN_ITEMS = new HashMap<>();
	
	public static Item EDIBLE_GOLD_COIN = null;
	public static Item EDIBLE_EMERALD_COIN = null;
	public static Item EDIBLE_NETHERITE_COIN = null;
	
	public static void execute() {
		registerEdibleVillagerCoinItems();
		
		EDIBLE_GOLD_COIN = EDIBLE_COIN_ITEMS.get( "edible_gold_villager_coin" );
		EDIBLE_EMERALD_COIN = EDIBLE_COIN_ITEMS.get( "edible_emerald_villager_coin" );
		EDIBLE_NETHERITE_COIN = EDIBLE_COIN_ITEMS.get( "edible_netherite_villager_coin" );
	}
	
	private static void registerEdibleVillagerCoinItems() {
		for (String coinType : EDIBLE_COIN_TYPES) {
			coinType = "edible_" + coinType + "_" + coinFeature.COIN_STRING;
			Item item = RegistryUtil.registerItem( coinType, new EdibleVillagerCoinItem( new Item.Settings() ), MOD_ID );
			
			EDIBLE_COIN_ITEMS.put( coinType, item );
			RegistryUtil.addItemToGroup( ItemGroups.FOOD_AND_DRINK, item );
		} // for
	}
	
}
