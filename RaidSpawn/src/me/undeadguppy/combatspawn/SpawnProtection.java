package me.undeadguppy.combatspawn;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnProtection extends JavaPlugin {
	private HashMap<Player, Integer> teleports;
	private Location spawn;
	private int TPDELAY;
	private int xRadius, zRadius;

	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {
		Bukkit.getLogger().log(Level.INFO, "SpawnProtection Enabling!");
		SettingsManager sm = SettingsManager.getManager();
		sm.setup(this);
		Bukkit.getServer().getPluginManager().registerEvents(new SpawnZoneListener(this), this);
		teleports = new HashMap<>();
		ConfigurationSection sec = sm.getConfig().getConfigurationSection("spawn");
		if (!(sec == null)) {
			@SuppressWarnings("rawtypes")
			Map map = sec.getValues(false);
			spawn = Location.deserialize(map);
		} else {
			spawn = null;
		}
		getCommand("setspawn").setExecutor(this);
		getCommand("spawn").setExecutor(this);
		TPDELAY = sm.getConfig().getInt("tpDelay");

		xRadius = sm.getConfig().getInt("xRadius");
		zRadius = sm.getConfig().getInt("zRadius");

	}

	public void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use commands!");
		}

		Player player = (Player) sender;

		if (label.equalsIgnoreCase("setspawn")) {
			if (player.hasPermission("combatpvp.setspawn")) {
				setSpawn(player.getLocation());
				player.sendMessage(ChatColor.GRAY + "Spawn set!");
				return true;
			}
		}

		if (label.equalsIgnoreCase("spawn")) {
			if (player.hasPermission("combatpvp.spawn")) {
				if (spawn == null) {
					player.sendMessage(ChatColor.GRAY + "No spawn has been set!");
					return true;
				} else {
					tpPlayerToSpawn(player);
				}
			}
		}

		return false;
	}

	private void setSpawn(Location loc) {
		SettingsManager sm = SettingsManager.getManager();
		sm.getConfig().set("spawn", loc.serialize());
		sm.saveConfig();
		spawn = loc;

	}

	private void tpPlayerToSpawn(Player p) {
		p.sendMessage(ChatColor.GRAY + "Teleporting to spawn in " + TPDELAY + " seconds, do not move!");
		int id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				p.teleport(spawn);
				teleports.remove(p);
			}
		}, TPDELAY * 20);
		teleports.put(p, id);
	}

	public HashMap<Player, Integer> getTeleports() {
		return teleports;
	}

	public Location getSpawn() {
		if (spawn == null) {
			return Bukkit.getWorlds().get(0).getSpawnLocation();
		}

		return spawn;
	}

	public boolean isInSpawn(Location loc) {
		if (!(spawn == null)) {
			int x = loc.getBlockX();
			int z = loc.getBlockZ();

			if (z > (spawn.getBlockZ() - zRadius) && z < (spawn.getBlockZ() + zRadius)
					&& x > (spawn.getBlockX() - xRadius) && x < (spawn.getBlockX() + xRadius)) {
				return true;
			}
		}
		return false;
	}
}
