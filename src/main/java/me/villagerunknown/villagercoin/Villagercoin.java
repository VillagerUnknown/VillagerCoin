package me.villagerunknown.villagercoin;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.PlatformMod;
import me.villagerunknown.platform.manager.featureManager;
import me.villagerunknown.villagercoin.component.*;
import me.villagerunknown.villagercoin.feature.*;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

import java.util.function.UnaryOperator;

public class Villagercoin implements ModInitializer {
	
	public static PlatformMod<VillagercoinConfigData> MOD = Platform.register( "villagercoin", Villagercoin.class, VillagercoinConfigData.class );
	public static String MOD_ID = MOD.getModId();
	public static Logger LOGGER = MOD.getLogger();
	public static VillagercoinConfigData CONFIG = MOD.getConfig();
	
	public static final int MAX_COUNT_CAP = 1073741822;
	public static int MAX_COUNT = 5000;
	
	public static final ComponentType<CoinComponent> COIN_COMPONENT;
	
	public static final ComponentType<DropComponent> DROP_COMPONENT;
	
	public static final ComponentType<LootTableComponent> LOOT_TABLE_COMPONENT;
	
	public static final ComponentType<CurrencyComponent> CURRENCY_COMPONENT;
	
	public static final ComponentType<CollectableComponent> COLLECTABLE_COMPONENT;
	
	public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "item_group"));
	
	public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(CoinItems.EMERALD_COIN))
			.displayName(Text.translatable("itemGroup." + MOD_ID))
			.build();
	
	@Override
	public void onInitialize() {
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
		featureManager.addFeature( "coinCrafting", CoinCraftingFeature::execute );
		featureManager.addFeature( "coin", CoinFeature::execute );
		
		featureManager.addFeature( "structuresIncludeCoins", StructuresIncludeCoinsFeature::execute );
		featureManager.addFeature( "mobsDropCoins", MobsDropCoinsFeature::execute );
		featureManager.addFeature( "merchantCoinTrading", MerchantCoinTradingFeature::execute );
		
		featureManager.addFeature( "inventoryEffectCoins", InventoryEffectCoinFeature::execute );
	}
	
	public static <T> ComponentType<T> registerComponentType(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return (ComponentType) Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MOD.getModId(), id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
	}
	
	public static TagKey<Item> getItemTagKey(String id ) {
		return TagKey.of( RegistryKeys.ITEM, Identifier.of(MOD_ID, id) );
	}
	
	public static TagKey<Block> getBlockTagKey(String id ) {
		return TagKey.of( RegistryKeys.BLOCK, Identifier.of(MOD_ID, id) );
	}
	
	static{
		COIN_COMPONENT = registerComponentType("coin", (builder) -> {
			return builder.codec(CoinComponent.CODEC).packetCodec(CoinComponent.PACKET_CODEC).cache();
		});
		DROP_COMPONENT = registerComponentType("drop", (builder) -> {
			return builder.codec(DropComponent.CODEC).packetCodec(DropComponent.PACKET_CODEC).cache();
		});
		LOOT_TABLE_COMPONENT = registerComponentType("loot_table", (builder) -> {
			return builder.codec(LootTableComponent.CODEC).packetCodec(LootTableComponent.PACKET_CODEC).cache();
		});
		CURRENCY_COMPONENT = registerComponentType("currency", (builder) -> {
			return builder.codec(CurrencyComponent.CODEC).packetCodec(CurrencyComponent.PACKET_CODEC).cache();
		});
		COLLECTABLE_COMPONENT = Villagercoin.registerComponentType("collectable", (builder) -> {
			return builder.codec(CollectableComponent.CODEC).packetCodec(CollectableComponent.PACKET_CODEC).cache();
		});
	}
	
}
