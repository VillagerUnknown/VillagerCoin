package me.villagerunknown.villagercoin.recipe;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.feature.LedgerCraftingFeature;
import me.villagerunknown.villagercoin.feature.ReceiptCraftingFeature;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WritableBookContentComponent;
import net.minecraft.component.type.WrittenBookContentComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class LedgerCloningRecipe extends SpecialCraftingRecipe {
	
	public LedgerCloningRecipe(CraftingRecipeCategory craftingRecipeCategory) {
		super(craftingRecipeCategory);
	}
	
	public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
		int i = 0;
		ItemStack itemStack = ItemStack.EMPTY;
		
		for(int j = 0; j < craftingRecipeInput.getSize(); ++j) {
			ItemStack itemStack2 = craftingRecipeInput.getStackInSlot(j);
			if (!itemStack2.isEmpty()) {
				if (itemStack2.isIn(Villagercoin.getItemTagKey( "ledger" ))) {
					if (!itemStack.isEmpty()) {
						return false;
					}
					
					itemStack = itemStack2;
				} else {
					if (!itemStack2.isOf(Items.WRITABLE_BOOK)) {
						return false;
					}
					
					++i;
				}
			}
		}
		
		return !itemStack.isEmpty() && i > 0;
	}
	
	public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
		int i = 0;
		ItemStack itemStack = ItemStack.EMPTY;
		
		for(int j = 0; j < craftingRecipeInput.getSize(); ++j) {
			ItemStack itemStack2 = craftingRecipeInput.getStackInSlot(j);
			if (!itemStack2.isEmpty()) {
				if (itemStack2.isIn(Villagercoin.getItemTagKey( "ledger" ))) {
					if (!itemStack.isEmpty()) {
						return ItemStack.EMPTY;
					}
					
					itemStack = itemStack2;
				} else {
					if (!itemStack2.isOf(Items.WRITABLE_BOOK)) {
						return ItemStack.EMPTY;
					}
					
					++i;
				}
			}
		}
		
		WritableBookContentComponent writableBookContentComponent = itemStack.get(DataComponentTypes.WRITABLE_BOOK_CONTENT);
		if (!itemStack.isEmpty() && i >= 1 && writableBookContentComponent != null) {
			return itemStack.copyWithCount(i);
		} else {
			return ItemStack.EMPTY;
		}
	}
	
	public DefaultedList<ItemStack> getRemainder(CraftingRecipeInput craftingRecipeInput) {
		DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(craftingRecipeInput.getSize(), ItemStack.EMPTY);
		
		for(int i = 0; i < defaultedList.size(); ++i) {
			ItemStack itemStack = craftingRecipeInput.getStackInSlot(i);
			if (itemStack.getItem().hasRecipeRemainder()) {
				defaultedList.set(i, new ItemStack(itemStack.getItem().getRecipeRemainder()));
			} else if (itemStack.getItem() instanceof WrittenBookItem) {
				defaultedList.set(i, itemStack.copyWithCount(1));
				break;
			}
		}
		
		return defaultedList;
	}
	
	public RecipeSerializer<?> getSerializer() {
		return LedgerCraftingFeature.CLONING_RECIPE_SERIALIZER;
	}
	
	public boolean fits(int width, int height) {
		return width >= 3 && height >= 3;
	}
	
}
