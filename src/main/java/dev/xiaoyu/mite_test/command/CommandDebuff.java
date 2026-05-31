package dev.xiaoyu.mite_test.command;

import dev.xiaoyu.mite_test.MITETest;
import dev.xiaoyu.mite_test.init.mixin.minecraft.accessor.EntityLivingBaseAccessor;
import net.minecraft.CommandBase;
import net.minecraft.EntityLivingBase;
import net.minecraft.ICommandSender;
import net.minecraft.ServerPlayer;
import net.minecraft.WrongUsageException;
import net.minecraft.server.MinecraftServer;

import java.util.List;

@SuppressWarnings("rawtypes")
public class CommandDebuff extends CommandBase {

	@Override
	public String getCommandName() {
		return "debuff";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.debuff.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (!MITETest.isAllowedPlayer(sender.getCommandSenderName())) {
			throw new WrongUsageException("commands.generic.notAllowed", String.join(", ", MITETest.PLAYER_IDS));
		}
		if (args.length < 1) {
			throw new WrongUsageException("commands.debuff.usage");
		}
		ServerPlayer targetPlayer = getPlayer(sender, args[0]);
		EntityLivingBase target = targetPlayer;
		EntityLivingBaseAccessor accessor = (EntityLivingBaseAccessor) target;
        assert accessor != null;
        boolean current = accessor.getMiteDebuffImmune();
		accessor.setMiteDebuffImmune(!current);
		if (!current) {
			target.clearActivePotions();
		}
		if (!current) {
			notifyAdmins(sender, "commands.debuff.enabled", targetPlayer.getCommandSenderName());
		} else {
			notifyAdmins(sender, "commands.debuff.disabled", targetPlayer.getCommandSenderName());
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}
}