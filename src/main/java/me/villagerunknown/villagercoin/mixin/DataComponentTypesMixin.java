package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.Components;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DataComponents.class)
public class DataComponentTypesMixin {
	
	@Mutable
	@Final
	@Shadow
	public static final DataComponentMap COMMON_ITEM_COMPONENTS;
	
	@Shadow
	public static final DataComponentType<Integer> MAX_STACK_SIZE = Components.registerComponentType("max_stack_size", (builder) -> builder.persistent(ExtraCodecs.intRange(1, Integer.MAX_VALUE)).networkSynchronized(ByteBufCodecs.VAR_INT));
	
	static {
		COMMON_ITEM_COMPONENTS = DataComponentMap.builder().set(MAX_STACK_SIZE, 64).set(DataComponents.LORE, ItemLore.EMPTY).set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).set(DataComponents.REPAIR_COST, 0).set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY).set(DataComponents.RARITY, Rarity.COMMON).build();
	}
	
}
