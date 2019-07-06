/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDeathEvent
 */
package me.undeadguppy.combatteams.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.undeadguppy.combatteams.Teams;
import me.undeadguppy.combatteams.TeamsPlugin;

public class PlayerDeathListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)event.getEntity();
        if (!Teams.getPlayerTeam().containsKey(player.getName()) && !Teams.getPlayerList().contains(player.getName())) {
            return;
        }
        Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
        team.addDeaths();
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerKill(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)event.getEntity();
        if (!(player.getKiller() instanceof Player)) {
            return;
        }
        Player killer = player.getKiller();
        if (!Teams.getPlayerTeam().containsKey(killer.getName()) && !Teams.getPlayerList().contains(killer.getName())) {
            return;
        }
        Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
        team.addKills();
    }
}

