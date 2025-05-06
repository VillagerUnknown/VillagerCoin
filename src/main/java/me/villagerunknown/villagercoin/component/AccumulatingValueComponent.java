package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record AccumulatingValueComponent(long value) {
	
	public static final Codec<AccumulatingValueComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.LONG.fieldOf( "value" ).forGetter(AccumulatingValueComponent::value)
		).apply(instance, AccumulatingValueComponent::new);
	});
	public static final PacketCodec<RegistryByteBuf, AccumulatingValueComponent> PACKET_CODEC;
	
	public AccumulatingValueComponent(long value) {
		this.value = value;
	}
	
	public long value() {
		return value;
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.VAR_LONG, AccumulatingValueComponent::value,
				AccumulatingValueComponent::new
		);
	}
	
}
