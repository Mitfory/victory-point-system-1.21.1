package me.xiaojibazhanshi.victorypointsystem.runnables;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;

public class ActionbarRunnable extends BukkitRunnable {

    private final Player player;
    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;

    public ActionbarRunnable(Player player, VPSystem main) {
        this.player = player;
        this.playerDataManager = main.getPlayerDataManager();
        this.configManager = main.getConfigManager();
    }

    @Override
    public void run() {
        UUID playerUUID = player.getUniqueId();
        Stats stats = playerDataManager.getStatsByUUID(playerUUID);
        if (stats == null) return; // failsafe

        Level currentLevel = configManager.getLevelById(stats.getLevel());

        int pointsNeeded = currentLevel.pointsToLevelUp() - stats.getPoints();
        String progressBar = getProgressBar(stats.getPoints(), currentLevel.pointsToLevelUp());

        int currentPoints = stats.getPoints();
        int levelUpPoints = currentLevel.pointsToLevelUp();

        String actionBarMessage = color("⚔ Victory Points: [" + progressBar + "] " +
                                "(" + currentPoints + "/" + levelUpPoints + ")" +
                                " | Level Up in " + pointsNeeded + " Points ⚔");

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(actionBarMessage));
    }

    private String getProgressBar(int currentPoints, int pointsToLvlUp) {
        int totalBars = 10;
        int filledBars = (int) ((double) currentPoints / pointsToLvlUp * totalBars);

        StringBuilder progressBar = new StringBuilder();
        for (int i = 0; i < totalBars; i++) {
            if (i < filledBars) {
                progressBar.append("#");
            } else {
                progressBar.append("-");
            }
        }

        return progressBar.toString();
    }
}
