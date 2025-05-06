package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.Components;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Rarity;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DataComponentTypes.class)
public class DataComponentTypesMixin {
	
	@Mutable
	@Final
	@Shadow
	public static final ComponentMap DEFAULT_ITEM_COMPONENTS;
	
	@Shadow
	public static final ComponentType<Integer> MAX_STACK_SIZE = Components.registerComponentType("max_stack_size", (builder) -> builder.codec(Codecs.rangedInt(1, Integer.MAX_VALUE)).packetCodec(PacketCodecs.VAR_INT));
	
	static {
		DEFAULT_ITEM_COMPONENTS = ComponentMap.builder().add(MAX_STACK_SIZE, 64).add(DataComponentTypes.LORE, LoreComponent.DEFAULT).add(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT).add(DataComponentTypes.REPAIR_COST, 0).add(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT).add(DataComponentTypes.RARITY, Rarity.COMMON).build();
	}
	
}
