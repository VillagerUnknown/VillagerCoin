package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.recipe.VillagerCoinRecipe;
import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.platform.util.RegistryUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;
import static net.minecraft.loot.LootTables.*;

public class coinFeature {
	
	public static String COIN_STRING = "villager_coin";
	
	public static Set<String> COIN_TYPES = Set.of(
			"copper",
			"iron",
			"gold",
			"emerald",
			"netherite"
	);
	
	public static final int MAX_COUNT = 5000;
	
	public static final Set<RegistryKey<LootTable>> COMMON_LOOT_TABLES = Set.of(
			SPAWN_BONUS_CHEST,
			VILLAGE_WEAPONSMITH_CHEST,
			VILLAGE_TOOLSMITH_CHEST,
			VILLAGE_ARMORER_CHEST,
			VILLAGE_CARTOGRAPHER_CHEST,
			VILLAGE_MASON_CHEST,
			VILLAGE_SHEPARD_CHEST,
			VILLAGE_BUTCHER_CHEST,
			VILLAGE_FLETCHER_CHEST,
			VILLAGE_FISHER_CHEST,
			VILLAGE_TANNERY_CHEST,
			VILLAGE_TEMPLE_CHEST,
			VILLAGE_DESERT_HOUSE_CHEST,
			VILLAGE_PLAINS_CHEST,
			VILLAGE_TAIGA_HOUSE_CHEST,
			VILLAGE_SNOWY_HOUSE_CHEST,
			VILLAGE_SAVANNA_HOUSE_CHEST,
			SHIPWRECK_MAP_CHEST,
			TRIAL_CHAMBERS_REWARD_CHEST,
			TRIAL_CHAMBERS_REWARD_OMINOUS_CHEST,
			TRIAL_CHAMBERS_SUPPLY_CHEST,
			TRIAL_CHAMBERS_CORRIDOR_CHEST,
			TRIAL_CHAMBERS_INTERSECTION_CHEST,
			TRIAL_CHAMBERS_INTERSECTION_BARREL_CHEST,
			TRIAL_CHAMBERS_ENTRANCE_CHEST
	);
	
	public static final Set<RegistryKey<LootTable>> UNCOMMON_LOOT_TABLES = Set.of(
			SIMPLE_DUNGEON_CHEST,
			ABANDONED_MINESHAFT_CHEST,
			DESERT_PYRAMID_CHEST,
			JUNGLE_TEMPLE_CHEST,
			JUNGLE_TEMPLE_DISPENSER_CHEST,
			IGLOO_CHEST_CHEST,
			SHIPWRECK_SUPPLY_CHEST,
			TRIAL_CHAMBERS_REWARD_COMMON_CHEST,
			TRIAL_CHAMBERS_REWARD_OMINOUS_COMMON_CHEST,
			FISHING_JUNK_GAMEPLAY
	);
	
	public static final Set<RegistryKey<LootTable>> RARE_LOOT_TABLES = Set.of(
			PILLAGER_OUTPOST_CHEST,
			TRIAL_CHAMBER_KEY_SPAWNER,
			NETHER_BRIDGE_CHEST,
			WOODLAND_MANSION_CHEST,
			UNDERWATER_RUIN_SMALL_CHEST,
			UNDERWATER_RUIN_BIG_CHEST,
			BASTION_OTHER_CHEST,
			BASTION_BRIDGE_CHEST,
			ANCIENT_CITY_CHEST,
			RUINED_PORTAL_CHEST,
			TRIAL_CHAMBERS_REWARD_RARE_CHEST,
			TRIAL_CHAMBERS_REWARD_OMINOUS_RARE_CHEST,
			TRIAL_CHAMBERS_CORRIDOR_POT,
			HERO_OF_THE_VILLAGE_ARMORER_GIFT_GAMEPLAY,
			HERO_OF_THE_VILLAGE_BUTCHER_GIFT_GAMEPLAY,
			HERO_OF_THE_VILLAGE_CARTOGRAPHER_GIFT_GAMEPLAY,
			HERO_OF_THE_VILLAGE_CLERIC_GIFT_GAMEPLAY,
			HERO_OF_THE_VILLAGE_FARMER_GIFT_GAMEPLAY,
			HERO_OF_THE_VILLAGE_FISHERMAN_GIFT_GAMEPLAY,
			HERO_OF_THE_VILLAGE_FLETCHER_GIFT_GAMEPLAY,
			HERO_OF_THE_VILLAGE_LEATHERWORKER_GIFT_GAMEPLAY,
			HERO_OF_THE_VILLAGE_LIBRARIAN_GIFT_GAMEPLAY,
			HERO_OF_THE_VILLAGE_MASON_GIFT_GAMEPLAY,
			HERO_OF_THE_VILLAGE_SHEPHERD_GIFT_GAMEPLAY,
			HERO_OF_THE_VILLAGE_TOOLSMITH_GIFT_GAMEPLAY,
			HERO_OF_THE_VILLAGE_WEAPONSMITH_GIFT_GAMEPLAY,
			DESERT_WELL_ARCHAEOLOGY,
			DESERT_PYRAMID_ARCHAEOLOGY,
			TRAIL_RUINS_COMMON_ARCHAEOLOGY,
			TRAIL_RUINS_RARE_ARCHAEOLOGY,
			OCEAN_RUIN_WARM_ARCHAEOLOGY,
			OCEAN_RUIN_COLD_ARCHAEOLOGY,
			FISHING_TREASURE_GAMEPLAY
	);
	
	public static final Set<RegistryKey<LootTable>> EPIC_LOOT_TABLES = Set.of(
			BASTION_TREASURE_CHEST,
			OMINOUS_TRIAL_CHAMBER_KEY_SPAWNER,
			STRONGHOLD_LIBRARY_CHEST,
			STRONGHOLD_CROSSING_CHEST,
			STRONGHOLD_CORRIDOR_CHEST,
			BURIED_TREASURE_CHEST,
			SHIPWRECK_TREASURE_CHEST,
			BASTION_HOGLIN_STABLE_CHEST,
			ANCIENT_CITY_ICE_BOX_CHEST,
			TRIAL_CHAMBERS_REWARD_UNIQUE_CHEST,
			TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE_CHEST,
			TRIAL_CHAMBER_ITEMS_TO_DROP_WHEN_OMINOUS_SPAWNER
	);
	
	public static final Set<EntityType<?>> COMMON_MOB_DROPS = Set.of(
			EntityType.DROWNED,
			EntityType.ENDERMAN,
			EntityType.PILLAGER,
			EntityType.SKELETON,
			EntityType.BOGGED,
			EntityType.STRAY,
			EntityType.WITCH,
			EntityType.VILLAGER,
			EntityType.WANDERING_TRADER,
			EntityType.ZOMBIFIED_PIGLIN,
			EntityType.HUSK,
			EntityType.ZOMBIE,
			EntityType.ZOMBIE_VILLAGER
	);
	
	public static final Set<EntityType<?>> RARE_MOB_DROPS = Set.of(
			EntityType.ILLUSIONER,
			EntityType.PIGLIN,
			EntityType.PIGLIN_BRUTE,
			EntityType.SHULKER,
			EntityType.EVOKER,
			EntityType.VEX,
			EntityType.VINDICATOR,
			EntityType.WITHER_SKELETON
	);
	
	public static final Set<EntityType<?>> EPIC_MOB_DROPS = Set.of(
			EntityType.ENDER_DRAGON,
			EntityType.ELDER_GUARDIAN,
			EntityType.WARDEN,
			EntityType.WITHER
	);
	
	public static HashMap<String, Item> COIN_ITEMS = new HashMap<>();
	
	public static Item COPPER_COIN = null;
	public static Item IRON_COIN = null;
	public static Item GOLD_COIN = null;
	public static Item EMERALD_COIN = null;
	public static Item NETHERITE_COIN = null;
	
	public static RecipeSerializer<VillagerCoinRecipe> RECIPE_SERIALIZER = null;
	
	public static Comparator<Integer> reverseSort = new Comparator<Integer>() {
		@Override
		public int compare(Integer num1, Integer num2) {
			return num2.compareTo(num1);
		}
	};
	
	public static void execute() {
		RECIPE_SERIALIZER = (RecipeSerializer) Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of( MOD_ID, "crafting_special_villager_coin" ), new SpecialRecipeSerializer(VillagerCoinRecipe::new));
		
		registerVillagerCoinItems();
		
		COPPER_COIN = coinFeature.COIN_ITEMS.get( "copper_villager_coin" );
		IRON_COIN = coinFeature.COIN_ITEMS.get( "iron_villager_coin" );
		GOLD_COIN = coinFeature.COIN_ITEMS.get( "gold_villager_coin" );
		EMERALD_COIN = coinFeature.COIN_ITEMS.get( "emerald_villager_coin" );
		NETHERITE_COIN = coinFeature.COIN_ITEMS.get( "netherite_villager_coin" );
		
		addCoinsToLootTables();
		addCoinsToMobDrops();
		
	}
	
	private static void registerVillagerCoinItems() {
		for (String coinType : COIN_TYPES) {
			registerVillagerCoinItem( coinType );
		} // for
	}
	
	private static void registerVillagerCoinItem( String coinType ) {
		String coin_type_string = coinType + "_" + COIN_STRING;
		
		Item item = RegistryUtil.registerItem(
				coin_type_string,
				new Item( new Item.Settings().maxCount( MAX_COUNT ) ),
				MOD_ID
		);
		
		COIN_ITEMS.put( coin_type_string, item);
		RegistryUtil.addItemToGroup( ItemGroups.INGREDIENTS, item );
	}
	
	private static void addCoinsToLootTables() {
		LootTableEvents.MODIFY.register((registryKey, lootBuilder, lootTableSource, registryWrapper) -> {
			if( Villagercoin.CONFIG.addCoinsToStructureLootTables ) {
				LootPool.Builder poolBuilder = LootPool.builder();
				
				if( COMMON_LOOT_TABLES.contains( registryKey ) ) {
					poolBuilder
							.with(ItemEntry.builder(COPPER_COIN).weight(10))
							.with(ItemEntry.builder(IRON_COIN).weight(7));
				} else if( UNCOMMON_LOOT_TABLES.contains( registryKey ) ) {
					poolBuilder
							.with(ItemEntry.builder(COPPER_COIN).weight(10))
							.with(ItemEntry.builder(IRON_COIN).weight(7))
							.with(ItemEntry.builder(GOLD_COIN).weight(5));
				} else if( RARE_LOOT_TABLES.contains( registryKey ) ) {
					poolBuilder
							.with(ItemEntry.builder(IRON_COIN).weight(7))
							.with(ItemEntry.builder(GOLD_COIN).weight(5));
				} else if( EPIC_LOOT_TABLES.contains( registryKey ) ) {
					poolBuilder
							.with(ItemEntry.builder(GOLD_COIN).weight(5));
				} // if, else if ...
				
				if( EPIC_LOOT_TABLES.contains( registryKey ) ) {
					poolBuilder.rolls(UniformLootNumberProvider.create(0, 2));
				} else {
					poolBuilder.rolls(UniformLootNumberProvider.create(0, 3));
				} // if, else
				
				lootBuilder.pool(poolBuilder);
			} // if
		});
	}
	
	private static void addCoinsToMobDrops() {
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
			if( Villagercoin.CONFIG.addCoinsToMobDrops ) {
				if( null != damageSource ) {
					if( damageSource.getAttacker().isPlayer() ) {
						Set<CoinDrop> coins = new HashSet<>();
						
						HashMap<Item, Float> dropChances = new HashMap<>();
						dropChances.put( COPPER_COIN, 0.5F );
						dropChances.put( IRON_COIN, 0.25F );
						dropChances.put( GOLD_COIN, 0.1F );
						
						HashMap<Item, Integer> maximums = new HashMap<>();
						maximums.put( COPPER_COIN, 10 );
						maximums.put( IRON_COIN, 5 );
						maximums.put( GOLD_COIN, 3 );
						
						if( COMMON_MOB_DROPS.contains( entity.getType() ) ) {
							coins.add( new CoinDrop( COPPER_COIN, dropChances.get( COPPER_COIN ), 0, maximums.get( COPPER_COIN ) ) );
							coins.add( new CoinDrop( IRON_COIN, dropChances.get( IRON_COIN ), 0, maximums.get( IRON_COIN ) ) );
						} else if( RARE_MOB_DROPS.contains( entity.getType() ) ) {
							int multiplier = 2;
							coins.add( new CoinDrop( COPPER_COIN, dropChances.get( COPPER_COIN ), 0, maximums.get( COPPER_COIN ) * multiplier ) );
							coins.add( new CoinDrop( IRON_COIN, dropChances.get( IRON_COIN ), 0, maximums.get( IRON_COIN ) * multiplier ) );
							coins.add( new CoinDrop( GOLD_COIN, dropChances.get( GOLD_COIN ), 0, maximums.get( GOLD_COIN ) * multiplier ) );
						} else if( EPIC_MOB_DROPS.contains( entity.getType() ) ) {
							int multiplier = 3;
							coins.add( new CoinDrop( COPPER_COIN, dropChances.get( COPPER_COIN ), 0, maximums.get( COPPER_COIN ) * multiplier ) );
							coins.add( new CoinDrop( IRON_COIN, dropChances.get( IRON_COIN ), 0, maximums.get( IRON_COIN ) * multiplier ) );
							coins.add( new CoinDrop( GOLD_COIN, dropChances.get( GOLD_COIN ), 0, maximums.get( GOLD_COIN ) * multiplier ) );
						} // if, else if ...
						
						dropCoins( entity, damageSource, coins );
					} // if
				} // if
			} // if
		});
	}
	
	public static void dropCoins( LivingEntity entity, DamageSource damageSource, Set<CoinDrop> coins ) {
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
		
		for (CoinDrop coinDrop : coins) {
			if( MathUtil.hasChance( coinDrop.chance * modifier ) ) {
				int amount = (int) MathUtil.getRandomWithinRange(coinDrop.min, coinDrop.max);
				
				entity.dropStack(new ItemStack(coinDrop.coin, amount));
			} // if
		} // for
	}
	
	public static int getCoinValue( Item item ) {
		int value = 1; // Copper
		
		if( item.equals( coinFeature.IRON_COIN ) ) {
			value = 100;
		}
		else if( item.equals( coinFeature.GOLD_COIN ) ) {
			value = 10000;
		}
		else if( item.equals( coinFeature.EMERALD_COIN ) ) {
			value = 1000000;
		}
		else if( item.equals( coinFeature.NETHERITE_COIN ) ) {
			value = 100000000;
		} // if, else if ...
		
		return value;
	}
	
	public static int getConversionValue( Item fromThis, Item toThis ) {
		int from = coinFeature.getCoinValue( fromThis );
		int to = coinFeature.getCoinValue( toThis );
		int result = 1;
		
		if( from > to ) {
			result = from / to;
		} else {
			result = to / from;
		} // if, else
		
		return result;
	}
	
	public static class CoinIngredient {
		
		public int slot;
		public int k;
		public int l;
		public ItemStack stack;
		
		public CoinIngredient(int slot, ItemStack stack, int k, int l) {
			this.slot = slot;
			this.k = k;
			this.l = l;
			this.stack = stack;
		}
		
	}
	
	public static class CoinDrop {
		
		public Item coin;
		public float chance = 0.33F;
		public int min = 1;
		public int max = 5;
		
		public CoinDrop( Item coin, Float chance, int min, int max ) {
			this.coin = coin;
			this.chance = chance;
			this.min = min;
			this.max = max;
		}
		
	}
	
}
