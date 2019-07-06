/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package me.undeadguppy.combatteams.utils;

import org.bukkit.entity.Player;

import me.undeadguppy.combatteams.Teams;
import me.undeadguppy.combatteams.TeamsPlugin;

public class TeamsAPI {
    public static String getTeamName(Player player) {
        return Teams.getPlayerTeam().get(player.getName());
    }

    public static boolean isInTeam(Player player) {
        if (Teams.getPlayerTeam().containsKey(player.getName()) && Teams.getPlayerList().contains(player.getName())) {
            return true;
        }
        return false;
    }

    public static boolean isCombatTagged(Player player) {
        if (CombatUtil.getCombatTag().containsKey(player.getName())) {
            return true;
        }
        return false;
    }

    public static Teams getPlayerTeam(Player player) {
        if (TeamsAPI.isInTeam(player)) {
            Teams team = TeamsPlugin.getInstance().getTeams().get(TeamsAPI.getTeamName(player));
            return team;
        }
        return null;
    }
}

