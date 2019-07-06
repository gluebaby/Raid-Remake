package me.undeaguppy.tracking;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class mainTracking extends JavaPlugin {
	
	private PluginPlayerListenerTracking playerListener;

	public mainTracking() {
		this.playerListener = new PluginPlayerListenerTracking(this);
	}

	public void onDisable() {
		System.out.println("Tracking disabled");
	}

	public void onEnable() {
		System.out.println("Tracking enabled");
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents((Listener) this.playerListener, (Plugin) this);
		UtilTracking.Initialize(this);
	}
}
