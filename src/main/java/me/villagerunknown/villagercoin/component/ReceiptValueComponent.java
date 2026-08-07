package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ReceiptValueComponent(long value) {
	
	public static final Codec<ReceiptValueComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.LONG.fieldOf( "value" ).forGetter(ReceiptValueComponent::value)
		).apply(instance, ReceiptValueComponent::new);
	});
	public static final StreamCodec<RegistryFriendlyByteBuf, ReceiptValueComponent> PACKET_CODEC;
	
	public ReceiptValueComponent(long value) {
		this.value = value;
	}
	
	public long value() {
		return value;
	}
	
	static {
		PACKET_CODEC = StreamCodec.composite(
				ByteBufCodecs.VAR_LONG, ReceiptValueComponent::value,
				ReceiptValueComponent::new
		);
	}
	
}
