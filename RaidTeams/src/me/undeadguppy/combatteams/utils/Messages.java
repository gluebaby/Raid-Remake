/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.file.FileConfiguration
 */
package me.undeadguppy.combatteams.utils;

import org.bukkit.ChatColor;

import me.undeadguppy.combatteams.TeamsPlugin;

public class Messages {
    public static String getJoinMessage() {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)TeamsPlugin.getInstance().getSettingsConfig().getString("Messages.Join"));
    }

    public static String getLeaveMessage() {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)TeamsPlugin.getInstance().getSettingsConfig().getString("Messages.Leave"));
    }

    public static String getCreateMessage() {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)TeamsPlugin.getInstance().getSettingsConfig().getString("Messages.Create"));
    }

    public static String getDisbandMessage() {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)TeamsPlugin.getInstance().getSettingsConfig().getString("Messages.Disband"));
    }
}

