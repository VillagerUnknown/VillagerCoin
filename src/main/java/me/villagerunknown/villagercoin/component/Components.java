package me.villagerunknown.villagercoin.component;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

import static me.villagerunknown.villagercoin.Villagercoin.MOD;

public class Components {
	
	public static final ComponentType<CoinComponent> COIN_COMPONENT;
	
	public static final ComponentType<DropComponent> DROP_COMPONENT;
	
	public static final ComponentType<LootTableComponent> LOOT_TABLE_COMPONENT;
	
	public static final ComponentType<CurrencyComponent> CURRENCY_COMPONENT;
	
	public static final ComponentType<CollectableComponent> COLLECTABLE_COMPONENT;
	
	public static final ComponentType<ReceiptValueComponent> RECEIPT_VALUE_COMPONENT;
	
	public static final ComponentType<ReceiptMessageComponent> RECEIPT_MESSAGE_COMPONENT;
	
	public static final ComponentType<DateComponent> DATE_COMPONENT;
	
	public static final ComponentType<UpdatedDateComponent> UPDATED_DATE_COMPONENT;
	
	public static final ComponentType<AccumulatingValueComponent> ACCUMULATING_VALUE_COMPONENT;
	
	public static final ComponentType<CopyCountComponent> COPY_COUNT_COMPONENT;
	
	public static <T> ComponentType<T> registerComponentType(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return (ComponentType) Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MOD.getModId(), id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
	}
	
	static{
		COIN_COMPONENT = registerComponentType("coin", (builder) -> {
			return builder.codec(CoinComponent.CODEC).packetCodec(CoinComponent.PACKET_CODEC).cache();
		});
		DROP_COMPONENT = registerComponentType("drop", (builder) -> {
			return builder.codec(DropComponent.CODEC).packetCodec(DropComponent.PACKET_CODEC).cache();
		});
		LOOT_TABLE_COMPONENT = registerComponentType("loot_table", (builder) -> {
			return builder.codec(LootTableComponent.CODEC).packetCodec(LootTableComponent.PACKET_CODEC).cache();
		});
		CURRENCY_COMPONENT = registerComponentType("currency", (builder) -> {
			return builder.codec(CurrencyComponent.CODEC).packetCodec(CurrencyComponent.PACKET_CODEC).cache();
		});
		COLLECTABLE_COMPONENT = registerComponentType("collectable", (builder) -> {
			return builder.codec(CollectableComponent.CODEC).packetCodec(CollectableComponent.PACKET_CODEC).cache();
		});
		RECEIPT_VALUE_COMPONENT = registerComponentType("receipt_value", (builder) -> {
			return builder.codec(ReceiptValueComponent.CODEC).packetCodec(ReceiptValueComponent.PACKET_CODEC).cache();
		});
		RECEIPT_MESSAGE_COMPONENT = registerComponentType("receipt_message", (builder) -> {
			return builder.codec(ReceiptMessageComponent.CODEC).packetCodec(ReceiptMessageComponent.PACKET_CODEC).cache();
		});
		DATE_COMPONENT = registerComponentType("date", (builder) -> {
			return builder.codec(DateComponent.CODEC).packetCodec(DateComponent.PACKET_CODEC).cache();
		});
		UPDATED_DATE_COMPONENT = registerComponentType("updated_date", (builder) -> {
			return builder.codec(UpdatedDateComponent.CODEC).packetCodec(UpdatedDateComponent.PACKET_CODEC).cache();
		});
		ACCUMULATING_VALUE_COMPONENT = registerComponentType("accumulating_value", (builder) -> {
			return builder.codec(AccumulatingValueComponent.CODEC).packetCodec(AccumulatingValueComponent.PACKET_CODEC).cache();
		});
		COPY_COUNT_COMPONENT = registerComponentType("copy_count", (builder) -> {
			return builder.codec(CopyCountComponent.CODEC).packetCodec(CopyCountComponent.PACKET_CODEC).cache();
		});
	}
	
}
