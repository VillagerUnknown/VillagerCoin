package me.villagerunknown.villagercoin.mixin;

import me.villagerunknown.villagercoin.Villagercoin;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.network.packet.s2c.play.OpenWrittenBookS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	
	@Inject( method = "useBook", at = @At("HEAD") )
	public void useBook(ItemStack book, Hand hand, CallbackInfo ci) {
		if (book.isOf(Items.WRITTEN_BOOK) || book.isIn(Villagercoin.getItemTagKey( "ledger" ) )) {
			ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
			
			if( WrittenBookItem.resolve(book, player.getCommandSource(), player) ) {
				player.currentScreenHandler.sendContentUpdates();
			}
			
			player.networkHandler.sendPacket(new OpenWrittenBookS2CPacket(hand));
		}
	}
	
}
