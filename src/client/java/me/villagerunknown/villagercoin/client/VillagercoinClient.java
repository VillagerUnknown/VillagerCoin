package me.villagerunknown.villagercoin.client;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.*;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import me.villagerunknown.villagercoin.item.AbstractLedgerItem;
import me.villagerunknown.villagercoin.item.AbstractReceiptItem;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.component.WritableBookContent;

import java.text.NumberFormat;

import static me.villagerunknown.villagercoin.component.Components.*;
import static me.villagerunknown.villagercoin.component.Components.ACCUMULATING_VALUE_COMPONENT;
import static me.villagerunknown.villagercoin.component.Components.COPY_COUNT_COMPONENT;

public class VillagercoinClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		registerCurrencyItemTooltips();
		registerReceiptItemTooltips();
		registerLedgerItemTooltips();
	}
	
	private static void registerCurrencyItemTooltips() {
		ItemTooltipCallback.EVENT.register((stack, tooltipConComponent, tooltipType, list) -> {
			if (!stack.getComponents().has( CURRENCY_COMPONENT )) {
				return;
			} // if
			
			CurrencyComponent currencyComponent = stack.get( CURRENCY_COMPONENT );
			
			if( null != currencyComponent ) {
				Long value = currencyComponent.value();
				
				if( stack.is( Villagercoin.getItemTagKey( "currency_coin" ) ) ) {
					value = currencyComponent.value() * stack.getCount();
				} // if
				
				NumberFormat numberFormat = NumberFormat.getNumberInstance();
				
				list.add(
						Component.translatable(
								"block.villagerunknown-villagercoin.coin_bank.tooltip",
								numberFormat.format( value ),
								CoinItems.COPPER_COIN.getDefaultInstance().getHoverName().getString()
						).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY)
				);
			} // if
		});
	}
	
	private static void registerReceiptItemTooltips() {
		ItemTooltipCallback.EVENT.register((stack, tooltipConComponent, tooltipType, list) -> {
			if (!(stack.getItem() instanceof AbstractReceiptItem)) {
				return;
			}
			
			DateComponent dateComponent = stack.get( DATE_COMPONENT );
			
			if( null != dateComponent ) {
				list.add(
						Component.translatable(
								"item.villagerunknown-villagercoin.receipt.tooltip.date",
								dateComponent.date()
						).withStyle(ChatFormatting.GRAY)
				);
			} // if
			
			ReceiptValueComponent receiptValueComponent = stack.get( RECEIPT_VALUE_COMPONENT );
			
			if( null != receiptValueComponent ) {
				NumberFormat numberFormat = NumberFormat.getIntegerInstance();
				
				list.add(
						Component.translatable(
								"item.villagerunknown-villagercoin.receipt.tooltip.value",
								numberFormat.format(receiptValueComponent.value() * stack.getCount() ),
								CoinItems.COPPER_COIN.getDefaultInstance().getHoverName().getString()
						).withStyle(ChatFormatting.GRAY)
				);
			} // if
			
			ReceiptMessageComponent receiptMessageComponent = stack.get( RECEIPT_MESSAGE_COMPONENT );
			
			if( null != receiptMessageComponent ) {
				list.add(
						Component.literal(
								receiptMessageComponent.message()
						).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY)
				);
			} // if
		});
	}
	
	private static void registerLedgerItemTooltips() {
		ItemTooltipCallback.EVENT.register((stack, tooltipConComponent, tooltipType, list) -> {
			if (!(stack.getItem() instanceof AbstractLedgerItem)) {
				return;
			}
			
			NumberFormat numberFormat = NumberFormat.getNumberInstance();
			
			DateComponent dateComponent = stack.get( DATE_COMPONENT );
			
			if( null != dateComponent ) {
				list.add(
						Component.translatable(
								"item.villagerunknown-villagercoin.ledger.tooltip.date",
								dateComponent.date()
						).withStyle(ChatFormatting.GRAY)
				);
			} // if
			
			UpdatedDateComponent updatedDateComponent = stack.get( UPDATED_DATE_COMPONENT );
			
			if( null != updatedDateComponent ) {
				list.add(
						Component.translatable(
								"item.villagerunknown-villagercoin.ledger.tooltip.updated",
								updatedDateComponent.date()
						).withStyle(ChatFormatting.GRAY)
				);
			} // if
			
			WritableBookContent writableBookContentComponent = stack.get(DataComponents.WRITABLE_BOOK_CONTENT);
			
			if( null != writableBookContentComponent ) {
				list.add(
						Component.translatable(
								"item.villagerunknown-villagercoin.ledger.tooltip.pages",
								numberFormat.format( writableBookContentComponent.pages().size() )
						).withStyle(ChatFormatting.GRAY)
				);
			} // if
			
			AccumulatingValueComponent accumulatingValueComponent = stack.get( ACCUMULATING_VALUE_COMPONENT );
			
			if( null != accumulatingValueComponent ) {
				list.add(
						Component.translatable(
								"item.villagerunknown-villagercoin.ledger.tooltip.amount",
								CoinFeature.humanReadableNumber( accumulatingValueComponent.value(), true ),
								CoinItems.COPPER_COIN.getDefaultInstance().getHoverName().getString()
						).withStyle(ChatFormatting.GRAY)
				);
			} // if
			
			CopyCountComponent copyCountComponent = stack.get( COPY_COUNT_COMPONENT );
			
			if( null != copyCountComponent ) {
				int copyCount = copyCountComponent.count();
				if ( copyCount == 0 ) {
					list.add(
							Component.translatable(
									"item.villagerunknown-villagercoin.ledger.tooltip.original"
							).withStyle(ChatFormatting.GRAY)
					);
				} else if ( copyCount == 1 ) {
					list.add(
							Component.translatable(
									"item.villagerunknown-villagercoin.ledger.tooltip.copy",
									CoinFeature.humanReadableNumber(copyCount, false)
							).withStyle(ChatFormatting.GRAY)
					);
				} else if( copyCount > 1 ) {
					list.add(
							Component.translatable(
									"item.villagerunknown-villagercoin.ledger.tooltip.copyOfCopy",
									CoinFeature.humanReadableNumber(copyCount, false)
							).withStyle(ChatFormatting.GRAY)
					);
				} // if, else if
			} else {
				list.add(
						Component.translatable(
								"item.villagerunknown-villagercoin.ledger.tooltip.original"
						).withStyle(ChatFormatting.GRAY)
				);
			} // if, else
		});
	}
	
}
