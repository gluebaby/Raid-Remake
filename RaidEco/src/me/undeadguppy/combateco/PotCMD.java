package me.undeadguppy.combateco;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import net.md_5.bungee.api.ChatColor;

public class PotCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		int amount = 10;
		double balance = BalanceUtils.getBalance(p.getUniqueId());
		PlayerInventory pi = p.getInventory();
		Potion pot = new Potion(PotionType.INSTANT_HEAL, 2).splash();
		ItemStack item = pot.toItemStack(1);
		if (balance >= amount) {
			BalanceUtils.withdrawAmount(p.getUniqueId(), (double) amount);
			p.sendMessage(ChatColor.GRAY + "You have been give your pots at the cost of 10 gold!");
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
			p.sendMessage(ChatColor.GRAY + "You don't have enough gold in your bank account!");
			return true;
		}
		return true;
	}

}
