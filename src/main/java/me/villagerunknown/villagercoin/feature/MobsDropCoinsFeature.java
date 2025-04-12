package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.data.type.CoinComponent;
import me.villagerunknown.villagercoin.data.type.CollectableComponent;
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
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Set;

import static me.villagerunknown.villagercoin.Villagercoin.COIN_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.COLLECTABLE_COMPONENT;

public class MobsDropCoinsFeature {
	
	public static final int COMMON_DROP_MULTIPLIER = 1;
	public static final int RARE_DROP_MULTIPLIER = 2;
	public static final int EPIC_DROP_MULTIPLIER = 3;
	
	public static Set<EntityType<?>> COMMON_MOB_DROPS = Set.of(
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
	
	public static Set<EntityType<?>> RARE_MOB_DROPS = Set.of(
			EntityType.ELDER_GUARDIAN,
			EntityType.ILLUSIONER,
			EntityType.PIGLIN,
			EntityType.PIGLIN_BRUTE,
			EntityType.SHULKER,
			EntityType.EVOKER,
			EntityType.VINDICATOR,
			EntityType.WITHER_SKELETON
	);
	
	public static Set<EntityType<?>> EPIC_MOB_DROPS = Set.of(
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
						
						if( Villagercoin.CONFIG.enablePigCoinDrops ) {
							if( entity.getType().equals( EntityType.PIG ) ) {
								coins.put( CoinItems.COPPER_COIN, COMMON_DROP_MULTIPLIER );
							} else if( entity.getType().equals( EntityType.HOGLIN ) || entity.getType().equals( EntityType.ZOGLIN ) ) {
								coins.put( CoinItems.IRON_COIN, COMMON_DROP_MULTIPLIER );
							} // if, else if
						}
						
						if( entity.getType().equals( EntityType.PIGLIN ) || entity.getType().equals( EntityType.PIGLIN_BRUTE ) ) {
							coins.put( CoinItems.GOLD_COIN, RARE_DROP_MULTIPLIER );
						} else if( COMMON_MOB_DROPS.contains( entity.getType() ) ) {
							coins.put( CoinItems.COPPER_COIN, COMMON_DROP_MULTIPLIER );
							coins.put( CoinItems.IRON_COIN, COMMON_DROP_MULTIPLIER );
						} else if( RARE_MOB_DROPS.contains( entity.getType() ) ) {
							coins.put( CoinItems.IRON_COIN, COMMON_DROP_MULTIPLIER );
							coins.put( CoinItems.GOLD_COIN, COMMON_DROP_MULTIPLIER );
						} else if( EPIC_MOB_DROPS.contains( entity.getType() ) ) {
							coins.put( CoinItems.GOLD_COIN, EPIC_DROP_MULTIPLIER );
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
			
			if( null != coinComponent ) {
				if( MathUtil.hasChance( (coinComponent.dropChance() * multiplier) * lootingModifier ) ) {
					int amount = (int) MathUtil.getRandomWithinRange(coinComponent.dropMinimum(), coinComponent.dropMaximum());
					
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
	
}
