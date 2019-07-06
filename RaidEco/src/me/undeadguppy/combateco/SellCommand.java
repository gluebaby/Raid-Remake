package me.undeadguppy.combateco;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SellCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {

			final Player player = (Player) sender;

			if (args.length == 3) {

				try {

					final int amount = Integer.parseInt(args[0]);

					if (amount <= 0) {

						player.sendMessage(ChatColor.GRAY + "You must use positive integers to sell items.");

						return true;

					}

					final Material material = Material.matchMaterial(args[1].toUpperCase());

					if (material == null) {

						player.sendMessage(ChatColor.GRAY + "Item '" + args[1] + "' not found.");

						return true;

					}

					double price = Double.parseDouble(args[2]);

					int count = 0;

					for (ItemStack is : player.getInventory().getContents()) {

						if (is != null && is.getType() == material && is.getData().getData() == 0x0) {

							count += is.getAmount();

						}

					}

					if (amount > count) {

						player.sendMessage(ChatColor.GRAY + "You only have " + count + " "
								+ ItemUtils.toFriendlyName(material) + ".");

						return true;

					}

					final double unitPrice = price / amount;

					new BukkitRunnable() {

						public void run() {

							for (int i = 0; i < amount; i++) {

								SaleUtils.constructSale(player, material, unitPrice);

							}

						}

					}.runTaskAsynchronously(Main.getInstance());

					player.getInventory().removeItem(new ItemStack(material, amount));

					player.sendMessage(ChatColor.GRAY + "You have put " + amount + " "
							+ ItemUtils.toFriendlyName(material) + " on the market for " + price + " gold.");

					return true;

				} catch (NumberFormatException ex) {

					player.sendMessage(ChatColor.GRAY + "Please use /sell <amount> <item> <price>");

					return true;

				}

			} else {

				player.sendMessage(ChatColor.GRAY + "Please use /sell <amount> <item> <price>");

				return true;

			}

		}
		return true;
	}

}
