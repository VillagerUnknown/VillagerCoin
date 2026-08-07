package me.villagerunknown.villagercoin.component;

import java.util.function.UnaryOperator;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import static me.villagerunknown.villagercoin.Villagercoin.MOD;
import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class Components {
	
	public static final DataComponentType<CoinComponent> COIN_COMPONENT;
	
	public static final DataComponentType<DropComponent> DROP_COMPONENT;
	
	public static final DataComponentType<LootTableComponent> LOOT_TABLE_COMPONENT;
	
	public static final DataComponentType<CurrencyComponent> CURRENCY_COMPONENT;
	
	public static final DataComponentType<CollectableComponent> COLLECTABLE_COMPONENT;
	
	public static final DataComponentType<ReceiptValueComponent> RECEIPT_VALUE_COMPONENT;
	
	public static final DataComponentType<ReceiptMessageComponent> RECEIPT_MESSAGE_COMPONENT;
	
	public static final DataComponentType<DateComponent> DATE_COMPONENT;
	
	public static final DataComponentType<UpdatedDateComponent> UPDATED_DATE_COMPONENT;
	
	public static final DataComponentType<AccumulatingValueComponent> ACCUMULATING_VALUE_COMPONENT;
	
	public static final DataComponentType<CopyCountComponent> COPY_COUNT_COMPONENT;
	
	public static <T> DataComponentType<T> registerComponentType(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
		return (DataComponentType) Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, id), ((DataComponentType.Builder)builderOperator.apply(DataComponentType.builder())).build());
	}
	
	static{
		COIN_COMPONENT = registerComponentType("coin", (builder) -> {
			return builder.persistent(CoinComponent.CODEC).networkSynchronized(CoinComponent.PACKET_CODEC).cacheEncoding();
		});
		DROP_COMPONENT = registerComponentType("drop", (builder) -> {
			return builder.persistent(DropComponent.CODEC).networkSynchronized(DropComponent.PACKET_CODEC).cacheEncoding();
		});
		LOOT_TABLE_COMPONENT = registerComponentType("loot_table", (builder) -> {
			return builder.persistent(LootTableComponent.CODEC).networkSynchronized(LootTableComponent.PACKET_CODEC).cacheEncoding();
		});
		CURRENCY_COMPONENT = registerComponentType("currency", (builder) -> {
			return builder.persistent(CurrencyComponent.CODEC).networkSynchronized(CurrencyComponent.PACKET_CODEC).cacheEncoding();
		});
		COLLECTABLE_COMPONENT = registerComponentType("collectable", (builder) -> {
			return builder.persistent(CollectableComponent.CODEC).networkSynchronized(CollectableComponent.PACKET_CODEC).cacheEncoding();
		});
		RECEIPT_VALUE_COMPONENT = registerComponentType("receipt_value", (builder) -> {
			return builder.persistent(ReceiptValueComponent.CODEC).networkSynchronized(ReceiptValueComponent.PACKET_CODEC).cacheEncoding();
		});
		RECEIPT_MESSAGE_COMPONENT = registerComponentType("receipt_message", (builder) -> {
			return builder.persistent(ReceiptMessageComponent.CODEC).networkSynchronized(ReceiptMessageComponent.PACKET_CODEC).cacheEncoding();
		});
		DATE_COMPONENT = registerComponentType("date", (builder) -> {
			return builder.persistent(DateComponent.CODEC).networkSynchronized(DateComponent.PACKET_CODEC).cacheEncoding();
		});
		UPDATED_DATE_COMPONENT = registerComponentType("updated_date", (builder) -> {
			return builder.persistent(UpdatedDateComponent.CODEC).networkSynchronized(UpdatedDateComponent.PACKET_CODEC).cacheEncoding();
		});
		ACCUMULATING_VALUE_COMPONENT = registerComponentType("accumulating_value", (builder) -> {
			return builder.persistent(AccumulatingValueComponent.CODEC).networkSynchronized(AccumulatingValueComponent.PACKET_CODEC).cacheEncoding();
		});
		COPY_COUNT_COMPONENT = registerComponentType("copy_count", (builder) -> {
			return builder.persistent(CopyCountComponent.CODEC).networkSynchronized(CopyCountComponent.PACKET_CODEC).cacheEncoding();
		});
	}
	
}
