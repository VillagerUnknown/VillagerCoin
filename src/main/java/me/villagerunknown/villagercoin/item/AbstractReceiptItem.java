package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.DateComponent;
import me.villagerunknown.villagercoin.component.ReceiptMessageComponent;
import me.villagerunknown.villagercoin.component.ReceiptValueComponent;
import net.minecraft.world.item.Item;
import java.text.NumberFormat;
import java.util.List;

import static me.villagerunknown.villagercoin.component.Components.*;


public class AbstractReceiptItem extends Item {
	
	public AbstractReceiptItem(Properties settings) {
		super(settings);
	}
	
}
