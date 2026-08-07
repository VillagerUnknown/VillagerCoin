package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.item.LedgerItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class LedgerFeature {
	
	public static final String LEDGER_STRING = "ledger";
	
	public static void execute() {}
	
	public static Item registerLedger( String namespace, String id, Item.Properties settings ) {
		settings.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID,id)));
		
		Item registeredItem = RegistryUtil.registerItem( id, new LedgerItem( settings ), namespace );
		
		Villagercoin.addItemToGroup( registeredItem );
		
		return registeredItem;
	}
	
	public static Item registerCraftableLedger( String namespace, String id, Item.Properties settings) {
		Item registeredItem = registerLedger( namespace, id, settings );
		
		LedgerCraftingFeature.registerCraftingResultLedger( registeredItem );
		
		return registeredItem;
	}
	
}
