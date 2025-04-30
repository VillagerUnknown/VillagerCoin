package me.villagerunknown.villagercoin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.villagercoin.feature.CoinCraftingFeature;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record CurrencyComponent(long value) {
	
	public static final Codec<CurrencyComponent> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.LONG.fieldOf( "value" ).forGetter(CurrencyComponent::value)
		).apply(instance, CurrencyComponent::new);
	});
	public static final PacketCodec<RegistryByteBuf, CurrencyComponent> PACKET_CODEC;
	
	public CurrencyComponent(long value) {
		this.value = value;
	}
	
	public long value() {
		return value;
	}
	
	public ItemStack getLargestCoin( long coinValue, boolean singleCount ) {
		return CoinCraftingFeature.getLargestCoin( coinValue, singleCount );
	}
	
	public ItemStack getLargerCoin( long coinValue, boolean singleCount ) {
		return CoinCraftingFeature.getLargerCoin( coinValue, singleCount );
	}
	
	public ItemStack getSmallerCoin( long coinValue ) {
		return CoinCraftingFeature.getSmallerCoin( coinValue );
	}
	
	public long getConversionValue( long fromValue, CurrencyComponent toCurrencyComponent ) {
		return CoinCraftingFeature.getConversionValue( fromValue, toCurrencyComponent.value());
	}
	
	public long getConversionValue( int fromValue, int toValue ) {
		return CoinCraftingFeature.getConversionValue( fromValue, toValue );
	}
	
	public long getConversionValue( long fromValue, long toValue ) {
		return CoinCraftingFeature.getConversionValue( fromValue, toValue );
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.VAR_LONG, CurrencyComponent::value,
				CurrencyComponent::new
		);
	}
	
}
