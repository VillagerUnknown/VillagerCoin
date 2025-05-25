package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.item.Item;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class LedgerFeature {
	
	public static final String LEDGER_STRING = "ledger";
	
	public static void execute() {}
	
	public static Item registerLedger( String namespace, String id, Item item ) {
		Item registeredItem = RegistryUtil.registerItem( id, item, namespace );
		
		Villagercoin.addItemToGroup( registeredItem );
		
		return registeredItem;
	}
	
	public static Item registerCraftableLedger( String namespace, String id, Item item) {
		Item registeredItem = registerLedger( namespace, id, item );
		
		LedgerCraftingFeature.registerCraftingResultLedger( registeredItem );
		
		return registeredItem;
	}
	
}
