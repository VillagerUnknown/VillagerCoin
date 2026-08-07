package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record UpdatedDateComponent(String date) {
	
	public static final Codec<UpdatedDateComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.STRING.fieldOf( "date" ).forGetter(UpdatedDateComponent::date)
		).apply(instance, UpdatedDateComponent::new);
	});
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdatedDateComponent> PACKET_CODEC;
	
	public UpdatedDateComponent(String date) {
		this.date = date;
	}
	
	public String date() {
		return date;
	}
	
	static {
		PACKET_CODEC = StreamCodec.composite(
				ByteBufCodecs.STRING_UTF8, UpdatedDateComponent::date,
				UpdatedDateComponent::new
		);
	}
	
}
