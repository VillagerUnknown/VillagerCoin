package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.type.CoinComponent;
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
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Set;

import static me.villagerunknown.villagercoin.Villagercoin.COIN_COMPONENT;
import static me.villagerunknown.villagercoin.feature.coinFeature.*;

public class mobsDropCoinsFeature {
	
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
			EntityType.ELDER_GUARDIAN,
			EntityType.ILLUSIONER,
			EntityType.PIGLIN,
			EntityType.PIGLIN_BRUTE,
			EntityType.SHULKER,
			EntityType.EVOKER,
			EntityType.VINDICATOR,
			EntityType.WITHER_SKELETON
	);
	
	public static final Set<EntityType<?>> EPIC_MOB_DROPS = Set.of(
			EntityType.ENDER_DRAGON,
			EntityType.WARDEN,
			EntityType.WITHER
	);
	
	public static void execute(){
		addCoinsToMobDrops();
	}
	
	private static void addCoinsToMobDrops() {
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
			if( Villagercoin.CONFIG.addCoinsToMobDrops ) {
				if( null != damageSource && null != damageSource.getAttacker() ) {
					if( damageSource.getAttacker().isPlayer() ) {
						HashMap<Item, Integer> coins = new HashMap<>();
						
						int multiplier = 2;
						
						if( Villagercoin.CONFIG.enablePigCoinDrops ) {
							if( entity.getType().equals( EntityType.PIG ) ) {
								coins.put( COPPER_COIN, 1 );
							} else if( entity.getType().equals( EntityType.HOGLIN ) || entity.getType().equals( EntityType.ZOGLIN ) ) {
								coins.put( IRON_COIN, 1 );
							} // if, else if
						}
						
						if( entity.getType().equals( EntityType.PIGLIN ) || entity.getType().equals( EntityType.PIGLIN_BRUTE ) ) {
							coins.put( GOLD_COIN, multiplier );
							
							if( Villagercoin.CONFIG.addEdibleCoinsToMobDrops ) {
								coins.put( edibleCoinFeature.EDIBLE_GOLD_COIN, 1 );
							} // if
						} else if( COMMON_MOB_DROPS.contains( entity.getType() ) ) {
							coins.put( COPPER_COIN, 1 );
							coins.put( IRON_COIN, 1 );
							
							if( Villagercoin.CONFIG.addEdibleCoinsToMobDrops ) {
								coins.put( edibleCoinFeature.EDIBLE_GOLD_COIN, 1 );
							} // if
						} else if( RARE_MOB_DROPS.contains( entity.getType() ) ) {
							coins.put( IRON_COIN, 1 );
							coins.put( GOLD_COIN, 1 );
							
							if( Villagercoin.CONFIG.addEdibleCoinsToMobDrops ) {
								coins.put( edibleCoinFeature.EDIBLE_EMERALD_COIN, 1 );
							} // if
						} else if( EPIC_MOB_DROPS.contains( entity.getType() ) ) {
							multiplier = 3;
							coins.put( GOLD_COIN, multiplier );
							
							if( Villagercoin.CONFIG.addEdibleCoinsToMobDrops ) {
								coins.put( edibleCoinFeature.EDIBLE_NETHERITE_COIN, 1 );
							} // if
						} // if, else if ...
						
						dropCoins( entity, damageSource, coins );
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
			CoinComponent coinComponent = coin.getComponents().get( COIN_COMPONENT );
			
			if( null != coinComponent && MathUtil.hasChance( (coinComponent.dropChance() * multiplier) * lootingModifier ) ) {
				int amount = (int) MathUtil.getRandomWithinRange(coinComponent.dropMinimum(), coinComponent.dropMaximum());
				
				entity.dropStack(new ItemStack(coin, amount));
			} // if
		} );
	}
	
}
