package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.time.LocalDate;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record DateComponent(String date) {
	
	public static final Codec<DateComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.STRING.fieldOf( "date" ).forGetter(DateComponent::date)
		).apply(instance, DateComponent::new);
	});
	public static final StreamCodec<RegistryFriendlyByteBuf, DateComponent> PACKET_CODEC;
	
	public DateComponent(String date) {
		this.date = date;
	}
	
	public String date() {
		return date;
	}
	
	static {
		PACKET_CODEC = StreamCodec.composite(
				ByteBufCodecs.STRING_UTF8, DateComponent::date,
				DateComponent::new
		);
	}
	
}
