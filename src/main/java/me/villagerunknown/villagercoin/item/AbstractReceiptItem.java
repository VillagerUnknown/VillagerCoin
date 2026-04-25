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
	
}
