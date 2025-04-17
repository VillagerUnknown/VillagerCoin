package me.villagerunknown.villagercoin.data.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import me.villagerunknown.platform.util.MathUtil;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Rarity;

public record CoinComponent( Rarity rarity, float flipChance ) {
	
	public static final Codec<CoinComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Rarity.CODEC.fieldOf( "rarity" ).forGetter(CoinComponent::rarity),
				Codec.FLOAT.fieldOf( "flipChance" ).forGetter(CoinComponent::flipChance)
		).apply(instance, CoinComponent::new);
	});
	public static final PacketCodec<ByteBuf, CoinComponent> PACKET_CODEC;
	
	public CoinComponent( Rarity rarity, float flipChance ) {
		this.rarity = rarity;
		this.flipChance = flipChance;
	}
	
	public Rarity rarity() {
		return rarity;
	}
	
	public float flipChance() {
		return flipChance;
	}
	
	public boolean flip() {
		return MathUtil.hasChance( flipChance );
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				Rarity.PACKET_CODEC, CoinComponent::rarity,
				PacketCodecs.FLOAT, CoinComponent::flipChance,
				CoinComponent::new
		);
	}
	
}
