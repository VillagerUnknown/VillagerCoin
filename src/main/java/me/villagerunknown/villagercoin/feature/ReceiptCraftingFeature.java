package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.component.DateComponent;
import me.villagerunknown.villagercoin.component.ReceiptMessageComponent;
import me.villagerunknown.villagercoin.component.ReceiptValueComponent;
import me.villagerunknown.villagercoin.recipe.LedgerRecipe;
import me.villagerunknown.villagercoin.recipe.ReceiptRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import java.time.LocalDate;
import java.util.HashSet;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;
import static me.villagerunknown.villagercoin.component.Components.*;

public class ReceiptCraftingFeature {
	
	public static Item RECIPE_CARRIER_ITEM = Items.PAPER;
	
	private static HashSet<Item> CRAFTING_RESULT_RECEIPTS = new HashSet<>();
	
	public static RecipeSerializer RECIPE_SERIALIZER;
	
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
	
	public static ItemStack setReceiptMessage( ItemStack itemStack, ItemStack ingredientStack ) {
		Component customNameComponent = ingredientStack.get(DataComponents.CUSTOM_NAME);
		String message = "";
		
		if( null != customNameComponent ) {
			message = customNameComponent.getString();
		} else {
			message = Villagercoin.CONFIG.defaultReceiptThankYouMessage;
		} // if, else
		
		itemStack.set( RECEIPT_MESSAGE_COMPONENT, new ReceiptMessageComponent( message ));
		
		return itemStack;
	}
	
	public static ItemStack setCustomName( Player player, ItemStack itemStack ) {
		itemStack.set( DataComponents.ITEM_NAME, Component.translatable( "item.villagerunknown-villagercoin.receipt.tooltip.merchant", player.getScoreboardName() ) );
		return itemStack;
	}
	
	public static ItemStack setCraftedDate( ItemStack itemStack ) {
		itemStack.set( DATE_COMPONENT, new DateComponent( LocalDate.now().toString() ) );
		return itemStack;
	}
	
	public static void subtractCarrierFromIngredients(CraftingContainer craftingInput, long amount ) {
		CraftingInput.Positioned positioned = craftingInput.asPositionedCraftInput();
		CraftingInput craftingRecipeInput = positioned.input();
		int left = positioned.left();
		int top = positioned.top();
		
		for(int y = 0; y < craftingRecipeInput.height(); ++y) {
			for (int x = 0; x < craftingRecipeInput.width(); ++x) {
				int m = x + left + (y + top) * craftingInput.getWidth();
				ItemStack ingredientStack = craftingInput.getItem(m);
				
				if( ingredientStack.is( RECIPE_CARRIER_ITEM ) ) {
					if( amount > Integer.MAX_VALUE ) {
						amount = Integer.MAX_VALUE;
					} // if
					
					craftingInput.removeItem( m, CoinCraftingFeature.toIntSafely(amount) );
				} // if
			} // for
		} // for
	}
	
	public static void subtractCarrierFromIngredients(NonNullList<ItemStack> ingredients, int amount ) {
		for (ItemStack ingredientStack : ingredients) {
			if( ingredientStack.is( RECIPE_CARRIER_ITEM ) ) {
				ingredientStack.shrink( amount );
			} // if
		} // for
	}
	
	static {
		RECIPE_SERIALIZER = Registry.register(
				BuiltInRegistries.RECIPE_SERIALIZER,
				Identifier.fromNamespaceAndPath( MOD_ID, "crafting_special_receipt" ),
				new RecipeSerializer<>(ReceiptRecipe.CODEC, ReceiptRecipe.STREAM_CODEC)
		);
	}
	
}
