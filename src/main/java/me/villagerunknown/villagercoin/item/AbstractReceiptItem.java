package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.DateComponent;
import me.villagerunknown.villagercoin.component.ReceiptValueComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.text.NumberFormat;
import java.util.List;

import static me.villagerunknown.villagercoin.Villagercoin.DATE_COMPONENT;
import static me.villagerunknown.villagercoin.Villagercoin.RECEIPT_VALUE_COMPONENT;


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
		
		if( !Villagercoin.CONFIG.receiptThankYouMessage.isEmpty() ) {
			tooltip.add(
					Text.literal(
							Villagercoin.CONFIG.receiptThankYouMessage
					).formatted(Formatting.ITALIC, Formatting.GRAY)
			);
		} // if
		
		super.appendTooltip(stack, context, tooltip, options);
	}
	
}
