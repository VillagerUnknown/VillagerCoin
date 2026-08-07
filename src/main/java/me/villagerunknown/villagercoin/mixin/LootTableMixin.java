package me.villagerunknown.villagercoin.mixin;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.villagerunknown.villagercoin.component.CollectableComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

import static me.villagerunknown.villagercoin.component.Components.COLLECTABLE_COMPONENT;

@Mixin(LootTable.class)
public class LootTableMixin {
	
	@Inject(method = "shuffleAndSplitItems", at = @At("HEAD"), cancellable = true)
	public void spreadStacks(ObjectArrayList<ItemStack> stacks, int freeSlots, RandomSource random, CallbackInfo ci) {
		List<ItemStack> list = Lists.newArrayList();
		Iterator<ItemStack> iterator = stacks.iterator();
		
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
		
		stacks.addAll(list);
		Util.shuffle(stacks, random);
	}
	
}
