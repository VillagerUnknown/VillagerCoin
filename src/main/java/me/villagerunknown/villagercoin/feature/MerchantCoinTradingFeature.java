package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.platform.util.VillagerUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Pair;
import net.minecraft.village.TradedItem;

import java.util.*;

public class MerchantCoinTradingFeature {
	
	public static Set<Item> COPPER_TRADE_ITEMS = new HashSet<>(Arrays.asList(
			Items.LEATHER_BOOTS,
			Items.LEATHER_HELMET,
			Items.LEATHER_CHESTPLATE,
			Items.LEATHER_LEGGINGS
	));
	
	public static Set<Item> IRON_TRADE_ITEMS = new HashSet<>(Arrays.asList(
			Items.BLACK_WOOL,
			Items.WHITE_WOOL,
			Items.BROWN_WOOL,
			Items.GRAY_WOOL,
			Items.SLIME_BALL,
			Items.PUFFERFISH_BUCKET,
			Items.TROPICAL_FISH_BUCKET,
			Items.SHEARS,
			Items.NAUTILUS_SHELL,
			Items.ICE,
			Items.PACKED_ICE,
			Items.BLUE_ICE,
			Items.SUGAR_CANE,
			Items.KELP,
			Items.CACTUS,
			Items.SEA_PICKLE,
			Items.SMALL_DRIPLEAF,
			Items.GUNPOWDER,
			Items.MOSS_BLOCK,
			Items.PODZOL,
			Items.ROOTED_DIRT,
			Items.SAND,
			Items.RED_SAND,
			Items.ACACIA_SAPLING,
			Items.SPRUCE_SAPLING,
			Items.BIRCH_SAPLING,
			Items.OAK_SAPLING,
			Items.CHERRY_SAPLING,
			Items.DARK_OAK_SAPLING,
			Items.JUNGLE_SAPLING,
			Items.MANGROVE_PROPAGULE,
			Items.IRON_HOE,
			Items.IRON_SHOVEL,
			Items.IRON_PICKAXE,
			Items.IRON_AXE,
			Items.IRON_SWORD,
			Items.IRON_BOOTS,
			Items.IRON_HELMET,
			Items.IRON_CHESTPLATE,
			Items.IRON_LEGGINGS,
			Items.CHAINMAIL_BOOTS,
			Items.CHAINMAIL_HELMET,
			Items.CHAINMAIL_CHESTPLATE,
			Items.CHAINMAIL_LEGGINGS,
			Items.GRAVEL
	));
	
	public static Set<Item> GOLD_TRADE_ITEMS = new HashSet<>(Arrays.asList(
			Items.SADDLE,
			Items.ENCHANTED_BOOK,
			Items.GOLD_INGOT,
			Items.GLOWSTONE,
			Items.GLOBE_BANNER_PATTERN,
			Items.QUARTZ_BLOCK,
			Items.QUARTZ_PILLAR,
			Items.ENDER_PEARL,
			Items.GOLDEN_CARROT,
			Items.GLISTERING_MELON_SLICE,
			Items.GLOWSTONE,
			Items.CLOCK,
			Items.BELL,
			Items.TIPPED_ARROW,
			Items.EXPERIENCE_BOTTLE,
			Items.DIAMOND_HOE,
			Items.DIAMOND_SHOVEL,
			Items.DIAMOND_PICKAXE,
			Items.DIAMOND_AXE,
			Items.DIAMOND_SWORD,
			Items.DIAMOND_BOOTS,
			Items.DIAMOND_HELMET,
			Items.DIAMOND_CHESTPLATE,
			Items.DIAMOND_LEGGINGS,
			Items.QUARTZ,
			Items.NETHER_WART,
			Items.BLAZE_ROD,
			Items.BLAZE_POWDER
	));
	
	public static Set<Item> EMERALD_TRADE_ITEMS = new HashSet<>(Arrays.asList(
			Items.ENDER_EYE,
			Items.CHORUS_FLOWER,
			Items.CHORUS_FRUIT,
			Items.SHULKER_BOX,
			Items.ENDER_CHEST,
			Items.ELYTRA,
			Items.WITHER_SKELETON_SKULL,
			Items.BEACON,
			Items.ANCIENT_DEBRIS,
			Items.NETHERITE_SCRAP
	));
	
	public static Set<Item> NETHERITE_TRADE_ITEMS = new HashSet<>(Arrays.asList(
			Items.NETHERITE_SWORD,
			Items.NETHERITE_SHOVEL,
			Items.NETHERITE_PICKAXE,
			Items.NETHERITE_AXE,
			Items.NETHERITE_HOE,
			Items.NETHERITE_HELMET,
			Items.NETHERITE_CHESTPLATE,
			Items.NETHERITE_LEGGINGS,
			Items.NETHERITE_BOOTS,
			Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
			Items.NETHERITE_BLOCK,
			Items.NETHERITE_INGOT,
			Items.NETHERITE_SWORD,
			Items.NETHERITE_SHOVEL,
			Items.NETHERITE_PICKAXE,
			Items.NETHERITE_AXE,
			Items.NETHERITE_HOE
	));
	
	public static void execute() {}
	
	public static boolean shouldReplaceTrades( TradedItem firstBuyItem, ItemStack sellItem ) {
		return Villagercoin.CONFIG.enableTradeModifications
				&& !CoinCraftingFeature.CRAFTABLE_COINS.containsValue( firstBuyItem.itemStack().getItem() )
				&& !CoinCraftingFeature.CRAFTABLE_COINS.containsValue( sellItem.getItem() );
	}
	
	public static Item getCoinForTrade(TradedItem firstBuyItem, ItemStack sellItem, int maxUses, boolean rewardingPlayerExperience, int specialPrice, int demandBonus, float priceMultiplier, int merchantExperience ) {
		Item coin = CoinItems.COPPER_COIN;
		
		// Netherite and Emerald trade checks implemented for modded trades
		if( NETHERITE_TRADE_ITEMS.contains( firstBuyItem.itemStack().getItem() ) || NETHERITE_TRADE_ITEMS.contains( sellItem.getItem() ) ) {
			coin = CoinItems.NETHERITE_COIN;
		} else if( EMERALD_TRADE_ITEMS.contains( firstBuyItem.itemStack().getItem() ) || EMERALD_TRADE_ITEMS.contains( sellItem.getItem() ) ) {
			coin = CoinItems.EMERALD_COIN;
		} else if(
			(
				merchantExperience >= VillagerUtil.JOURNEYMAN_BUY_XP
				&&
				(
					priceMultiplier == VillagerUtil.HIGH_PRICE_MULTIPLIER
					|| maxUses == VillagerUtil.RARE_MAX_USES
					|| sellItem.hasEnchantments()
				)
			)
			|| (sellItem.hasEnchantments() && merchantExperience >= VillagerUtil.JOURNEYMAN_SELL_XP)
			|| GOLD_TRADE_ITEMS.contains( firstBuyItem.itemStack().getItem() )
			|| GOLD_TRADE_ITEMS.contains( sellItem.getItem() )
		) {
			coin = CoinItems.GOLD_COIN;
		} else if(
			merchantExperience > VillagerUtil.NOVICE_BUY_XP
			|| sellItem.hasEnchantments()
			|| IRON_TRADE_ITEMS.contains( firstBuyItem.itemStack().getItem() )
			|| IRON_TRADE_ITEMS.contains( sellItem.getItem() )
		) {
			coin = CoinItems.IRON_COIN;
		} // if
		
		// Force to Copper Coins
		if( COPPER_TRADE_ITEMS.contains( firstBuyItem.itemStack().getItem() ) || COPPER_TRADE_ITEMS.contains( sellItem.getItem() ) ) {
			coin = CoinItems.COPPER_COIN;
		} // if
		
		return coin;
	}
	
	public static int getModifiedAmount( int amount, int divisor, int maximum ) {
		return Math.clamp( amount / divisor, 1, maximum );
	}
	
	public static TradedItem replaceEmeraldsInTradedItem( TradedItem tradedItem, Item coin ) {
		ItemStack replacedStack = replaceEmeraldsInItemStack( tradedItem.itemStack(), coin );
		return new TradedItem( replacedStack.getItem(), replacedStack.getCount() );
	}
	
	public static ItemStack replaceEmeraldsInItemStack( ItemStack itemStack, Item coin ) {
		int amount = itemStack.getCount();
		
		if( coin.equals( CoinItems.GOLD_COIN ) ) {
			amount = MerchantCoinTradingFeature.getModifiedAmount( amount, Villagercoin.CONFIG.goldCoinSellItemDivisor, Villagercoin.CONFIG.goldCoinSellItemMaximum );
		} // if
		
		if( itemStack.getItem().equals( Items.EMERALD ) ) {
			itemStack = new ItemStack( coin, amount );
		} // if
		
		return itemStack;
	}
	
	public static Pair<TradedItem, ItemStack> modifyDiamondTrade( TradedItem firstBuyItem, ItemStack sellItem ) {
		if( firstBuyItem.itemStack().getItem().equals( Items.DIAMOND ) ) {
			int sellAmount = Villagercoin.CONFIG.goldForDiamond;
			
			if( MathUtil.hasChance( Villagercoin.CONFIG.chanceDiamondBecomesEmeraldTrade) ) {
				sellAmount = Villagercoin.CONFIG.goldForEmerald;
				firstBuyItem = new TradedItem( Items.EMERALD, firstBuyItem.itemStack().getCount() );
			} // if
			
			sellItem = new ItemStack( CoinItems.GOLD_COIN, sellAmount );
		} // if
		
		return new Pair<>( firstBuyItem, sellItem );
	}
	
	public static ModifiedTrade modifyTrade( TradedItem firstBuyItem, Optional<TradedItem> secondBuyItem, ItemStack sellItem, Item coin ) {
		firstBuyItem = MerchantCoinTradingFeature.replaceEmeraldsInTradedItem( firstBuyItem, coin );
		
		if( secondBuyItem.isPresent() ) {
			secondBuyItem = Optional.of( MerchantCoinTradingFeature.replaceEmeraldsInTradedItem( secondBuyItem.get(), coin ) );
		} // if
		
		sellItem = MerchantCoinTradingFeature.replaceEmeraldsInItemStack( sellItem, coin );
		
		Pair<TradedItem, ItemStack> modifiedDiamondTrade = MerchantCoinTradingFeature.modifyDiamondTrade( firstBuyItem, sellItem );
		
		firstBuyItem = modifiedDiamondTrade.getLeft();
		sellItem = modifiedDiamondTrade.getRight();
		
		return new ModifiedTrade(firstBuyItem, secondBuyItem, sellItem);
	}
	
	public static class ModifiedTrade {
		
		public TradedItem firstBuyItem;
		public Optional secondBuyItem;
		public ItemStack sellItem;
		
		public ModifiedTrade( TradedItem firstBuyItem, Optional secondBuyItem, ItemStack sellItem ) {
			this.firstBuyItem = firstBuyItem;
			this.secondBuyItem = secondBuyItem;
			this.sellItem = sellItem;
		}
		
	}
	
}
