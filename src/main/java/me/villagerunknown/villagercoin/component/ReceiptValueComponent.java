package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ReceiptValueComponent(long value) {
	
	public static final Codec<ReceiptValueComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.LONG.fieldOf( "value" ).forGetter(ReceiptValueComponent::value)
		).apply(instance, ReceiptValueComponent::new);
	});
	public static final PacketCodec<RegistryByteBuf, ReceiptValueComponent> PACKET_CODEC;
	
	public ReceiptValueComponent(long value) {
		this.value = value;
	}
	
	public long value() {
		return value;
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.VAR_LONG, ReceiptValueComponent::value,
				ReceiptValueComponent::new
		);
	}
	
}
