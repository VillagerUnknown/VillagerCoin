package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record UpdatedDateComponent(String date) {
	
	public static final Codec<UpdatedDateComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.STRING.fieldOf( "date" ).forGetter(UpdatedDateComponent::date)
		).apply(instance, UpdatedDateComponent::new);
	});
	public static final PacketCodec<RegistryByteBuf, UpdatedDateComponent> PACKET_CODEC;
	
	public UpdatedDateComponent(String date) {
		this.date = date;
	}
	
	public String date() {
		return date;
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.STRING, UpdatedDateComponent::date,
				UpdatedDateComponent::new
		);
	}
	
}
