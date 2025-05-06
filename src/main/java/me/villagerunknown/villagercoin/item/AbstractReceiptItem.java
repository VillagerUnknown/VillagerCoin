package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.DateComponent;
import me.villagerunknown.villagercoin.component.ReceiptMessageComponent;
import me.villagerunknown.villagercoin.component.ReceiptValueComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.text.NumberFormat;
import java.util.List;

import static me.villagerunknown.villagercoin.component.Components.*;


public class AbstractReceiptItem extends Item {
	
	public AbstractReceiptItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
		DateComponent dateComponent = stack.get( DATE_COMPONENT );
		
		if( null != dateComponent ) {
			tooltip.add(
					Text.translatable(
							"item.villagerunknown-villagercoin.receipt.tooltip.date",
							dateComponent.date()
					).formatted(Formatting.GRAY)
			);
		} // if
		
		ReceiptValueComponent receiptValueComponent = stack.get( RECEIPT_VALUE_COMPONENT );
		
		if( null != receiptValueComponent ) {
			NumberFormat numberFormat = NumberFormat.getIntegerInstance();
			
			tooltip.add(
					Text.translatable(
							"item.villagerunknown-villagercoin.receipt.tooltip.value",
							numberFormat.format(receiptValueComponent.value() * stack.getCount() ),
							CoinItems.COPPER_COIN.getName().getString()
					).formatted(Formatting.GRAY)
			);
		} // if
		
		ReceiptMessageComponent receiptMessageComponent = stack.get( RECEIPT_MESSAGE_COMPONENT );
		
		if( null != receiptMessageComponent ) {
			tooltip.add(
					Text.literal(
							receiptMessageComponent.message()
					).formatted(Formatting.ITALIC, Formatting.GRAY)
			);
		} // if
		
		super.appendTooltip(stack, context, tooltip, options);
	}
	
}
