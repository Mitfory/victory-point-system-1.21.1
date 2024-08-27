package me.xiaojibazhanshi.victorypointsystem.util;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;

import java.text.DecimalFormat;

public class PlayerChatUtil {

    protected PlayerChatUtil() {

    }

    public String replaceStatPlaceholders(VPSystem main, String text, Stats playerStats) {
        double kdRatio = playerStats.getPlayerKD();
        int allKills = playerStats.getAllKills();
        int passiveKills = playerStats.getPassiveKills();
        int aggressiveKills = playerStats.getAggressiveKills();
        int deaths = playerStats.getDeaths();
        int points = playerStats.getPoints();

        DecimalFormat df = new DecimalFormat("0.00");

        String kdr = df.format(kdRatio);

        String newText = text
                .replace("%my_kd%", kdr)
                .replace("%my_kills%")
                .replace("%my_passive_kills%")
                .replace("%my_aggressive_kills%")
                .replace("%my_deaths%")
                .replace("%my_points%");

        return newText;
    }

}
