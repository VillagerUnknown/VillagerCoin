package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.item.CoinItems;
import me.villagerunknown.villagercoin.item.CoinItem;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.*;
import net.minecraft.util.Rarity;

import java.util.Set;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class CoinFeature {
	
	public static String COIN_STRING = "villager_coin";
	
	public static final int CURRENCY_CONVERSION_MULTIPLIER = Villagercoin.CONFIG.currencyConversionMultiplier;
	
	public static final int COPPER_VALUE = 1;
	public static final int IRON_VALUE = COPPER_VALUE * CURRENCY_CONVERSION_MULTIPLIER;
	public static final int GOLD_VALUE = IRON_VALUE * CURRENCY_CONVERSION_MULTIPLIER;
	public static final int EMERALD_VALUE = GOLD_VALUE * CURRENCY_CONVERSION_MULTIPLIER;
	public static final int NETHERITE_VALUE = EMERALD_VALUE * CURRENCY_CONVERSION_MULTIPLIER;
	
	public static final Rarity COPPER_RARITY = Rarity.COMMON;
	public static final Rarity IRON_RARITY = Rarity.UNCOMMON;
	public static final Rarity GOLD_RARITY = Rarity.RARE;
	public static final Rarity EMERALD_RARITY = Rarity.EPIC;
	public static final Rarity NETHERITE_RARITY = Rarity.EPIC;
	
	public static final float COPPER_FLIP_CHANCE = 0.5F;
	public static final float IRON_FLIP_CHANCE = 0.25F;
	public static final float GOLD_FLIP_CHANCE = 0.75F;
	public static final float EMERALD_FLIP_CHANCE = 1F;
	public static final float NETHERITE_FLIP_CHANCE = 0F;
	
	public static void execute() {
		registerItemGroup();
		new CoinItems();
	}
	
	private static void registerItemGroup() {
		Registry.register(Registries.ITEM_GROUP, Villagercoin.ITEM_GROUP_KEY, Villagercoin.ITEM_GROUP);
	}
	
	public static Item registerCoinItem(String id, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance ) {
		return registerCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, new Item.Settings() );
	}
	
	public static Item registerCoinItem( String id, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new CoinItem( settings, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance ), MOD_ID );
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		return item;
	}
	
	public static Item registerCoinItem(String id, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, Item.Settings settings ) {
		Item item = registerCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
	public static Item registerCraftableCoinItem( String id, int value, Rarity rarity, int dropMinimum, int dropMaximum, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float dropChance, float flipChance ) {
		return registerCraftableCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, new Item.Settings() );
	}
	
	public static Item registerCraftableCoinItem( String id, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops ) {
		return registerCraftableCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, lootTables, entityDrops, new Item.Settings() );
	}
	
	public static Item registerCraftableCoinItem( String id, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Item.Settings settings ) {
		Item item = registerCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, settings );
		
		CoinCraftingFeature.registerCraftingResultCoin( item, value );
		
		return item;
	}
	
	public static Item registerCraftableCoinItem( String id, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, Item.Settings settings ) {
		Item item = registerCraftableCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
	
	
}
