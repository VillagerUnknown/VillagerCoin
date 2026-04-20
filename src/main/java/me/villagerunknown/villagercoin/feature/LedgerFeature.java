package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.item.LedgerItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class LedgerFeature {
	
	public static final String LEDGER_STRING = "ledger";
	
	public static void execute() {}
	
	public static Item registerLedger( String namespace, String id, Item.Settings settings ) {
		settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID,id)));
		
		Item registeredItem = RegistryUtil.registerItem( id, new LedgerItem( settings ), namespace );
		
		Villagercoin.addItemToGroup( registeredItem );
		
		return registeredItem;
	}
	
	public static Item registerCraftableLedger( String namespace, String id, Item.Settings settings) {
		Item registeredItem = registerLedger( namespace, id, settings );
		
		LedgerCraftingFeature.registerCraftingResultLedger( registeredItem );
		
		return registeredItem;
	}
	
}
