/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 */
package me.undeadguppy.combatteams.schedulers;

import me.undeadguppy.combatteams.TeamsPlugin;

public class SettingsScheduler
implements Runnable {
    @Override
    public void run() {
        try {
            TeamsPlugin.getInstance().getTeamsConfig().load(TeamsPlugin.getInstance().getTeamsFile());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

