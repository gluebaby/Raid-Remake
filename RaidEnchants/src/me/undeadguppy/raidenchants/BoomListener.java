package me.undeadguppy.raidenchants;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.rit.sucy.EnchantmentAPI;

public class BoomListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInHand();
		if (EnchantmentAPI.itemHasEnchantment(item, "Boom")) {
			Location loc = e.getBlock().getLocation().clone().add(0.0, 0.0, 0.0);
			int radius = 1;
			for (int xMod = -radius; xMod <= radius; xMod++) {
				for (int zMod = -radius; zMod <= radius; zMod++) {
					Block theBlock = loc.getBlock().getRelative(xMod, 0, zMod);
					theBlock.breakNaturally();
				}
			}
		}
	}

}
