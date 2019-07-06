package me.undeadguppy.gCommon;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class StaffChatCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("sc")) {
			Player p = (Player) sender;
			if (p.hasPermission("gcommon.mod")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.GRAY + "Please use /staffchat <message>!");
					return true;
				}
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String msg = sb.toString();
				for (Player staff : Bukkit.getOnlinePlayers()) {
					if (staff.hasPermission("raid.mod")) {
						staff.sendMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + "StaffChat" + ChatColor.GRAY + "] "
								+ ChatColor.DARK_AQUA + p.getName() + ChatColor.GRAY + ": " + msg);
					}
				}
			} else {
				p.sendMessage("Unknown command. Type \"/help\" for help.");
				return true;
			}
			return true;
		}
		return true;
	}

}
