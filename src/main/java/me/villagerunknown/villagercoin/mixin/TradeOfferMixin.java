package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.feature.MerchantCoinTradingFeature;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Pair;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
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
		if( !MerchantCoinTradingFeature.shouldReplaceTrades( firstBuyItem, sellItem ) ) {
			return;
		} // if
		
		Item coin = MerchantCoinTradingFeature.getCoinForTrade( firstBuyItem, sellItem, maxUses, rewardingPlayerExperience, specialPrice, demandBonus, priceMultiplier, merchantExperience );
		
		MerchantCoinTradingFeature.ModifiedTrade modifiedTrade = MerchantCoinTradingFeature.modifyTrade( firstBuyItem, secondBuyItem, sellItem, coin );
		
		this.firstBuyItem = modifiedTrade.firstBuyItem;
		this.secondBuyItem = modifiedTrade.secondBuyItem;
		this.sellItem = modifiedTrade.sellItem;
	}
	
}
