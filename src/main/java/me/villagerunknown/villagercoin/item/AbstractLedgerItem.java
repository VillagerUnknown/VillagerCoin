package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.component.DateComponent;
import me.villagerunknown.villagercoin.component.ReceiptMessageComponent;
import me.villagerunknown.villagercoin.component.ReceiptValueComponent;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WritableBookItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.text.NumberFormat;
import java.util.List;

import static me.villagerunknown.villagercoin.Villagercoin.*;

public class AbstractLedgerItem extends WritableBookItem {
	
	public AbstractLedgerItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
		stack.set( DataComponentTypes.ITEM_NAME, Text.translatable(
				"item.villagerunknown-villagercoin.ledger.name",
				player.getNameForScoreboard()
		) );
		
		super.onCraftByPlayer(stack, world, player);
	}
	
	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
		DateComponent dateComponent = stack.get( DATE_COMPONENT );
		
		if( null != dateComponent ) {
			tooltip.add(
					Text.translatable(
							"item.villagerunknown-villagercoin.ledger.tooltip.date",
							dateComponent.date()
					).formatted(Formatting.GRAY)
			);
		} // if
		
		super.appendTooltip(stack, context, tooltip, options);
	}
	
}
