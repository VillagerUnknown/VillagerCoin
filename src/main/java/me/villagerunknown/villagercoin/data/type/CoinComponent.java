package me.villagerunknown.villagercoin.data.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Rarity;

public record CoinComponent( Rarity rarity, int dropMinimum, int dropMaximum, float dropChance ) {
	
	public static final Codec<CoinComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Rarity.CODEC.fieldOf( "rarity" ).forGetter(CoinComponent::rarity),
				Codec.INT.fieldOf( "dropMinimum" ).forGetter(CoinComponent::dropMinimum),
				Codec.INT.fieldOf( "dropMaximum" ).forGetter(CoinComponent::dropMaximum),
				Codec.FLOAT.fieldOf( "dropChance" ).forGetter(CoinComponent::dropChance)
		).apply(instance, CoinComponent::new);
	});
	public static final PacketCodec<ByteBuf, CoinComponent> PACKET_CODEC;
	
	public CoinComponent( Rarity rarity, int dropMinimum, int dropMaximum, float dropChance ) {
		this.rarity = rarity;
		this.dropMinimum = dropMinimum;
		this.dropMaximum = dropMaximum;
		this.dropChance = dropChance;
	}
	
	public Rarity rarity() {
		return rarity;
	}
	
	public int dropMinimum() {
		return dropMinimum;
	}
	
	public int dropMaximum() {
		return dropMaximum;
	}
	
	public float dropChance() {
		return dropChance;
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				Rarity.PACKET_CODEC, CoinComponent::rarity,
				PacketCodecs.INTEGER, CoinComponent::dropMinimum,
				PacketCodecs.INTEGER, CoinComponent::dropMaximum,
				PacketCodecs.FLOAT, CoinComponent::dropChance,
				CoinComponent::new
		);
	}
	
}
