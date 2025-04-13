package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.data.persistent.PersistentItemExistenceData;
import me.villagerunknown.villagercoin.item.CollectableCoinItem;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Rarity;

import java.util.HashMap;
import java.util.Set;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class CollectableCoinFeature {
	
	public static int COPPER_MAXIMUM_IN_CIRCULATION = 10;
	public static int IRON_MAXIMUM_IN_CIRCULATION = 5;
	public static int GOLD_MAXIMUM_IN_CIRCULATION = 2;
	public static int EMERALD_MAXIMUM_IN_CIRCULATION = 1;
	public static int NETHERITE_MAXIMUM_IN_CIRCULATION = 1;
	
	public static int COPPER_VALUE = CoinFeature.IRON_VALUE;
	public static int IRON_VALUE = CoinFeature.IRON_VALUE * 50;
	public static int GOLD_VALUE = CoinFeature.GOLD_VALUE * 20;
	public static int EMERALD_VALUE = CoinFeature.EMERALD_VALUE;
	public static int NETHERITE_VALUE = CoinFeature.NETHERITE_VALUE;
	
	public static float COPPER_DROP_CHANCE = 0.001F;
	public static float IRON_DROP_CHANCE = 0.0001F;
	public static float GOLD_DROP_CHANCE = 0.00001F;
	public static float EMERALD_DROP_CHANCE = 0.000001F;
	public static float NETHERITE_DROP_CHANCE = 0.0000001F;
	
	private static MinecraftServer server;
	
	public static void execute() {
		registerServerStartedEvent();
	}
	
	public static void registerServerStartedEvent() {
		ServerLifecycleEvents.SERVER_STARTED.register(( server ) -> {
			CollectableCoinFeature.server = server;
		});
	}
	
	public static Item registerCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer ) {
		return registerCollectableCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, new Item.Settings() );
	}
	
	public static Item registerCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops ) {
		return registerCollectableCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, lootTables, entityDrops, new Item.Settings() );
	}
	
	public static Item registerCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new CollectableCoinItem( settings, value, rarity, 1, 1, dropChance, flipChance, maximumAllowedInServer ), MOD_ID );
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		return item;
	}
	
	public static Item registerCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, Item.Settings settings ) {
		Item item = registerCollectableCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
	public static Item registerCraftableCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer ) {
		return registerCraftableCollectableCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, new Item.Settings() );
	}
	
	public static Item registerCraftableCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops ) {
		return registerCraftableCollectableCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, lootTables, entityDrops, new Item.Settings() );
	}
	
	public static Item registerCraftableCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, Item.Settings settings ) {
		Item item = registerCollectableCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, settings );
		
		CoinCraftingFeature.registerCoin( item, value );
		
		return item;
	}
	
	public static Item registerCraftableCollectableCoinItem(String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, Item.Settings settings ) {
		return registerCollectableCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, lootTables, entityDrops, settings );
	}
	
	public static HashMap<Item, Integer> getItemsInExistence() {
		PersistentItemExistenceData state = PersistentItemExistenceData.getServerState( CollectableCoinFeature.server );
		
		return state.ITEMS_IN_EXISTENCE;
	}
	
	public static void setItemsInExistence( HashMap<Item, Integer> itemsInExistence ) {
		PersistentItemExistenceData state = PersistentItemExistenceData.getServerState( CollectableCoinFeature.server );
		
		state.ITEMS_IN_EXISTENCE = itemsInExistence;
	}
	
	public static int collectablesInCirculation() {
		return getItemsInExistence().size();
	}
	
	public static boolean isInCirculation( Item item ) {
		return getItemsInExistence().containsKey( item );
	}
	
	public static boolean canAddToCirculation( Item item, int maximumAllowedInServer ) {
		HashMap<Item, Integer> itemsInExistence = getItemsInExistence();
		
		if( itemsInExistence.containsKey( item ) ) {
			return itemsInExistence.get(item) < maximumAllowedInServer;
		} // if
		
		return maximumAllowedInServer >= 1;
	}
	
	public static void addToCirculation( Item item ) {
		addToCirculation( item, 1 );
	}
	
	public static void addToCirculation( Item item, int amount ) {
		HashMap<Item, Integer> itemsInExistence = getItemsInExistence();
		
		if( itemsInExistence.containsKey( item ) ) {
			amount = itemsInExistence.get( item ) + amount;
		} // if
		
		itemsInExistence.put( item, amount );
		
		setItemsInExistence( itemsInExistence );
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
		HashMap<Item, Integer> itemsInExistence = getItemsInExistence();
		
		if( !itemsInExistence.containsKey( item ) ) {
			return;
		} // if
		
		int newAmount = itemsInExistence.get( item ) - amount;
		
		if( newAmount <= 0 ) {
			itemsInExistence.remove( item );
		} else {
			itemsInExistence.put( item, newAmount );
		} // if, else
		
		setItemsInExistence( itemsInExistence );
	}
	
}
