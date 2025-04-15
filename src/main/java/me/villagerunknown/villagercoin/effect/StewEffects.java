package me.villagerunknown.villagercoin.effect;

import me.villagerunknown.villagercoin.feature.InventoryEffectCoinFeature;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;

public class StewEffects {
	
	public static final SuspiciousStewEffectsComponent.StewEffect ABSORPTION;
	public static final SuspiciousStewEffectsComponent.StewEffect BAD_OMEN;
	public static final SuspiciousStewEffectsComponent.StewEffect BLINDNESS;
	public static final SuspiciousStewEffectsComponent.StewEffect CONDUIT_POWER;
	public static final SuspiciousStewEffectsComponent.StewEffect DARKNESS;
	public static final SuspiciousStewEffectsComponent.StewEffect DOLPHINS_GRACE;
	public static final SuspiciousStewEffectsComponent.StewEffect FIRE_RESISTANCE;
	public static final SuspiciousStewEffectsComponent.StewEffect GLOWING;
	public static final SuspiciousStewEffectsComponent.StewEffect HASTE;
	public static final SuspiciousStewEffectsComponent.StewEffect HEALTH_BOOST;
	public static final SuspiciousStewEffectsComponent.StewEffect HERO_OF_THE_VILLAGE;
	public static final SuspiciousStewEffectsComponent.StewEffect HUNGER;
	public static final SuspiciousStewEffectsComponent.StewEffect INFESTED;
	public static final SuspiciousStewEffectsComponent.StewEffect INSTANT_DAMAGE;
	public static final SuspiciousStewEffectsComponent.StewEffect INSTANT_HEALTH;
	public static final SuspiciousStewEffectsComponent.StewEffect INVISIBILITY;
	public static final SuspiciousStewEffectsComponent.StewEffect JUMP_BOOST;
	public static final SuspiciousStewEffectsComponent.StewEffect LEVITATION;
	public static final SuspiciousStewEffectsComponent.StewEffect LUCK;
	public static final SuspiciousStewEffectsComponent.StewEffect MINING_FATIGUE;
	public static final SuspiciousStewEffectsComponent.StewEffect NAUSEA;
	public static final SuspiciousStewEffectsComponent.StewEffect NIGHT_VISION;
	public static final SuspiciousStewEffectsComponent.StewEffect OOZING;
	public static final SuspiciousStewEffectsComponent.StewEffect POISON;
	public static final SuspiciousStewEffectsComponent.StewEffect RAID_OMEN;
	public static final SuspiciousStewEffectsComponent.StewEffect REGENERATION;
	public static final SuspiciousStewEffectsComponent.StewEffect RESISTANCE;
	public static final SuspiciousStewEffectsComponent.StewEffect SATURATION;
	public static final SuspiciousStewEffectsComponent.StewEffect SLOW_FALLING;
	public static final SuspiciousStewEffectsComponent.StewEffect SLOWNESS;
	public static final SuspiciousStewEffectsComponent.StewEffect SPEED;
	public static final SuspiciousStewEffectsComponent.StewEffect STRENGTH;
	public static final SuspiciousStewEffectsComponent.StewEffect TRIAL_OMEN;
	public static final SuspiciousStewEffectsComponent.StewEffect UNLUCK;
	public static final SuspiciousStewEffectsComponent.StewEffect WATER_BREATHING;
	public static final SuspiciousStewEffectsComponent.StewEffect WEAKNESS;
	public static final SuspiciousStewEffectsComponent.StewEffect WEAVING;
	public static final SuspiciousStewEffectsComponent.StewEffect WIND_CHARGED;
	public static final SuspiciousStewEffectsComponent.StewEffect WITHER;
	
	public StewEffects() {}
	
	static{
		ABSORPTION = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.ABSORPTION, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		BAD_OMEN = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.BAD_OMEN, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		BLINDNESS = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.BLINDNESS, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		CONDUIT_POWER = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.CONDUIT_POWER, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		DARKNESS = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.DARKNESS, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		DOLPHINS_GRACE = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.DOLPHINS_GRACE, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		FIRE_RESISTANCE = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.FIRE_RESISTANCE, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		GLOWING = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.GLOWING, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		HASTE = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.HASTE, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		HEALTH_BOOST = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.HEALTH_BOOST, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		HERO_OF_THE_VILLAGE = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.HERO_OF_THE_VILLAGE, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		HUNGER = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.HUNGER, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		INFESTED = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.INFESTED, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		INSTANT_DAMAGE = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.INSTANT_DAMAGE, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		INSTANT_HEALTH = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.INSTANT_HEALTH, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		INVISIBILITY = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.INVISIBILITY, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		JUMP_BOOST = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.JUMP_BOOST, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		LEVITATION = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.LEVITATION, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		LUCK = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.LUCK, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		MINING_FATIGUE = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.MINING_FATIGUE, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		NAUSEA = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.NAUSEA, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		NIGHT_VISION = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.NIGHT_VISION, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		OOZING = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.OOZING, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		POISON = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.POISON, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		RAID_OMEN = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.RAID_OMEN, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		REGENERATION = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.REGENERATION, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		RESISTANCE = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.RESISTANCE, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		SATURATION = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.SATURATION, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		SLOW_FALLING = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.SLOW_FALLING, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		SLOWNESS = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.SLOWNESS, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		SPEED = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.SPEED, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		STRENGTH = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.STRENGTH, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		TRIAL_OMEN = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.TRIAL_OMEN, InventoryEffectCoinFeature.EXTENDED_EFFECT_DURATION );
		UNLUCK = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.UNLUCK, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		WATER_BREATHING = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.WATER_BREATHING, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		WEAKNESS = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.WEAKNESS, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		WEAVING = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.WEAVING, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		WIND_CHARGED = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.WIND_CHARGED, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
		WITHER = new SuspiciousStewEffectsComponent.StewEffect( StatusEffects.WITHER, InventoryEffectCoinFeature.DEFAULT_EFFECT_DURATION );
	}
	
}
