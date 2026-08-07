package me.villagerunknown.villagercoin.data.persistent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.adapter.ItemCountMapTypeAdapter;
import me.villagerunknown.platform.data.persistent.AbstractPersistentData;
import me.villagerunknown.villagercoin.data.ItemExistenceData;
import net.fabricmc.fabric.impl.transfer.VariantCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.SavedDataStorage;
import java.util.HashMap;
import java.util.Map;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class PersistentItemExistenceData extends AbstractPersistentData {
	
	public static final Gson gson = getGsonWithTypeAdapter();
	
	private static final Codec<PersistentItemExistenceData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.unboundedMap(
					Codec.STRING,
					Codec.INT
			).fieldOf("itemsInExistence").forGetter( PersistentItemExistenceData::getItemsInExistence )
	).apply( instance, PersistentItemExistenceData::new ));
	
	private static SavedDataType<PersistentItemExistenceData> type = new SavedDataType<>(
			Identifier.fromNamespaceAndPath( Platform.MOD_ID, "item_existence_data" ),
			PersistentItemExistenceData::new,
			CODEC,
			null
	);
	
	public HashMap<String, Integer> ITEMS_IN_EXISTENCE = new HashMap<>();
	
	PersistentItemExistenceData() {}
	
	public PersistentItemExistenceData( Map<String, Integer> itemsInExistence ) {
		ITEMS_IN_EXISTENCE = new HashMap<>(itemsInExistence);
	}
	
	public HashMap<String, Integer> getItemsInExistence() {
		return ITEMS_IN_EXISTENCE;
	}
	
	private static Gson getGsonWithTypeAdapter() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		gsonBuilder.registerTypeAdapter( new TypeToken<HashMap<String, Integer>>() {}.getType(), new ItemCountMapTypeAdapter() );
		
		return gsonBuilder.create();
	}
	
	public static PersistentItemExistenceData getServerState(MinecraftServer server) {
		SavedDataStorage persistentStateManager = server.getLevel(Level.OVERWORLD).getDataStorage();
		
		PersistentItemExistenceData state = persistentStateManager.computeIfAbsent(type);
		
		state.setDirty();
		
		return state;
	}
	
}
