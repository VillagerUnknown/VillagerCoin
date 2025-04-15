package me.villagerunknown.villagercoin.data.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.villagercoin.feature.CoinCraftingFeature;
import me.villagerunknown.villagercoin.feature.CollectableCoinFeature;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

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
	
	public ItemStack getLargestCoin( int coinValue, boolean singleCount ) {
		return CoinCraftingFeature.getLargestCoin( coinValue, singleCount );
	}
	
	public ItemStack getLargerCoin( int coinValue, boolean singleCount ) {
		return CoinCraftingFeature.getLargerCoin( coinValue, singleCount );
	}
	
	public ItemStack getSmallerCoin( int coinValue ) {
		return CoinCraftingFeature.getSmallerCoin( coinValue );
	}
	
	public int getConversionValue( int fromValue, CurrencyComponent toCurrencyComponent ) {
		return CoinCraftingFeature.getConversionValue( fromValue, toCurrencyComponent.value());
	}
	
	public int getConversionValue( int fromValue, int toValue ) {
		return CoinCraftingFeature.getConversionValue( fromValue, toValue );
	}
	
	static {
		PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.INTEGER, CurrencyComponent::value,
				CurrencyComponent::new
		);
	}
	
}
