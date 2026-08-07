package me.villagerunknown.villagercoin.recipe;

import com.mojang.serialization.MapCodec;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.CopyCountComponent;
import me.villagerunknown.villagercoin.feature.LedgerCraftingFeature;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import static me.villagerunknown.villagercoin.component.Components.COPY_COUNT_COMPONENT;

public class LedgerCloningRecipe extends CustomRecipe {
	
	public static final LedgerCloningRecipe INSTANCE = new LedgerCloningRecipe();
	public static final MapCodec<LedgerCloningRecipe> CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, LedgerCloningRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
	
	public LedgerCloningRecipe() {
		super();
	}
	
	public boolean matches(CraftingInput craftingRecipeInput, Level world) {
		int i = 0;
		ItemStack itemStack = ItemStack.EMPTY;
		
		for(int j = 0; j < craftingRecipeInput.size(); ++j) {
			ItemStack itemStack2 = craftingRecipeInput.getItem(j);
			if (!itemStack2.isEmpty()) {
				if (itemStack2.is(Villagercoin.getItemTagKey( "ledger" ))) {
					if (!itemStack.isEmpty()) {
						return false;
					}
					
					itemStack = itemStack2;
				} else {
					if (!itemStack2.is(Items.WRITABLE_BOOK)) {
						return false;
					}
					
					++i;
				}
			}
		}
		
		return !itemStack.isEmpty() && i > 0;
	}
	
	@Override
	public @NonNull ItemStack assemble(CraftingInput craftingRecipeInput) {
		int i = 0;
		ItemStack itemStack = ItemStack.EMPTY;
		
		for(int j = 0; j < craftingRecipeInput.size(); ++j) {
			ItemStack itemStack2 = craftingRecipeInput.getItem(j);
			if (!itemStack2.isEmpty()) {
				if (itemStack2.is(Villagercoin.getItemTagKey( "ledger" ))) {
					if (!itemStack.isEmpty()) {
						return ItemStack.EMPTY;
					}
					
					itemStack = itemStack2;
				} else {
					if (!itemStack2.is(Items.WRITABLE_BOOK)) {
						return ItemStack.EMPTY;
					}
					
					++i;
				}
			}
		}
		
		WritableBookContent writableBookContentComponent = itemStack.get(DataComponents.WRITABLE_BOOK_CONTENT);
		if (!itemStack.isEmpty() && i >= 1 && writableBookContentComponent != null) {
			ItemStack returnStack = itemStack.copyWithCount(i);
			
			CopyCountComponent copyCountComponent = itemStack.get(COPY_COUNT_COMPONENT);
			
			if( null != copyCountComponent ) {
				returnStack.set( COPY_COUNT_COMPONENT, new CopyCountComponent(copyCountComponent.count() + 1) );
			} else {
				returnStack.set( COPY_COUNT_COMPONENT, new CopyCountComponent(1) );
			} // if, else
			
			return returnStack;
		} else {
			return ItemStack.EMPTY;
		}
	}
	
	public NonNullList<ItemStack> getRemainder(CraftingInput craftingRecipeInput) {
		NonNullList<ItemStack> defaultedList = NonNullList.withSize(craftingRecipeInput.size(), ItemStack.EMPTY);
		
		for(int i = 0; i < defaultedList.size(); ++i) {
			ItemStack itemStack = craftingRecipeInput.getItem(i);
			ItemStackTemplate craftingRemainder = itemStack.getItem().getCraftingRemainder();
			if ( null != craftingRemainder && !craftingRemainder.create().isEmpty()) {
				defaultedList.set(i, craftingRemainder.create());
			} else if (itemStack.getItem() instanceof WrittenBookItem) {
				defaultedList.set(i, itemStack.copyWithCount(1));
				break;
			}
		}
		
		return defaultedList;
	}
	
	public RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return LedgerCraftingFeature.CLONING_RECIPE_SERIALIZER;
	}
	
	public boolean fits(int width, int height) {
		return width >= 3 && height >= 3;
	}
	
}
