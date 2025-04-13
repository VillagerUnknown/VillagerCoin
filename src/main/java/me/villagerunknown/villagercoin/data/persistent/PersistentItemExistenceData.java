package me.villagerunknown.villagercoin.data.persistent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.villagerunknown.villagercoin.adapter.ItemCountMapTypeAdapter;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;

public class PersistentItemExistenceData extends PersistentState {
	
	public static final Gson gson = getGsonWithTypeAdapter();
	
	private static Type<PersistentItemExistenceData> type = new Type<>(
			PersistentItemExistenceData::new,
			PersistentItemExistenceData::createFromNbt,
			null
	);
	
	public HashMap<Item, Integer> ITEMS_IN_EXISTENCE = new HashMap<>();
	
	private static Gson getGsonWithTypeAdapter() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		gsonBuilder.registerTypeAdapter( new TypeToken<HashMap<Item, Integer>>() {}.getType(), new ItemCountMapTypeAdapter() );
		
		return gsonBuilder.create();
	}
	
	@Override
	public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		nbt.putString( "itemsInExistence", gson.toJson( ITEMS_IN_EXISTENCE, new TypeToken<HashMap<Item, Integer>>() {}.getType() ) );
		
		return nbt;
	}
	
	public static PersistentItemExistenceData createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		PersistentItemExistenceData state = new PersistentItemExistenceData();

		state.ITEMS_IN_EXISTENCE = gson.fromJson( tag.getString("itemsInExistence"), new TypeToken<HashMap<Item, Integer>>() {}.getType() );
		
		return state;
	}
	
	public static PersistentItemExistenceData getServerState(MinecraftServer server) {
		PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
		
		PersistentItemExistenceData state = persistentStateManager.getOrCreate(type, MOD_ID);
		
		state.markDirty();
		
		return state;
	}
	
}
