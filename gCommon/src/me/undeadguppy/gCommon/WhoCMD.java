package me.undeadguppy.gCommon;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class WhoCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("who")) {
			StringBuilder sb = new StringBuilder();
			for (Player playerList : Bukkit.getServer().getOnlinePlayers()) {
				if (!(Bukkit.getServer().getOnlinePlayers().size() == 1)) {
					sb.append(playerList.getDisplayName() + ", ");
				} else {
					sb.append(playerList.getDisplayName());
				}
			}
			String result = sb.toString();
			sender.sendMessage(ChatColor.GRAY + "There are " + ChatColor.BLUE
					+ Bukkit.getServer().getOnlinePlayers().size() + ChatColor.GRAY + " players online!");
			sender.sendMessage(ChatColor.GRAY + "Ranks: " + ChatColor.WHITE + "NORMAL" + ChatColor.GRAY + ", "
					+ ChatColor.GREEN + "VIP" + ChatColor.GRAY + ", " + ChatColor.AQUA + "MEDIA" + ChatColor.GRAY + ", "
					+ ChatColor.BLUE + "MODERATOR" + ChatColor.GRAY + ", " + ChatColor.DARK_AQUA + "OWNER - Undead_Guppy");
			sender.sendMessage(result);
			return true;
		}
		return true;
	}

}
