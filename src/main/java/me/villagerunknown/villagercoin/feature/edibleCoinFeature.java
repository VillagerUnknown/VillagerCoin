package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.item.EdibleVillagerCoinItem;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class edibleCoinFeature {
	
	public static Item EDIBLE_GOLD_COIN;
	public static Item EDIBLE_EMERALD_COIN;
	public static Item EDIBLE_NETHERITE_COIN;
	
	public static void execute() {
	}
	
	private static Item registerVillagerCoinItem( String id, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, float flipChance ) {
		return registerVillagerCoinItem( id, rarity, dropMinimum, dropMaximum, dropChance, flipChance, new Item.Settings() );
	}
	
	private static Item registerVillagerCoinItem( String id, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, float flipChance, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new EdibleVillagerCoinItem( settings, rarity, dropMinimum, dropMaximum, dropChance, flipChance ), MOD_ID );
		RegistryUtil.addItemToGroup( coinFeature.CUSTOM_ITEM_GROUP_KEY, item );
		return item;
	}
	
	static {
		EDIBLE_GOLD_COIN = registerVillagerCoinItem( "edible_gold_" + coinFeature.COIN_STRING, Rarity.RARE, 0, 5, 0.1F, 0 );
		EDIBLE_EMERALD_COIN = registerVillagerCoinItem( "edible_emerald_" + coinFeature.COIN_STRING, Rarity.EPIC, 0, 1, 0.05F, 0 );
		EDIBLE_NETHERITE_COIN = registerVillagerCoinItem( "edible_netherite_" + coinFeature.COIN_STRING, Rarity.EPIC, 0, 1, 0.005F, 0 );
	}
	
}
