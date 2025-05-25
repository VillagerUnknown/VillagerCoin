package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.platform.util.VillagerUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.ComponentPredicate;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Pair;
import net.minecraft.village.TradedItem;

import java.util.*;

public class MerchantCoinTradingFeature {
	
	public static void execute() {}
	
	public static boolean shouldReplaceTrades( TradedItem firstBuyItem, ItemStack sellItem ) {
		return Villagercoin.CONFIG.enableTradeModifications
				&& !CoinCraftingFeature.isCraftingResultCoin( firstBuyItem.itemStack().getItem() )
				&& !CoinCraftingFeature.isCraftingResultCoin( sellItem.getItem() );
	}
	
	public static Item getCoinForTrade(TradedItem firstBuyItem, ItemStack sellItem, int maxUses, boolean rewardingPlayerExperience, int specialPrice, int demandBonus, float priceMultiplier, int merchantExperience ) {
		Item coin = CoinItems.COPPER_COIN;
		
		// Netherite and Emerald trade checks implemented for modded trades
		if( firstBuyItem.itemStack().isIn( Villagercoin.getItemTagKey( "netherite_coin_trade" ) ) || sellItem.isIn( Villagercoin.getItemTagKey( "netherite_coin_trade" ) ) ) {
			coin = CoinItems.NETHERITE_COIN;
		} else if( firstBuyItem.itemStack().isIn( Villagercoin.getItemTagKey( "emerald_coin_trade" ) ) || sellItem.isIn( Villagercoin.getItemTagKey( "emerald_coin_trade" ) ) ) {
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
			|| firstBuyItem.itemStack().isIn( Villagercoin.getItemTagKey( "gold_coin_trade" ) )
			|| sellItem.isIn( Villagercoin.getItemTagKey( "gold_coin_trade" ) )
		) {
			coin = CoinItems.GOLD_COIN;
		} else if(
			merchantExperience > VillagerUtil.NOVICE_BUY_XP
			|| sellItem.hasEnchantments()
			|| firstBuyItem.itemStack().isIn( Villagercoin.getItemTagKey( "iron_coin_trade" ) )
					|| sellItem.isIn( Villagercoin.getItemTagKey( "iron_coin_trade" ) )
		) {
			coin = CoinItems.IRON_COIN;
		} // if
		
		// Force to Copper Coins
		if( firstBuyItem.itemStack().isIn( Villagercoin.getItemTagKey( "copper_coin_trade" ) ) || sellItem.isIn( Villagercoin.getItemTagKey( "copper_coin_trade" ) ) ) {
			coin = CoinItems.COPPER_COIN;
		} // if
		
		return coin;
	}
	
	public static int getModifiedAmount( int amount, int divisor, int maximum ) {
		return Math.clamp( amount / divisor, 1, maximum );
	}
	
	public static TradedItem replaceEmeraldsInTradedItem( TradedItem tradedItem, Item coin ) {
		if( tradedItem.itemStack().getItem().equals( Items.EMERALD ) ) {
			ItemStack replacedStack = replaceEmeraldsInItemStack(tradedItem.itemStack(), coin);
			return new TradedItem(replacedStack.getItem(), replacedStack.getCount());
		} // if
		
		return tradedItem;
	}
	
	public static ItemStack replaceEmeraldsInItemStack( ItemStack itemStack, Item coin ) {
		if( itemStack.getItem().equals( Items.EMERALD ) ) {
			int amount = itemStack.getCount();
			
			if( coin.equals( CoinItems.GOLD_COIN ) ) {
				amount = MerchantCoinTradingFeature.getModifiedAmount( amount, Villagercoin.CONFIG.goldCoinSellItemDivisor, Villagercoin.CONFIG.goldCoinSellItemMaximum );
			} // if
		
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
		firstBuyItem = replaceEmeraldsInTradedItem( firstBuyItem, coin );
		
		if( secondBuyItem.isPresent() ) {
			secondBuyItem = Optional.of( replaceEmeraldsInTradedItem( secondBuyItem.get(), coin ) );
		} // if
		
		sellItem = replaceEmeraldsInItemStack( sellItem, coin );
		
		Pair<TradedItem, ItemStack> modifiedDiamondTrade = modifyDiamondTrade( firstBuyItem, sellItem );
		
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
