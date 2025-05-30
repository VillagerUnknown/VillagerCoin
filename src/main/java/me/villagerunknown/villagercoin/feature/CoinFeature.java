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

import java.text.DecimalFormat;
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
	
	public static final String[] NUMBER_SUFFIXES = {"k+", "m+", "b+", "t+", "q+", "Q+"};
	public static final long[] NUMBER_DIVISORS = {
			1_000L,
			1_000_000L,
			1_000_000_000L,
			1_000_000_000_000L,
			1_000_000_000_000_000L,
			1_000_000_000_000_000_000L
	};
	
	public static void execute() {
		new CoinItems();
	}
	
	public static String humanReadableNumber( int number, boolean includeDecimals ) {
		return humanReadableNumber( (long) number, includeDecimals );
	}
	
	public static String humanReadableNumber( long number, boolean includeDecimals) {
		if( number >= NUMBER_DIVISORS[0] ) {
			DecimalFormat df = includeDecimals ? new DecimalFormat("0.##") : new DecimalFormat("0");
			
			for (int i = NUMBER_DIVISORS.length - 1; i >= 0; i--) {
				if (number >= NUMBER_DIVISORS[i]) {
					double value = (double) number / NUMBER_DIVISORS[i];
					
					String formatted = df.format(value);
					return formatted + NUMBER_SUFFIXES[i];
				} // if
			} // for
		} // if
		
		return String.valueOf(number);
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
	
	public static Item registerCoinItem( String namespace, String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance ) {
		return registerCoinItem( namespace, id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, new Item.Settings() );
	}
	
	public static Item registerCoinItem( String namespace, String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new CoinItem( settings, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance ), namespace );
		
		Villagercoin.addItemToGroup( item );
		
		return item;
	}
	
	public static Item registerCoinItem( String namespace, String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Set<RegistryKey<LootTable>> lootTables, Item.Settings settings ) {
		Item item = registerCoinItem( namespace, id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		
		return item;
	}
	
	public static Item registerCraftableCoinItem( String namespace, String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float dropChance, float flipChance ) {
		return registerCraftableCoinItem( namespace, id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, new Item.Settings() );
	}
	
	public static Item registerCraftableCoinItem( String namespace, String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Set<RegistryKey<LootTable>> lootTables ) {
		return registerCraftableCoinItem( namespace, id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, lootTables, new Item.Settings() );
	}
	
	public static Item registerCraftableCoinItem( String namespace, String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Item.Settings settings ) {
		Item item = registerCoinItem( namespace, id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, settings );
		
		CoinCraftingFeature.registerCraftingResultCoin( item, value );
		
		return item;
	}
	
	public static Item registerCraftableCoinItem( String namespace, String id, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, Set<RegistryKey<LootTable>> lootTables, Item.Settings settings ) {
		Item item = registerCraftableCoinItem( namespace, id, value, rarity, dropMinimum, dropMaximum, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		
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
