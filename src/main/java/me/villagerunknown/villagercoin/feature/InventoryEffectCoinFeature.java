package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.EntityUtil;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CoinComponent;
import me.villagerunknown.villagercoin.component.CollectableComponent;
import me.villagerunknown.villagercoin.component.DropComponent;
import me.villagerunknown.villagercoin.component.LootTableComponent;
import me.villagerunknown.villagercoin.effect.StewEffects;
import me.villagerunknown.villagercoin.item.InventoryEffectCoinItem;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.storage.loot.LootTable;
import java.util.*;

import static me.villagerunknown.villagercoin.component.Components.COIN_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class InventoryEffectCoinFeature {
	
	public static final int SECOND_EFFECT_DURATION = 20;
	
	public static final int DEFAULT_EFFECT_DURATION = SECOND_EFFECT_DURATION * 10;
	
	public static final int EXTENDED_EFFECT_MODIFIER = 2;
	
	public static final int EXTENDED_EFFECT_DURATION = DEFAULT_EFFECT_DURATION * EXTENDED_EFFECT_MODIFIER;
	
	public static Set<Holder<MobEffect>> CONSTANT_EFFECTS = new HashSet<>(Arrays.asList(
			MobEffects.NIGHT_VISION,
			MobEffects.MINING_FATIGUE,
			MobEffects.BAD_OMEN,
			MobEffects.TRIAL_OMEN,
			MobEffects.RAID_OMEN,
			MobEffects.DOLPHINS_GRACE
	));
	
	public static Set<Holder<MobEffect>> IGNORE_EFFECTS = new HashSet<>(Arrays.asList());
	
	public static void execute() {
		new StewEffects();
	}
	
	public static Item registerInventoryEffectCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffects.Entry> statusEffects ) {
		return registerInventoryEffectCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, statusEffects, new Item.Properties() );
	}
	
	public static Item registerInventoryEffectCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<ResourceKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops,  List<SuspiciousStewEffects.Entry> statusEffects ) {
		return registerInventoryEffectCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, lootTables, entityDrops, statusEffects, new Item.Properties() );
	}
	
	public static Item registerInventoryEffectCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffects.Entry> statusEffects, Item.Properties settings ) {
		settings.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID,id)));
		
		Item item = RegistryUtil.registerItem( id, new InventoryEffectCoinItem( settings, value, rarity, 1, 1, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, statusEffects ), namespace );
		
		Villagercoin.addItemToGroup( item );
		
		return item;
	}
	
	public static Item registerInventoryEffectCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<ResourceKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, List<SuspiciousStewEffects.Entry> statusEffects, Item.Properties settings ) {
		Item item = registerInventoryEffectCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, statusEffects, settings );
		
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
	
	public static Item registerCraftableInventoryEffectCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffects.Entry> statusEffects ) {
		return registerCraftableInventoryEffectCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, statusEffects, new Item.Properties() );
	}
	
	public static Item registerCraftableInventoryEffectCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<ResourceKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, List<SuspiciousStewEffects.Entry> statusEffects ) {
		return registerCraftableInventoryEffectCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, lootTables, entityDrops, statusEffects, new Item.Properties() );
	}
	
	public static Item registerCraftableInventoryEffectCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, List<SuspiciousStewEffects.Entry> statusEffects, Item.Properties settings ) {
		return registerInventoryEffectCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, statusEffects );
	}
	
	public static Item registerCraftableInventoryEffectCoinItem( String namespace, String id, long value, Rarity rarity, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance, int maximumAllowedInServer, Set<ResourceKey<LootTable>> lootTables, Set<EntityType<?>> entityDrops, List<SuspiciousStewEffects.Entry> statusEffects, Item.Properties settings ) {
		Item item = registerCraftableInventoryEffectCoinItem( namespace, id, value, rarity, dropChance, dropChanceMultiplier, lootTableWeight, lootTableRolls, flipChance, maximumAllowedInServer, statusEffects, settings );
		
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
	
	public static boolean canApplyEffect(LivingEntity entity, SuspiciousStewEffects.Entry effect ) {
		return canApplyConstantEffect( entity, effect )
				||
				(
					!entity.hasEffect( effect.effect() )
					&& entity.canBeAffected( effect.createEffectInstance() )
					&& !IGNORE_EFFECTS.contains( effect )
				);
	}
	
	public static boolean canApplyConstantEffect(LivingEntity entity, SuspiciousStewEffects.Entry effect ) {
		Map<Holder<MobEffect>, MobEffectInstance> activeEffects = entity.getActiveEffectsMap();
		
		if( CONSTANT_EFFECTS.contains( effect.effect() ) && activeEffects.containsKey( effect.effect() ) ) {
			MobEffectInstance activeEffect = activeEffects.get(effect.effect());
			return activeEffect.getDuration() <= EXTENDED_EFFECT_DURATION / EXTENDED_EFFECT_MODIFIER;
		} // if
		
		return false;
	}
	
	public static void applyStatusEffect( LivingEntity entity, SuspiciousStewEffects.Entry effect, Item coin ) {
		CoinComponent coinComponent = coin.components().get( COIN_COMPONENT );
		
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
