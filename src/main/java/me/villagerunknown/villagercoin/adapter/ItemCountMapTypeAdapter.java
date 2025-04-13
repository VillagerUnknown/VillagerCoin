package me.villagerunknown.villagercoin.adapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.HashMap;

public class ItemCountMapTypeAdapter extends TypeAdapter<HashMap<Item, Integer>> {
	
	@Override
	public void write(JsonWriter out, HashMap<Item, Integer> value) throws IOException {
		out.beginObject();
		
		value.forEach(( item, count ) -> {
			Identifier itemId = Registries.ITEM.getId( item );
			
			try {
				out.name( itemId.toString() );
				out.value( count );
			} catch (IOException e) {
				throw new RuntimeException(e);
			} // try, catch
		});
		
		out.endObject();
	}
	
	@Override
	public HashMap<Item, Integer> read(JsonReader in) throws IOException {
		HashMap<Item, Integer> itemCounts = new HashMap<>();
		
		in.beginObject();
		
		while (in.hasNext()) {
			itemCounts.put( Registries.ITEM.get(Identifier.of(in.nextName())), in.nextInt() );
		} // while
		
		in.endObject();
		
		return itemCounts;
	}
}
