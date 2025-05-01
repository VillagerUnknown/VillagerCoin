package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.time.LocalDate;

public record DateComponent(String date) {
	
	public static final Codec<DateComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.STRING.fieldOf( "date" ).forGetter(DateComponent::date)
		).apply(instance, DateComponent::new);
	});
	public static final PacketCodec<RegistryByteBuf, DateComponent> PACKET_CODEC;
	
	public DateComponent(String date) {
		this.date = date;
	}
	
	public String date() {
		return date;
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.STRING, DateComponent::date,
				DateComponent::new
		);
	}
	
}
