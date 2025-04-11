package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;

import java.util.Set;

import static me.villagerunknown.villagercoin.feature.coinFeature.*;
import static net.minecraft.loot.LootTables.*;

public class structuresIncludeCoinsFeature {
	
	public static final Set<RegistryKey<LootTable>> COMMON_LOOT_TABLES = Set.of(
			FISHING_JUNK_GAMEPLAY,
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
			VILLAGE_SAVANNA_HOUSE_CHEST
	);
	
	public static final Set<RegistryKey<LootTable>> UNCOMMON_LOOT_TABLES = Set.of(
			SPAWN_BONUS_CHEST,
			SIMPLE_DUNGEON_CHEST,
			ABANDONED_MINESHAFT_CHEST,
			JUNGLE_TEMPLE_DISPENSER_CHEST,
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
	);
	
	public static final Set<RegistryKey<LootTable>> RARE_LOOT_TABLES = Set.of(
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
	);
	
	public static final Set<RegistryKey<LootTable>> EPIC_LOOT_TABLES = Set.of(
			BASTION_TREASURE_CHEST,
			BASTION_HOGLIN_STABLE_CHEST,
			BASTION_OTHER_CHEST,
			BASTION_BRIDGE_CHEST
	);
	
	public static void execute(){
		addCoinsToLootTables();
	}
	
	private static void addCoinsToLootTables() {
		LootTableEvents.MODIFY.register((registryKey, lootBuilder, lootTableSource, registryWrapper) -> {
			if( lootTableSource.isBuiltin() && Villagercoin.CONFIG.addCoinsToStructureLootTables ) {
				LootPool.Builder poolBuilder = LootPool.builder();
				
				if( COMMON_LOOT_TABLES.contains( registryKey ) || UNCOMMON_LOOT_TABLES.contains( registryKey ) ) {
					poolBuilder
							.with(ItemEntry.builder(COPPER_COIN).weight(10))
							.rolls(UniformLootNumberProvider.create(0, 5));
				} // if
				
				if( UNCOMMON_LOOT_TABLES.contains( registryKey ) ) {
					poolBuilder
							.with(ItemEntry.builder(IRON_COIN).weight(7));
				} // if
				
				if( RARE_LOOT_TABLES.contains( registryKey ) ) {
					poolBuilder
							.with(ItemEntry.builder(IRON_COIN).weight(7))
							.rolls(UniformLootNumberProvider.create(0, 7));
				}  // if
				
				if( RARE_LOOT_TABLES.contains( registryKey ) || EPIC_LOOT_TABLES.contains( registryKey ) ) {
					poolBuilder
							.with(ItemEntry.builder(GOLD_COIN).weight(5));
				}  // if
				
				if( EPIC_LOOT_TABLES.contains( registryKey ) ) {
					poolBuilder
							.rolls(UniformLootNumberProvider.create(0, 10));
				} // if
				
				lootBuilder.pool(poolBuilder);
			} // if
		});
	}
	
}
