package me.xiaojibazhanshi.victorypointsystem.util;

import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;

public class RunnableHelper {

    private RunnableHelper() {

    }

    public static void showProgressBar(Player player, Stats stats, Level currentLevel, ConfigManager configManager) {
        if (stats == null) return; // failsafe

        int pointsNeeded = currentLevel.pointsToLevelUp() - stats.getPoints();
        String progressBar = getProgressBar(stats.getPoints(), currentLevel.pointsToLevelUp());

        int currentPoints = stats.getPoints();
        int levelUpPoints = currentLevel.pointsToLevelUp();

        int percentage = currentPoints == 0 ? 0 : ((int) ((double) currentPoints / levelUpPoints * 100));
        int pointsGatheredInTotal = currentPoints;

        for (Level level : configManager.getAllLevels()) {
            pointsGatheredInTotal += level.pointsToLevelUp();
        }

        String actionBarMessage;

        actionBarMessage = currentLevel.id() != configManager.getAllLevels().size()
                ? color("&6⚔ Victory Level &a&l" + currentLevel.id() + "&6 ⚔&7 | &b" + percentage + "&7% " +
                "&7[&b&l" + progressBar + "&7] " + "&7| &c" + pointsNeeded + " &7points until level up")
                : color("&6⚔ Victory Level " + "&a&lMAX &6⚔ &7| &a"
                + pointsGatheredInTotal + " &7points gathered in total");

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(actionBarMessage));
    }

    public static String getProgressBar(int currentPoints, int pointsToLvlUp) {
        int totalBars = 16;
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
