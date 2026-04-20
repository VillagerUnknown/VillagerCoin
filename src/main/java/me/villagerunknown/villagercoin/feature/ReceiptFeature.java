package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.item.ReceiptItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class ReceiptFeature {
	
	public static final String RECEIPT_STRING = "receipt";
	
	public static void execute() {}
	
	public static Item registerReceipt( String namespace, String id, Item.Settings settings ) {
		settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID,id)));
		
		Item registeredItem = RegistryUtil.registerItem( id, new ReceiptItem( settings ), namespace );
		
		Villagercoin.addItemToGroup( registeredItem );
		
		return registeredItem;
	}
	
	public static Item registerCraftableReceipt( String namespace, String id, Item.Settings settings ) {
		Item registeredItem = registerReceipt( namespace, id, settings );
		
		ReceiptCraftingFeature.registerCraftingResultReceipt( registeredItem );
		
		return registeredItem;
	}
	
}
