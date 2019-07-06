package me.undeadguppy.combateco;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

public class BalanceUtils {

	public static double getBalance(UUID playerUUID) {

		FileConfiguration config = Main.getBalancesYML().getConfig();

		return config.contains(playerUUID.toString()) ? config.getDouble(playerUUID.toString()) : 0.0D;

	}

	public static void depositAmount(UUID playerUUID, double amount) {

		double balance = getBalance(playerUUID);

		balance += amount;

		Main.getBalancesYML().getConfig().set(playerUUID.toString(), balance);

	}

	public static void withdrawAmount(UUID playerUUID, double amount) {

		double balance = getBalance(playerUUID);

		balance -= amount;

		Main.getBalancesYML().getConfig().set(playerUUID.toString(), balance);

	}

}
