package me.villagerunknown.villagercoin.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ItemExistenceData {
	
	public static final Codec<ItemExistenceData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.STRING.fieldOf("itemsInExistence").forGetter(data -> data.itemsInExistence)
	).apply(instance, ItemExistenceData::new));
	
	// @type HashMap<String, Integer>
	public String itemsInExistence;
	
	public ItemExistenceData( String items ) {
		itemsInExistence = items;
	}

}
