package me.villagerunknown.villagercoin.feature;

import com.mojang.datafixers.util.Pair;
import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.platform.util.VillagerUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import java.util.*;

public class MerchantCoinTradingFeature {
	
	public static void execute() {}
	
	public static boolean shouldReplaceTrades( ItemCost firstBuyItem, ItemStack sellItem ) {
		return Villagercoin.CONFIG.enableTradeModifications
				&& !CoinCraftingFeature.isCraftingResultCoin( firstBuyItem.itemStack().getItem() )
				&& !CoinCraftingFeature.isCraftingResultCoin( sellItem.getItem() );
	}
	
	public static Item getCoinForTrade(ItemCost firstBuyItem, ItemStack sellItem, int maxUses, boolean rewardingPlayerExperience, int specialPrice, int demandBonus, float priceMultiplier, int merchantExperience ) {
		Item coin = CoinItems.COPPER_COIN;
		
		// Netherite and Emerald trade checks implemented for modded trades
		if( firstBuyItem.itemStack().is( Villagercoin.getItemTagKey( "netherite_coin_trade" ) ) || sellItem.is( Villagercoin.getItemTagKey( "netherite_coin_trade" ) ) ) {
			coin = CoinItems.NETHERITE_COIN;
		} else if( firstBuyItem.itemStack().is( Villagercoin.getItemTagKey( "emerald_coin_trade" ) ) || sellItem.is( Villagercoin.getItemTagKey( "emerald_coin_trade" ) ) ) {
			coin = CoinItems.EMERALD_COIN;
		} else if(
			(
				merchantExperience >= VillagerUtil.JOURNEYMAN_BUY_XP
				&&
				(
					priceMultiplier == VillagerUtil.HIGH_PRICE_MULTIPLIER
					|| maxUses == VillagerUtil.RARE_MAX_USES
					|| sellItem.isEnchanted()
				)
			)
			|| (sellItem.isEnchanted() && merchantExperience >= VillagerUtil.JOURNEYMAN_SELL_XP)
			|| firstBuyItem.itemStack().is( Villagercoin.getItemTagKey( "gold_coin_trade" ) )
			|| sellItem.is( Villagercoin.getItemTagKey( "gold_coin_trade" ) )
		) {
			coin = CoinItems.GOLD_COIN;
		} else if(
			merchantExperience > VillagerUtil.NOVICE_BUY_XP
			|| sellItem.isEnchanted()
			|| firstBuyItem.itemStack().is( Villagercoin.getItemTagKey( "iron_coin_trade" ) )
					|| sellItem.is( Villagercoin.getItemTagKey( "iron_coin_trade" ) )
		) {
			coin = CoinItems.IRON_COIN;
		} // if
		
		// Force to Copper Coins
		if( firstBuyItem.itemStack().is( Villagercoin.getItemTagKey( "copper_coin_trade" ) ) || sellItem.is( Villagercoin.getItemTagKey( "copper_coin_trade" ) ) ) {
			coin = CoinItems.COPPER_COIN;
		} // if
		
		return coin;
	}
	
	public static int getModifiedAmount( int amount, int divisor, int maximum ) {
		return Math.clamp( amount / divisor, 1, maximum );
	}
	
	public static ItemCost replaceEmeraldsInTradedItem( ItemCost tradedItem, Item coin ) {
		if( tradedItem.itemStack().getItem().equals( Items.EMERALD ) ) {
			ItemStack replacedStack = replaceEmeraldsInItemStack(tradedItem.itemStack(), coin);
			return new ItemCost(replacedStack.getItem(), replacedStack.getCount());
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
	
	public static Pair<ItemCost, ItemStack> modifyDiamondTrade(ItemCost firstBuyItem, ItemStack sellItem ) {
		if( firstBuyItem.itemStack().getItem().equals( Items.DIAMOND ) ) {
			int sellAmount = Villagercoin.CONFIG.goldForDiamond;
			
			if( MathUtil.hasChance( Villagercoin.CONFIG.chanceDiamondBecomesEmeraldTrade) ) {
				sellAmount = Villagercoin.CONFIG.goldForEmerald;
				firstBuyItem = new ItemCost( Items.EMERALD, firstBuyItem.itemStack().getCount() );
			} // if
			
			sellItem = new ItemStack( CoinItems.GOLD_COIN, sellAmount );
		} // if
		
		return new Pair<>( firstBuyItem, sellItem );
	}
	
	public static ModifiedTrade modifyTrade( ItemCost firstBuyItem, Optional<ItemCost> secondBuyItem, ItemStack sellItem, Item coin ) {
		firstBuyItem = replaceEmeraldsInTradedItem( firstBuyItem, coin );
		
		if( secondBuyItem.isPresent() ) {
			secondBuyItem = Optional.of( replaceEmeraldsInTradedItem( secondBuyItem.get(), coin ) );
		} // if
		
		sellItem = replaceEmeraldsInItemStack( sellItem, coin );
		
		Pair<ItemCost, ItemStack> modifiedDiamondTrade = modifyDiamondTrade( firstBuyItem, sellItem );
		
		firstBuyItem = modifiedDiamondTrade.getFirst();
		sellItem = modifiedDiamondTrade.getSecond();
		
		return new ModifiedTrade(firstBuyItem, secondBuyItem, sellItem);
	}
	
	public static class ModifiedTrade {
		
		public ItemCost firstBuyItem;
		public Optional secondBuyItem;
		public ItemStack sellItem;
		
		public ModifiedTrade( ItemCost firstBuyItem, Optional secondBuyItem, ItemStack sellItem ) {
			this.firstBuyItem = firstBuyItem;
			this.secondBuyItem = secondBuyItem;
			this.sellItem = sellItem;
		}
		
	}
	
}
