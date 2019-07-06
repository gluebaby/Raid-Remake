/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 */
package me.undeadguppy.combatteams.listeners;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.undeadguppy.combatteams.TeamsPlugin;

public class PlayerJoinListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isDead()) {
            player.sendMessage("\u00a77\u00a7k||\u00a79Combat\u00a77\u00a7k|| \u00a77\u00a7l\u00bb \u00a79You died for combat logging!");
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!TeamsPlugin.getInstance().getUserdataConfig().contains("Players." + player.getName())) {
            TeamsPlugin.getInstance().getUserdataConfig().set("Players." + player.getName() + ".Chat", (Object)false);
            try {
                TeamsPlugin.getInstance().getUserdataConfig().save(TeamsPlugin.getInstance().getUserdataFile());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                TeamsPlugin.getInstance().getUserdataConfig().load(TeamsPlugin.getInstance().getUserdataFile());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

