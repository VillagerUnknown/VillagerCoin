package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.component.*;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WritableBookContentComponent;
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
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		
		DateComponent dateComponent = stack.get( DATE_COMPONENT );
		
		if( null != dateComponent ) {
			tooltip.add(
					Text.translatable(
							"item.villagerunknown-villagercoin.ledger.tooltip.date",
							dateComponent.date()
					).formatted(Formatting.GRAY)
			);
		} // if
		
		UpdatedDateComponent updatedDateComponent = stack.get( UPDATED_DATE_COMPONENT );
		
		if( null != updatedDateComponent ) {
			tooltip.add(
					Text.translatable(
							"item.villagerunknown-villagercoin.ledger.tooltip.updated",
							updatedDateComponent.date()
					).formatted(Formatting.GRAY)
			);
		} // if
		
		WritableBookContentComponent writableBookContentComponent = stack.get( DataComponentTypes.WRITABLE_BOOK_CONTENT );
		
		if( null != writableBookContentComponent ) {
			tooltip.add(
					Text.translatable(
							"item.villagerunknown-villagercoin.ledger.tooltip.pages",
							numberFormat.format( writableBookContentComponent.pages().size() )
					).formatted(Formatting.GRAY)
			);
		} // if
		
		AccumulatingValueComponent accumulatingValueComponent = stack.get( ACCUMULATING_VALUE_COMPONENT );
		
		if( null != accumulatingValueComponent ) {
			tooltip.add(
					Text.translatable(
							"item.villagerunknown-villagercoin.ledger.tooltip.amount",
							CoinFeature.humanReadableNumber( accumulatingValueComponent.value(), true )
					).formatted(Formatting.GRAY)
			);
		} // if
		
		super.appendTooltip(stack, context, tooltip, options);
	}
	
}
