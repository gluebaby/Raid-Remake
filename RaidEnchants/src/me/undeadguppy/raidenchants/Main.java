package me.undeadguppy.raidenchants;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.rit.sucy.EnchantPlugin;
import com.rit.sucy.EnchantmentAPI;

public class Main extends EnchantPlugin {

	public void onEnable() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new BoomListener(), this);
		registerEnchantments();
		getCommand("applyench").setExecutor(new ApplyEnchantCMD());
	}

	public void onDisable() {

	}

	@Override
	public void registerEnchantments() {
		EnchantmentAPI.registerCustomEnchantment(new FishEnchantment());
		EnchantmentAPI.registerCustomEnchantment(new BlazeEnchantment());
		EnchantmentAPI.registerCustomEnchantment(new BoomEnchantment());
		EnchantmentAPI.registerCustomEnchantment(new HasteEnchantment());
		EnchantmentAPI.registerCustomEnchantment(new StrengthEnchant());
	}

}
