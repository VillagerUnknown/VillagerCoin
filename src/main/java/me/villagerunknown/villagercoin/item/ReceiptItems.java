package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.feature.ReceiptCraftingFeature;
import net.minecraft.item.Item;

public class ReceiptItems {
	
	public static final Item RECEIPT;
	
	static{
		RECEIPT = ReceiptCraftingFeature.registerCraftableReceipt( "villager_coin_receipt", new Item.Settings() );
	}
	
}
