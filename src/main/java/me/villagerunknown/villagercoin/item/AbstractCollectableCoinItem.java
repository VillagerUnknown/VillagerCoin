package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.data.component.CollectableComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static me.villagerunknown.villagercoin.Villagercoin.COLLECTABLE_COMPONENT;

public abstract class AbstractCollectableCoinItem extends AbstractFlippableCoinItem {
	
	public AbstractCollectableCoinItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		playCoinSound( user );
		return super.use( world, user, hand );
	}
	
	public void onItemEntityDestroyed(ItemEntity entity) {
		ItemStack itemStack = entity.getStack();
		CollectableComponent collectableComponent = itemStack.get( COLLECTABLE_COMPONENT );
		
		if( null != collectableComponent ) {
			Item item = itemStack.getItem();
			
			if( collectableComponent.isInCirculation( item ) ) {
				collectableComponent.removeFromCirculation( item, itemStack.getCount() );
			} // if
		} // if
		
		super.onItemEntityDestroyed(entity);
	}
	
	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if( !player.isInCreativeMode() ) {
			Item item = stack.getItem();
			CollectableComponent collectableComponent = stack.get(COLLECTABLE_COMPONENT);
			
			if( null != collectableComponent && !collectableComponent.canAddToCirculation( item ) ) {
				collectableComponent.addToCirculation( item );
			} // if
		} // if
		
		return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
	}
	
}
