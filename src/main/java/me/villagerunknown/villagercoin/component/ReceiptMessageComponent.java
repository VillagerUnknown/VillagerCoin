package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ReceiptMessageComponent(String message) {
	
	public static final Codec<ReceiptMessageComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.STRING.fieldOf( "message" ).forGetter(ReceiptMessageComponent::message)
		).apply(instance, ReceiptMessageComponent::new);
	});
	public static final StreamCodec<RegistryFriendlyByteBuf, ReceiptMessageComponent> PACKET_CODEC;
	
	public ReceiptMessageComponent(String message) {
		this.message = message;
	}
	
	public String message() {
		return message;
	}
	
	static {
		PACKET_CODEC = StreamCodec.composite(
				ByteBufCodecs.STRING_UTF8, ReceiptMessageComponent::message,
				ReceiptMessageComponent::new
		);
	}
	
}
