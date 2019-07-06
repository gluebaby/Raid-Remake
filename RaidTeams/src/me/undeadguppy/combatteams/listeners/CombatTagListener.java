/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
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
import me.undeadguppy.combatteams.TeamsPlugin;
import me.undeadguppy.combatteams.utils.CombatUtil;
import me.undeadguppy.combatteams.utils.TeamsAPI;

public class CombatTagListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        Player victim = (Player)event.getEntity();
        Player damager = (Player)event.getDamager();
        if (!CombatUtil.getCombatTag().containsKey(victim.getName()) && !CombatUtil.getCombatTag().containsKey(damager.getName())) {
            if (TeamsAPI.isInTeam(victim)) {
                Teams team = TeamsAPI.getPlayerTeam(victim);
                if (team.getMembers().contains(damager.getName())) {
                    return;
                }
                CombatUtil.getCombatTag().put(victim.getName(), TeamsPlugin.getInstance().getSettingsConfig().getInt("Combat-Interval"));
                CombatUtil.getCombatTag().put(damager.getName(), TeamsPlugin.getInstance().getSettingsConfig().getInt("Combat-Interval"));
                victim.sendMessage("\u00a77\u00a7k||\u00a79Combat\u00a77\u00a7k|| \u00a77\u00a7l\u00bb \u00a79You have been tagged for \u00a77" + TeamsPlugin.getInstance().getSettingsConfig().getInt("Combat-Interval") + "s\u00a79!");
                damager.sendMessage("\u00a77\u00a7k||\u00a79Combat\u00a77\u00a7k|| \u00a77\u00a7l\u00bb \u00a79You have been tagged for \u00a77" + TeamsPlugin.getInstance().getSettingsConfig().getInt("Combat-Interval") + "s\u00a79!");
            } else {
                CombatUtil.getCombatTag().put(victim.getName(), TeamsPlugin.getInstance().getSettingsConfig().getInt("Combat-Interval"));
                CombatUtil.getCombatTag().put(damager.getName(), TeamsPlugin.getInstance().getSettingsConfig().getInt("Combat-Interval"));
                victim.sendMessage("\u00a77\u00a7k||\u00a79Combat\u00a77\u00a7k|| \u00a77\u00a7l\u00bb \u00a79You have been tagged for \u00a77" + TeamsPlugin.getInstance().getSettingsConfig().getInt("Combat-Interval") + "s\u00a79!");
                damager.sendMessage("\u00a77\u00a7k||\u00a79Combat\u00a77\u00a7k|| \u00a77\u00a7l\u00bb \u00a79You have been tagged for \u00a77" + TeamsPlugin.getInstance().getSettingsConfig().getInt("Combat-Interval") + "s\u00a79!");
            }
        } else if (TeamsAPI.isInTeam(victim)) {
            Teams team = TeamsAPI.getPlayerTeam(victim);
            if (team.getMembers().contains(damager.getName())) {
                return;
            }
            CombatUtil.getCombatTag().put(victim.getName(), TeamsPlugin.getInstance().getSettingsConfig().getInt("Combat-Interval"));
            CombatUtil.getCombatTag().put(damager.getName(), TeamsPlugin.getInstance().getSettingsConfig().getInt("Combat-Interval"));
        } else {
            CombatUtil.getCombatTag().put(victim.getName(), TeamsPlugin.getInstance().getSettingsConfig().getInt("Combat-Interval"));
            CombatUtil.getCombatTag().put(damager.getName(), TeamsPlugin.getInstance().getSettingsConfig().getInt("Combat-Interval"));
        }
    }
}

