package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.builder.StringsListBuilder;
import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CollectableComponent;
import me.villagerunknown.villagercoin.component.DropComponent;
import me.villagerunknown.villagercoin.component.LootTableComponent;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import java.util.*;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;
import static me.villagerunknown.villagercoin.component.Components.*;
import static net.minecraft.world.level.storage.loot.BuiltInLootTables.*;

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
	
	public static Set<ResourceKey<LootTable>> NETHERITE_LOOT_TABLES = new HashSet<>(Arrays.asList(
			END_CITY_TREASURE
	));
	
	public static Set<ResourceKey<LootTable>> EMERALD_LOOT_TABLES = NETHERITE_LOOT_TABLES;
	
	public static Set<ResourceKey<LootTable>> GOLD_LOOT_TABLES = combineLootTables( EMERALD_LOOT_TABLES, new HashSet<>(Arrays.asList(
			BASTION_TREASURE,
			BASTION_HOGLIN_STABLE,
			BASTION_OTHER,
			BASTION_BRIDGE,
			JUNGLE_TEMPLE,
			TRAIL_RUINS_ARCHAEOLOGY_RARE,
			SPAWNER_TRIAL_CHAMBER_KEY,
			NETHER_BRIDGE,
			WOODLAND_MANSION,
			ANCIENT_CITY,
			TRIAL_CHAMBERS_REWARD_RARE,
			TRIAL_CHAMBERS_REWARD_OMINOUS_RARE,
			TRIAL_CHAMBERS_REWARD_UNIQUE,
			TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE,
			SPAWNER_TRIAL_ITEMS_TO_DROP_WHEN_OMINOUS,
			TRIAL_CHAMBERS_REWARD,
			TRIAL_CHAMBERS_REWARD_OMINOUS,
			SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY,
			END_CITY_TREASURE,
			STRONGHOLD_LIBRARY,
			STRONGHOLD_CROSSING,
			STRONGHOLD_CORRIDOR,
			ANCIENT_CITY_ICE_BOX,
			BURIED_TREASURE
	)));
	
	public static Set<ResourceKey<LootTable>> IRON_LOOT_TABLES = combineLootTables( GOLD_LOOT_TABLES, new HashSet<>(Arrays.asList(
			VILLAGE_WEAPONSMITH,
			VILLAGE_TOOLSMITH,
			VILLAGE_ARMORER,
			SPAWN_BONUS_CHEST,
			SIMPLE_DUNGEON,
			ABANDONED_MINESHAFT,
			IGLOO_CHEST,
			SHIPWRECK_SUPPLY,
			SHIPWRECK_MAP,
			TRIAL_CHAMBERS_REWARD_COMMON,
			TRIAL_CHAMBERS_REWARD_OMINOUS_COMMON,
			ARMORER_GIFT,
			BUTCHER_GIFT,
			CARTOGRAPHER_GIFT,
			CLERIC_GIFT,
			FARMER_GIFT,
			FISHERMAN_GIFT,
			FLETCHER_GIFT,
			LEATHERWORKER_GIFT,
			LIBRARIAN_GIFT,
			MASON_GIFT,
			SHEPHERD_GIFT,
			TOOLSMITH_GIFT,
			WEAPONSMITH_GIFT,
			FISHING_TREASURE,
			DESERT_WELL_ARCHAEOLOGY,
			DESERT_PYRAMID_ARCHAEOLOGY,
			TRAIL_RUINS_ARCHAEOLOGY_COMMON,
			OCEAN_RUIN_WARM_ARCHAEOLOGY,
			OCEAN_RUIN_COLD_ARCHAEOLOGY,
			TRIAL_CHAMBERS_CORRIDOR_POT,
			TRIAL_CHAMBERS_SUPPLY,
			TRIAL_CHAMBERS_CORRIDOR,
			TRIAL_CHAMBERS_INTERSECTION,
			TRIAL_CHAMBERS_INTERSECTION_BARREL,
			TRIAL_CHAMBERS_ENTRANCE,
			SHIPWRECK_TREASURE,
			DESERT_PYRAMID,
			UNDERWATER_RUIN_SMALL,
			UNDERWATER_RUIN_BIG,
			RUINED_PORTAL,
			PILLAGER_OUTPOST
	)));
	
	public static Set<ResourceKey<LootTable>> COPPER_LOOT_TABLES = combineLootTables( IRON_LOOT_TABLES, new HashSet<>(Arrays.asList(
			FISHING_JUNK,
			VILLAGE_CARTOGRAPHER,
			VILLAGE_MASON,
			VILLAGE_SHEPHERD,
			VILLAGE_BUTCHER,
			VILLAGE_FLETCHER,
			VILLAGE_FISHER,
			VILLAGE_TANNERY,
			VILLAGE_TEMPLE,
			VILLAGE_DESERT_HOUSE,
			VILLAGE_PLAINS_HOUSE,
			VILLAGE_TAIGA_HOUSE,
			VILLAGE_SNOWY_HOUSE,
			VILLAGE_SAVANNA_HOUSE
	)));
	
	public static HashMap<ResourceKey<LootTable>, Set<Item>> LOOT_TABLES = new HashMap<>();
	
	public static HashMap<Item, LootTableComponent> LOOT_TABLE_COMPONENTS = new HashMap<>();
	
	public static void execute(){
		registerLootTableEvent();
	}
	
	@SafeVarargs
	private static Set<ResourceKey<LootTable>> combineLootTables(Set<ResourceKey<LootTable>>... lootTableCollections ) {
		Set<ResourceKey<LootTable>> combinedLootTables = new HashSet<>();
		
		for( Set<ResourceKey<LootTable>> lootTables : lootTableCollections) {
			combinedLootTables.addAll( lootTables );
		} // for
		
		return combinedLootTables;
	}
	
	public static void addCoinToLootTableComponents(Item item, LootTableComponent lootTableComponent){
		LOOT_TABLE_COMPONENTS.put( item, lootTableComponent );
	}
	
	public static void addCoinToLootTables(Item coin, Set<ResourceKey<LootTable>> lootTables ) {
		for (ResourceKey<LootTable> lootTable : lootTables) {
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
				String namespace = registryKey.identifier().getNamespace();
				boolean isVillagerCoin = namespace.equals( MOD_ID );
				
				if( Villagercoin.CONFIG.addCoinsToStructureLootTables && LOOT_TABLES.containsKey( registryKey ) ) {
					// Included Vanilla Loot Table
					LootPool.Builder poolBuilder = LootPool.lootPool();
					
					Set<Item> items = LOOT_TABLES.get( registryKey );
					
					for (Item item : items) {
						buildLootPool(poolBuilder, item);
					} // for
					
					lootBuilder.withPool(poolBuilder);
				} else if( Villagercoin.CONFIG.addCoinsToModdedStructureLootTables && lootTableSource != LootTableSource.VANILLA && !isVillagerCoin ) {
					// Modded Loot Table
					LootPool.Builder poolBuilder = LootPool.lootPool();
					String path = registryKey.identifier().getPath();
					
					boolean includeCoins = true;
					
					for (String excludeCoinKeyword : excludeCoinKeywords.getList()) {
						if( namespace.contains( excludeCoinKeyword ) || path.contains( excludeCoinKeyword ) ) {
							includeCoins = false;
							break;
						} // if
					} // for
					
					if( includeCoins && moddedLootTableContainsKeyword( path ) ) {
						Set<Item> items = new HashSet<>();
						
						Optional<ResourceKey<LootTable>> commonLootTable = IRON_LOOT_TABLES.stream().findAny();
						
						if (commonLootTable.isPresent()) {
							items = LOOT_TABLES.get(commonLootTable.get());
						} // if
						
						Optional<ResourceKey<LootTable>> rareLootTable = GOLD_LOOT_TABLES.stream().findAny();
						
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
							
							lootBuilder.withPool(poolBuilder);
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
		LootTableComponent lootTableComponent = LOOT_TABLE_COMPONENTS.get( item );
		
		if( null != lootTableComponent ) {
			CollectableComponent collectableComponent = CollectableCoinFeature.COLLECTABLE_COMPONENTS.get( item );
			DropComponent dropComponent = MobsDropCoinsFeature.MOB_DROP_COMPONENTS.get( item );
			
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
						poolBuilder.add(LootItem.lootTableItem(item).setWeight( lootTableWeight ));
						poolBuilder.setRolls( UniformGenerator.between( lootTableRolls, lootTableRolls ) );
					} // if
				} else {
					// Coins
					// Every coin has a minimum roll equal to lootTableRolls divided by COPPER_LOOT_TABLE_ROLLS
					// with the larger number dividing into the smaller number.
					poolBuilder.add( LootItem.lootTableItem( item ).setWeight( lootTableWeight ) );
					poolBuilder.setRolls( UniformGenerator.between( getMinimumLootTableRolls( lootTableRolls ), lootTableRolls ) );
				} // if, else
			} // if
		} // if
	}
	
	public static int getLootTableWeight( ResourceKey<LootTable> registryKey ) {
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
	
	public static int getLootTableRolls( ResourceKey<LootTable> registryKey ) {
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
