package me.undeadguppy.raid;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.undeadguppy.gCommon.AdminCMD;
import me.undeadguppy.gCommon.BroadcastCMD;
import me.undeadguppy.gCommon.HelpCMD;
import me.undeadguppy.gCommon.JoinLeave;
import me.undeadguppy.gCommon.StaffChatCMD;

public class Main extends JavaPlugin {

	public void onEnable() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new JoinLeave(), this);
		pm.registerEvents(new Kill(), this);
		getCommand("admin").setExecutor(new AdminCMD());
		getCommand("help").setExecutor(new HelpCMD());
		getCommand("who").setExecutor(new WhoCMD());
		getCommand("sc").setExecutor(new StaffChatCMD());
		getCommand("bc").setExecutor(new BroadcastCMD());
	}

	public void onDisable() {

	}

}
