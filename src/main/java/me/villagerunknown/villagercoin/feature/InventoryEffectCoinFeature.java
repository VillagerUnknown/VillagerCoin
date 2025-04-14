package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.EntityUtil;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.component.CoinComponent;
import me.villagerunknown.villagercoin.item.InventoryEffectCoinItem;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Rarity;

import java.util.*;

import static me.villagerunknown.villagercoin.Villagercoin.COIN_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class InventoryEffectCoinFeature {
	
	public static Set<RegistryEntry<StatusEffect>> DELAY_EFFECTS = new HashSet<>(Arrays.asList(
			StatusEffects.BAD_OMEN,
			StatusEffects.RAID_OMEN,
			StatusEffects.TRIAL_OMEN,
			StatusEffects.MINING_FATIGUE
	));
	
	public static Set<RegistryEntry<StatusEffect>> IGNORE_EFFECTS = new HashSet<>(Arrays.asList());
	
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
	
	public static boolean canApplyEffect(LivingEntity entity, SuspiciousStewEffectsComponent.StewEffect effect ) {
		for (RegistryEntry<StatusEffect> delayEffectEntry : DELAY_EFFECTS) {
			if( entity.hasStatusEffect( delayEffectEntry ) ) {
				return false;
			} // if
		} // for
		
		return !IGNORE_EFFECTS.contains( effect )
				&& entity.canHaveStatusEffect( effect.createStatusEffectInstance() );
	}
	
	public static void applyStatusEffect( LivingEntity entity, SuspiciousStewEffectsComponent.StewEffect effect, Item coin ) {
		CoinComponent coinComponent = coin.getComponents().get( COIN_COMPONENT );
		
		if( null != coinComponent ) {
			int level = switch (coinComponent.rarity()) {
				case Rarity.EPIC -> 3;
				case Rarity.RARE -> 2;
				case Rarity.UNCOMMON -> 1;
				default -> 0;
			};
			
			EntityUtil.addStatusEffect(entity, effect.effect(), effect.duration(), level, true, true, true);
		} // if
	}
	
}
