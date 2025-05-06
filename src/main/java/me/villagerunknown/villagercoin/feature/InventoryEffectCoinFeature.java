package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.EntityUtil;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CoinComponent;
import me.villagerunknown.villagercoin.effect.StewEffects;
import me.villagerunknown.villagercoin.item.InventoryEffectCoinItem;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Rarity;

import java.util.*;

import static me.villagerunknown.villagercoin.component.Components.COIN_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class InventoryEffectCoinFeature {
	
	public static final int SECOND_EFFECT_DURATION = 20;
	
	public static final int DEFAULT_EFFECT_DURATION = SECOND_EFFECT_DURATION * 10;
	
	public static final int EXTENDED_EFFECT_MODIFIER = 2;
	
	public static final int EXTENDED_EFFECT_DURATION = DEFAULT_EFFECT_DURATION * EXTENDED_EFFECT_MODIFIER;
	
	public static Set<RegistryEntry<StatusEffect>> CONSTANT_EFFECTS = new HashSet<>(Arrays.asList(
			StatusEffects.NIGHT_VISION,
			StatusEffects.MINING_FATIGUE,
			StatusEffects.BAD_OMEN,
			StatusEffects.TRIAL_OMEN,
			StatusEffects.RAID_OMEN,
			StatusEffects.DOLPHINS_GRACE
	));
	
	public static Set<RegistryEntry<StatusEffect>> IGNORE_EFFECTS = new HashSet<>(Arrays.asList());
	
	public static void execute() {
		new StewEffects();
	}
	
	public static Item registerInventoryEffectCoinItem( String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects ) {
		return registerInventoryEffectCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, statusEffects, new Item.Settings() );
	}
	
	public static Item registerInventoryEffectCoinItem( String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops,  List<SuspiciousStewEffectsComponent.StewEffect> statusEffects ) {
		return registerInventoryEffectCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, lootTables, entityDrops, statusEffects, new Item.Settings() );
	}
	
	public static Item registerInventoryEffectCoinItem(String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects, Item.Settings settings ) {
		Item item = RegistryUtil.registerItem( id, new InventoryEffectCoinItem( settings, value, rarity, 1, 1, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, statusEffects ), MOD_ID );
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, item );
		
		return item;
	}
	
	public static Item registerInventoryEffectCoinItem( String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects, Item.Settings settings ) {
		Item item = registerInventoryEffectCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, statusEffects, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
	public static Item registerCraftableInventoryEffectCoinItem( String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects ) {
		return registerCraftableInventoryEffectCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, statusEffects, new Item.Settings() );
	}
	
	public static Item registerCraftableInventoryEffectCoinItem( String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects ) {
		return registerCraftableInventoryEffectCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, lootTables, entityDrops, statusEffects, new Item.Settings() );
	}
	
	public static Item registerCraftableInventoryEffectCoinItem( String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects, Item.Settings settings ) {
		return registerInventoryEffectCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, statusEffects );
	}
	
	public static Item registerCraftableInventoryEffectCoinItem( String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<RegistryKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, List<SuspiciousStewEffectsComponent.StewEffect> statusEffects, Item.Settings settings ) {
		Item item = registerCraftableInventoryEffectCoinItem( id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, statusEffects, settings );
		
		StructuresIncludeCoinsFeature.addCoinToLootTables( item, lootTables );
		MobsDropCoinsFeature.addCoinToMobDrops( item, entityDrops );
		
		return item;
	}
	
	public static boolean canApplyEffect(LivingEntity entity, SuspiciousStewEffectsComponent.StewEffect effect ) {
		return canApplyConstantEffect( entity, effect )
				||
				(
					!entity.hasStatusEffect( effect.effect() )
					&& entity.canHaveStatusEffect( effect.createStatusEffectInstance() )
					&& !IGNORE_EFFECTS.contains( effect )
				);
	}
	
	public static boolean canApplyConstantEffect(LivingEntity entity, SuspiciousStewEffectsComponent.StewEffect effect ) {
		Map<RegistryEntry<StatusEffect>, StatusEffectInstance> activeEffects = entity.getActiveStatusEffects();
		
		if( CONSTANT_EFFECTS.contains( effect.effect() ) && activeEffects.containsKey( effect.effect() ) ) {
			StatusEffectInstance activeEffect = activeEffects.get(effect.effect());
			return activeEffect.getDuration() <= EXTENDED_EFFECT_DURATION / EXTENDED_EFFECT_MODIFIER;
		} // if
		
		return false;
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
