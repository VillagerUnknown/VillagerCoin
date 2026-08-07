package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.text.NumberFormat;
import java.util.List;

import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;


public abstract class AbstractCoinItem extends Item {
	
	public AbstractCoinItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickType, Player player) {
		if( !player.level().isClientSide() ) {
			playCoinSound(player);
		} // if
		
		return super.overrideStackedOnOther(stack, slot, clickType, player);
	}
	
	@Override
	public void onCraftedBy(ItemStack stack, Player player) {
		Level world = player.level();
		
		if( !world.isClientSide() ) {
			playCoinSound(player);
		} // if
		
		super.onCraftedBy(stack, player);
	}
	
	public static void playCoinSound( Player player ) {
		CoinFeature.playCoinSound( player );
	}
	
}
