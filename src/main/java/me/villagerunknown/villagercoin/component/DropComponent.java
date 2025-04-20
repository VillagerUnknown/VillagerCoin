package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record DropComponent(int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier ) {
	
	public static final Codec<DropComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.INT.fieldOf( "dropMinimum" ).forGetter(DropComponent::dropMinimum),
				Codec.INT.fieldOf( "dropMaximum" ).forGetter(DropComponent::dropMaximum),
				Codec.FLOAT.fieldOf( "dropChance" ).forGetter(DropComponent::dropChance),
				Codec.INT.fieldOf( "dropChanceMultiplier" ).forGetter(DropComponent::dropChanceMultiplier)
		).apply(instance, DropComponent::new);
	});
	public static final PacketCodec<ByteBuf, DropComponent> PACKET_CODEC;
	
	public DropComponent( int dropMinimum, int dropMaximum, float dropChance, int dropChanceMultiplier ) {
		this.dropMinimum = dropMinimum;
		this.dropMaximum = dropMaximum;
		this.dropChance = dropChance;
		this.dropChanceMultiplier = dropChanceMultiplier;
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
	
	public int dropChanceMultiplier() {
		return dropChanceMultiplier;
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.INTEGER, DropComponent::dropMinimum,
				PacketCodecs.INTEGER, DropComponent::dropMaximum,
				PacketCodecs.FLOAT, DropComponent::dropChance,
				PacketCodecs.INTEGER, DropComponent::dropChanceMultiplier,
				DropComponent::new
		);
	}

}
