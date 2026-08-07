package me.villagerunknown.villagercoin.item;

import me.villagerunknown.villagercoin.component.CollectableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static me.villagerunknown.villagercoin.component.Components.COLLECTABLE_COMPONENT;

public abstract class AbstractCollectableCoinItem extends AbstractFlippableCoinItem {
	
	public AbstractCollectableCoinItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public InteractionResult use(Level world, Player user, InteractionHand hand) {
		playCoinSound( user );
		return super.use( world, user, hand );
	}
	
	public void onDestroyed(ItemEntity entity) {
		ItemStack itemStack = entity.getItem();
		CollectableComponent collectableComponent = itemStack.get( COLLECTABLE_COMPONENT );
		
		if( null != collectableComponent ) {
			Item item = itemStack.getItem();
			
			if( collectableComponent.isInCirculation( item ) ) {
				collectableComponent.removeFromCirculation( item, itemStack.getCount() );
			} // if
		} // if
		
		super.onDestroyed(entity);
	}
	
}
