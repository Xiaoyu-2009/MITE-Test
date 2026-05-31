package dev.xiaoyu.mite_test.command;

import java.util.List;
import net.minecraft.CommandBase;
import net.minecraft.ICommandSender;
import net.minecraft.ServerPlayer;
import net.minecraft.WrongUsageException;
import net.minecraft.server.MinecraftServer;

@SuppressWarnings("rawtypes")
public class CommandFlight extends CommandBase {

	@Override
	public String getCommandName() {
		return "flight";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.flight.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length != 1) {
			throw new WrongUsageException("commands.flight.usage", new Object[0]);
		}
		ServerPlayer targetPlayer = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(args[0]);
		if (targetPlayer == null) {
			throw new WrongUsageException("commands.generic.player.notFound", new Object[0]);
		}
		targetPlayer.capabilities.allowFlying = !targetPlayer.capabilities.allowFlying;
		if (!targetPlayer.capabilities.allowFlying) {
			targetPlayer.capabilities.isFlying = false;
		}
		targetPlayer.sendPlayerAbilities();
		notifyAdmins(sender, "commands.flight.success", targetPlayer.getCommandSenderName());
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
	}
}