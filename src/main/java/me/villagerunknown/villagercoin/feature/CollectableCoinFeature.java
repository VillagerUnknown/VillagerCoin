package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.component.CollectableComponent;
import me.villagerunknown.villagercoin.component.DropComponent;
import me.villagerunknown.villagercoin.component.LootTableComponent;
import me.villagerunknown.villagercoin.data.persistent.PersistentItemExistenceData;
import me.villagerunknown.villagercoin.item.CollectableCoinItem;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.storage.loot.LootTable;
import java.util.HashMap;
import java.util.Set;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class CollectableCoinFeature {
	
	public static int COPPER_MAXIMUM_IN_CIRCULATION = Villagercoin.CONFIG.copperMaximumCollectables;
	public static int IRON_MAXIMUM_IN_CIRCULATION = Villagercoin.CONFIG.ironMaximumCollectables;
	public static int GOLD_MAXIMUM_IN_CIRCULATION = Villagercoin.CONFIG.goldMaximumCollectables;
	public static int EMERALD_MAXIMUM_IN_CIRCULATION = Villagercoin.CONFIG.emeraldMaximumCollectables;
	public static int NETHERITE_MAXIMUM_IN_CIRCULATION = Villagercoin.CONFIG.netheriteMaximumCollectables;
	
	public static long COPPER_VALUE = Villagercoin.CONFIG.copperCollectableValue;
	public static long IRON_VALUE = Villagercoin.CONFIG.ironCollectableValue;
	public static long GOLD_VALUE = Villagercoin.CONFIG.goldCollectableValue;
	public static long EMERALD_VALUE = Villagercoin.CONFIG.emeraldCollectableValue;
	public static long NETHERITE_VALUE = Villagercoin.CONFIG.netheriteCollectableValue;
	
	public static float COPPER_DROP_CHANCE = Villagercoin.CONFIG.copperCollectableDropChance;
	public static float IRON_DROP_CHANCE = Villagercoin.CONFIG.ironCollectableDropChance;
	public static float GOLD_DROP_CHANCE = Villagercoin.CONFIG.goldCollectableDropChance;
	public static float EMERALD_DROP_CHANCE = Villagercoin.CONFIG.emeraldCollectableDropChance;
	public static float NETHERITE_DROP_CHANCE = Villagercoin.CONFIG.netheriteCollectableDropChance;
	
	public static HashMap<Item, CollectableComponent> COLLECTABLE_COMPONENTS = new HashMap<>();
	
	private static MinecraftServer server = null;
	
	public static void execute() {
		registerServerStartedEvent();
	}
	
	public static void registerServerStartedEvent() {
		ServerLifecycleEvents.SERVER_STARTED.register(( server ) -> {
			CollectableCoinFeature.server = server;
		});
	}
	
	public static void addCoinToCollectableComponents( Item item, CollectableComponent collectableComponent ) {
		COLLECTABLE_COMPONENTS.put( item, collectableComponent );
	}
	
	public static Item registerCollectableCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer ) {
		return registerCollectableCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, new Item.Properties() );
	}
	
	public static Item registerCollectableCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<ResourceKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops ) {
		return registerCollectableCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, lootTables, entityDrops, new Item.Properties() );
	}
	
	public static Item registerCollectableCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Item.Properties settings ) {
		settings.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID,id)));
		
		Item item = RegistryUtil.registerItem( id, new CollectableCoinItem( settings, value, rarity, 1, 1, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer ), namespace );
		
		Villagercoin.addItemToGroup( item );
		
		return item;
	}
	
	public static Item registerCollectableCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<ResourceKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, Item.Properties settings ) {
		Item item = registerCollectableCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, settings );
		
		CoinFeature.addComponents(
				item,
				new LootTableComponent( lootTableWeight, lootTableRolls ),
				new DropComponent( 1, 1, dropChance, dropChanceMultiplier ),
				new CollectableComponent( maximumAllowedInServer )
		);
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
	public static Item registerCraftableCollectableCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer ) {
		return registerCraftableCollectableCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, new Item.Properties() );
	}
	
	public static Item registerCraftableCollectableCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<ResourceKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops ) {
		return registerCraftableCollectableCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, lootTables, entityDrops, new Item.Properties() );
	}
	
	public static Item registerCraftableCollectableCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Item.Properties settings ) {
		return registerCollectableCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, settings );
	}
	
	public static Item registerCraftableCollectableCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<ResourceKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, Item.Properties settings ) {
		Item item = registerCraftableCollectableCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, settings );
		
		CoinFeature.addComponents(
				item,
				new LootTableComponent( lootTableWeight, lootTableRolls ),
				new DropComponent( 1, 1, dropChance, dropChanceMultiplier ),
				new CollectableComponent( maximumAllowedInServer )
		);
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
	public static HashMap<String, Integer> getItemsInExistence() {
		if( null != CollectableCoinFeature.server ) {
			PersistentItemExistenceData state = PersistentItemExistenceData.getServerState(CollectableCoinFeature.server);
			return state.ITEMS_IN_EXISTENCE;
		} // if
		
		return new HashMap<>();
	}
	
	public static void setItemsInExistence( HashMap<String, Integer> itemsInExistence ) {
		if( null != CollectableCoinFeature.server ) {
			PersistentItemExistenceData state = PersistentItemExistenceData.getServerState(CollectableCoinFeature.server);
			
			state.ITEMS_IN_EXISTENCE = itemsInExistence;
		} // if
	}
	
	public static int collectablesInCirculation() {
		return getItemsInExistence().size();
	}
	
	public static boolean isInCirculation( Item item ) {
		return getItemsInExistence().containsKey( item.getDescriptionId() );
	}
	
	public static boolean canAddToCirculation( Item item, int maximumAllowedInServer ) {
		HashMap<String, Integer> itemsInExistence = getItemsInExistence();
		
		if( itemsInExistence.containsKey( item.getDescriptionId() ) ) {
			return itemsInExistence.get(item.getDescriptionId()) < maximumAllowedInServer;
		} // if
		
		return maximumAllowedInServer >= 1;
	}
	
	public static void addToCirculation( Item item ) {
		addToCirculation( item, 1 );
	}
	
	public static void addToCirculation( Item item, int amount ) {
		HashMap<String, Integer> itemsInExistence = getItemsInExistence();
		
		if( itemsInExistence.containsKey( item.getDescriptionId() ) ) {
			amount = itemsInExistence.get( item.getDescriptionId() ) + amount;
		} // if
		
		itemsInExistence.put( item.getDescriptionId(), amount );
		
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
		HashMap<String, Integer> itemsInExistence = getItemsInExistence();
		
		if( !itemsInExistence.containsKey( item.getDescriptionId() ) ) {
			return;
		} // if
		
		int newAmount = itemsInExistence.get( item.getDescriptionId() ) - amount;
		
		if( newAmount <= 0 ) {
			itemsInExistence.remove( item.getDescriptionId() );
		} else {
			itemsInExistence.put( item.getDescriptionId(), newAmount );
		} // if, else
		
		setItemsInExistence( itemsInExistence );
	}
	
}
