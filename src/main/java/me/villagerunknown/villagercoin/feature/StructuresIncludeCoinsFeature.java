package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.builder.StringsListBuilder;
import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CollectableComponent;
import me.villagerunknown.villagercoin.component.DropComponent;
import me.villagerunknown.villagercoin.component.LootTableComponent;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;

import java.util.*;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;
import static me.villagerunknown.villagercoin.component.Components.*;
import static net.minecraft.loot.LootTables.*;

public class StructuresIncludeCoinsFeature {
	
	public static final List<String> HIGH_VALUE_COIN_KEYWORDS = List.of(
			"gold",
			"treasure",
			"reward",
			"buried",
			"cache",
			"stash",
			"bonus"
	);
	
	public static final List<String> MODDED_LOOT_TABLE_KEYWORDS = List.of(
			"chest",
			"gameplay",
			"pot"
	);
	
	public static StringsListBuilder highValueCoinKeywords = new StringsListBuilder( MOD_ID + "-high-value-keywords-structures.json", HIGH_VALUE_COIN_KEYWORDS );
	public static StringsListBuilder moddedLootTableKeywords = new StringsListBuilder( MOD_ID + "-loot-table-keywords-modded.json", MODDED_LOOT_TABLE_KEYWORDS );
	public static StringsListBuilder excludeCoinKeywords = new StringsListBuilder( MOD_ID + "-exclude-keywords-modded-structures.json", List.of() );
	
	public static final int COPPER_LOOT_TABLE_ROLLS = Villagercoin.CONFIG.copperLootTableRolls;
	public static final int IRON_LOOT_TABLE_ROLLS = Villagercoin.CONFIG.ironLootTableRolls;
	public static final int GOLD_LOOT_TABLE_ROLLS = Villagercoin.CONFIG.goldLootTableRolls;
	public static final int EMERALD_LOOT_TABLE_ROLLS = Villagercoin.CONFIG.emeraldLootTableRolls;
	public static final int NETHERITE_LOOT_TABLE_ROLLS = Villagercoin.CONFIG.netheriteLootTableRolls;
	
	public static final int COPPER_LOOT_TABLE_WEIGHT = Villagercoin.CONFIG.copperLootTableWeight;
	public static final int IRON_LOOT_TABLE_WEIGHT = Villagercoin.CONFIG.ironLootTableWeight;
	public static final int GOLD_LOOT_TABLE_WEIGHT = Villagercoin.CONFIG.goldLootTableWeight;
	public static final int EMERALD_LOOT_TABLE_WEIGHT = Villagercoin.CONFIG.emeraldLootTableWeight;
	public static final int NETHERITE_LOOT_TABLE_WEIGHT = Villagercoin.CONFIG.netheriteLootTableWeight;
	
	public static Set<RegistryKey<LootTable>> NETHERITE_LOOT_TABLES = new HashSet<>(Arrays.asList(
			END_CITY_TREASURE_CHEST
	));
	
	public static Set<RegistryKey<LootTable>> EMERALD_LOOT_TABLES = NETHERITE_LOOT_TABLES;
	
	public static Set<RegistryKey<LootTable>> GOLD_LOOT_TABLES = combineLootTables( EMERALD_LOOT_TABLES, new HashSet<>(Arrays.asList(
			BASTION_TREASURE_CHEST,
			BASTION_HOGLIN_STABLE_CHEST,
			BASTION_OTHER_CHEST,
			BASTION_BRIDGE_CHEST,
			JUNGLE_TEMPLE_CHEST,
			TRAIL_RUINS_RARE_ARCHAEOLOGY,
			TRIAL_CHAMBER_KEY_SPAWNER,
			NETHER_BRIDGE_CHEST,
			WOODLAND_MANSION_CHEST,
			ANCIENT_CITY_CHEST,
			TRIAL_CHAMBERS_REWARD_RARE_CHEST,
			TRIAL_CHAMBERS_REWARD_OMINOUS_RARE_CHEST,
			TRIAL_CHAMBERS_REWARD_UNIQUE_CHEST,
			TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE_CHEST,
			TRIAL_CHAMBER_ITEMS_TO_DROP_WHEN_OMINOUS_SPAWNER,
			TRIAL_CHAMBERS_REWARD_CHEST,
			TRIAL_CHAMBERS_REWARD_OMINOUS_CHEST,
			OMINOUS_TRIAL_CHAMBER_KEY_SPAWNER,
			END_CITY_TREASURE_CHEST,
			STRONGHOLD_LIBRARY_CHEST,
			STRONGHOLD_CROSSING_CHEST,
			STRONGHOLD_CORRIDOR_CHEST,
			ANCIENT_CITY_ICE_BOX_CHEST,
			BURIED_TREASURE_CHEST
	)));
	
	public static Set<RegistryKey<LootTable>> IRON_LOOT_TABLES = combineLootTables( GOLD_LOOT_TABLES, new HashSet<>(Arrays.asList(
			VILLAGE_WEAPONSMITH_CHEST,
			VILLAGE_TOOLSMITH_CHEST,
			VILLAGE_ARMORER_CHEST,
			SPAWN_BONUS_CHEST,
			SIMPLE_DUNGEON_CHEST,
			ABANDONED_MINESHAFT_CHEST,
			IGLOO_CHEST_CHEST,
			SHIPWRECK_SUPPLY_CHEST,
			SHIPWRECK_MAP_CHEST,
			TRIAL_CHAMBERS_REWARD_COMMON_CHEST,
			TRIAL_CHAMBERS_REWARD_OMINOUS_COMMON_CHEST,
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
			FISHING_TREASURE_GAMEPLAY,
			DESERT_WELL_ARCHAEOLOGY,
			DESERT_PYRAMID_ARCHAEOLOGY,
			TRAIL_RUINS_COMMON_ARCHAEOLOGY,
			OCEAN_RUIN_WARM_ARCHAEOLOGY,
			OCEAN_RUIN_COLD_ARCHAEOLOGY,
			TRIAL_CHAMBERS_CORRIDOR_POT,
			TRIAL_CHAMBERS_SUPPLY_CHEST,
			TRIAL_CHAMBERS_CORRIDOR_CHEST,
			TRIAL_CHAMBERS_INTERSECTION_CHEST,
			TRIAL_CHAMBERS_INTERSECTION_BARREL_CHEST,
			TRIAL_CHAMBERS_ENTRANCE_CHEST,
			SHIPWRECK_TREASURE_CHEST,
			DESERT_PYRAMID_CHEST,
			UNDERWATER_RUIN_SMALL_CHEST,
			UNDERWATER_RUIN_BIG_CHEST,
			RUINED_PORTAL_CHEST,
			PILLAGER_OUTPOST_CHEST
	)));
	
	public static Set<RegistryKey<LootTable>> COPPER_LOOT_TABLES = combineLootTables( IRON_LOOT_TABLES, new HashSet<>(Arrays.asList(
			FISHING_JUNK_GAMEPLAY,
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
			VILLAGE_SAVANNA_HOUSE_CHEST
	)));
	
	public static HashMap<RegistryKey<LootTable>, Set<Item>> LOOT_TABLES = new HashMap<>();
	
	public static void execute(){
		registerLootTableEvent();
	}
	
	@SafeVarargs
	private static Set<RegistryKey<LootTable>> combineLootTables(Set<RegistryKey<LootTable>>... lootTableCollections ) {
		Set<RegistryKey<LootTable>> combinedLootTables = new HashSet<>();
		
		for( Set<RegistryKey<LootTable>> lootTables : lootTableCollections) {
			combinedLootTables.addAll( lootTables );
		} // for
		
		return combinedLootTables;
	}
	
	public static void addCoinToLootTables(Item coin, Set<RegistryKey<LootTable>> lootTables ) {
		for (RegistryKey<LootTable> lootTable : lootTables) {
			if( !LOOT_TABLES.containsKey( lootTable ) ) {
				LOOT_TABLES.put( lootTable, new HashSet<>() );
			} // if
			
			Set<Item> coins = LOOT_TABLES.get( lootTable );
			coins.add( coin );
			
			LOOT_TABLES.replace( lootTable, coins );
		} // for
	}
	
	private static void registerLootTableEvent() {
		LootTableEvents.MODIFY.register((registryKey, lootBuilder, lootTableSource, registryWrapper) -> {
			if( lootTableSource.isBuiltin() ) {
				String namespace = registryKey.getValue().getNamespace();
				boolean isVillagerCoin = namespace.equals( MOD_ID );
				
				if( Villagercoin.CONFIG.addCoinsToStructureLootTables && LOOT_TABLES.containsKey( registryKey ) ) {
					// Included Vanilla Loot Table
					LootPool.Builder poolBuilder = LootPool.builder();
					
					Set<Item> items = LOOT_TABLES.get( registryKey );
					
					for (Item item : items) {
						buildLootPool(poolBuilder, item);
					} // for
					
					lootBuilder.pool(poolBuilder);
				} else if( Villagercoin.CONFIG.addCoinsToModdedStructureLootTables && lootTableSource != LootTableSource.VANILLA && !isVillagerCoin ) {
					// Modded Loot Table
					LootPool.Builder poolBuilder = LootPool.builder();
					String path = registryKey.getValue().getPath();
					
					boolean includeCoins = true;
					
					for (String excludeCoinKeyword : excludeCoinKeywords.getList()) {
						if( namespace.contains( excludeCoinKeyword ) || path.contains( excludeCoinKeyword ) ) {
							includeCoins = false;
							break;
						} // if
					} // for
					
					if( includeCoins && moddedLootTableContainsKeyword( path ) ) {
						Set<Item> items = new HashSet<>();
						
						Optional<RegistryKey<LootTable>> commonLootTable = IRON_LOOT_TABLES.stream().findAny();
						
						if (commonLootTable.isPresent()) {
							items = LOOT_TABLES.get(commonLootTable.get());
						} // if
						
						Optional<RegistryKey<LootTable>> rareLootTable = GOLD_LOOT_TABLES.stream().findAny();
						
						if (rareLootTable.isPresent()) {
							for (String goldCoinKeyword : highValueCoinKeywords.getList()) {
								if (path.contains(goldCoinKeyword)) {
									items = LOOT_TABLES.get(rareLootTable.get());
									break;
								} // if
							} // for
						} // if
						
						if (!items.isEmpty()) {
							for (Item item : items) {
								buildLootPool(poolBuilder, item);
							} // for
							
							lootBuilder.pool(poolBuilder);
						} // if
					} // if
				} // if
			} // if
		});
	}
	
	private static boolean moddedLootTableContainsKeyword(String path ) {
		for(String moddedLootTableKeyword : moddedLootTableKeywords.getList()) {
			if( path.contains( moddedLootTableKeyword ) ) {
				return true;
			} // if
		} // for
		
		return false;
	}
	
	private static void buildLootPool( LootPool.Builder poolBuilder, Item item ) {
		LootTableComponent lootTableComponent = item.getComponents().get( LOOT_TABLE_COMPONENT );
		
		if( null != lootTableComponent ) {
			CollectableComponent collectableComponent = item.getComponents().get( COLLECTABLE_COMPONENT );
			DropComponent dropComponent = item.getComponents().get( DROP_COMPONENT );
			
			int lootTableWeight = lootTableComponent.lootTableWeight();
			int lootTableRolls = lootTableComponent.lootTableRolls();
			
			if( lootTableWeight > 0 && lootTableRolls > 0 ) {
				if( null != collectableComponent && null != dropComponent ) {
					// Collectable Coins
					if(
							MathUtil.hasChance( dropComponent.dropChance() * dropComponent.dropChanceMultiplier() )
									&& collectableComponent.canAddToCirculation( item )
					) {
						// Every collectable coin has a minimum roll of 1 after passing the drop chance check
						poolBuilder.with(ItemEntry.builder(item).weight( lootTableWeight ));
						poolBuilder.rolls( UniformLootNumberProvider.create( lootTableRolls, lootTableRolls ) );
					} // if
				} else {
					// Coins
					// Every coin has a minimum roll equal to lootTableRolls divided by COPPER_LOOT_TABLE_ROLLS
					// with the larger number dividing into the smaller number.
					poolBuilder.with( ItemEntry.builder( item ).weight( lootTableWeight ) );
					poolBuilder.rolls( UniformLootNumberProvider.create( getMinimumLootTableRolls( lootTableRolls ), lootTableRolls ) );
				} // if, else
			} // if
		} // if
	}
	
	public static int getLootTableWeight( RegistryKey<LootTable> registryKey ) {
		int lootTableWeight = COPPER_LOOT_TABLE_WEIGHT;
		
		if( NETHERITE_LOOT_TABLES.contains(registryKey) ) {
			lootTableWeight = NETHERITE_LOOT_TABLE_WEIGHT;
		} else if( EMERALD_LOOT_TABLES.contains(registryKey) ) {
			lootTableWeight = EMERALD_LOOT_TABLE_WEIGHT;
		} else if( GOLD_LOOT_TABLES.contains(registryKey) ) {
			lootTableWeight = GOLD_LOOT_TABLE_WEIGHT;
		} else if( IRON_LOOT_TABLES.contains(registryKey) ) {
			lootTableWeight = IRON_LOOT_TABLE_WEIGHT;
		} // if, else if ...
		
		return lootTableWeight;
	}
	
	public static int getLootTableRolls( RegistryKey<LootTable> registryKey ) {
		int lootTableRolls = COPPER_LOOT_TABLE_ROLLS;
		
		if( NETHERITE_LOOT_TABLES.contains(registryKey) ) {
			lootTableRolls = NETHERITE_LOOT_TABLE_ROLLS;
		} else if( EMERALD_LOOT_TABLES.contains(registryKey) ) {
			lootTableRolls = EMERALD_LOOT_TABLE_ROLLS;
		} else if( GOLD_LOOT_TABLES.contains(registryKey) ) {
			lootTableRolls = GOLD_LOOT_TABLE_ROLLS;
		} else if( IRON_LOOT_TABLES.contains(registryKey) ) {
			lootTableRolls = IRON_LOOT_TABLE_ROLLS;
		} // if, else if ...
		
		return lootTableRolls;
	}
	
	public static int getMinimumLootTableRolls( int lootTableRolls ) {
		if( lootTableRolls > COPPER_LOOT_TABLE_ROLLS ) {
			lootTableRolls = lootTableRolls / COPPER_LOOT_TABLE_ROLLS;
		} else {
			lootTableRolls = COPPER_LOOT_TABLE_ROLLS / lootTableRolls;
		} // if, else
		
		return lootTableRolls;
	}
	
}
