package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.EntityUtil;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.item.CoinItems;
import me.villagerunknown.villagercoin.item.CoinItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Rarity;

import java.util.Set;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class CoinFeature {
	
	public static String COIN_STRING = "villager_coin";
	
	public static final SoundEvent COIN_HEAVY_SOUND = RegistryUtil.registerSound( "coin_heavy", MOD_ID );
	public static final SoundEvent COIN_LIGHT_SOUND = RegistryUtil.registerSound( "coin_light", MOD_ID );
	public static final SoundEvent COIN_FLIP_SOUND = RegistryUtil.registerSound( "coin_flip", MOD_ID );
	
	public static final BlockSoundGroup COIN = new BlockSoundGroup(
			1.0F,
			1.0F,
			COIN_HEAVY_SOUND,
			COIN_LIGHT_SOUND,
			COIN_HEAVY_SOUND,
			COIN_LIGHT_SOUND,
			COIN_LIGHT_SOUND
	);
	
	public static final int CURRENCY_CONVERSION_MULTIPLIER = Villagercoin.CONFIG.currencyConversionMultiplier;
	
	public static final long COPPER_VALUE = 1;
	public static final long IRON_VALUE = COPPER_VALUE * CURRENCY_CONVERSION_MULTIPLIER;
	public static final long GOLD_VALUE = IRON_VALUE * CURRENCY_CONVERSION_MULTIPLIER;
	public static final long EMERALD_VALUE = GOLD_VALUE * CURRENCY_CONVERSION_MULTIPLIER;
	public static final long NETHERITE_VALUE = EMERALD_VALUE * CURRENCY_CONVERSION_MULTIPLIER;
	
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
	
	public static String humanReadableNumber( int number ) {
		String text = Integer.toString(number);
		int digits = text.length();
		
		if (digits > 9) {
			// >999,999,999
			text = number / 1000000000 + "b+";
		} else if (digits > 6) {
			// >999,999
			text = number / 1000000 + "m+";
		} else if (digits > 4) {
			// >9999 prints >10k
			text = number / 1000 + "k+";
		} // if, else if ...
		
		return text;
	}
	
	public static float humanReadableNumberScale( int digits ) {
		float scale = 0.8F;
		
		if( digits > 4 ) {
			scale = 0.5F;
		} else if( digits > 3 ) {
			scale = 0.6F;
		} // if, else if
		
		return scale;
	}
	
	private static void registerItemGroup() {
		Registry.register(Registries.ITEM_GROUP, Villagercoin.ITEM_GROUP_KEY, Villagercoin.ITEM_GROUP);
	}
	
	public static Item registerCoinItem(String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance ) {
		return registerCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, new Item.Settings() );
	}
	
	public static Item registerCoinItem( String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new CoinItem( settings, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance ), MOD_ID );
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		return item;
	}
	
	public static Item registerCoinItem(String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, Item.Settings settings ) {
		Item item = registerCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
	public static Item registerCraftableCoinItem( String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float dropChance, float flipChance ) {
		return registerCraftableCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, new Item.Settings() );
	}
	
	public static Item registerCraftableCoinItem( String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops ) {
		return registerCraftableCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, lootTables, entityDrops, new Item.Settings() );
	}
	
	public static Item registerCraftableCoinItem( String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Item.Settings settings ) {
		Item item = registerCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, settings );
		
		CoinCraftingFeature.registerCraftingResultCoin( item, value );
		
		return item;
	}
	
	public static Item registerCraftableCoinItem( String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, Item.Settings settings ) {
		Item item = registerCraftableCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
	public static void playSound( PlayerEntity player, SoundEvent sound ) {
		if( player.getWorld().isClient() ) {
			return;
		} // if
		
		EntityUtil.playSound(player, sound, SoundCategory.PLAYERS, 0.5F, 1F, false);
	}
	
	public static void playCoinSound( PlayerEntity player ) {
		playSound( player, COIN_LIGHT_SOUND );
	}
	
	public static void playHeavyCoinSound( PlayerEntity player ) {
		playSound( player, COIN_HEAVY_SOUND );
	}
	
	public static void playCoinFlipSound( PlayerEntity player ) {
		playSound( player, COIN_FLIP_SOUND );
	}
	
}
