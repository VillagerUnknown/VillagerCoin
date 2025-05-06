package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ReceiptMessageComponent(String message) {
	
	public static final Codec<ReceiptMessageComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.STRING.fieldOf( "message" ).forGetter(ReceiptMessageComponent::message)
		).apply(instance, ReceiptMessageComponent::new);
	});
	public static final PacketCodec<RegistryByteBuf, ReceiptMessageComponent> PACKET_CODEC;
	
	public ReceiptMessageComponent(String message) {
		this.message = message;
	}
	
	public String message() {
		return message;
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.STRING, ReceiptMessageComponent::message,
				ReceiptMessageComponent::new
		);
	}
	
}
