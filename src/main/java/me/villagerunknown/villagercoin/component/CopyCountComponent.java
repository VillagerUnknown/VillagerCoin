package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record CopyCountComponent(int count) {
	
	public static final Codec<CopyCountComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.INT.fieldOf( "count" ).forGetter(CopyCountComponent::count)
		).apply(instance, CopyCountComponent::new);
	});
	public static final StreamCodec<RegistryFriendlyByteBuf, CopyCountComponent> PACKET_CODEC;
	
	public CopyCountComponent(int count) {
		this.count = count;
	}
	
	public int count() {
		return count;
	}
	
	static {
		PACKET_CODEC = StreamCodec.composite(
				ByteBufCodecs.INT, CopyCountComponent::count,
				CopyCountComponent::new
		);
	}
	
}
