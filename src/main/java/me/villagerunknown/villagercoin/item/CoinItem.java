package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CoinComponent;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.component.DropComponent;
import me.villagerunknown.villagercoin.component.LootTableComponent;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import me.villagerunknown.villagercoin.feature.MobsDropCoinsFeature;
import me.villagerunknown.villagercoin.feature.StructuresIncludeCoinsFeature;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static me.villagerunknown.villagercoin.component.Components.*;

public class CoinItem extends AbstractFlippableCoinItem {
	
	public CoinItem(Settings settings) {
		super(
				settings
						.maxCount( Villagercoin.MAX_STACK_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( CoinFeature.COPPER_RARITY, CoinFeature.COPPER_FLIP_CHANCE ) )
						.component( DROP_COMPONENT, new DropComponent( MobsDropCoinsFeature.COPPER_DROP_MINIMUM, MobsDropCoinsFeature.COPPER_DROP_MAXIMUM, MobsDropCoinsFeature.COPPER_DROP_CHANCE, MobsDropCoinsFeature.COPPER_DROP_MULTIPLIER) )
						.component( LOOT_TABLE_COMPONENT, new LootTableComponent( StructuresIncludeCoinsFeature.COPPER_LOOT_TABLE_WEIGHT, StructuresIncludeCoinsFeature.COPPER_LOOT_TABLE_ROLLS ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( CoinFeature.COPPER_VALUE ) )
		);
	}
	
	public CoinItem(Settings settings, long value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier, int lootTableWeight, int lootTableRolls, float flipChance) {
		super(
				settings
						.maxCount( Villagercoin.MAX_STACK_COUNT )
						.component( COIN_COMPONENT, new CoinComponent( rarity, flipChance ) )
						.component( DROP_COMPONENT, new DropComponent( dropMinimum, dropMaximum, dropChance, dropChanceMultiplier ) )
						.component( LOOT_TABLE_COMPONENT, new LootTableComponent( lootTableWeight, lootTableRolls ) )
						.component( CURRENCY_COMPONENT, new CurrencyComponent( value ) )
		);
	}
	
}
