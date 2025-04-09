package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.feature.coinFeature;
import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.platform.util.VillagerUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(TradeOffer.class)
public class TradeOfferMixin {
	
	@Mutable
	@Final
	@Shadow
	private TradedItem firstBuyItem;
	
	@Mutable
	@Final
	@Shadow
	private Optional<TradedItem> secondBuyItem;
	
	@Mutable
	@Final
	@Shadow
	private ItemStack sellItem;
	
	@Inject(method = "<init>(Lnet/minecraft/village/TradedItem;Ljava/util/Optional;Lnet/minecraft/item/ItemStack;IIZIIFI)V", at = @At("TAIL"))
	private void TradeOffer(TradedItem firstBuyItem, Optional secondBuyItem, ItemStack sellItem, int _uses, int maxUses, boolean rewardingPlayerExperience, int specialPrice, int demandBonus, float priceMultiplier, int merchantExperience, CallbackInfo ci) {
		if(
			!Villagercoin.CONFIG.enableTradeModifications
			|| coinFeature.COIN_ITEMS.containsValue( this.firstBuyItem.itemStack().getItem() )
			|| coinFeature.COIN_ITEMS.containsValue( this.sellItem.getItem() )
		) {
			return;
		} // if
		
		Item coin = coinFeature.COPPER_COIN;
		
		if(
			(
				merchantExperience >= VillagerUtil.JOURNEYMAN_BUY_XP
				&& (priceMultiplier == VillagerUtil.HIGH_PRICE_MULTIPLIER || maxUses == VillagerUtil.RARE_MAX_USES)
			)
			|| this.sellItem.getItem().equals( Items.ENCHANTED_BOOK )
			|| this.firstBuyItem.itemStack().getItem().equals( Items.GOLD_INGOT )
			|| this.sellItem.getItem().equals( Items.GLOWSTONE )
		) {
			coin = coinFeature.GOLD_COIN;
		} else if( merchantExperience > VillagerUtil.NOVICE_BUY_XP || this.sellItem.hasEnchantments() ) {
			coin = coinFeature.IRON_COIN;
		} // if, else if ...
		
		// # Change Items in Trades for Coins
		
		if( this.firstBuyItem.itemStack().getItem().equals( Items.DIAMOND ) ) {
			
			// # Give 20 Gold Coins for Diamonds and Emeralds
			
			if( MathUtil.hasChance(0.5F) ) {
				this.firstBuyItem = new TradedItem( Items.EMERALD, this.firstBuyItem.itemStack().getCount() );
			} // if
			this.sellItem = new ItemStack(coinFeature.GOLD_COIN, 10);
			
		} else {
			
			// # Replace Emeralds with Coins
			
			if( this.firstBuyItem.itemStack().getItem().equals( Items.EMERALD ) ) {
				this.firstBuyItem = new TradedItem( coin, this.firstBuyItem.itemStack().getCount() );
			} // if
			
			if( this.secondBuyItem.isPresent() ) {
				if( this.secondBuyItem.get().itemStack().getItem().equals(Items.EMERALD) ) {
					if( this.firstBuyItem.itemStack().getItem().equals( coinFeature.IRON_COIN ) ) {
						this.secondBuyItem = Optional.of(new TradedItem(coinFeature.COPPER_COIN, this.secondBuyItem.get().itemStack().getCount()));
					} else if( this.firstBuyItem.itemStack().getItem().equals( coinFeature.GOLD_COIN ) ) {
						this.secondBuyItem = Optional.of(new TradedItem(coinFeature.IRON_COIN, this.secondBuyItem.get().itemStack().getCount()));
					} // if, else if
				} // if
			} // if
			
			if( this.sellItem.getItem().equals( Items.EMERALD ) ) {
				this.sellItem = new ItemStack( coin, this.sellItem.getCount() );
			} // if
			
			// # Give Gold Coin for Nether items
			
			if( this.firstBuyItem.itemStack().getItem().equals( Items.QUARTZ ) || this.firstBuyItem.itemStack().getItem().equals( Items.NETHER_WART ) ) {
				this.sellItem = new ItemStack( coinFeature.GOLD_COIN, this.firstBuyItem.itemStack().getCount() );
			} // if
			
			// # Require Gold Coin for Nether items, late-game items, and Enchanted Journeyman items
			
			if(
				(this.sellItem.hasEnchantments() && merchantExperience > VillagerUtil.JOURNEYMAN_SELL_XP)
				|| this.sellItem.getItem().equals( Items.ENDER_PEARL )
				|| this.sellItem.getItem().equals( Items.GOLDEN_CARROT )
				|| this.sellItem.getItem().equals( Items.GLISTERING_MELON_SLICE )
				|| this.sellItem.getItem().equals( Items.GLOWSTONE )
				|| this.sellItem.getItem().equals( Items.CLOCK )
				|| this.sellItem.getItem().equals( Items.BELL )
				|| this.sellItem.getItem().equals( Items.TIPPED_ARROW )
				|| this.sellItem.getItem().equals( Items.EXPERIENCE_BOTTLE )
			) {
				this.firstBuyItem = new TradedItem( coinFeature.GOLD_COIN, this.firstBuyItem.itemStack().getCount() );
			} // if
			
			// # Require Iron Coin (Mostly for the Wandering Trader)
			if(
				this.sellItem.getItem().equals( Items.SLIME_BALL )
				|| this.sellItem.getItem().equals( Items.PUFFERFISH_BUCKET )
				|| this.sellItem.getItem().equals( Items.TROPICAL_FISH_BUCKET )
				|| this.sellItem.getItem().equals( Items.SHEARS )
				|| this.sellItem.getItem().equals( Items.NAUTILUS_SHELL )
				|| this.sellItem.getItem().equals( Items.ICE )
				|| this.sellItem.getItem().equals( Items.PACKED_ICE )
				|| this.sellItem.getItem().equals( Items.BLUE_ICE )
				|| this.sellItem.getItem().equals( Items.SUGAR_CANE )
				|| this.sellItem.getItem().equals( Items.KELP )
				|| this.sellItem.getItem().equals( Items.CACTUS )
				|| this.sellItem.getItem().equals( Items.SEA_PICKLE )
				|| this.sellItem.getItem().equals( Items.SMALL_DRIPLEAF )
				|| this.sellItem.getItem().equals( Items.GUNPOWDER )
				|| this.sellItem.getItem().equals( Items.MOSS_BLOCK )
				|| this.sellItem.getItem().equals( Items.PODZOL )
				|| this.sellItem.getItem().equals( Items.ROOTED_DIRT )
				|| this.sellItem.getItem().equals( Items.SAND )
				|| this.sellItem.getItem().equals( Items.RED_SAND )
				|| this.sellItem.getItem().equals( Items.ACACIA_SAPLING )
				|| this.sellItem.getItem().equals( Items.SPRUCE_SAPLING )
				|| this.sellItem.getItem().equals( Items.BIRCH_SAPLING )
				|| this.sellItem.getItem().equals( Items.OAK_SAPLING )
				|| this.sellItem.getItem().equals( Items.CHERRY_SAPLING )
				|| this.sellItem.getItem().equals( Items.DARK_OAK_SAPLING )
				|| this.sellItem.getItem().equals( Items.JUNGLE_SAPLING )
				|| this.sellItem.getItem().equals( Items.MANGROVE_PROPAGULE )
			) {
				this.firstBuyItem = new TradedItem( coinFeature.IRON_COIN, this.firstBuyItem.itemStack().getCount() );
			} // if
			
		} // if, else
	}
	
}
