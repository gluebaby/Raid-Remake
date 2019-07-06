package me.undeaguppy.tracking;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PluginPlayerListenerTracking implements Listener {
	private mainTracking plugin;

	public PluginPlayerListenerTracking(mainTracking plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String[] split = event.getMessage().split(" ");
		split[0] = split[0].substring(1);
		String label = split[0];
		String[] args = new String[split.length - 1];
		int i = 1;
		while (i < split.length) {
			args[i - 1] = split[i];
			++i;
		}
		if (label.equalsIgnoreCase("track")) {
			if (args.length == 1) {
				if (args[0].equals("all")) {
					Block block = new Location(player.getWorld(), (double) player.getLocation().getBlockX(),
							(double) (player.getLocation().getBlockY() - 1), (double) player.getLocation().getBlockZ())
									.getBlock();
					if (block.getType() == Material.DIAMOND_BLOCK) {
						Tracking tracker = new Tracking(this.plugin);
						tracker.setLoc(player.getLocation().getBlockX(), player.getLocation().getBlockY() - 1,
								player.getLocation().getBlockZ());
						tracker.Track(player, null);
					} else {
						player.sendMessage(ChatColor.GRAY + "You cannot track all with this type of tracker");
					}
				} else if (args[0].equals("help")) {
					player.sendMessage((Object) ChatColor.GRAY + "*---[" + ChatColor.BLUE + "Tracking Help"
							+ ChatColor.GRAY + "]---*");
					player.sendMessage((Object) ChatColor.GRAY + "1) Stand on a diamond block");
					player.sendMessage((Object) ChatColor.GRAY + "2) Use " + (Object) ChatColor.WHITE + "/track all"
							+ (Object) ChatColor.GRAY + " or " + (Object) ChatColor.WHITE + "/track [playername]");
					player.sendMessage((Object) ChatColor.GRAY + "*---[" + ChatColor.BLUE + "Tracking Info"
							+ ChatColor.GRAY + "]---*");
					player.sendMessage((Object) ChatColor.GRAY
							+ "- Each block of obsidian represents 25 blocks in tracking distance");
					player.sendMessage(
							(Object) ChatColor.GRAY + "- At the end of each tracking 'arm' there must be a gold block");
					player.sendMessage(
							(Object) ChatColor.GRAY + "- You can find 'North' by looking at the cloud direction");
				} else {
					Player oplayer = UtilTracking.MatchPlayer(args[0]);
					if (oplayer != null) {
						Tracking tracker = new Tracking(this.plugin);
						tracker.setLoc(player.getLocation().getBlockX(), player.getLocation().getBlockY() - 1,
								player.getLocation().getBlockZ());
						tracker.Track(player, oplayer);
					} else {
						player.sendMessage((Object) ChatColor.GRAY + "There is no player with this name online!");
					}
				}
			} else {
				player.sendMessage((Object) ChatColor.GRAY + "*---[" + ChatColor.BLUE + "Tracking Commands"
						+ ChatColor.GRAY + "]---*");
				player.sendMessage((Object) ChatColor.GRAY + "- /track all ~ Tracks all players!");
				player.sendMessage((Object) ChatColor.GRAY + "- /track [playername] ~ Tracks a specific player!");
				player.sendMessage((Object) ChatColor.GRAY + "- /track help ~ Learn how to track!");
			}
		}
	}
}
