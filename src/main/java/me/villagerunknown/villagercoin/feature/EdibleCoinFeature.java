package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.item.EdibleCoinItem;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Rarity;

import java.util.List;
import java.util.Set;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class EdibleCoinFeature {
	
	public static int COPPER_DROP_MAXIMUM = Villagercoin.CONFIG.copperEdibleDropMaximum;
	public static int IRON_DROP_MAXIMUM = Villagercoin.CONFIG.ironEdibleDropMaximum;
	public static int GOLD_DROP_MAXIMUM = Villagercoin.CONFIG.goldEdibleDropMaximum;
	public static int EMERALD_DROP_MAXIMUM = Villagercoin.CONFIG.emeraldEdibleDropMaximum;
	public static int NETHERITE_DROP_MAXIMUM = Villagercoin.CONFIG.netheriteEdibleDropMaximum;
	
	public static float COPPER_DROP_CHANCE = Villagercoin.CONFIG.copperEdibleDropChance;
	public static float IRON_DROP_CHANCE = Villagercoin.CONFIG.ironEdibleDropChance;
	public static float GOLD_DROP_CHANCE = Villagercoin.CONFIG.goldEdibleDropChance;
	public static float EMERALD_DROP_CHANCE = Villagercoin.CONFIG.emeraldEdibleDropChance;
	public static float NETHERITE_DROP_CHANCE = Villagercoin.CONFIG.netheriteEdibleDropChance;
	
	public static FoodComponent COPPER_FOOD = FoodComponents.COOKIE;
	public static FoodComponent IRON_FOOD = FoodComponents.BREAD;
	public static FoodComponent GOLD_FOOD = FoodComponents.GOLDEN_CARROT;
	public static FoodComponent EMERALD_FOOD = FoodComponents.COOKED_PORKCHOP;
	public static FoodComponent NETHERITE_FOOD = FoodComponents.GOLDEN_APPLE;
	
	public static void execute() {}
	
	public static Item registerEdibleCoinItem( String id, FoodComponent foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls ) {
		return registerEdibleCoinItem( id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, new Item.Settings() );
	}
	
	public static Item registerEdibleCoinItem( String id, FoodComponent foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, Set<RegistryKey<LootTable>> lootTables ) {
		return registerEdibleCoinItem( id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, lootTables, new Item.Settings() );
	}
	
	public static Item registerEdibleCoinItem( String id, FoodComponent foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, Set<RegistryKey<LootTable>> lootTables, List<SuspiciousStewEffectsComponent.StewEffect> stewEffects ) {
		return registerEdibleCoinItem( id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, lootTables, stewEffects, new Item.Settings() );
	}
	
	public static Item registerEdibleCoinItem(String id, FoodComponent foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new EdibleCoinItem( settings, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls ), MOD_ID );
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		return item;
	}
	
	public static Item registerEdibleCoinItem(String id, FoodComponent foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, List<SuspiciousStewEffectsComponent.StewEffect> stewEffects, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new EdibleCoinItem( settings, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, stewEffects ), MOD_ID );
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		return item;
	}
	
	public static Item registerEdibleCoinItem(String id, FoodComponent foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, Set<RegistryKey<LootTable>> lootTables, List<SuspiciousStewEffectsComponent.StewEffect> stewEffects, Item.Settings settings ) {
		Item item = registerEdibleCoinItem( id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, stewEffects, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		
		return item;
	}
	
	public static Item registerEdibleCoinItem(String id, FoodComponent foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, Set<RegistryKey<LootTable>> lootTables, Item.Settings settings ) {
		Item item = registerEdibleCoinItem( id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		
		return item;
	}
	
}
