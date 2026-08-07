package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.component.CollectableComponent;
import me.villagerunknown.villagercoin.component.DropComponent;
import me.villagerunknown.villagercoin.component.LootTableComponent;
import me.villagerunknown.villagercoin.item.EdibleCoinItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.storage.loot.LootTable;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
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
	
	public static FoodProperties COPPER_FOOD = Foods.COOKIE;
	public static FoodProperties IRON_FOOD = Foods.BREAD;
	public static FoodProperties GOLD_FOOD = Foods.GOLDEN_CARROT;
	public static FoodProperties EMERALD_FOOD = Foods.COOKED_PORKCHOP;
	public static FoodProperties NETHERITE_FOOD = Foods.GOLDEN_APPLE;
	
	public static void execute() {}
	
	public static Item registerEdibleCoinItem( String namespace, String id, FoodProperties foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls ) {
		return registerEdibleCoinItem( namespace, id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, new Item.Properties() );
	}
	
	public static Item registerEdibleCoinItem( String namespace, String id, FoodProperties foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, Set<ResourceKey<LootTable>> lootTables ) {
		return registerEdibleCoinItem( namespace, id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, lootTables, new Item.Properties() );
	}
	
	public static Item registerEdibleCoinItem( String namespace, String id, FoodProperties foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, Set<ResourceKey<LootTable>> lootTables, List<SuspiciousStewEffects.Entry> stewEffects ) {
		return registerEdibleCoinItem( namespace, id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, lootTables, stewEffects, new Item.Properties() );
	}
	
	public static Item registerEdibleCoinItem( String namespace, String id, FoodProperties foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, Item.Properties settings ) {
		settings.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID,id)));
		
		Item item = RegistryUtil.registerItem( id, new EdibleCoinItem( settings, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls ), namespace );
		
		Villagercoin.addItemToGroup( item );
		
		return item;
	}
	
	public static Item registerEdibleCoinItem( String namespace, String id, FoodProperties foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, List<SuspiciousStewEffects.Entry> stewEffects, Item.Properties settings ) {
		settings.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID,id)));
		
		Item item = RegistryUtil.registerItem( id, new EdibleCoinItem( settings, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, stewEffects ), namespace );
		
		Villagercoin.addItemToGroup( item );
		
		return item;
	}
	
	public static Item registerEdibleCoinItem( String namespace, String id, FoodProperties foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, Set<ResourceKey<LootTable>> lootTables, List<SuspiciousStewEffects.Entry> stewEffects, Item.Properties settings ) {
		Item item = registerEdibleCoinItem( namespace, id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, stewEffects, settings );
		
		CoinFeature.addComponents(
				item,
				new LootTableComponent( lootTableWeight, lootTableRolls ),
				new DropComponent( dropMinimum, dropMaximum, dropChance, dropChanceMultiplier ),
				null
		);
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		
		return item;
	}
	
	public static Item registerEdibleCoinItem( String namespace, String id, FoodProperties foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, Set<ResourceKey<LootTable>> lootTables, Item.Properties settings ) {
		Item item = registerEdibleCoinItem( namespace, id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, settings );
		
		CoinFeature.addComponents(
				item,
				new LootTableComponent( lootTableWeight, lootTableRolls ),
				new DropComponent( dropMinimum, dropMaximum, dropChance, dropChanceMultiplier ),
				null
		);
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		
		return item;
	}
	
}
