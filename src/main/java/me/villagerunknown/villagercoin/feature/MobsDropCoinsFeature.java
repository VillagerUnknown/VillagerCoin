package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.component.CoinComponent;
import me.villagerunknown.villagercoin.data.component.CollectableComponent;
import me.villagerunknown.villagercoin.data.component.CurrencyComponent;
import me.villagerunknown.villagercoin.data.component.DropComponent;
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
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static me.villagerunknown.villagercoin.Villagercoin.*;

public class MobsDropCoinsFeature {
	
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
	
	public static Set<EntityType<?>> OPTIONAL_MOB_DROPS = new HashSet<>(Arrays.asList(
			EntityType.PIG,
			EntityType.HOGLIN,
			EntityType.ZOGLIN,
			EntityType.COW,
			EntityType.SHEEP,
			EntityType.HORSE,
			EntityType.LLAMA,
			EntityType.TRADER_LLAMA,
			EntityType.RABBIT,
			EntityType.PANDA,
			EntityType.CAMEL,
			EntityType.CAT,
			EntityType.WOLF,
			EntityType.FOX,
			EntityType.CHICKEN,
			EntityType.MOOSHROOM,
			EntityType.FROG,
			EntityType.GOAT,
			EntityType.DONKEY,
			EntityType.MULE,
			EntityType.OCELOT,
			EntityType.PARROT,
			EntityType.AXOLOTL,
			EntityType.POLAR_BEAR,
			EntityType.SNIFFER,
			EntityType.TURTLE
	));
	
	public static Set<EntityType<?>> NETHERITE_MOB_DROPS = new HashSet<>(Arrays.asList(
			EntityType.ENDER_DRAGON,
			EntityType.WARDEN,
			EntityType.WITHER
	));
	
	public static Set<EntityType<?>> EMERALD_MOB_DROPS = combineEntityTypes( NETHERITE_MOB_DROPS, new HashSet<>(Arrays.asList(
			EntityType.SHULKER
	)));
	
	public static Set<EntityType<?>> GOLD_MOB_DROPS = combineEntityTypes( EMERALD_MOB_DROPS, new HashSet<>(Arrays.asList(
			EntityType.ELDER_GUARDIAN,
			EntityType.ILLUSIONER,
			EntityType.PIGLIN,
			EntityType.PIGLIN_BRUTE,
			EntityType.EVOKER,
			EntityType.VINDICATOR,
			EntityType.WITHER_SKELETON
	)));
	
	public static Set<EntityType<?>> IRON_MOB_DROPS = combineEntityTypes( GOLD_MOB_DROPS, new HashSet<>(Arrays.asList(
			EntityType.ENDERMAN,
			EntityType.PILLAGER,
			EntityType.BOGGED,
			EntityType.STRAY,
			EntityType.WITCH,
			EntityType.ZOMBIFIED_PIGLIN
	)));
	
	public static Set<EntityType<?>> COPPER_MOB_DROPS = buildCopperMobDrops(new HashSet<>(Arrays.asList(
			EntityType.DROWNED,
			EntityType.SKELETON,
			EntityType.VILLAGER,
			EntityType.WANDERING_TRADER,
			EntityType.HUSK,
			EntityType.ZOMBIE,
			EntityType.ZOMBIE_VILLAGER
	)));
	
	public static HashMap<EntityType<?>, Set<Item>> MOB_DROPS = new HashMap<>();
	
	public static void execute(){
		if( Villagercoin.CONFIG.enableBreedableMobDrops ) {
			COPPER_MOB_DROPS = combineEntityTypes( COPPER_MOB_DROPS, OPTIONAL_MOB_DROPS );
		} // if
		
		registerMobDropsEvent();
	}
	
	@SafeVarargs
	private static Set<EntityType<?>> combineEntityTypes(Set<EntityType<?>>... entityTypeCollections ) {
		Set<EntityType<?>> combinedEntityTypes = new HashSet<>();
		
		for( Set<EntityType<?>> entityTypes : entityTypeCollections) {
			combinedEntityTypes.addAll( entityTypes );
		} // for
		
		return combinedEntityTypes;
	}
	
	private static Set<EntityType<?>> buildCopperMobDrops( Set<EntityType<?>> entityTypes ) {
		Set<EntityType<?>> copperMobDrops = combineEntityTypes( IRON_MOB_DROPS, entityTypes );
		
		if( Villagercoin.CONFIG.enableBreedableMobDrops ) {
			copperMobDrops = combineEntityTypes( copperMobDrops, OPTIONAL_MOB_DROPS );
		} // if
		
		return copperMobDrops;
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
	
	private static void registerMobDropsEvent() {
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
			if( Villagercoin.CONFIG.addCoinsToMobDrops ) {
				if( null != damageSource && null != damageSource.getAttacker() ) {
					if( damageSource.getAttacker().isPlayer() ) {
						HashMap<Item, Integer> coinsToDrop = new HashMap<>();
						
						EntityType<?> entityType = entity.getType();
						
						if( MOB_DROPS.containsKey( entityType ) ) {
							Set<Item> items = MOB_DROPS.get( entityType );
							
							for (Item item : items) {
								DropComponent dropComponent = item.getComponents().get( DROP_COMPONENT );
								
								int dropMultiplier = getDropMultiplier( entityType );
								
								if( null != dropComponent ) {
									dropMultiplier = dropComponent.dropChanceMultiplier();
								} // if
								
								coinsToDrop.put( item, dropMultiplier );
							} // for
						} // if
						
						dropCoins( entity, damageSource, coinsToDrop );
					} // if
				} // if
			} // if
		});
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
		
		float lootingModifier = modifier;
		coins.forEach( ( coin, multiplier ) -> {
			DropComponent dropComponent = coin.getComponents().get( DROP_COMPONENT );
			
			if( null != dropComponent ) {
				if( dropComponent.dropMaximum() > 0 && MathUtil.hasChance( (dropComponent.dropChance() * multiplier) * lootingModifier ) ) {
					int amount = (int) MathUtil.getRandomWithinRange(dropComponent.dropMinimum(), dropComponent.dropMaximum());
					
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
		
		if( NETHERITE_MOB_DROPS.contains( entityType ) ) {
			dropMultiplier = NETHERITE_DROP_MULTIPLIER;
		} else if( EMERALD_MOB_DROPS.contains( entityType ) ) {
			dropMultiplier = EMERALD_DROP_MULTIPLIER;
		} else if( GOLD_MOB_DROPS.contains( entityType ) ) {
			dropMultiplier = GOLD_DROP_MULTIPLIER;
		} else if( IRON_MOB_DROPS.contains( entityType ) ) {
			dropMultiplier = IRON_DROP_MULTIPLIER;
		} // if, else if ...
		
		return dropMultiplier;
	}
	
}
