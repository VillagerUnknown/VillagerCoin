package me.villagerunknown.villagercoin.data.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.villagercoin.feature.coinFeature;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record CurrencyComponent(int value) {
	
	public static final Codec<CurrencyComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.INT.fieldOf( "value" ).forGetter(CurrencyComponent::value)
		).apply(instance, CurrencyComponent::new);
	});
	public static final PacketCodec<RegistryByteBuf, CurrencyComponent> PACKET_CODEC;
	
	public CurrencyComponent(int value) {
		this.value = value;
	}
	
	public int value() {
		return value;
	}
	
	public ItemStack getLargestCoin( int coinValue ) {
		return coinFeature.getLargestCoin( coinValue );
	}
	
	public ItemStack getLargerCoin( int coinValue ) {
		return coinFeature.getLargerCoin( coinValue );
	}
	
	public ItemStack getSmallerCoin( int coinValue ) {
		return coinFeature.getSmallerCoin( coinValue );
	}
	
	public int getConversionValue( int fromValue, CurrencyComponent toCurrencyComponent ) {
		return coinFeature.getConversionValue( fromValue, toCurrencyComponent.value());
	}
	
	public int getConversionValue( int fromValue, int toValue ) {
		return coinFeature.getConversionValue( fromValue, toValue );
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.INTEGER, CurrencyComponent::value,
				CurrencyComponent::new
		);
	}
	
}
