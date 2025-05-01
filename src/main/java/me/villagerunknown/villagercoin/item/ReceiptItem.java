package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CoinComponent;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.component.DropComponent;
import me.villagerunknown.villagercoin.component.LootTableComponent;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import me.villagerunknown.villagercoin.feature.MobsDropCoinsFeature;
import me.villagerunknown.villagercoin.feature.StructuresIncludeCoinsFeature;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;

import java.text.NumberFormat;
import java.util.List;

import static me.villagerunknown.villagercoin.Villagercoin.*;

public class ReceiptItem extends AbstractReceiptItem {
	
	public ReceiptItem(Settings settings) {
		super(
				settings
		);
	}
	
}
