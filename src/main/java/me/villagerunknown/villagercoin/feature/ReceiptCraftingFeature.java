package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.component.DateComponent;
import me.villagerunknown.villagercoin.component.ReceiptValueComponent;
import me.villagerunknown.villagercoin.recipe.ReceiptRecipe;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.time.LocalDate;
import java.util.HashSet;

import static me.villagerunknown.villagercoin.Villagercoin.*;

public class ReceiptCraftingFeature {
	
	public static Item RECIPE_CARRIER_ITEM = Items.PAPER;
	
	private static HashSet<Item> CRAFTING_RESULT_RECEIPTS = new HashSet<>();
	
	public static RecipeSerializer<ReceiptRecipe> RECIPE_SERIALIZER;
	
	public static void execute(){}
	
	public static void registerCraftingResultReceipt( Item item ) {
		CRAFTING_RESULT_RECEIPTS.add( item );
	}
	
	public static boolean isCraftingResultReceipt( Item item ) {
		return CRAFTING_RESULT_RECEIPTS.contains( item );
	}
	
	public static HashSet<Item> getCraftingResultReceipts() {
		return CRAFTING_RESULT_RECEIPTS;
	}
	
	public static ItemStack setReceiptValue( ItemStack itemStack, long totalValue ) {
		itemStack.set( RECEIPT_VALUE_COMPONENT, new ReceiptValueComponent( totalValue ));
		return itemStack;
	}
	
	public static ItemStack setCustomName( PlayerEntity player, ItemStack itemStack ) {
		itemStack.set( DataComponentTypes.ITEM_NAME, Text.translatable( "item.villagerunknown-villagercoin.receipt.tooltip.merchant", player.getNameForScoreboard() ) );
		return itemStack;
	}
	
	public static ItemStack setCraftedDate( ItemStack itemStack ) {
		itemStack.set( DATE_COMPONENT, new DateComponent( LocalDate.now().toString() ) );
		return itemStack;
	}
	
	public static void subtractCarrierFromIngredients(RecipeInputInventory craftingInput, long amount ) {
		CraftingRecipeInput.Positioned positioned = craftingInput.createPositionedRecipeInput();
		CraftingRecipeInput craftingRecipeInput = positioned.input();
		int left = positioned.left();
		int top = positioned.top();
		
		for(int y = 0; y < craftingRecipeInput.getHeight(); ++y) {
			for (int x = 0; x < craftingRecipeInput.getWidth(); ++x) {
				int m = x + left + (y + top) * craftingInput.getWidth();
				ItemStack ingredientStack = craftingInput.getStack(m);
				
				if( ingredientStack.isOf( RECIPE_CARRIER_ITEM ) ) {
					if( amount > Integer.MAX_VALUE ) {
						amount = Integer.MAX_VALUE;
					} // if
					
					craftingInput.removeStack( m, CoinCraftingFeature.toIntSafely(amount) );
				} // if
			} // for
		} // for
	}
	
	public static void subtractCarrierFromIngredients(DefaultedList<ItemStack> ingredients, int amount ) {
		for (ItemStack ingredientStack : ingredients) {
			if( ingredientStack.isOf( RECIPE_CARRIER_ITEM ) ) {
				ingredientStack.decrement( amount );
			} // if
		} // for
	}
	
	static {
		RECIPE_SERIALIZER = (RecipeSerializer) Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of( MOD_ID, "crafting_special_receipt" ), new SpecialRecipeSerializer(ReceiptRecipe::new));
	}
	
}
