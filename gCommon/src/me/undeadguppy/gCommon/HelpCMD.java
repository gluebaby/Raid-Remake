package me.undeadguppy.gCommon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class HelpCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("help")) {
			sender.sendMessage(ChatColor.GRAY + "----[" + ChatColor.BLUE + "RaidMC" + ChatColor.GRAY + "]----");
			sender.sendMessage(ChatColor.BLUE + "- " + ChatColor.GRAY + "Use /who to view online players!");
			sender.sendMessage(ChatColor.BLUE + "- " + ChatColor.GRAY + "Use /tag to change your nametag!");
			sender.sendMessage(ChatColor.BLUE + "- " + ChatColor.GRAY + "Use /team to join a team!");
			sender.sendMessage(ChatColor.BLUE + "- " + ChatColor.GRAY + "Use /go to set personal warps!");
			sender.sendMessage(ChatColor.BLUE + "- " + ChatColor.GRAY + "Use /eco to learn about the economy!");
			sender.sendMessage(ChatColor.BLUE + "- " + ChatColor.GRAY + "Use /who to see all online players!");
			sender.sendMessage(ChatColor.GRAY + "----[" + ChatColor.BLUE + "www.combatpvp.net" + ChatColor.GRAY + "]----");

		}
		return true;
	}

}
