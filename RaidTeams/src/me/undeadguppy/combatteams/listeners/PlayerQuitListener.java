/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package me.undeadguppy.combatteams.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.undeadguppy.combatteams.utils.CombatUtil;
import me.undeadguppy.combatteams.utils.TeamsAPI;

public class PlayerQuitListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (TeamsAPI.isCombatTagged(player)) {
            player.damage(100.0);
            CombatUtil.getCombatTag().remove(player.getName());
        }
    }
}

