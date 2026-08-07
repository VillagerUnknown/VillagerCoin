package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record AccumulatingValueComponent(long value) {
	
	public static final Codec<AccumulatingValueComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.LONG.fieldOf( "value" ).forGetter(AccumulatingValueComponent::value)
		).apply(instance, AccumulatingValueComponent::new);
	});
	public static final StreamCodec<RegistryFriendlyByteBuf, AccumulatingValueComponent> PACKET_CODEC;
	
	public AccumulatingValueComponent(long value) {
		this.value = value;
	}
	
	public long value() {
		return value;
	}
	
	static {
		PACKET_CODEC = StreamCodec.composite(
				ByteBufCodecs.VAR_LONG, AccumulatingValueComponent::value,
				AccumulatingValueComponent::new
		);
	}
	
}
