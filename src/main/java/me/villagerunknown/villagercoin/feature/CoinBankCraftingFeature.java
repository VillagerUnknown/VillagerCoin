package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.item.ItemStack;

public class CoinBankCraftingFeature {
	
	public static void execute() {}
	
	public static boolean isCraftingResultCoinBank( ItemStack itemStack ) {
		return itemStack.isIn( Villagercoin.getItemTagKey( "coin_bank" ) );
	}
	
}
