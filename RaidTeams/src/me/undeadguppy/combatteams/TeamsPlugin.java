
package me.undeadguppy.combatteams;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.undeadguppy.combatteams.commands.TeamsCommand;
import me.undeadguppy.combatteams.listeners.CombatTagListener;
import me.undeadguppy.combatteams.listeners.FriendlyFireListener;
import me.undeadguppy.combatteams.listeners.PlayerChatListener;
import me.undeadguppy.combatteams.listeners.PlayerDeathListener;
import me.undeadguppy.combatteams.listeners.PlayerJoinListener;
import me.undeadguppy.combatteams.listeners.PlayerQuitListener;
import me.undeadguppy.combatteams.schedulers.CombatScheduler;
import me.undeadguppy.combatteams.schedulers.SettingsScheduler;

public class TeamsPlugin extends JavaPlugin {
	private static TeamsPlugin instance;
	private File teamsFile;
	private FileConfiguration teamsConfig;
	private File settingsFile;
	private FileConfiguration settingsConfig;
	private File userdataFile;
	private FileConfiguration userdataConfig;
	private Map<String, Teams> teams = new HashMap<String, Teams>();
	private static int SETTINGS_VERSION;
	public String deleteTeam;

	static {
		SETTINGS_VERSION = 2;
	}

	public void onEnable() {
		instance = this;
		this.checkFiles();
		this.loadTeams();
		this.getCommand("team").setExecutor((CommandExecutor) new TeamsCommand());
		Bukkit.getPluginManager().registerEvents((Listener) new CombatTagListener(), (Plugin) this);
		Bukkit.getPluginManager().registerEvents((Listener) new FriendlyFireListener(), (Plugin) this);
		Bukkit.getPluginManager().registerEvents((Listener) new PlayerDeathListener(), (Plugin) this);
		Bukkit.getPluginManager().registerEvents((Listener) new PlayerJoinListener(), (Plugin) this);
		Bukkit.getPluginManager().registerEvents((Listener) new PlayerQuitListener(), (Plugin) this);
		Bukkit.getPluginManager().registerEvents((Listener) new PlayerChatListener(), (Plugin) this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) this, (Runnable) new CombatScheduler(), 0, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) this, (Runnable) new SettingsScheduler(), 0, 20);
	}

	public void onDisable() {
		this.saveTeams();
	}

	public void loadTeams() {
		try {
			this.teamsConfig.load(this.teamsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (this.teamsConfig.getConfigurationSection("Teams") != null) {
			for (String teamName : this.teamsConfig.getConfigurationSection("Teams").getKeys(false)) {
				Location loc;
				String path = "Teams." + teamName + ".";
				Teams team = new Teams(teamName, this.teamsConfig.getString(String.valueOf(path) + "Password"),
						this.teamsConfig.getString(String.valueOf(path) + "Leader"),
						this.teamsConfig.getInt(String.valueOf(path) + "Kills"),
						this.teamsConfig.getInt(String.valueOf(path) + "Deaths"),
						this.teamsConfig.getBoolean(String.valueOf(path) + "FriendlyFire"));
				List members = this.teamsConfig.getStringList(String.valueOf(path) + "Members");
				List managers = this.teamsConfig.getStringList(String.valueOf(path) + "Managers");
				for (String m : members) {
					if (Teams.getPlayerTeam().containsKey(m) || Teams.getPlayerList().contains(m))
						continue;
					Teams.getPlayerTeam().put(m, teamName);
					Teams.getPlayerList().add(m);
					team.getMembers().add(m);
					if (!managers.contains(m))
						continue;
					team.getManagers().add(m);
				}
				if (this.teamsConfig.getConfigurationSection(String.valueOf(path) + "HQ") != null) {
					loc = new Location(
							Bukkit.getWorld((String) this.teamsConfig.getString(String.valueOf(path) + "HQ.World")),
							(double) this.teamsConfig.getInt(String.valueOf(path) + "HQ.X"),
							(double) this.teamsConfig.getInt(String.valueOf(path) + "HQ.Y"),
							(double) this.teamsConfig.getInt(String.valueOf(path) + "HQ.Z"));
					team.setHq(loc);
				}
				if (this.teamsConfig.getConfigurationSection(String.valueOf(path) + "Rally") != null) {
					loc = new Location(
							Bukkit.getWorld((String) this.teamsConfig.getString(String.valueOf(path) + "Rally.World")),
							(double) this.teamsConfig.getInt(String.valueOf(path) + "Rally.X"),
							(double) this.teamsConfig.getInt(String.valueOf(path) + "Rally.Y"),
							(double) this.teamsConfig.getInt(String.valueOf(path) + "Rally.Z"));
					team.setRally(loc);
				}
				this.teams.put(teamName, team);
			}
		}
	}

	public void saveTeams() {
		for (String teamName : this.teams.keySet()) {
			Teams team = this.teams.get(teamName);
			String path = "Teams." + teamName + ".";
			this.teamsConfig.set(String.valueOf(path) + "Leader", (Object) team.getLeader());
			this.teamsConfig.set(String.valueOf(path) + "Password", (Object) team.getPassword());
			this.teamsConfig.set(String.valueOf(path) + "Kills", (Object) team.getKills());
			this.teamsConfig.set(String.valueOf(path) + "Deaths", (Object) team.getDeaths());
			this.teamsConfig.set(String.valueOf(path) + "FriendlyFire", (Object) team.getFriendlyFire());
			List members = this.teamsConfig.getStringList(String.valueOf(path) + "Members");
			members.clear();
			for (String m : team.getMembers()) {
				members.add(m);
			}
			this.teamsConfig.set(String.valueOf(path) + "Members", (Object) members);
			List managers = this.teamsConfig.getStringList(String.valueOf(path) + "Managers");
			managers.clear();
			for (String ma : team.getManagers()) {
				managers.add(ma);
			}
			this.teamsConfig.set(String.valueOf(path) + "Managers", (Object) managers);
			if (team.getHq() != null) {
				this.teamsConfig.set(String.valueOf(path) + "HQ.World", (Object) team.getHq().getWorld().getName());
				this.teamsConfig.set(String.valueOf(path) + "HQ.X", (Object) team.getHq().getBlockX());
				this.teamsConfig.set(String.valueOf(path) + "HQ.Y", (Object) team.getHq().getBlockY());
				this.teamsConfig.set(String.valueOf(path) + "HQ.Z", (Object) team.getHq().getBlockZ());
			}
			if (team.getRally() == null)
				continue;
			this.teamsConfig.set(String.valueOf(path) + "Rally.World", (Object) team.getRally().getWorld().getName());
			this.teamsConfig.set(String.valueOf(path) + "Rally.X", (Object) team.getRally().getBlockX());
			this.teamsConfig.set(String.valueOf(path) + "Rally.Y", (Object) team.getRally().getBlockY());
			this.teamsConfig.set(String.valueOf(path) + "Rally.Z", (Object) team.getRally().getBlockZ());
		}
		try {
			this.teamsConfig.save(this.teamsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkFiles() {
		this.teamsFile = new File(this.getDataFolder(), "teams.yml");
		if (!this.teamsFile.exists()) {
			this.saveResource("teams.yml", false);
		}
		this.teamsConfig = YamlConfiguration.loadConfiguration((File) this.teamsFile);
		this.settingsFile = new File(this.getDataFolder(), "settings.properties");
		if (!this.settingsFile.exists()) {
			this.saveResource("settings.properties", false);
		}
		this.settingsConfig = YamlConfiguration.loadConfiguration((File) this.settingsFile);
		if (this.settingsConfig.getInt("Settings-Version", 0) != SETTINGS_VERSION) {
			this.moveFile(this.settingsFile);
			this.saveResource("settings.properties", false);
		}
		this.userdataFile = new File(this.getDataFolder(), "userdata.yml");
		if (!this.userdataFile.exists()) {
			this.saveResource("userdata.yml", false);
		}
		this.userdataConfig = YamlConfiguration.loadConfiguration((File) this.userdataFile);
	}

	public void deleteTeam() {
		if (this.teamsConfig.getConfigurationSection("").contains("Teams")
				&& this.teamsConfig.getConfigurationSection("Teams").contains(this.deleteTeam)) {
			Iterator iterator = this.teamsConfig.getKeys(false).iterator();
			int i = 0;
			while (iterator.hasNext()) {
				++i;
				iterator.next();
			}
			if (i != 1) {
				this.teamsConfig.set("Teams." + this.deleteTeam, (Object) null);
				this.reloadTeams();
			} else {
				this.teamsConfig.set("Teams", (Object) null);
				this.reloadTeams();
			}
		}
	}

	public void reloadTeams() {
		try {
			this.teamsConfig.save(this.teamsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.teamsConfig.load(this.teamsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean moveFile(File ff) {
		System.out.println("[Teams]: Moving outdated file " + ff.getName());
		String name = ff.getName();
		File ff2 = new File(this.getDataFolder(), this.getNextName(name, 0));
		return ff.renameTo(ff2);
	}

	private String getNextName(String name, int n) {
		File ff = new File(this.getDataFolder(), String.valueOf(name) + ".outdated#" + n);
		if (!ff.exists()) {
			return ff.getName();
		}
		return this.getNextName(name, n + 1);
	}

	public static TeamsPlugin getInstance() {
		return instance;
	}

	public File getTeamsFile() {
		return this.teamsFile;
	}

	public void setTeamsFile(File teamsFile) {
		this.teamsFile = teamsFile;
	}

	public FileConfiguration getTeamsConfig() {
		return this.teamsConfig;
	}

	public void setTeamsConfig(FileConfiguration teamsConfig) {
		this.teamsConfig = teamsConfig;
	}

	public Map<String, Teams> getTeams() {
		return this.teams;
	}

	public void setTeams(Map<String, Teams> teams) {
		this.teams = teams;
	}

	public File getSettingsFile() {
		return this.settingsFile;
	}

	public void setSettingsFile(File settingsFile) {
		this.settingsFile = settingsFile;
	}

	public FileConfiguration getSettingsConfig() {
		return this.settingsConfig;
	}

	public void setSettingsConfig(FileConfiguration settingsConfig) {
		this.settingsConfig = settingsConfig;
	}

	public File getUserdataFile() {
		return this.userdataFile;
	}

	public void setUserdataFile(File userdataFile) {
		this.userdataFile = userdataFile;
	}

	public FileConfiguration getUserdataConfig() {
		return this.userdataConfig;
	}

	public void setUserdataConfig(FileConfiguration userdataConfig) {
		this.userdataConfig = userdataConfig;
	}
}
