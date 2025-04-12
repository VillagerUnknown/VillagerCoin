package me.villagerunknown.villagercoin.mixin;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.villagerunknown.villagercoin.data.type.CollectableComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

import static me.villagerunknown.villagercoin.Villagercoin.COLLECTABLE_COMPONENT;

@Mixin(LootTable.class)
public class LootTableMixin {
	
	@Inject(method = "shuffle", at = @At("HEAD"), cancellable = true)
	public void shuffle(ObjectArrayList<ItemStack> drops, int freeSlots, Random random, CallbackInfo ci) {
		List<ItemStack> list = Lists.newArrayList();
		Iterator<ItemStack> iterator = drops.iterator();
		
		while(iterator.hasNext()) {
			ItemStack itemStack = (ItemStack)iterator.next();
			
			if( !itemStack.isEmpty() ) {
				Item item = itemStack.getItem();
				
				CollectableComponent collectableComponent = itemStack.get( COLLECTABLE_COMPONENT );
				
				if( null != collectableComponent ) {
					if( collectableComponent.canAddToCirculation( item ) ) {
						collectableComponent.addToCirculation( item, itemStack.getCount() );
						list.add( itemStack );
					} // if
					
					iterator.remove();
				} // if
			} // if
		}
		
		drops.addAll(list);
	}
	
}
