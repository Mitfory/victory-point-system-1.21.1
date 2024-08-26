package me.xiaojibazhanshi.victorypointsystem.util;

import org.bukkit.ChatColor;

public class GeneralUtil {

    private GeneralUtil() {

    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
