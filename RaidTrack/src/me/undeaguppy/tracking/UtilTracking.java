package me.undeaguppy.tracking;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class UtilTracking {
	public static mainTracking plugin;
	public static Server server;

	public static void Initialize(mainTracking plugin) {
		UtilTracking.plugin = plugin;
		server = plugin.getServer();
	}

	public static Player MatchPlayer(String player) {
		List players = Bukkit.getServer().matchPlayer(player);
		if (players.size() == 1) {
			return (Player) players.get(0);
		}
		return null;
	}

	public static Player findPlayer(String str) {
		List<Player> plist = UtilTracking.Who();
		int i = 0;
		while (i < plist.size()) {
			if (plist.get(i).getName().equals(str)) {
				return plist.get(i);
			}
			++i;
		}
		return null;
	}

	public static List<Player> Who() {
		Player[] players = server.getOnlinePlayers();
		ArrayList<Player> players1 = new ArrayList<Player>();
		int i = 0;
		while (i < players.length) {
			players1.add(players[i]);
			++i;
		}
		return players1;
	}
}
