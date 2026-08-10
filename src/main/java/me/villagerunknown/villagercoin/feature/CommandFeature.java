package me.villagerunknown.villagercoin.feature;

import com.mojang.brigadier.CommandDispatcher;
import me.villagerunknown.platform.feature.commandsFeature;
import me.villagerunknown.platform.util.MessageUtil;
import me.villagerunknown.villagercoin.Villagercoin;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public class CommandFeature {
	
	public static CommandSourceStack COMMAND_SOURCE;
	
	public static void execute() {
		CommandRegistrationCallback.EVENT.register( CommandFeature::registerCommands );
	}
	
	private static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection registrationEnvironment) {
		dispatcher.register( Commands.literal( Villagercoin.ID ).executes(context -> {
			COMMAND_SOURCE = context.getSource();
			
			try {
				Component message = MessageUtil.formClickableMessage(
						"Thank you for using Villager Coin!",
						"https://github.com/VillagerUnknown/VillagerCoin/issues"
				);
				
				COMMAND_SOURCE.sendSystemMessage( message );
				
				message = MessageUtil.formClickableMessage(
						"Click here to go to Github for help.",
						"https://github.com/VillagerUnknown/VillagerCoin/issues"
				);
				
				COMMAND_SOURCE.sendSystemMessage( message );
				commandsFeature.playSound(SoundEvents.VILLAGER_YES);
			} catch( Exception e ) {
				commandsFeature.sendCommandFeedback( Villagercoin.ID + " command encountered an error!", SoundEvents.VILLAGER_NO );
			} // try, catch
			
			return 1;
		} ) );
	}
	
}
