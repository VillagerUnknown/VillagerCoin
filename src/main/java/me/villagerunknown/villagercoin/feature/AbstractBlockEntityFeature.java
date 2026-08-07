package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;

public abstract class AbstractBlockEntityFeature {
	
	public static BlockEntityType<?> registerBlockEntities(FabricBlockEntityTypeBuilder<?> builder, String id ) {
		return Registry.register(
				BuiltInRegistries.BLOCK_ENTITY_TYPE,
				Identifier.fromNamespaceAndPath(Villagercoin.MOD_ID, id),
				builder.build()
		);
	}
	
}
