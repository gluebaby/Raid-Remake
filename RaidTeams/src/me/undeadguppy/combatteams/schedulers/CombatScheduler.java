/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package me.undeadguppy.combatteams.schedulers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.undeadguppy.combatteams.utils.CombatUtil;

public class CombatScheduler
implements Runnable {
    @Override
    public void run() {
        if (CombatUtil.getCombatTag() != null) {
            for (String names : CombatUtil.getCombatTag().keySet()) {
                if (CombatUtil.getCombatTag().get(names) > 0) {
                    CombatUtil.getCombatTag().put(names, CombatUtil.getCombatTag().get(names) - 1);
                    continue;
                }
                CombatUtil.getCombatTag().remove(names);
                Player p = Bukkit.getPlayer((String)names);
                p.sendMessage("\u00a77\u00a7k||\u00a79Combat\u00a77\u00a7k|| \u00a77\u00a7l\u00bb \u00a79You are no longer combat tagged!");
            }
        }
    }
}

