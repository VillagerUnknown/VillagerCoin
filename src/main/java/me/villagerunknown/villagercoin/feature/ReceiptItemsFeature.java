package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.item.Item;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class ReceiptItemsFeature {
	
	public static final String RECEIPT_STRING = "receipt";
	
	public static void execute() {}
	
	public static Item registerReceipt(String id, Item item ) {
		Item registeredItem = RegistryUtil.registerItem( id, item, MOD_ID );
		
		RegistryUtil.addItemToGroup( Villagercoin.ITEM_GROUP_KEY, registeredItem );
		
		return registeredItem;
	}
	
	public static Item registerCraftableReceipt(String id, Item item) {
		Item registeredItem = registerReceipt( id, item );
		
		ReceiptCraftingFeature.registerCraftingResultReceipt( registeredItem );
		
		return registeredItem;
	}
	
}
