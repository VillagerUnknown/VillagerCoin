package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.data.persistent.PersistentItemExistenceData;
import me.villagerunknown.villagercoin.item.CollectableCoinItem;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Rarity;

import java.util.HashMap;
import java.util.Set;

public class CollectableCoinFeature {
	
	public static int COPPER_MAXIMUM_IN_CIRCULATION = Villagercoin.CONFIG.copperMaximumCollectables;
	public static int IRON_MAXIMUM_IN_CIRCULATION = Villagercoin.CONFIG.ironMaximumCollectables;
	public static int GOLD_MAXIMUM_IN_CIRCULATION = Villagercoin.CONFIG.goldMaximumCollectables;
	public static int EMERALD_MAXIMUM_IN_CIRCULATION = Villagercoin.CONFIG.emeraldMaximumCollectables;
	public static int NETHERITE_MAXIMUM_IN_CIRCULATION = Villagercoin.CONFIG.netheriteMaximumCollectables;
	
	public static int COPPER_VALUE = Villagercoin.CONFIG.copperCollectableValue;
	public static int IRON_VALUE = Villagercoin.CONFIG.ironCollectableValue;
	public static int GOLD_VALUE = Villagercoin.CONFIG.goldCollectableValue;
	public static int EMERALD_VALUE = Villagercoin.CONFIG.emeraldCollectableValue;
	public static int NETHERITE_VALUE = Villagercoin.CONFIG.netheriteCollectableValue;
	
	public static float COPPER_DROP_CHANCE = Villagercoin.CONFIG.copperCollectableDropChance;
	public static float IRON_DROP_CHANCE = Villagercoin.CONFIG.ironCollectableDropChance;
	public static float GOLD_DROP_CHANCE = Villagercoin.CONFIG.goldCollectableDropChance;
	public static float EMERALD_DROP_CHANCE = Villagercoin.CONFIG.emeraldCollectableDropChance;
	public static float NETHERITE_DROP_CHANCE = Villagercoin.CONFIG.netheriteCollectableDropChance;
	
	private static MinecraftServer server = null;
	
	public static void execute() {
		registerServerStartedEvent();
	}
	
	public static void registerServerStartedEvent() {
		ServerLifecycleEvents.SERVER_STARTED.register(( server ) -> {
			CollectableCoinFeature.server = server;
		});
	}
	
	public static Item registerCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer ) {
		return registerCollectableCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, new Item.Settings() );
	}
	
	public static Item registerCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops ) {
		return registerCollectableCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, lootTables, entityDrops, new Item.Settings() );
	}
	
	public static Item registerCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new CollectableCoinItem( settings, value, rarity, 1, 1, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer ), Villagercoin.MOD_ID );
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		return item;
	}
	
	public static Item registerCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, Item.Settings settings ) {
		Item item = registerCollectableCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
	public static Item registerCraftableCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer ) {
		return registerCraftableCollectableCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, new Item.Settings() );
	}
	
	public static Item registerCraftableCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops ) {
		return registerCraftableCollectableCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, lootTables, entityDrops, new Item.Settings() );
	}
	
	public static Item registerCraftableCollectableCoinItem( String id, int value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Item.Settings settings ) {
		return registerCollectableCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, settings );
	}
	
	public static Item registerCraftableCollectableCoinItem(String id, int value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, Item.Settings settings ) {
		Item item = registerCraftableCollectableCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
	public static HashMap<Item, Integer> getItemsInExistence() {
		if( null != CollectableCoinFeature.server ) {
			PersistentItemExistenceData state = PersistentItemExistenceData.getServerState(CollectableCoinFeature.server);
			return state.ITEMS_IN_EXISTENCE;
		} // if
		
		return new HashMap<>();
	}
	
	public static void setItemsInExistence( HashMap<Item, Integer> itemsInExistence ) {
		if( null != CollectableCoinFeature.server ) {
			PersistentItemExistenceData state = PersistentItemExistenceData.getServerState(CollectableCoinFeature.server);
			
			state.ITEMS_IN_EXISTENCE = itemsInExistence;
		} // if
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
