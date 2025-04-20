package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record LootTableComponent(int lootTableWeight, int lootTableRolls ) {
	
	public static final Codec<LootTableComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.INT.fieldOf( "lootTableWeight" ).forGetter(LootTableComponent::lootTableWeight),
				Codec.INT.fieldOf( "lootTableRolls" ).forGetter(LootTableComponent::lootTableRolls)
		).apply(instance, LootTableComponent::new);
	});
	public static final PacketCodec<ByteBuf, LootTableComponent> PACKET_CODEC;
	
	public LootTableComponent( int lootTableWeight, int lootTableRolls ) {
		this.lootTableWeight = lootTableWeight;
		this.lootTableRolls = lootTableRolls;
	}
	
	public int lootTableWeight() {
		return lootTableWeight;
	}
	
	public int lootTableRolls() {
		return lootTableRolls;
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.INTEGER, LootTableComponent::lootTableWeight,
				PacketCodecs.INTEGER, LootTableComponent::lootTableRolls,
				LootTableComponent::new
		);
	}
	
}
