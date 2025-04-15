package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.data.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinCraftingFeature;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends ScreenHandler {
	
	@Final
	@Shadow
	private RecipeInputInventory craftingInput;
	
	@Final
	@Shadow
	private CraftingResultInventory craftingResult;
	
	protected PlayerScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
		super(type, syncId);
	}
	
	@Inject(method = "quickMove", at = @At("HEAD"), cancellable = true)
	public void quickMove(PlayerEntity player, int slot, CallbackInfoReturnable<ItemStack> cir) {
		if( 0 == slot ) {
			ItemStack craftedItemStack = this.craftingResult.getStack( slot );
			
			if( CoinCraftingFeature.isCraftingResultCoin( craftedItemStack.getItem() ) ) {
				CraftingRecipeInput.Positioned positioned = this.craftingInput.createPositionedRecipeInput();
				CraftingRecipeInput craftingRecipeInput = positioned.input();
				DefaultedList<ItemStack> defaultedList = player.getWorld().getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, craftingRecipeInput, player.getWorld());
				
				CurrencyComponent currencyComponent = craftedItemStack.get( CURRENCY_COMPONENT );
				
				if( null != currencyComponent ) {
					AtomicInteger totalCost = new AtomicInteger(craftedItemStack.getCount() * currencyComponent.value());
					TreeMap<Integer, CoinCraftingFeature.CoinIngredient> ingredientsMap = CoinCraftingFeature.getCoinIngredientsMap( this.craftingInput );
					
					ingredientsMap.forEach( ( order, coinIngredient ) -> {
						int ingredientSlot = coinIngredient.slot;
						ItemStack ingredient = coinIngredient.stack;
						ItemStack itemStack2 = (ItemStack)defaultedList.get(coinIngredient.x + coinIngredient.y * craftingRecipeInput.getWidth());
						
						totalCost.set( CoinCraftingFeature.subtractCoinValueFromTotalCost( ingredient, totalCost, this.craftingInput, ingredientSlot ) );
						
						if (!craftedItemStack.isEmpty()) {
							craftedItemStack.getItem().onCraftByPlayer(craftedItemStack, player.getWorld(), player);
							
							if (!this.insertItem(craftedItemStack, 9, 45, true)) {
								player.dropItem(craftedItemStack, false);
							}
							
							Slot slot2 = (Slot) this.slots.get(slot);
							slot2.onQuickTransfer(craftedItemStack, craftedItemStack);
							
							slot2.markDirty();
						} // if
					} );
					
					if( !craftedItemStack.isEmpty() ) {
						cir.setReturnValue( craftedItemStack );
					} // if
				} // if
				
				cir.setReturnValue( ItemStack.EMPTY );
			} // if
		} // if
	}
	
}
