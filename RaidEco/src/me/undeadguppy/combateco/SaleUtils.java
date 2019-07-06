package me.undeadguppy.combateco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SaleUtils {

	private static List<Sale> sales = new ArrayList<Sale>();

	public static void constructSale(Player player, Material material, double price) {

		Sale sale = new Sale(player.getUniqueId(), material, price);

		sales.add(sale);

	}

	public static void loadSales() {

		FileConfiguration config = Main.getSalesYML().getConfig();

		if (!config.contains("sales"))
			return;

		for (String key : config.getConfigurationSection("sales").getKeys(false)) {

			UUID uuid = UUID.fromString(key);

			UUID playerUUID = UUID.fromString(config.getString("sales." + key + ".player"));

			Material material = Material.getMaterial(config.getString("sales." + key + ".material"));

			double price = config.getDouble("sales." + key + ".price");

			Sale sale = new Sale(uuid, playerUUID, material, price);

			sales.add(sale);

		}

	}

	public static List<Sale> getSales(final Material material, final int amount, boolean isBuying) {

		final List<Sale> list = new ArrayList<Sale>();

		final List<Double> prices = new ArrayList<Double>();

		new BukkitRunnable() {

			public void run() {

				for (Sale sale : sales) {

					if (sale.getMaterial() == material) {

						prices.add(sale.getPrice());

					}

				}

			}

		}.runTaskAsynchronously(Main.getInstance());

		Collections.sort(prices);

		if (prices.isEmpty())
			return list;

		if (prices.size() < amount) {

			new BukkitRunnable() {

				public void run() {

					for (int i = 0; i < prices.size(); i++) {

						list.add(null);

					}

				}

			}.runTaskAsynchronously(Main.getInstance());

			return list;

		}

		new BukkitRunnable() {

			public void run() {

				for (int i = 0; i < amount; i++) {

					list.add(getSale(prices.get(i)));

				}

			}

		}.runTaskAsynchronously(Main.getInstance());

		if (!isBuying) {

			new BukkitRunnable() {

				public void run() {

					for (Sale sale : list) {

						sales.add(sale);

					}

				}

			}.runTaskAsynchronously(Main.getInstance());

		}

		return list;

	}

	public static Sale getSale(double price) {

		Sale sale = null;

		for (Sale s : sales) {

			if (s.getPrice() == price) {

				sale = s;

			}

		}

		sales.remove(sale);

		return sale;

	}

	public static void buyItems(final Player player, final List<Sale> list) {

		new BukkitRunnable() {

			public void run() {

				for (Sale sale : list) {

					player.getInventory().addItem(new ItemStack(sale.getMaterial(), 1));

					Main.getSalesYML().getConfig().set("sales." + sale.getUUID().toString(), null);

					Main.getSalesYML().saveConfig();

					BalanceUtils.withdrawAmount(player.getUniqueId(), sale.getPrice());

					BalanceUtils.depositAmount(sale.getPlayerUUID(), sale.getPrice());

					if (Bukkit.getPlayer(sale.getPlayerUUID()) != null) {

						Bukkit.getPlayer(sale.getPlayerUUID()).sendMessage(ChatColor.GOLD + "A player has bought 1 "
								+ ItemUtils.toFriendlyName(sale.getMaterial()) + " from you for " + sale.getPrice()
								+ " gold. Your new balance is " + BalanceUtils.getBalance(sale.getPlayerUUID()) + ".");

					}

				}

			}

		}.runTaskAsynchronously(Main.getInstance());

	}

	public static void migrateUUIDs() {

		for (String name : Main.getBalancesYML().getConfig().getKeys(false)) {

			double balance = Main.getBalancesYML().getConfig().getDouble(name);

			Main.getBalancesYML().getConfig().set(name, null);

			Main.getBalancesYML().getConfig().set(UUID.fromString(name).toString(), balance);

			Main.getBalancesYML().saveConfig();

		}

		for (String saleId : Main.getSalesYML().getConfig().getConfigurationSection("sales").getKeys(false)) {

			String name = Main.getSalesYML().getConfig().getString("sales." + saleId + ".player");

			Main.getSalesYML().getConfig().set("sales." + saleId + ".player",
					Bukkit.getPlayer(name).getUniqueId().toString());

			Main.getSalesYML().saveConfig();

		}

	}

}
