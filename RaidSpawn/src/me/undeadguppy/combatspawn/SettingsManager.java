package me.undeadguppy.combatspawn;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SettingsManager {

	private static SettingsManager manager = new SettingsManager();

	// used to create a singleton instance
	private SettingsManager() {
	}

	@SuppressWarnings("unused")
	private SpawnProtection p;

	public static SettingsManager getManager() {
		return manager;
	}

	FileConfiguration config;
	File configFile;

	public void setup(SpawnProtection p) {
		this.p = p;

		configFile = new File(p.getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			p.saveResource("config.yml", true);
		}

		config = YamlConfiguration.loadConfiguration(configFile);
		saveConfig();
	}

	public void saveConfig() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.WARNING, "Not able to save config!");
		}
	}

	public FileConfiguration getConfig() {
		return config;
	}
}
