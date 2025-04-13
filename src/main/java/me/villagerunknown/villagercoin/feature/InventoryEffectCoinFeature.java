package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.persistent.PersistentItemExistenceData;
import me.villagerunknown.villagercoin.item.InventoryEffectCoinItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Rarity;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class InventoryEffectCoinFeature {

	public static void execute() {}
	
	public static Item registerInventoryEffectCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects ) {
		return registerInventoryEffectCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, statusEffects, new Item.Settings() );
	}
	
	public static Item registerInventoryEffectCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops,  List<SuspiciousStewEffectsComponent.StewEffect> statusEffects ) {
		return registerInventoryEffectCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, lootTables, entityDrops, statusEffects, new Item.Settings() );
	}
	
	public static Item registerInventoryEffectCoinItem(String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new InventoryEffectCoinItem( settings, value, rarity, 1, 1, dropChance, flipChance, maximumAllowedInServer, statusEffects ), MOD_ID );
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		return item;
	}
	
	public static Item registerInventoryEffectCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects, Item.Settings settings ) {
		Item item = registerInventoryEffectCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, statusEffects, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
	public static Item registerCraftableInventoryEffectCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects ) {
		return registerCraftableInventoryEffectCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, statusEffects, new Item.Settings() );
	}
	
	public static Item registerCraftableInventoryEffectCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects ) {
		return registerCraftableInventoryEffectCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, lootTables, entityDrops, statusEffects, new Item.Settings() );
	}
	
	public static Item registerCraftableInventoryEffectCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new InventoryEffectCoinItem( settings, value, rarity, 1, 1, dropChance, flipChance, maximumAllowedInServer, statusEffects ), MOD_ID );
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		CoinCraftingFeature.registerCoin( item, value );
		
		return item;
	}
	
	public static Item registerCraftableInventoryEffectCoinItem( String id, int value, Rarity rarity, float dropChance, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects, Item.Settings settings ) {
		Item item = registerCraftableInventoryEffectCoinItem( id, value, rarity, dropChance, flipChance, maximumAllowedInServer, statusEffects, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
}
