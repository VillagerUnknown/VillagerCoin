package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.component.*;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WritableBookItem;
import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;

import static me.villagerunknown.villagercoin.component.Components.*;

public class AbstractLedgerItem extends WritableBookItem {
	
	public AbstractLedgerItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public void onCraftedBy(ItemStack stack, Player player) {
		Component nameComponent = stack.get(DataComponents.ITEM_NAME);
		Component customNameComponent = stack.get(DataComponents.CUSTOM_NAME);
		
		if(
			(null == nameComponent || Objects.equals( nameComponent, Component.translatable(stack.getItem().getDescriptionId()) ))
			&& null == customNameComponent
		) {
			stack.set(DataComponents.ITEM_NAME, Component.translatable(
					"item.villagerunknown-villagercoin.ledger.name",
					player.getScoreboardName()
			));
		} // if
		
		super.onCraftedBy(stack, player);
	}
	
}
