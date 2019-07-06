package me.undeadguppy.combatspawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnZoneListener implements Listener {
	SpawnProtection p;

	public SpawnZoneListener(SpawnProtection p) {
		this.p = p;
	}

	@EventHandler
	public void onPlayerHit(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();

			if (p.isInSpawn(player.getLocation())) {
				e.setCancelled(true);
				return;
			}

			if (p.getTeleports().containsKey(player)) {
				Bukkit.getServer().getScheduler().cancelTask(p.getTeleports().get(player));
				player.sendMessage(ChatColor.GRAY + "Warp cancelled!");
				p.getTeleports().remove(player);
			}
		}
	}

	@EventHandler
	public void onPlayerBlockBreak(BlockBreakEvent e) {
		if (p.isInSpawn(e.getPlayer().getLocation())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerBlockPlace(BlockPlaceEvent e) {
		if (p.isInSpawn(e.getPlayer().getLocation())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onHungerLoss(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			if (p.isInSpawn(e.getEntity().getLocation())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (p.isInSpawn(e.getFrom()) && !p.isInSpawn(e.getTo())) {
			e.getPlayer().sendMessage(ChatColor.GRAY + "You have lost your spawn protection.");
		}
		if (!p.isInSpawn(e.getFrom()) && p.isInSpawn(e.getTo())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.GRAY + "You may not re-enter spawn! Use /spawn to get back.");
		}
		if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY()
				|| e.getFrom().getZ() != e.getTo().getZ()) {
			if (p.getTeleports().containsKey(e.getPlayer())) {
				Bukkit.getServer().getScheduler().cancelTask(p.getTeleports().get(e.getPlayer()));
				e.getPlayer().sendMessage(ChatColor.GRAY + "Warp cancelled!");
				p.getTeleports().remove(e.getPlayer());
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerAttack(EntityDamageByEntityEvent e) {
		if (p.isInSpawn(e.getDamager().getLocation()) || p.isInSpawn(e.getEntity().getLocation())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.getPlayer().teleport(p.getSpawn());
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		e.setRespawnLocation(p.getSpawn());
	}

	@EventHandler
	public void onMobSpawn(EntitySpawnEvent e) {
		if (p.isInSpawn(e.getLocation())) {
			e.setCancelled(true);
		}
	}
}
