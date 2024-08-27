package me.xiaojibazhanshi.victorypointsystem.util;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.UUID;

import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;

public class PlayerChatUtil {

    private PlayerChatUtil() {

    }

    public static String replaceStatPlaceholders(VPSystem main, String text, Player player) {
        ConfigManager configManager = main.getConfigManager();
        PlayerDataManager playerDataManager = main.getPlayerDataManager();
        Stats playerStats = playerDataManager.getStatsByUUID(player.getUniqueId());
        String playerName = player.getName();

        double kdRatio = playerStats.getPlayerKD();
        int allKills = playerStats.getAllKills();
        int passiveKills = playerStats.getPassiveKills();
        int aggressiveKills = playerStats.getAggressiveKills();
        int deaths = playerStats.getDeaths();
        int currentPoints = playerStats.getPoints();
        int level = playerStats.getLevel();

        int pointsTilPlayersLevel = configManager.getAllLvlUpPointsTilLevel(level, true);
        int overallPoints = currentPoints + pointsTilPlayersLevel;

        DecimalFormat df = new DecimalFormat("0.00");
        String kdr = df.format(kdRatio);

        return text
                .replace("%my_level%",
                        color("&7[&6" + playerName + "&7's &alevel&7: &b" + level + "&7]"))
                .replace("%my_kd%",
                       color("&7[&6" + playerName + "&7's &aK/D ratio&7: &b" + kdr + "&7]"))
                .replace("%my_kills%",
                       color("&7[&6" + playerName + "&7's &akills&7: &b" + allKills + "&7]"))
                .replace("%my_passive_kills%",
                       color("&7[&6" + playerName + "&7's &apassive mob kills&7: &b" + passiveKills + "&7]"))
                .replace("%my_aggressive_kills%",
                       color("&7[&6" + playerName + "&7's &a8aggressive mob kills&7: &b" + aggressiveKills + "&7]"))
                .replace("%my_deaths%",
                       color("&7[&6" + playerName + "&7's &adeaths&7: &b" + deaths + "&7]"))
                .replace("%my_points%",
                       color("&7[&6" + playerName + "&7's &aaccumulated points&7: &b" + overallPoints + "&7]"));
    }

}
