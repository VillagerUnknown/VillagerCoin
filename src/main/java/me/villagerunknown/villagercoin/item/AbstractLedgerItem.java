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
import java.util.Objects;

import static me.villagerunknown.villagercoin.component.Components.*;

public class AbstractLedgerItem extends WritableBookItem {
	
	public AbstractLedgerItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public void onCraftByPlayer(ItemStack stack, PlayerEntity player) {
		Text nameComponent = stack.get(DataComponentTypes.ITEM_NAME);
		Text customNameComponent = stack.get(DataComponentTypes.CUSTOM_NAME);
		
		if(
			(null == nameComponent || Objects.equals( nameComponent, Text.translatable(stack.getItem().getTranslationKey()) ))
			&& null == customNameComponent
		) {
			stack.set(DataComponentTypes.ITEM_NAME, Text.translatable(
					"item.villagerunknown-villagercoin.ledger.name",
					player.getNameForScoreboard()
			));
		} // if
		
		super.onCraftByPlayer(stack, player);
	}
	
}
