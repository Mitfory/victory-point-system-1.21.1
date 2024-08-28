package me.xiaojibazhanshi.victorypointsystem.listeners;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DebugLogger {

    /*private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;
    private final VPSystem main;

    public DebugLogger(VPSystem main) {
        this.main = main;
        this.playerDataManager = main.getPlayerDataManager();
        this.configManager = main.getConfigManager();
    }

    public void logKillEvent(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Player killer = entity.getKiller();

        if (killer == null) {
            log("Entity killed by non-player or null killer.");
            return;
        }

        UUID killerUUID = killer.getUniqueId();
        Stats stats = playerDataManager.getStatsByUUID(killerUUID);
        Level level = configManager.getLevelById(stats.getLevel());

        log("Killer UUID: " + killerUUID);
        log("Entity Type: " + entity.getType());
        log("Current Level: " + level.title());
        log("Current Points: " + stats.getPoints());

        Map<EntityType, Integer> overrides = configManager.getPointsPerKillOverrides();
        boolean shouldOverridePoints = overrides.containsKey(entity.getType());
        int addedPoints = shouldOverridePoints ? overrides.get(entity.getType()) : getDefaultPointsForKilling(entity);

        log("Points to Add: " + addedPoints);

        int updatedPoints = stats.getPoints() + addedPoints;
        log("Updated Points: " + updatedPoints);

        int pointsToLevelUp = level.pointsToLevelUp();
        boolean levelUp = updatedPoints >= pointsToLevelUp;

        log("Points to Level Up: " + pointsToLevelUp);
        log("Level Up: " + levelUp);

        if (levelUp) {
            Level nextLevel;
            do {
                nextLevel = configManager.getAllLevels().get(level.id());
                log("Next Level: " + nextLevel.title());
                boolean arePerksCumulative = configManager.getArePerksCumulative();

                List<Level> previousLevels = getPreviousLevels(configManager, nextLevel);
                handleAttributeChange(killer, nextLevel, previousLevels, arePerksCumulative);

                stats.incrementLevel(true);
                updatedPoints -= pointsToLevelUp;
                log("Points After Level Up: " + updatedPoints);
            } while (updatedPoints >= pointsToLevelUp && level.id() < configManager.getAllLevels().size() - 1);

            if (updatedPoints < 0) {
                updatedPoints = 0;
            }

            stats.setPoints(updatedPoints);
            log("Final Points Set: " + updatedPoints);

            killer.sendTitle(Component.text(nextLevel.title()), Component.text("is your new title!"));
            killer.playSound(killer, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        }

        playerDataManager.savePlayerDataAsync();
        log("Player data saved asynchronously.");
    }

    private int getDefaultPointsForKilling(Entity entity) {
        // Example method, replace with actual logic
        return 10;
    }

    private List<Level> getPreviousLevels(ConfigManager configManager, Level level) {
        // Example method, replace with actual logic
        return List.of();
    }

    private void handleAttributeChange(Player player, Level level, List<Level> previousLevels, boolean arePerksCumulative) {
        // Example method, replace with actual logic
        log("Handling attribute change for player: " + player.getName());
    }

    private void log(String message) {
        Bukkit.getLogger().info("[DEBUG] " + message);
    }*/
}
