package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.builder.StringsListBuilder;
import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CollectableComponent;
import me.villagerunknown.villagercoin.component.DropComponent;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.*;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;
import static me.villagerunknown.villagercoin.component.Components.*;

public class MobsDropCoinsFeature {
	
	public static final List<String> HIGH_VALUE_COIN_KEYWORDS = List.of(
			"gold",
			"diamond",
			"emerald",
			"netherite",
			"wither",
			"dragon",
			"boss",
			"giant"
	);
	
	public static StringsListBuilder highValueCoinKeywords = new StringsListBuilder( MOD_ID + "-high-value-keywords-mobs.json", HIGH_VALUE_COIN_KEYWORDS );
	public static StringsListBuilder excludeCoinKeywords = new StringsListBuilder( MOD_ID + "-exclude-keywords-modded-mobs.json", List.of() );
	
	public static final int COPPER_DROP_MINIMUM = Villagercoin.CONFIG.copperDropMinimum;
	public static final int IRON_DROP_MINIMUM = Villagercoin.CONFIG.ironDropMinimum;
	public static final int GOLD_DROP_MINIMUM = Villagercoin.CONFIG.goldDropMinimum;
	public static final int EMERALD_DROP_MINIMUM = Villagercoin.CONFIG.emeraldDropMinimum;
	public static final int NETHERITE_DROP_MINIMUM = Villagercoin.CONFIG.netheriteDropMinimum;
	
	public static final int COPPER_DROP_MAXIMUM = Villagercoin.CONFIG.copperDropMaximum;
	public static final int IRON_DROP_MAXIMUM = Villagercoin.CONFIG.ironDropMaximum;
	public static final int GOLD_DROP_MAXIMUM = Villagercoin.CONFIG.goldDropMaximum;
	public static final int EMERALD_DROP_MAXIMUM = Villagercoin.CONFIG.emeraldDropMaximum;
	public static final int NETHERITE_DROP_MAXIMUM = Villagercoin.CONFIG.netheriteDropMaximum;
	
	public static final float COPPER_DROP_CHANCE = Villagercoin.CONFIG.copperDropChance;
	public static final float IRON_DROP_CHANCE = Villagercoin.CONFIG.ironDropChance;
	public static final float GOLD_DROP_CHANCE = Villagercoin.CONFIG.goldDropChance;
	public static final float EMERALD_DROP_CHANCE = Villagercoin.CONFIG.emeraldDropChance;
	public static final float NETHERITE_DROP_CHANCE = Villagercoin.CONFIG.netheriteDropChance;
	
	public static final int COPPER_DROP_MULTIPLIER = Villagercoin.CONFIG.copperDropMultiplier;
	public static final int IRON_DROP_MULTIPLIER = Villagercoin.CONFIG.ironDropMultiplier;
	public static final int GOLD_DROP_MULTIPLIER = Villagercoin.CONFIG.goldDropMultiplier;
	public static final int EMERALD_DROP_MULTIPLIER = Villagercoin.CONFIG.emeraldDropMultiplier;
	public static final int NETHERITE_DROP_MULTIPLIER = Villagercoin.CONFIG.netheriteDropMultiplier;
	
	public static HashMap<EntityType<?>, Set<Item>> MOB_DROPS = new HashMap<>();
	
	public static Set<Item> COPPER_COIN_DROPS = new HashSet<>();
	
	public static Set<Item> IRON_COIN_DROPS = new HashSet<>();
	
	public static Set<Item> GOLD_COIN_DROPS = new HashSet<>();
	
	public static Set<Item> EMERALD_COIN_DROPS = new HashSet<>();
	
	public static Set<Item> NETHERITE_COIN_DROPS = new HashSet<>();
	
	public static void execute(){
		addDefaultCoinDrops();
		registerMobDropsEvent();
	}
	
	private static void addDefaultCoinDrops() {
		addCoinToDropList( CoinItems.COPPER_COIN, COPPER_COIN_DROPS );
		addCoinToDropList( CoinItems.IRON_COIN, IRON_COIN_DROPS );
		addCoinToDropList( CoinItems.GOLD_COIN, GOLD_COIN_DROPS );
		addCoinToDropList( CoinItems.EMERALD_COIN, EMERALD_COIN_DROPS );
		addCoinToDropList( CoinItems.NETHERITE_COIN, NETHERITE_COIN_DROPS );
	}
	
	public static void addCoinToMobDrops( Item coin, Set<EntityType<?>> entityTypes ) {
		for (EntityType<?> entityType : entityTypes) {
			if( !MOB_DROPS.containsKey( entityType ) ) {
				MOB_DROPS.put( entityType, new HashSet<>() );
			} // if
			
			Set<Item> coins = MOB_DROPS.get( entityType );
			coins.add( coin );
			
			MOB_DROPS.replace( entityType, coins );
		} // for
	}
	
	public static void addCoinToDropList( Item coin, Set<Item> list ) {
		list.add( coin );
	}
	
	private static void registerMobDropsEvent() {
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
			if( Villagercoin.CONFIG.addCoinsToMobDrops ) {
				if( null != damageSource && null != damageSource.getAttacker() ) {
					if( damageSource.getAttacker().isPlayer() ) {
						EntityType<?> entityType = entity.getType();
						
						String namespace = entityType.getLootTableId().getValue().getNamespace();
						String path = entityType.getLootTableId().getValue().getPath();
						
						Set<Item> items = new HashSet<>();
						
						boolean isVanilla = namespace.contains("minecraft");
						
						if( isVanilla ) {
							// # Included Vanilla Entity
							
							// Copper & Optionals
							if(
									entityType.isIn( TagKey.of( RegistryKeys.ENTITY_TYPE, Identifier.of( Villagercoin.MOD_ID, "copper" ) ) )
									||
									(
										Villagercoin.CONFIG.enableBreedableMobDrops
										&& entityType.isIn( TagKey.of( RegistryKeys.ENTITY_TYPE, Identifier.of( Villagercoin.MOD_ID, "optional" ) ) )
									)
							) {
								items.addAll( COPPER_COIN_DROPS );
							} // if
							
							// Iron
							if( entityType.isIn( TagKey.of( RegistryKeys.ENTITY_TYPE, Identifier.of( Villagercoin.MOD_ID, "iron" ) ) ) ) {
								items.addAll( IRON_COIN_DROPS );
							} // if
							
							// Gold
							if( entityType.isIn( TagKey.of( RegistryKeys.ENTITY_TYPE, Identifier.of( Villagercoin.MOD_ID, "gold" ) ) ) ) {
								items.addAll( GOLD_COIN_DROPS );
							} // if
							
							// Emerald
							if( entityType.isIn( TagKey.of( RegistryKeys.ENTITY_TYPE, Identifier.of( Villagercoin.MOD_ID, "emerald" ) ) ) ) {
								items.addAll( EMERALD_COIN_DROPS );
							} // if
							
							// Netherite
							if( entityType.isIn( TagKey.of( RegistryKeys.ENTITY_TYPE, Identifier.of( Villagercoin.MOD_ID, "netherite" ) ) ) ) {
								items.addAll( NETHERITE_COIN_DROPS );
							} // if
							
							// Specifics
							if( MOB_DROPS.containsKey( entityType ) ) {
								items.addAll( MOB_DROPS.get( entityType ) );
							} // if
						} else if( !isVanilla && Villagercoin.CONFIG.addCoinsToModdedMobDrops ) {
							// # Modded Entity
							boolean includeCoins = true;
							
							for (String excludeCoinKeyword : excludeCoinKeywords.getList()) {
								if( namespace.contains( excludeCoinKeyword ) || path.contains( excludeCoinKeyword ) ) {
									includeCoins = false;
									break;
								} // if
							} // for
							
							if( includeCoins ) {
								items.add(CoinItems.COPPER_COIN);
								items.add(CoinItems.IRON_COIN);
								
								for (String highValueCoinKeyword : highValueCoinKeywords.getList()) {
									if (path.contains(highValueCoinKeyword)) {
										items.add(CoinItems.GOLD_COIN);
										break;
									} // if
								} // for
							} // if
						} // if
						
						HashMap<Item, Integer> coinsToDrop = buildCoinsList( items, entityType );
						
						dropCoins( entity, damageSource, coinsToDrop );
					} // if
				} // if
			} // if
		});
	}
	
	public static HashMap<Item, Integer> buildCoinsList(Set<Item> items, EntityType<?> entityType ) {
		HashMap<Item, Integer> coinsToDrop = new HashMap<>();
		
		int dropMultiplier = getDropMultiplier( entityType );
		
		for (Item item : items) {
			coinsToDrop.put( item, dropMultiplier );
		} // for
		
		return coinsToDrop;
	}
	
	public static void dropCoins(LivingEntity entity, DamageSource damageSource, HashMap<Item, Integer> coins ) {
		if( null == damageSource ) {
			return;
		} // if
		
		World world = entity.getWorld();
		Entity source = damageSource.getSource();
		
		float modifier = 1;
		
		if( null != source ) {
			ItemStack weapon = damageSource.getWeaponStack();
			
			if (null != weapon) {
				ItemEnchantmentsComponent enchantments = EnchantmentHelper.getEnchantments(weapon);
				DynamicRegistryManager drm = world.getRegistryManager();
				
				if (null != drm) {
					Registry<Enchantment> registry = drm.get(RegistryKeys.ENCHANTMENT);
					Enchantment lootingEnchantment = registry.get(Enchantments.LOOTING);
					RegistryEntry<Enchantment> lootingEntry = registry.getEntry(lootingEnchantment);
					
					int enchantmentLevel = enchantments.getLevel(lootingEntry);
					
					if (enchantmentLevel > 0) {
						modifier = (1 + (Villagercoin.CONFIG.lootingBonusPerLevel * enchantmentLevel));
					} // if
				} // if
			} // if
		} // if
		
		float lootingModifier = getDropMultiplier( entity.getType() ) + modifier;
		coins.forEach( ( coin, multiplier ) -> {
			DropComponent dropComponent = coin.getComponents().get( DROP_COMPONENT );
			
			if( null != dropComponent ) {
				if( dropComponent.dropMaximum() > 0 && MathUtil.hasChance( (dropComponent.dropChance() * dropComponent.dropChanceMultiplier()) * lootingModifier ) ) {
					int amount = (int) MathUtil.getRandomWithinRange(dropComponent.dropMinimum(), dropComponent.dropMaximum() * multiplier);
					
					if( amount > 0 ) {
						CollectableComponent collectableComponent = coin.getComponents().get( COLLECTABLE_COMPONENT );
						
						if( null != collectableComponent ) {
							if( collectableComponent.canAddToCirculation( coin ) ) {
								collectableComponent.addToCirculation( coin, amount );
								
								entity.dropStack(new ItemStack(coin, amount));
							} // if
						} else {
							entity.dropStack(new ItemStack(coin, amount));
						} // if, else
					} // if
				} // if
			} // if
		} );
	}
	
	public static int getDropMultiplier( EntityType<?> entityType ) {
		int dropMultiplier = COPPER_DROP_MULTIPLIER;
		
		if( entityType.isIn( TagKey.of( RegistryKeys.ENTITY_TYPE, Identifier.of( Villagercoin.MOD_ID, "netherite" ) ) ) ) {
			dropMultiplier = NETHERITE_DROP_MULTIPLIER;
		} else if( entityType.isIn( TagKey.of( RegistryKeys.ENTITY_TYPE, Identifier.of( Villagercoin.MOD_ID, "emerald" ) ) ) ) {
			dropMultiplier = EMERALD_DROP_MULTIPLIER;
		} else if( entityType.isIn( TagKey.of( RegistryKeys.ENTITY_TYPE, Identifier.of( Villagercoin.MOD_ID, "gold" ) ) ) ) {
			dropMultiplier = GOLD_DROP_MULTIPLIER;
		} else if( entityType.isIn( TagKey.of( RegistryKeys.ENTITY_TYPE, Identifier.of( Villagercoin.MOD_ID, "iron" ) ) ) ) {
			dropMultiplier = IRON_DROP_MULTIPLIER;
		} // if, else if ...
		
		return dropMultiplier;
	}
	
}
