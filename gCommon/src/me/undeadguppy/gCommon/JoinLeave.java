package me.undeadguppy.gCommon;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeave implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!p.hasPlayedBefore()) {
			p.sendMessage(ChatColor.GRAY + "Welcome back " + ChatColor.BLUE + p.getName() + ChatColor.GRAY + "!");
		} else {
			p.sendMessage(ChatColor.GRAY + "Welcome to " + ChatColor.BLUE + "RaidMC" + ChatColor.GRAY + "!");
		}
		if (!p.hasPermission("gcommon.mod")) {
			for (UUID id : AdminCMD.isAdmin) {
				Player admins = Bukkit.getPlayer(id);
				p.hidePlayer(admins);
			}
		}
		if (p.hasPermission("gcommon.mod")) {
			p.setDisplayName(ChatColor.BLUE + p.getName() + ChatColor.WHITE + ChatColor.GRAY);
			p.setPlayerListName(ChatColor.BLUE + p.getName());
			AdminCMD.setAdmin(p);
			p.sendMessage(ChatColor.GRAY + "You have joined the game and have been placed into " + ChatColor.BLUE
					+ "Admin" + ChatColor.GRAY + " mode.");
		} else if (p.hasPermission("gcommon.vip")) {
			p.setDisplayName(ChatColor.GREEN + p.getName() + ChatColor.WHITE + ChatColor.GRAY);
			p.setPlayerListName(ChatColor.GREEN + p.getName());
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("gcommon.mod")) {
			if (AdminCMD.isAdmin.contains(p.getUniqueId())) {
				AdminCMD.setPlayer(p);
			}
		}
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if (AdminCMD.isAdmin.contains(p.getUniqueId())) {
			e.setCancelled(true);
		}
	}

}
