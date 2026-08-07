package me.villagerunknown.villagercoin.effect;

import me.villagerunknown.villagercoin.feature.InventoryEffectCoinFeature;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.SuspiciousStewEffects;

public class StewEffects {
	
	public static final SuspiciousStewEffects.Entry ABSORPTION;
	public static final SuspiciousStewEffects.Entry BAD_OMEN;
	public static final SuspiciousStewEffects.Entry BLINDNESS;
	public static final SuspiciousStewEffects.Entry CONDUIT_POWER;
	public static final SuspiciousStewEffects.Entry DARKNESS;
	public static final SuspiciousStewEffects.Entry DOLPHINS_GRACE;
	public static final SuspiciousStewEffects.Entry FIRE_RESISTANCE;
	public static final SuspiciousStewEffects.Entry GLOWING;
	public static final SuspiciousStewEffects.Entry HASTE;
	public static final SuspiciousStewEffects.Entry HEALTH_BOOST;
	public static final SuspiciousStewEffects.Entry HERO_OF_THE_VILLAGE;
	public static final SuspiciousStewEffects.Entry HUNGER;
	public static final SuspiciousStewEffects.Entry INFESTED;
	public static final SuspiciousStewEffects.Entry INSTANT_DAMAGE;
	public static final SuspiciousStewEffects.Entry INSTANT_HEALTH;
	public static final SuspiciousStewEffects.Entry INVISIBILITY;
	public static final SuspiciousStewEffects.Entry JUMP_BOOST;
	public static final SuspiciousStewEffects.Entry LEVITATION;
	public static final SuspiciousStewEffects.Entry LUCK;
	public static final SuspiciousStewEffects.Entry MINING_FATIGUE;
	public static final SuspiciousStewEffects.Entry NAUSEA;
	public static final SuspiciousStewEffects.Entry NIGHT_VISION;
	public static final SuspiciousStewEffects.Entry OOZING;
	public static final SuspiciousStewEffects.Entry POISON;
	public static final SuspiciousStewEffects.Entry RAID_OMEN;
	public static final SuspiciousStewEffects.Entry REGENERATION;
	public static final SuspiciousStewEffects.Entry RESISTANCE;
	public static final SuspiciousStewEffects.Entry SATURATION;
	public static final SuspiciousStewEffects.Entry SLOW_FALLING;
	public static final SuspiciousStewEffects.Entry SLOWNESS;
	public static final SuspiciousStewEffects.Entry SPEED;
	public static final SuspiciousStewEffects.Entry STRENGTH;
	public static final SuspiciousStewEffects.Entry TRIAL_OMEN;
	public static final SuspiciousStewEffects.Entry UNLUCK;
	public static final SuspiciousStewEffects.Entry WATER_BREATHING;
	public static final SuspiciousStewEffects.Entry WEAKNESS;
	public static final SuspiciousStewEffects.Entry WEAVING;
	public static final SuspiciousStewEffects.Entry WIND_CHARGED;
	public static final SuspiciousStewEffects.Entry WITHER;
	
	public StewEffects() {}
	
	static{
		ABSORPTION = new SuspiciousStewEffects.Entry( MobEffects.ABSORPTION, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		BAD_OMEN = new SuspiciousStewEffects.Entry( MobEffects.BAD_OMEN, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		BLINDNESS = new SuspiciousStewEffects.Entry( MobEffects.BLINDNESS, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		CONDUIT_POWER = new SuspiciousStewEffects.Entry( MobEffects.CONDUIT_POWER, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		DARKNESS = new SuspiciousStewEffects.Entry( MobEffects.DARKNESS, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		DOLPHINS_GRACE = new SuspiciousStewEffects.Entry( MobEffects.DOLPHINS_GRACE, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		FIRE_RESISTANCE = new SuspiciousStewEffects.Entry( MobEffects.FIRE_RESISTANCE, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		GLOWING = new SuspiciousStewEffects.Entry( MobEffects.GLOWING, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		HASTE = new SuspiciousStewEffects.Entry( MobEffects.HASTE, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		HEALTH_BOOST = new SuspiciousStewEffects.Entry( MobEffects.HEALTH_BOOST, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		HERO_OF_THE_VILLAGE = new SuspiciousStewEffects.Entry( MobEffects.HERO_OF_THE_VILLAGE, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		HUNGER = new SuspiciousStewEffects.Entry( MobEffects.HUNGER, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		INFESTED = new SuspiciousStewEffects.Entry( MobEffects.INFESTED, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		INSTANT_DAMAGE = new SuspiciousStewEffects.Entry( MobEffects.INSTANT_DAMAGE, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		INSTANT_HEALTH = new SuspiciousStewEffects.Entry( MobEffects.INSTANT_HEALTH, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		INVISIBILITY = new SuspiciousStewEffects.Entry( MobEffects.INVISIBILITY, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		JUMP_BOOST = new SuspiciousStewEffects.Entry( MobEffects.JUMP_BOOST, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		LEVITATION = new SuspiciousStewEffects.Entry( MobEffects.LEVITATION, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		LUCK = new SuspiciousStewEffects.Entry( MobEffects.LUCK, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		MINING_FATIGUE = new SuspiciousStewEffects.Entry( MobEffects.MINING_FATIGUE, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		NAUSEA = new SuspiciousStewEffects.Entry( MobEffects.NAUSEA, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		NIGHT_VISION = new SuspiciousStewEffects.Entry( MobEffects.NIGHT_VISION, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		OOZING = new SuspiciousStewEffects.Entry( MobEffects.OOZING, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		POISON = new SuspiciousStewEffects.Entry( MobEffects.POISON, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		RAID_OMEN = new SuspiciousStewEffects.Entry( MobEffects.RAID_OMEN, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		REGENERATION = new SuspiciousStewEffects.Entry( MobEffects.REGENERATION, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		RESISTANCE = new SuspiciousStewEffects.Entry( MobEffects.RESISTANCE, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		SATURATION = new SuspiciousStewEffects.Entry( MobEffects.SATURATION, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		SLOW_FALLING = new SuspiciousStewEffects.Entry( MobEffects.SLOW_FALLING, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		SLOWNESS = new SuspiciousStewEffects.Entry( MobEffects.SLOWNESS, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		SPEED = new SuspiciousStewEffects.Entry( MobEffects.SPEED, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		STRENGTH = new SuspiciousStewEffects.Entry( MobEffects.STRENGTH, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		TRIAL_OMEN = new SuspiciousStewEffects.Entry( MobEffects.TRIAL_OMEN, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		UNLUCK = new SuspiciousStewEffects.Entry( MobEffects.UNLUCK, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		WATER_BREATHING = new SuspiciousStewEffects.Entry( MobEffects.WATER_BREATHING, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		WEAKNESS = new SuspiciousStewEffects.Entry( MobEffects.WEAKNESS, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		WEAVING = new SuspiciousStewEffects.Entry( MobEffects.WEAVING, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		WIND_CHARGED = new SuspiciousStewEffects.Entry( MobEffects.WIND_CHARGED, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		WITHER = new SuspiciousStewEffects.Entry( MobEffects.WITHER, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
	}
	
}
