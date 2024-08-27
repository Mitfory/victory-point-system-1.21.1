package me.xiaojibazhanshi.victorypointsystem.util;

import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;

public class RunnableHelper {

    private RunnableHelper() {

    }

    public static void showRegularProgressBar(Player player, Stats stats, Level currentLevel) {
        if (stats == null) return; // failsafe

        int pointsNeeded = currentLevel.pointsToLevelUp() - stats.getPoints();
        String progressBar = getProgressBar(stats.getPoints(), currentLevel.pointsToLevelUp());

        int currentPoints = stats.getPoints();
        int levelUpPoints = currentLevel.pointsToLevelUp();

        String actionBarMessage = color(
                "&6Victory Level: &a" + currentLevel.id() +
                        " &7| &a" + currentPoints + "&7/&a" + levelUpPoints +
                        " &7[" + progressBar + "] " +
                        "&7| &c" + pointsNeeded + " &7points until level up");

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(actionBarMessage));
    }

    public static String getProgressBar(int currentPoints, int pointsToLvlUp) {
        int totalBars = 20;
        int filledBars = (int) ((double) currentPoints / pointsToLvlUp * totalBars);

        StringBuilder progressBar = new StringBuilder();

        for (int i = 0; i < totalBars; i++) {

            if (i < filledBars) {
                progressBar.append("▰");
            } else {
                progressBar.append("▱");
            }
        }

        return progressBar.toString();
    }

}
