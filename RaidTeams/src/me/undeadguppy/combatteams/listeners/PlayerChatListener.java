/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 */
package me.undeadguppy.combatteams.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.undeadguppy.combatteams.Teams;
import me.undeadguppy.combatteams.TeamsPlugin;

public class PlayerChatListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!TeamsPlugin.getInstance().getUserdataConfig().getBoolean("Players." + player.getName() + ".Chat")) {
            return;
        }
        if (!Teams.getPlayerTeam().containsKey(player.getName())) {
            return;
        }
        if (!Teams.getPlayerList().contains(player.getName())) {
            return;
        }
        Teams team = TeamsPlugin.getInstance().getTeams().get(Teams.getPlayerTeam().get(player.getName()));
        event.setCancelled(true);
        for (String members : team.getMembers()) {
            Player p = Bukkit.getPlayer((String)members);
            p.sendMessage("\u00a77\u00a7l\u00a7k||\u00a79\u00a7l" + team.getName() + "\u00a77\u00a7l\u00a7k|| \u00a79\u00a7l" + player.getName() + " \u00a77\u00a7l\u00bb \u00a79" + event.getMessage());
        }
    }
}

