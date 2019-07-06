package me.undeadguppy.combateco;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.md_5.bungee.api.ChatColor;

public class SoupCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		int amount = 10;
		double balance = BalanceUtils.getBalance(p.getUniqueId());
		PlayerInventory pi = p.getInventory();
		ItemStack item = new ItemStack(Material.MUSHROOM_SOUP);
		if (balance >= amount) {
			BalanceUtils.withdrawAmount(p.getUniqueId(), (double) amount);
			p.sendMessage(ChatColor.GRAY + "You have been granted your soup at the cost of 10 gold!");
			pi.addItem(item);
			pi.addItem(item);
			pi.addItem(item);
			pi.addItem(item);
			pi.addItem(item);
			pi.addItem(item);
			pi.addItem(item);
			pi.addItem(item);
			pi.addItem(item);
		} else {
			p.sendMessage(ChatColor.RED + "You don't have enough gold in your bank account!");
			return true;
		}
		return true;
	}

}
