package me.undeadguppy.gCommon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.undeadguppy.invadedcore.commands.BroadcastCMD;

public class Main extends JavaPlugin {

	public void onEnable() {
		getCommand("admin").setExecutor(new AdminCMD());
		getCommand("help").setExecutor(new HelpCMD());
		getCommand("bc").setExecutor(new BroadcastCMD());
		getCommand("sc").setExecutor(new StaffChatCMD());
		getCommand("who").setExecutor(new WhoCMD());
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new JoinLeave(), this);
	}

	public void onDisable() {

	}

}
