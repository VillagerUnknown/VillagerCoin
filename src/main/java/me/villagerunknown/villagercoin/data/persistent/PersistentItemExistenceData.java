package me.villagerunknown.villagercoin.data.persistent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.platform.adapter.ItemCountMapTypeAdapter;
import me.villagerunknown.platform.data.persistent.AbstractPersistentData;
import me.villagerunknown.villagercoin.data.ItemExistenceData;
import net.fabricmc.fabric.impl.transfer.VariantCodecs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;

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
	
	private static PersistentStateType<PersistentItemExistenceData> type = new PersistentStateType<>(
			MOD_ID,
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
		PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
		
		PersistentItemExistenceData state = persistentStateManager.getOrCreate(type);
		
		state.markDirty();
		
		return state;
	}
	
}
