package raid.go.me;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		List<String> list = getConfig().getStringList("help-message");
		list.add("/go <warp>");
		list.add("/go set <warp>");
		list.add("/go delete <warp>");
		list.add("/go list");
		list.add("/go reload");
		getConfig().addDefault("help-message", list);
		getConfig().addDefault("could-not-find-warp", "&c[Error] Could not found that warp.");
		getConfig().addDefault("already-created", "&c[Error] That warp is already created.");
		getConfig().addDefault("not-exits", "&c[Error] That warp is not exits.");
		getConfig().addDefault("teleport", "&a[Success] You have been teleported to warp %warp%.");
		getConfig().addDefault("set", "&a[Success] You have created warp %warp%.");
		getConfig().addDefault("delete", "&a[Success] You have deleted warp %warp%.");
		getConfig().addDefault("permission", "&c[Error] You dont have enough permissions to create more warps.");
		getConfig().addDefault("cancel", "&c[Error] Teleport has been cancelled because you have moved.");
		getConfig().addDefault("cooldown", "&a[Success] You will be teleported to that warp in 5 seconds.");
		saveConfig();
	}

	public String convert(String s) {
		return s.replaceAll("&", "§");

	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("go")) {
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("set")) {
					List<String> list = getConfig().getStringList(player.getUniqueId() + ".warps");
					if (list.contains(args[1])) {
						player.sendMessage(convert(getConfig().getString("already-created")));
						return true;
					}
					int sie = list.size();

					if (player.hasPermission("raid.go." + sie)) {
						if (!player.isOp()) {
							player.sendMessage(convert(getConfig().getString("permission")));
							return true;
						}
					}
					player.sendMessage(convert(getConfig().getString("set").replaceAll("%warp%", args[1])));
					list.add(args[1]);
					getConfig().set(player.getUniqueId() + ".warps", list);
					setLobby(player, args[1]);
					saveConfig();
					return true;
				}

				if (args[0].equalsIgnoreCase("delete")) {
					List<String> list = getConfig().getStringList(player.getUniqueId() + ".warps");
					if (!list.contains(args[1])) {
						player.sendMessage(convert(getConfig().getString("not-exits")));
						return true;
					}
					player.sendMessage(convert(getConfig().getString("delete").replaceAll("%warp%", args[1])));
					list.remove(args[1]);
					getConfig().set(player.getUniqueId() + ".warps", list);
					getConfig().set(player.getUniqueId() + "." + args[1], null);
					saveConfig();
					return true;

				}
			}
			if (args.length == 0) {
				List<String> list = getConfig().getStringList("help-message");
				for (String s : list) {
					player.sendMessage(convert(s));
				}
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (player.hasPermission("raid.go.reload")) {
						reloadConfig();
						player.sendMessage("[RaidGo] Config has been reloaded!");
					}
					return true;
				}
				if (args[0].equalsIgnoreCase("list")) {
					List<String> list = getConfig().getStringList(player.getUniqueId() + ".warps");
					player.sendMessage("Warps: " + list.toString());
					player.sendMessage("Amount: " + list.size() + " warps.");
					return true;
				}
				List<String> list = getConfig().getStringList(player.getUniqueId() + ".warps");
				if (!list.contains(args[0])) {
					player.sendMessage(convert(getConfig().getString("could-not-find-warp")));
					return true;
				}
				players.add(player);
				player.sendMessage(convert(getConfig().getString("cooldown")));
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					public void run() {
						if (players.contains(player)) {
							player.sendMessage(
									convert(getConfig().getString("teleport").replaceAll("%warp%", args[0])));
							player.teleport(getLobby(player, args[0]));
						}
					}
				}, 20 * 5);

				return true;
			}

		}

		if (label.equalsIgnoreCase("warp")) {
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("set")) {
					List<String> list = getConfig().getStringList(player.getUniqueId() + ".warps");
					if (list.contains(args[1])) {
						player.sendMessage(convert(getConfig().getString("already-created")));
						return true;
					}
					int sie = list.size();

					if (player.hasPermission("raid.go." + sie)) {
						if (!player.isOp()) {
							player.sendMessage(convert(getConfig().getString("permission")));
							return true;
						}
					}
					player.sendMessage(convert(getConfig().getString("set").replaceAll("%warp%", args[1])));
					list.add(args[1]);
					getConfig().set(player.getUniqueId() + ".warps", list);
					setLobby(player, args[1]);
					saveConfig();
					return true;
				}

				if (args[0].equalsIgnoreCase("delete")) {
					List<String> list = getConfig().getStringList(player.getUniqueId() + ".warps");
					if (!list.contains(args[1])) {
						player.sendMessage(convert(getConfig().getString("not-exits")));
						return true;
					}
					player.sendMessage(convert(getConfig().getString("delete").replaceAll("%warp%", args[1])));
					list.remove(args[1]);
					getConfig().set(player.getUniqueId() + ".warps", list);
					getConfig().set(player.getUniqueId() + "." + args[1], null);
					saveConfig();
					return true;

				}
			}
			if (args.length == 0) {
				List<String> list = getConfig().getStringList("help-message");
				for (String s : list) {
					player.sendMessage(convert(s));
				}
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (player.hasPermission("raid.go.reload")) {
						reloadConfig();
						player.sendMessage("[RaidGo] Config has been reloaded!");
					}
					return true;
				}
				if (args[0].equalsIgnoreCase("list")) {
					List<String> list = getConfig().getStringList(player.getUniqueId() + ".warps");
					player.sendMessage("Warps: " + list.toString());
					player.sendMessage("Amount: " + list.size() + " warps.");
					return true;
				}
				List<String> list = getConfig().getStringList(player.getUniqueId() + ".warps");
				if (!list.contains(args[0])) {
					player.sendMessage(convert(getConfig().getString("could-not-find-warp")));
					return true;
				}
				players.add(player);
				player.sendMessage(convert(getConfig().getString("cooldown")));
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					public void run() {
						if (players.contains(player)) {
							player.sendMessage(
									convert(getConfig().getString("teleport").replaceAll("%warp%", args[0])));
							player.teleport(getLobby(player, args[0]));
						}
					}
				}, 20 * 5);

				return true;
			}

		}
		return true;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {

		if (players.contains(e.getPlayer())) {

			if (e.getTo().getX() != e.getFrom().getX() && e.getTo().getZ() != e.getFrom().getZ()) {
				players.remove(e.getPlayer());
				e.getPlayer().sendMessage(convert(getConfig().getString("cancel")));
				return;
			}

		}

	}

	ArrayList<Player> players = new ArrayList<Player>();

	public void setLobby(Player p, String warp) {
		getConfig().set(p.getUniqueId() + "." + warp + ".world", p.getLocation().getWorld().getName());
		getConfig().set(p.getUniqueId() + "." + warp + ".x", Double.valueOf(p.getLocation().getX()));
		getConfig().set(p.getUniqueId() + "." + warp + ".y", Double.valueOf(p.getLocation().getY()));
		getConfig().set(p.getUniqueId() + "." + warp + ".z", Double.valueOf(p.getLocation().getZ()));
		saveConfig();
	}

	public Location getLobby(Player p, String warp) {
		World w = Bukkit.getWorld(getConfig().getString(p.getUniqueId() + "." + warp + ".world"));
		double x = getConfig().getDouble(p.getUniqueId() + "." + warp + ".x");
		double y = getConfig().getDouble(p.getUniqueId() + "." + warp + ".y");
		double z = getConfig().getDouble(p.getUniqueId() + "." + warp + ".z");
		return new Location(w, x, y, z);
	}
}
