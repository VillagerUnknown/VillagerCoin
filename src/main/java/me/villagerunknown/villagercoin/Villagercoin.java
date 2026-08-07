package me.villagerunknown.villagercoin;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.PlatformMod;
import me.villagerunknown.platform.manager.featureManager;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.component.*;
import me.villagerunknown.villagercoin.feature.*;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;

import java.util.Comparator;

public class Villagercoin implements ModInitializer {
	
	public static final String ID = "villagercoin";
	public static final PlatformMod<VillagercoinConfigData> MOD = Platform.register( ID, Villagercoin.class, VillagercoinConfigData.class );
	public static final String MOD_ID = MOD.getModId();
	public static final Logger LOGGER = MOD.getLogger();
	public static final VillagercoinConfigData CONFIG = MOD.getConfig();
	
	public static final int MAX_STACK_COUNT_CAP = 1073741822;
	public static int MAX_STACK_COUNT = CONFIG.maximumCoinStackSize;
	
	private static boolean loaded = false;
	
	public static final ResourceKey<CreativeModeTab> ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(MOD_ID, "item_group"));
	
	public static final CreativeModeTab ITEM_GROUP = FabricCreativeModeTab.builder()
			.icon(() -> new ItemStack(CoinItems.EMERALD_COIN))
			.title(Component.translatable("itemGroup." + MOD_ID))
			.build();
	
	@Override
	public void onInitialize() {
		if( CONFIG.maximumCoinStackSize > MAX_STACK_COUNT_CAP ) {
			Villagercoin.LOGGER.warn( "Maximum Coin Stack Size exceeds limit of " + MAX_STACK_COUNT_CAP );
			Villagercoin.LOGGER.info( "Maximum Coin Stack Size has been set to: " + MAX_STACK_COUNT_CAP );
			
			MAX_STACK_COUNT = MAX_STACK_COUNT_CAP;
		} // if
		
		// Initialize features
		init();
	}
	
	private static void init() {
		if( loaded ) {
			return;
		} // if
		
		// # Initialize mod with Platform
		Platform.init_mod( MOD );
		
		// # Load Components
		new Components();
		
		// # Activate Primary Features
		featureManager.addFeature( "villagercoin-item-group", CustomItemGroupFeature::execute );
		
		featureManager.addFeature( "coin", CoinFeature::execute );
		featureManager.addFeature( "coin-crafting", CoinCraftingFeature::execute );
		
		featureManager.addFeature( "collectable-coin", CollectableCoinFeature::execute );
		featureManager.addFeature( "edible-coin", EdibleCoinFeature::execute );
		featureManager.addFeature( "inventory-effect-coin", InventoryEffectCoinFeature::execute );
		
		featureManager.addFeature( "receipt", ReceiptFeature::execute );
		featureManager.addFeature( "ledger", LedgerFeature::execute );
		featureManager.addFeature( "receipt-crafting", ReceiptCraftingFeature::execute );
		featureManager.addFeature( "ledger-crafting", LedgerCraftingFeature::execute );
		
		featureManager.addFeature( "coin-bank", CoinBankBlocksFeature::execute );
		featureManager.addFeature( "coin-stack", CoinStackBlocksFeature::execute );
		featureManager.addFeature( "coin-bank-crafting", CoinBankCraftingFeature::execute );
		featureManager.addFeature( "coin-stack-crafting", CoinStackCraftingFeature::execute );
		
		// # Activate Block Entity Loaders
		featureManager.addFeatureLast( "coin-bank-block-entities", CoinBankBlockEntityFeature::execute );
		featureManager.addFeatureLast( "coin-stack-block-entities", CoinStackBlockEntityFeature::execute );
		
		// # Activate Supporting Features
		featureManager.addFeatureLast( "structures-include-coins", StructuresIncludeCoinsFeature::execute );
		featureManager.addFeatureLast( "mobs-drop-coins", MobsDropCoinsFeature::execute );
		featureManager.addFeatureLast( "merchant-coin-trading", MerchantCoinTradingFeature::execute );
		
		// # Load Features
		featureManager.loadFeatures();
		
		loaded = true;
	}
	
	public static void load() {
		if( loaded ) {
			return;
		} // if
		
		init();
	}
	
	public static String formOfficialAddonID( String id ) {
		return ID + "-" + id;
	}
	
	public static String formAddonID( String id ) {
		return id + "-" + ID;
	}
	
	public static TagKey<Item> getItemTagKey(String id ) {
		return TagKey.create( Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, id) );
	}
	
	public static TagKey<Block> getBlockTagKey(String id ) {
		return TagKey.create( Registries.BLOCK, Identifier.fromNamespaceAndPath(MOD_ID, id) );
	}
	
	public static void addItemToGroup( Item item ) {
		RegistryUtil.addItemToGroup( ITEM_GROUP_KEY, item );
	}
	
	public static Comparator<Integer> reverseSort = new Comparator<Integer>() {
		@Override
		public int compare(Integer num1, Integer num2) {
			return num2.compareTo(num1);
		}
	};
	
	public static Comparator<Long> reverseSortLong = new Comparator<Long>() {
		@Override
		public int compare(Long num1, Long num2) {
			return num2.compareTo(num1);
		}
	};
	
}
