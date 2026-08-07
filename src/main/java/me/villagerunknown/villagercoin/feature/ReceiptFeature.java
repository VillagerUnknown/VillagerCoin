package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.item.ReceiptItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class ReceiptFeature {
	
	public static final String RECEIPT_STRING = "receipt";
	
	public static void execute() {}
	
	public static Item registerReceipt( String namespace, String id, Item.Properties settings ) {
		settings.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID,id)));
		
		Item registeredItem = RegistryUtil.registerItem( id, new ReceiptItem( settings ), namespace );
		
		Villagercoin.addItemToGroup( registeredItem );
		
		return registeredItem;
	}
	
	public static Item registerCraftableReceipt( String namespace, String id, Item.Properties settings ) {
		Item registeredItem = registerReceipt( namespace, id, settings );
		
		ReceiptCraftingFeature.registerCraftingResultReceipt( registeredItem );
		
		return registeredItem;
	}
	
}
