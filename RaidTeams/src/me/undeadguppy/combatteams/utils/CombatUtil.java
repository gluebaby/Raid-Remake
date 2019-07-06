/*
 * Decompiled with CFR 0_114.
 */
package me.undeadguppy.combatteams.utils;

import java.util.HashMap;
import java.util.Map;

public class CombatUtil {
    private static Map<String, Integer> combatTag = new HashMap<String, Integer>();

    public static Map<String, Integer> getCombatTag() {
        return combatTag;
    }

    public static void setCombatTag(Map<String, Integer> combatTag) {
        CombatUtil.combatTag = combatTag;
    }
}

