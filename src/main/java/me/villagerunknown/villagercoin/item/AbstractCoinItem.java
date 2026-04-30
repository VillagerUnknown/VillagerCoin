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

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;


public abstract class AbstractCoinItem extends Item {
	
	public AbstractCoinItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
		if( !player.getEntityWorld().isClient() ) {
			playCoinSound(player);
		} // if
		
		return super.onStackClicked(stack, slot, clickType, player);
	}
	
	@Override
	public void onCraftByPlayer(ItemStack stack, PlayerEntity player) {
		World world = player.getEntityWorld();
		
		if( !world.isClient() ) {
			playCoinSound(player);
		} // if
		
		super.onCraftByPlayer(stack, player);
	}
	
	public static void playCoinSound( PlayerEntity player ) {
		CoinFeature.playCoinSound( player );
	}
	
}
