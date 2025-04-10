package me.villagerunknown.villagercoin.item;

import me.villagerunknown.platform.util.EntityUtil;
import me.villagerunknown.platform.util.MathUtil;
import me.villagerunknown.platform.util.MessageUtil;
import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class EdibleVillagerCoinItem extends Item {
	
	public static SoundEvent SOUND = SoundEvents.ENTITY_GENERIC_EAT;
	
	public EdibleVillagerCoinItem(Settings settings) {
		super(settings.food(FoodComponents.COOKIE));
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.EAT;
	}
	
	@Override
	public SoundEvent getEatSound() {
		return SOUND;
	}
	
}
