package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.item.CollectableCoinItem;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

import java.util.HashMap;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class CollectableCoinFeature {
	
	public static int COPPER_MAXIMUM_IN_CIRCULATION = 10;
	public static int IRON_MAXIMUM_IN_CIRCULATION = 5;
	public static int GOLD_MAXIMUM_IN_CIRCULATION = 3;
	public static int EMERALD_MAXIMUM_IN_CIRCULATION = 2;
	public static int NETHERITE_MAXIMUM_IN_CIRCULATION = 1;
	
	public static int COPPER_VALUE = CoinFeature.IRON_VALUE;
	public static int IRON_VALUE = CoinFeature.IRON_VALUE * 2;
	public static int GOLD_VALUE = CoinFeature.GOLD_VALUE * 2;
	public static int EMERALD_VALUE = CoinFeature.EMERALD_VALUE;
	public static int NETHERITE_VALUE = CoinFeature.NETHERITE_VALUE;
	
	public static float COPPER_DROP_CHANCE = 0.001F;
	public static float IRON_DROP_CHANCE = 0.0001F;
	public static float GOLD_DROP_CHANCE = 0.00001F;
	public static float EMERALD_DROP_CHANCE = 0.000001F;
	public static float NETHERITE_DROP_CHANCE = 0.0000001F;
	
	public static HashMap<Item, Integer> IN_CIRCULATION = new HashMap<>();
	
	public static void execute() {}
	
	public static Item registerCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer ) {
		return registerCollectableCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, new Item.Settings() );
	}
	
	public static Item registerCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new CollectableCoinItem( settings, value, rarity, 1, 1, dropChance, flipChance, maximumAllowedInServer ), MOD_ID );
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		return item;
	}
	
	public static int collectablesInCirculation() {
		return IN_CIRCULATION.size();
	}
	
	public static boolean isInCirculation( Item item ) {
		return IN_CIRCULATION.containsKey( item );
	}
	
	public static boolean canAddToCirculation( Item item, int maximumAllowedInServer ) {
		if( IN_CIRCULATION.containsKey( item ) ) {
			return IN_CIRCULATION.get(item) < maximumAllowedInServer;
		} // if
		
		return maximumAllowedInServer >= 1;
	}
	
	public static void addToCirculation( Item item ) {
		addToCirculation( item, 1 );
	}
	
	public static void addToCirculation( Item item, int amount ) {
		if( IN_CIRCULATION.containsKey( item ) ) {
			amount = IN_CIRCULATION.get( item ) + amount;
		} // if
		
		IN_CIRCULATION.put( item, amount );
	}
	
	public static void removeFromCirculation( Item item ) {
		removeFromCirculation( item, 1 );
	}
	
	public static void removeFromCirculation( Item item, boolean maximum ) {
		if( maximum ) {
			removeFromCirculation( item, Integer.MAX_VALUE );
		} else {
			removeFromCirculation( item, 1 );
		} // if, else
	}
	
	public static void removeFromCirculation( Item item, int amount ) {
		if( !IN_CIRCULATION.containsKey( item ) ) {
			return;
		} // if
		
		int newAmount = IN_CIRCULATION.get( item ) - amount;
		
		if( newAmount <= 0 ) {
			IN_CIRCULATION.remove( item );
		} else {
			IN_CIRCULATION.put( item, newAmount );
		} // if, else
	}
	
}
