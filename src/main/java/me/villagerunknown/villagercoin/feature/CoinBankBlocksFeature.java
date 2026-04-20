package me.villagerunknown.villagercoin.feature;

import me.villagerunknown.platform.list.BlocksList;
import me.villagerunknown.platform.util.RegistryUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.block.entity.AbstractCurrencyValueBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static me.villagerunknown.villagercoin.Villagercoin.MOD_ID;
import static me.villagerunknown.villagercoin.component.Components.CURRENCY_COMPONENT;

public class CoinBankBlocksFeature {
	
	public static final String COIN_BANK_STRING = "coin_bank";
	
	public static final Identifier COINS_INSERTED_STAT_ID = RegistryUtil.registerStat( "inserted_coins_in_banks", Villagercoin.MOD_ID, StatFormatter.DEFAULT );
	
	public static BlocksList blocks = new BlocksList();
	
	public static void execute(){}
	
	public static void addBlock( Block block ) {
		blocks.addBlock( block );
	}
	
	public static Block registerCoinBankBlock( String namespace, String id, Block block) {
		Block registeredBlock = RegistryUtil.registerBlock( id, block, namespace );
		
		Item.Settings settings = new Item.Settings()
				.useBlockPrefixedTranslationKey()
				.component(CURRENCY_COMPONENT, new CurrencyComponent(0))
				.registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID,id)));
		
		Item item = RegistryUtil.registerItem(id, new BlockItem(registeredBlock, settings), namespace);
		
		Villagercoin.addItemToGroup( item );
		
		addBlock( registeredBlock );
		
		return block;
	}
	
	public static Block registerFireproofCoinBankBlock( String namespace, String id, Block block) {
		Block registeredBlock = RegistryUtil.registerBlock( id, block, namespace );
		
		Item.Settings settings = new Item.Settings()
				.useBlockPrefixedTranslationKey()
				.component(CURRENCY_COMPONENT, new CurrencyComponent(0))
				.registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID,id)))
				.fireproof();
		
		Item item = RegistryUtil.registerItem(id, new BlockItem(registeredBlock, settings), namespace);
		
		Villagercoin.addItemToGroup( item );
		
		addBlock( registeredBlock );
		
		return block;
	}
	
	public static int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity( pos );
		
		if( blockEntity instanceof AbstractCurrencyValueBlockEntity coinBankBlockEntity ) {
			long value = coinBankBlockEntity.getTotalCurrencyValue();
			if( value > 0 ) {
				if( Long.MAX_VALUE == value ) {
					return 15;
				} else {
					return Math.max(1, (int) Math.ceil(Math.log(value) / Math.log(Long.MAX_VALUE) * 14));
				} // if, else
			} // if
		} // if
		
		return 0;
	}
	
}
