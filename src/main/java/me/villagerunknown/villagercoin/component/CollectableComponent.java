package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.villagercoin.feature.CollectableCoinFeature;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

public record CollectableComponent(int maximumAllowedInServer) {
	
	public static final Codec<CollectableComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.INT.fieldOf( "maximumAllowedInWorld" ).forGetter(CollectableComponent::maximumAllowedInServer)
		).apply(instance, CollectableComponent::new);
	});
	public static final StreamCodec<RegistryFriendlyByteBuf, CollectableComponent> PACKET_CODEC;
	
	public CollectableComponent(int maximumAllowedInServer) {
		this.maximumAllowedInServer = maximumAllowedInServer;
	}
	
	public int maximumAllowedInWorld() {
		return maximumAllowedInServer;
	}
	
	public int collectablesInCirculation() {
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
	
	public void removeFromCirculation( Item item, int amount ) {
		CollectableCoinFeature.removeFromCirculation( item, amount );
	}
	
	public void removeFromCirculation( Item item, boolean maximum ) {
		CollectableCoinFeature.removeFromCirculation( item, maximum );
	}
	
	static {
		PACKET_CODEC = StreamCodec.composite(
				ByteBufCodecs.INT, CollectableComponent::maximumAllowedInServer,
				CollectableComponent::new
		);
	}
	
}
