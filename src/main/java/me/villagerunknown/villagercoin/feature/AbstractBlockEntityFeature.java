package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public abstract class AbstractBlockEntityFeature {
	
	public static BlockEntityType<?> registerBlockEntities( BlockEntityType.Builder<?> builder, String id ) {
		return Registry.register(
				Registries.BLOCK_ENTITY_TYPE,
				Identifier.of(Villagercoin.MOD_ID, id),
				builder.build()
		);
	}
	
}
