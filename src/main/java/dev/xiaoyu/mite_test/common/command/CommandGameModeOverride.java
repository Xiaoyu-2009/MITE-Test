package dev.xiaoyu.mite_test.common.command;

import dev.xiaoyu.mite_test.MITETest;
import net.minecraft.ChatMessageComponent;
import net.minecraft.CommandBase;
import net.minecraft.EnumGameType;
import net.minecraft.ICommandSender;
import net.minecraft.ServerPlayer;
import net.minecraft.WorldSettings;
import net.minecraft.WrongUsageException;
import net.minecraft.server.MinecraftServer;

import java.util.List;

@SuppressWarnings("rawtypes")
public class CommandGameModeOverride extends CommandBase {

	@Override
	public String getCommandName() {
		return "gamemode";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.gamemode.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (!MITETest.isAllowedPlayer(sender.getCommandSenderName())) {
			throw new WrongUsageException("commands.generic.notAllowed", String.join(", ", MITETest.PLAYER_IDS));
		}
		if (args.length > 0) {
			EnumGameType gameType = getGameModeFromCommand(sender, args[0]);
			ServerPlayer player = args.length >= 2 ? getPlayer(sender, args[1]) : getCommandSenderAsPlayer(sender);

			player.setGameType(gameType);
			player.fallDistance = 0.0f;

			ChatMessageComponent message = ChatMessageComponent.createFromTranslationKey("gameMode." + gameType.getName());
			if (player != sender) {
				notifyAdmins(sender, 1, "commands.gamemode.success.other", player.getEntityName(), message);
			} else {
				notifyAdmins(sender, 1, "commands.gamemode.success.self", message);
			}
		} else {
			throw new WrongUsageException("commands.gamemode.usage");
		}
	}

	private EnumGameType getGameModeFromCommand(ICommandSender sender, String str) {
		if (str.equalsIgnoreCase(EnumGameType.SURVIVAL.getName()) || str.equalsIgnoreCase("s")) {
			return EnumGameType.SURVIVAL;
		} else if (str.equalsIgnoreCase(EnumGameType.CREATIVE.getName()) || str.equalsIgnoreCase("c")) {
			return EnumGameType.CREATIVE;
		} else if (str.equalsIgnoreCase(EnumGameType.ADVENTURE.getName()) || str.equalsIgnoreCase("a")) {
			return EnumGameType.ADVENTURE;
		} else {
			return WorldSettings.getGameTypeById(parseIntBounded(sender, str, 0, EnumGameType.values().length - 2));
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, "survival", "creative", "adventure")
				: (args.length == 2 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null);
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 1;
	}
}