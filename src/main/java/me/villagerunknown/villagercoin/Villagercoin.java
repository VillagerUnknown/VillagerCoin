package me.villagerunknown.villagercoin;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.PlatformMod;
import me.villagerunknown.platform.manager.featureManager;
import me.villagerunknown.villagercoin.data.type.CoinComponent;
import me.villagerunknown.villagercoin.data.type.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.coinFeature;
import me.villagerunknown.villagercoin.feature.edibleCoinFeature;
import me.villagerunknown.villagercoin.feature.mobsDropCoinsFeature;
import me.villagerunknown.villagercoin.feature.structuresIncludeCoinsFeature;
import net.fabricmc.api.ModInitializer;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;

import java.util.Comparator;
import java.util.function.UnaryOperator;

public class Villagercoin implements ModInitializer {
	
	public static PlatformMod<VillagercoinConfigData> MOD = Platform.register( "villagercoin", Villagercoin.class, VillagercoinConfigData.class );
	
	public static final int MAX_COUNT_CAP = 1073741822;
	public static int MAX_COUNT = 5000;
	
	public static String MOD_ID = null;
	public static Logger LOGGER = null;
	public static VillagercoinConfigData CONFIG = null;
	
	public static final ComponentType<CoinComponent> COIN_COMPONENT;
	
	public static final ComponentType<CurrencyComponent> CURRENCY_COMPONENT;
	
	public static Comparator<Integer> reverseSort = new Comparator<Integer>() {
		@Override
		public int compare(Integer num1, Integer num2) {
			return num2.compareTo(num1);
		}
	};
	
	@Override
	public void onInitialize() {
		// # Register Mod w/ Platform
		MOD_ID = MOD.getModId();
		LOGGER = MOD.getLogger();
		CONFIG = MOD.getConfig();
		
		MAX_COUNT = CONFIG.maximumCoinStackSize;
		
		if( CONFIG.maximumCoinStackSize > MAX_COUNT_CAP ) {
			Villagercoin.LOGGER.warn( "Maximum Coin Stack Size exceeds limit of " + MAX_COUNT_CAP );
			Villagercoin.LOGGER.info( "Maximum Coin Stack Size has been set to: " + MAX_COUNT_CAP );
			
			MAX_COUNT = MAX_COUNT_CAP;
		} // if
		
		// # Initialize Mod
		init();
	}
	
	private static void init() {
		Platform.init_mod( MOD );
		
		// # Activate Features
		featureManager.addFeature( "coin", coinFeature::execute );
		featureManager.addFeature( "edibleCoin", edibleCoinFeature::execute );
		
		featureManager.addFeature( "structuresIncludeCoins", structuresIncludeCoinsFeature::execute );
		featureManager.addFeature( "mobsDropCoins", mobsDropCoinsFeature::execute );
	}
	
	private static <T> ComponentType<T> registerComponentType(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return (ComponentType) Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MOD.getModId(), id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
	}
	
	public static Item registerCoin(String id, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance ) {
		return registerCoin( id, value, rarity, dropMinimum, dropMaximum, dropChance, new Item.Settings() );
	}
	
	public static Item registerCoin(String id, int value, Rarity rarity, int dropMinimum, int dropMaximum, float dropChance, Item.Settings settings ) {
		return coinFeature.registerVillagerCoinItem( id, value, rarity, dropMinimum, dropMaximum, dropChance, settings );
	}
	
	static{
		COIN_COMPONENT = registerComponentType("coin", (builder) -> {
			return builder.codec(CoinComponent.CODEC).packetCodec(CoinComponent.PACKET_CODEC).cache();
		});
		CURRENCY_COMPONENT = registerComponentType("currency", (builder) -> {
			return builder.codec(CurrencyComponent.CODEC).packetCodec(CurrencyComponent.PACKET_CODEC).cache();
		});
	}
	
}
