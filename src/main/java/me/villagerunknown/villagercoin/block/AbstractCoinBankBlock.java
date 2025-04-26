package me.villagerunknown.villagercoin.block;

import com.mojang.serialization.MapCodec;
import me.villagerunknown.villagercoin.Villagercoin;
import me.villagerunknown.villagercoin.block.entity.AbstractCoinBankBlockEntity;
import me.villagerunknown.villagercoin.component.CurrencyComponent;
import me.villagerunknown.villagercoin.feature.CoinBankBlocksFeature;
import me.villagerunknown.villagercoin.feature.CoinFeature;
import me.villagerunknown.villagercoin.item.CoinItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.component.ComponentMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static me.villagerunknown.villagercoin.Villagercoin.CURRENCY_COMPONENT;

public abstract class AbstractCoinBankBlock extends BlockWithEntity {
	
	public static final MapCodec<? extends AbstractCoinBankBlock> CODEC = null;
	
	public AbstractCoinBankBlock(Settings settings) {
		super(
				settings
						.breakInstantly()
						.pistonBehavior(PistonBehavior.DESTROY)
						.sounds( BlockSoundGroup.CHAIN )
		);
	}
	
	@Override
	protected MapCodec<? extends AbstractCoinBankBlock> getCodec() {
		return CODEC;
	}
	
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}
	
	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		CurrencyComponent currencyComponent = itemStack.get( CURRENCY_COMPONENT );
		
		if( null != currencyComponent ) {
			BlockEntity blockEntity = world.getBlockEntity( pos );
			
			setBlockEntityCurrencyValue( blockEntity, itemStack, currencyComponent );
		} // if
		
		super.onPlaced(world, pos, state, placer, itemStack);
	}
	
	public boolean setBlockEntityCurrencyValue( BlockEntity blockEntity, ItemStack itemStack, CurrencyComponent currencyComponent ) {
		if( blockEntity instanceof AbstractCoinBankBlockEntity coinBankBlockEntity ) {
			coinBankBlockEntity.setComponents( itemStack.getComponents() );
			coinBankBlockEntity.setTotalCurrencyValue( currencyComponent.value() );
			
			return true;
		} // if
		
		return false;
	}
	
	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		CoinFeature.playCoinSound( player );
		return super.onUse(state, world, pos, player, hit);
	}
	
	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if( world.isClient() ) {
			return ItemActionResult.SUCCESS;
		} // if
		
		CurrencyComponent currencyComponent = stack.get(CURRENCY_COMPONENT);
		
		if( null != currencyComponent && stack.isIn( Villagercoin.getItemTagKey( "villager_coin" ) ) ) {
			BlockEntity blockEntity = world.getBlockEntity( pos );
			
			if( incrementBlockEntityCurrencyValue( blockEntity, currencyComponent ) ) {
				CoinFeature.playCoinSound( player );
				stack.decrementUnlessCreative(1, player);
				
				return ItemActionResult.CONSUME;
			} // if
		} // if
		
		return ItemActionResult.FAIL;
	}
	
	public boolean incrementBlockEntityCurrencyValue( BlockEntity blockEntity, CurrencyComponent currencyComponent ) {
		if( blockEntity instanceof AbstractCoinBankBlockEntity coinBankBlockEntity ) {
			if( coinBankBlockEntity.canIncrementCurrencyValue( currencyComponent.value() ) ) {
				coinBankBlockEntity.incrementCurrencyValueAndSetComponent(currencyComponent.value());
				
				return true;
			} // if
		} // if
		
		return false;
	}
	
	@Override
	protected void onExploded(BlockState state, World world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
		super.onExploded(state, world, pos, explosion, stackMerger);
	}
	
	@Override
	protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
		BlockPos blockPos = hit.getBlockPos();
		if (!world.isClient && projectile.canModifyAt(world, blockPos) && projectile.canBreakBlocks(world)) {
			world.breakBlock(blockPos, true, projectile);
		}
	}
	
	@Override
	protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
		BlockEntity blockEntity = world.getBlockEntity( pos );
		
		if( blockEntity instanceof AbstractCoinBankBlockEntity coinBankBlockEntity ) {
			coinBankBlockEntity.dropTotalValueAsCoins();
		} // if
		
		super.onStacksDropped(state, world, pos, tool, dropExperience);
	}
	
	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		MinecraftServer server = world.getServer();

		if( null != server ) {
			ServerWorld serverWorld = server.getWorld( world.getRegistryKey() );

			if( null != serverWorld ) {
				DynamicRegistryManager drm =serverWorld.getRegistryManager();
				Registry<Enchantment> reg = drm.get(RegistryKeys.ENCHANTMENT);

				Optional<RegistryEntry.Reference<Enchantment>> optional = reg.getEntry( Enchantments.SILK_TOUCH );
				RegistryEntry<Enchantment> silkTouchEnchantmentEntry = optional.orElseThrow();

				if( !player.getStackInHand( player.getActiveHand() ).getEnchantments().getEnchantments().contains( silkTouchEnchantmentEntry ) ) {
					BlockEntity blockEntity = world.getBlockEntity( pos );

					if( blockEntity instanceof AbstractCoinBankBlockEntity coinBankBlockEntity ) {
						coinBankBlockEntity.dropTotalValueAsCoins();
					} // if
				} // if
			} // if
		} // if

		return super.onBreak(world, pos, state, player);
	}
	
	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
		CurrencyComponent currencyComponent = stack.get( CURRENCY_COMPONENT );
		
		if( null != currencyComponent ) {
			tooltip.add(
					Text.translatable(
							"block.villagerunknown-villagercoin.coin_bank.tooltip",
							currencyComponent.value(),
							CoinItems.COPPER_COIN.getName().getString()
					).formatted(Formatting.ITALIC, Formatting.GRAY)
			);
		} // if
		
		super.appendTooltip(stack, context, tooltip, options);
	}
	
	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return CoinBankBlocksFeature.getComparatorOutput(state, world, pos);
	}
	
	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}
	
	@Override
	public boolean canMobSpawnInside(BlockState state) {
		return false;
	}
	
}
