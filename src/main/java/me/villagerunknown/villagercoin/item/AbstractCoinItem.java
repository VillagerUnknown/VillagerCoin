package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.text.NumberFormat;
import java.util.List;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;


public abstract class AbstractCoinItem extends Item {
	
	public AbstractCoinItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
		if( !player.getWorld().isClient() ) {
			playCoinSound(player);
		} // if
		
		return super.onStackClicked(stack, slot, clickType, player);
	}
	
	@Override
	public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
		if( !world.isClient() ) {
			playCoinSound(player);
		} // if
		
		super.onCraftByPlayer(stack, world, player);
	}
	
	public static void playCoinSound( PlayerEntity player ) {
		CoinFeature.playCoinSound( player );
	}
	
	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
		CurrencyComponent currencyComponent = stack.get( CURRENCY_COMPONENT );
		
		if( null != currencyComponent ) {
			NumberFormat numberFormat = NumberFormat.getIntegerInstance();
			
			tooltip.add(
					Text.translatable(
							"block.villagerunknown-villagercoin.coin_bank.tooltip",
							numberFormat.format((long) currencyComponent.value() * stack.getCount() ),
							CoinItems.COPPER_COIN.getName().getString()
					).formatted(Formatting.ITALIC, Formatting.GRAY)
			);
		} // if
		
		super.appendTooltip(stack, context, tooltip, options);
	}
	
}
