package me.undeadguppy.gCommon;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class AdminCMD implements CommandExecutor {

	public static ArrayList<UUID> isAdmin = new ArrayList<UUID>();

	public static void setAdmin(Player p) {
		isAdmin.add(p.getUniqueId());
		p.setGameMode(GameMode.CREATIVE);
		p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.BLUE + "*" + ChatColor.GRAY + "] " + ChatColor.RESET
				+ p.getDisplayName());
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (!pl.hasPermission("gcommon.mod")) {
				pl.hidePlayer(p);
			}
		}
	}

	public static void setPlayer(Player p) {
		isAdmin.remove(p.getUniqueId());
		p.setGameMode(GameMode.SURVIVAL);
		p.setPlayerListName(ChatColor.RESET + p.getDisplayName());
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			pl.showPlayer(p);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("admin")) {
			Player p = (Player) sender;
			if (p.hasPermission("gcommon.mod")) {
				if (isAdmin.contains(p.getUniqueId())) {
					setPlayer(p);
					p.sendMessage(
							ChatColor.GRAY + "You are now in  " + ChatColor.BLUE + "Play " + ChatColor.GRAY + " mode.");
					return true;
				}
				setAdmin(p);
				p.sendMessage(
						ChatColor.GRAY + "You are now in " + ChatColor.BLUE + "Admin " + ChatColor.GRAY + " mode.");
				return true;
			} else {
				p.sendMessage("Unknown command. Type \"/help\" for help.");
				return true;
			}
		}
		return true;
	}

}
