package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.item.Item;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class ReceiptFeature {
	
	public static final String RECEIPT_STRING = "receipt";
	
	public static void execute() {}
	
	public static Item registerReceipt( String namespace, String id, Item item ) {
		Item registeredItem = RegistryUtil.registerItem( id, item, namespace );
		
		Villagercoin.addItemToGroup( registeredItem );
		
		return registeredItem;
	}
	
	public static Item registerCraftableReceipt( String namespace, String id, Item item) {
		Item registeredItem = registerReceipt( namespace, id, item );
		
		ReceiptCraftingFeature.registerCraftingResultReceipt( registeredItem );
		
		return registeredItem;
	}
	
}
