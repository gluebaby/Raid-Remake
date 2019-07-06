/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package me.undeadguppy.combatteams.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.undeadguppy.combatteams.Teams;
import me.undeadguppy.combatteams.utils.TeamsAPI;

public class FriendlyFireListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Teams team;
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)event.getEntity();
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        Player damager = (Player)event.getDamager();
        if (TeamsAPI.isInTeam(player) && (team = TeamsAPI.getPlayerTeam(player)).getMembers().contains(damager.getName())) {
            if (team.getFriendlyFire().booleanValue()) {
                return;
            }
            event.setCancelled(true);
        }
    }
}

