package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.item.EdibleCoinItem;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Rarity;

import java.util.Set;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class EdibleCoinFeature {
	
	public static int COPPER_DROP_MAXIMUM = 5;
	public static int IRON_DROP_MAXIMUM = 4;
	public static int GOLD_DROP_MAXIMUM = 3;
	public static int EMERALD_DROP_MAXIMUM = 2;
	public static int NETHERITE_DROP_MAXIMUM = 1;
	
	public static float COPPER_DROP_CHANCE = CoinFeature.COPPER_DROP_CHANCE / 4;
	public static float IRON_DROP_CHANCE = CoinFeature.IRON_DROP_CHANCE / 3;
	public static float GOLD_DROP_CHANCE =CoinFeature.GOLD_DROP_CHANCE / 2;
	public static float EMERALD_DROP_CHANCE = 0.005F;
	public static float NETHERITE_DROP_CHANCE = 0.0005F;
	
	public static FoodComponent COPPER_FOOD = FoodComponents.COOKIE;
	public static FoodComponent IRON_FOOD = FoodComponents.BREAD;
	public static FoodComponent GOLD_FOOD = FoodComponents.GOLDEN_CARROT;
	public static FoodComponent EMERALD_FOOD = FoodComponents.COOKED_PORKCHOP;
	public static FoodComponent NETHERITE_FOOD = FoodComponents.GOLDEN_APPLE;
	
	public static void execute() {}
	
	public static Item registerEdibleCoinItem( String id, FoodComponent foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance ) {
		return registerEdibleCoinItem( id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, new Item.Settings() );
	}
	
	public static Item registerEdibleCoinItem( String id, FoodComponent foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops ) {
		return registerEdibleCoinItem( id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, lootTables, entityDrops, new Item.Settings() );
	}
	
	public static Item registerEdibleCoinItem(String id, FoodComponent foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new EdibleCoinItem( settings, foodComponent, rarity, dropMinimum, dropMaximum, dropChance ), MOD_ID );
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		return item;
	}
	
	public static Item registerEdibleCoinItem(String id, FoodComponent foodComponent, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, Item.Settings settings ) {
		Item item = registerEdibleCoinItem( id, foodComponent, rarity, dropMinimum, dropMaximum, dropChance, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
}
