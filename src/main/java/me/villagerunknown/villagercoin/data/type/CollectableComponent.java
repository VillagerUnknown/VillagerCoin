package me.villagerunknown.villagercoin.data.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.villagercoin.feature.CollectableCoinFeature;
import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record CollectableComponent(int maximumAllowedInServer) {
	
	public static final Codec<CollectableComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.INT.fieldOf( "maximumAllowedInWorld" ).forGetter(CollectableComponent::maximumAllowedInServer)
		).apply(instance, CollectableComponent::new);
	});
	public static final PacketCodec<RegistryByteBuf, CollectableComponent> PACKET_CODEC;
	
	public CollectableComponent(int maximumAllowedInServer) {
		this.maximumAllowedInServer = maximumAllowedInServer;
	}
	
	public boolean isCollectable( Item item ) {
		return CollectableCoinFeature.isCollectable( item );
	}
	
	public void addCollectable( Item item ) {
		CollectableCoinFeature.addCollectable( item );
	}
	
	public int maximumAllowedInWorld() {
		return maximumAllowedInServer;
	}
	
	public static int collectablesInCirculation() {
		return CollectableCoinFeature.collectablesInCirculation();
	}
	
	public boolean isInCirculation( Item coin ) {
		return CollectableCoinFeature.isInCirculation( coin );
	}
	
	public boolean canAddToCirculation( Item item ) {
		return CollectableCoinFeature.canAddToCirculation(item, maximumAllowedInServer);
	}
	
	public void addToCirculation( Item item ) {
		CollectableCoinFeature.addToCirculation( item );
	}
	
	public void addToCirculation( Item item, int amount ) {
		CollectableCoinFeature.addToCirculation( item, amount );
	}
	
	public void removeFromCirculation( Item item ) {
		CollectableCoinFeature.removeFromCirculation( item );
	}
	
	public static void removeFromCirculation( Item item, boolean maximum ) {
		CollectableCoinFeature.removeFromCirculation( item, maximum );
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.INTEGER, CollectableComponent::maximumAllowedInServer,
				CollectableComponent::new
		);
	}
	
}
