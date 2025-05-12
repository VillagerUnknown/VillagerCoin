package me.villagerunknown.villagercoin;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.PlatformMod;
import me.villagerunknown.platform.manager.featureManager;
import me.villagerunknown.villagercoin.component.*;
import me.villagerunknown.villagercoin.feature.*;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
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

import java.util.Comparator;
import java.util.function.UnaryOperator;

public class Villagercoin implements ModInitializer {
	
	public static PlatformMod<VillagercoinConfigData> MOD = Platform.register( "villagercoin", Villagercoin.class, VillagercoinConfigData.class );
	public static String MOD_ID = MOD.getModId();
	public static Logger LOGGER = MOD.getLogger();
	public static VillagercoinConfigData CONFIG = MOD.getConfig();
	
	public static final int MAX_STACK_COUNT_CAP = 1073741822;
	public static int MAX_STACK_COUNT = 5000;
	
	public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "item_group"));
	
	public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(CoinItems.EMERALD_COIN))
			.displayName(Text.translatable("itemGroup." + MOD_ID))
			.build();
	
	@Override
	public void onInitialize() {
		MAX_STACK_COUNT = CONFIG.maximumCoinStackSize;
		
		if( CONFIG.maximumCoinStackSize > MAX_STACK_COUNT_CAP ) {
			Villagercoin.LOGGER.warn( "Maximum Coin Stack Size exceeds limit of " + MAX_STACK_COUNT_CAP );
			Villagercoin.LOGGER.info( "Maximum Coin Stack Size has been set to: " + MAX_STACK_COUNT_CAP );
			
			MAX_STACK_COUNT = MAX_STACK_COUNT_CAP;
		} // if
		
		// # Initialize mod with Platform
		Platform.init_mod( MOD );
		
		// # Load Components
		new Components();
		
		// # Activate Primary Features
		featureManager.addFeature( "coin", CoinFeature::execute );
		
		featureManager.addFeature( "collectableCoin", CollectableCoinFeature::execute );
		featureManager.addFeature( "edibleCoin", EdibleCoinFeature::execute );
		featureManager.addFeature( "inventoryEffectCoin", InventoryEffectCoinFeature::execute );
		
		featureManager.addFeature( "receipt", ReceiptFeature::execute );
		featureManager.addFeature( "ledger", LedgerFeature::execute );
		
		featureManager.addFeature( "coinBank", CoinBankBlocksFeature::execute );
		featureManager.addFeature( "coinStack", CoinStackBlocksFeature::execute );
		
		featureManager.addFeature( "coinCrafting", CoinCraftingFeature::execute );
		featureManager.addFeature( "coinStackCrafting", CoinStackCraftingFeature::execute );
		featureManager.addFeature( "receiptCrafting", ReceiptCraftingFeature::execute );
		featureManager.addFeature( "ledgerCrafting", LedgerCraftingFeature::execute );
		
		// # Activate Supporting Features
		featureManager.addFeature( "structuresIncludeCoins", StructuresIncludeCoinsFeature::execute );
		featureManager.addFeature( "mobsDropCoins", MobsDropCoinsFeature::execute );
		featureManager.addFeature( "merchantCoinTrading", MerchantCoinTradingFeature::execute );
		
		// # Activate Block Entity Loaders
		featureManager.addFeatureLast( "coinBankBlockEntities", CoinBankBlockEntityFeature::execute );
		featureManager.addFeatureLast( "coinStackBlockEntities", CoinStackBlockEntityFeature::execute );
		
		// # Load Features
		featureManager.loadFeatures();
	}
	
	public static TagKey<Item> getItemTagKey(String id ) {
		return TagKey.of( RegistryKeys.ITEM, Identifier.of(MOD_ID, id) );
	}
	
	public static TagKey<Block> getBlockTagKey(String id ) {
		return TagKey.of( RegistryKeys.BLOCK, Identifier.of(MOD_ID, id) );
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
