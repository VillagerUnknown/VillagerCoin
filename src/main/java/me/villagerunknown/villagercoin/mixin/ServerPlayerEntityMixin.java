package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.ResolutionContext;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.WrittenBookContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerEntityMixin {
	
	@Inject( method = "openItemGui", at = @At("HEAD") )
	public void openItemGui(ItemStack book, InteractionHand hand, CallbackInfo ci) {
		if( book.has(DataComponents.WRITTEN_BOOK_CONTENT) || book.is(Villagercoin.getItemTagKey( "ledger" )) ) {
			ServerPlayer player = (ServerPlayer) (Object) this;
			
			if (WrittenBookContent.resolveForItem(book, ResolutionContext.create(player.createCommandSourceStack()), player.registryAccess())) {
				player.containerMenu.broadcastChanges();
			}
			
			player.connection.send(new ClientboundOpenBookPacket(hand));
		}
	}
	
}
