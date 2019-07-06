package me.undeadguppy.raidenchants;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rit.sucy.EnchantmentAPI;

import net.md_5.bungee.api.ChatColor;

public class ApplyEnchantCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("applyench")) {
			Player p = (Player) sender;
			if (p.hasPermission("raidmc.applyench")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.RED + "/applyench <type> ~ Will apply it to your held item.");
					return true;
				}
				if (args[0].equalsIgnoreCase("fish")) {
					ItemStack item = p.getInventory().getItemInHand();
					EnchantmentAPI.getEnchantment("Fish").addToItem(item, 1);
					p.sendMessage(ChatColor.GREEN + "You have added the fish enchantment to your held item!");
				} else if (args[0].equalsIgnoreCase("blaze")) {
					ItemStack item = p.getInventory().getItemInHand();
					EnchantmentAPI.getEnchantment("Blaze").addToItem(item, 2);
					p.sendMessage(ChatColor.GREEN + "You have added the blaze enchantment to your held item!");
				} else if (args[0].equalsIgnoreCase("boom")) {
					ItemStack item = p.getInventory().getItemInHand();
					EnchantmentAPI.getEnchantment("Boom").addToItem(item, 1);
					p.sendMessage(ChatColor.GREEN + "You have added the boom enchantment to your held item!");
				} else if (args[0].equalsIgnoreCase("haste")) {
					ItemStack item = p.getInventory().getItemInHand();
					EnchantmentAPI.getEnchantment("Haste").addToItem(item, 1);
					p.sendMessage(ChatColor.GREEN + "You have added the haste enchantment to your held item!");
				} else if (args[0].equalsIgnoreCase("strength")) {
					ItemStack item = p.getInventory().getItemInHand();
					EnchantmentAPI.getEnchantment("Strength").addToItem(item, 3);
					p.sendMessage(ChatColor.GREEN + "You have added the strength enchantment to your held item!");
				} else {
					p.sendMessage(ChatColor.RED + "That is not a vaild enchantment!");
					return true;
				}
			} else {
				p.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
			}
		}
		return true;
	}

}
