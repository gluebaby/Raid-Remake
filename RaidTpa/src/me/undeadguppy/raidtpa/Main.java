package me.undeadguppy.raidtpa;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	Map<String, Long> tpaCooldown = new HashMap<String, Long>();
	Map<String, String> currentRequest = new HashMap<String, String>();

	public void onEnable() {
		loadConfig();
		log.log(Level.INFO, String.format("[%s] Successfully enabled version %s!", getDescription().getName(),
				getDescription().getVersion()));
	}

	public void onDisable() {
		log.log(Level.INFO, String.format("[%s] Successfully disabled version %s!", getDescription().getName(),
				getDescription().getVersion()));
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = null;
		if (sender instanceof Player) {
			p = (Player) sender;
		}

		if (cmd.getName().equalsIgnoreCase("tpa")) {
			if (!(p == null)) {
				if (p.hasPermission("raid.overridecooldown")) {

				} else {
					int cooldown = getConfig().getInt("tpa-cooldown");
					if (tpaCooldown.containsKey(p.getName())) {
						long diff = (System.currentTimeMillis() - tpaCooldown.get(sender.getName())) / 1000;
						if (diff < cooldown) {
							p.sendMessage(ChatColor.GRAY + "Error: You must wait a " + cooldown
									+ " second cooldown in between teleport requests!");
							return false;
						}
					}
				}

				if (args.length > 0) {
					final Player target = getServer().getPlayer(args[0]);
					long keepAlive = getConfig().getLong("keep-alive") * 20;

					if (target == null) {
						sender.sendMessage(
								ChatColor.RED + "Error: You can only send a teleport request to online players!");
						return false;
					}

					if (target == p) {
						sender.sendMessage(ChatColor.RED + "Error: You can't teleport to yourself!");
						return false;
					}

					sendRequest(p, target);

					getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						public void run() {
							killRequest(target.getName());
						}
					}, keepAlive);

					tpaCooldown.put(p.getName(), System.currentTimeMillis());
				} else {
					p.sendMessage(ChatColor.GRAY + "Send a teleport request to a player.");
					p.sendMessage(ChatColor.GRAY + "/tpa <player>");
				}
			} else {
				sender.sendMessage(ChatColor.GRAY + "Error: The console can't teleport to people, silly!");
				return false;
			}
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("tpaccept")) {
			if (!(p == null)) {
				if (currentRequest.containsKey(p.getName())) {

					Player heIsGoingOutOnADate = getServer().getPlayer(currentRequest.get(p.getName()));
					currentRequest.remove(p.getName());

					if (!(heIsGoingOutOnADate == null)) {
						heIsGoingOutOnADate.teleport(p);
						p.sendMessage(ChatColor.GRAY + "Teleporting...");
						heIsGoingOutOnADate.sendMessage(ChatColor.GRAY + "Teleporting...");
					} else {
						sender.sendMessage(ChatColor.GRAY
								+ "That player went offline!");
						return false;
					}
				} else {
					sender.sendMessage(
							ChatColor.GRAY + "You haven't received any teleport requests in the last 30 seconds.");
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.GRAY + "Error: The console can't accept teleport requests, silly!");
				return false;
			}
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("tpdeny")) {
			if (!(p == null)) {
				if (currentRequest.containsKey(p.getName())) {
					Player poorRejectedGuy = getServer().getPlayer(currentRequest.get(p.getName()));
					currentRequest.remove(p.getName());

					if (!(poorRejectedGuy == null)) {
						poorRejectedGuy.sendMessage(ChatColor.GRAY + p.getName() + " denied your teleport request!");
						p.sendMessage(ChatColor.GRAY + poorRejectedGuy.getName() + " was denied!");
						return true;
					}
				} else {
					sender.sendMessage(
							ChatColor.GRAY + "You haven't received any teleport requests in the last 30 seconds.");
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.GRAY + "The console can't deny teleport requests, silly!");
				return false;
			}
			return true;
		}
		return false;
	}

	public boolean killRequest(String key) {
		if (currentRequest.containsKey(key)) {
			Player loser = getServer().getPlayer(currentRequest.get(key));
			if (!(loser == null)) {
				loser.sendMessage(ChatColor.GRAY + "Your teleport request timed out.");
			}

			currentRequest.remove(key);

			return true;
		} else {
			return false;
		}
	}

	public void sendRequest(Player sender, Player recipient) {
		sender.sendMessage(ChatColor.GRAY + "Sent a teleport request to " + recipient.getName() + ".");

		String sendtpaccept = "";
		String sendtpdeny = "";

		if (recipient.hasPermission("raid.tpa")) {
			sendtpaccept = ChatColor.GRAY + " To accept the teleport request, type /tpaccept.";
		} else {
			sendtpaccept = "";
		}

		if (recipient.hasPermission("raid.tpa")) {
			sendtpdeny = ChatColor.GRAY + " To deny the teleport request, type /tpdeny.";
		} else {
			sendtpdeny = "";
		}

		recipient.sendMessage(ChatColor.GRAY + sender.getName() + " has sent a request to teleport to you."
				+ sendtpaccept + sendtpdeny);
		currentRequest.put(recipient.getName(), sender.getName());
	}

	public void loadConfig() {
		getConfig().addDefault("tpa-cooldown", 5);
		getConfig().addDefault("keep-alive", 30);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}