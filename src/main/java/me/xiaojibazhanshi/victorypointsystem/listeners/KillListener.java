package me.xiaojibazhanshi.victorypointsystem.listeners;

import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.data.config.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Map;
import java.util.UUID;

import static me.xiaojibazhanshi.victorypointsystem.util.KillListenerUtil.*;

public class KillListener implements Listener {

    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;

    public KillListener(PlayerDataManager playerDataManager, ConfigManager configManager) {
        this.playerDataManager = playerDataManager;
        this.configManager = configManager;
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent event) {
        if (!(event.getEntity().getKiller() instanceof Player killer)) return;

        Entity entity = event.getEntity();
        EntityType entityType = entity.getType();

        Map<EntityType, Integer> overrides = configManager.getPointsPerKillOverrides();

        UUID killerUUID = killer.getUniqueId();
        Stats stats = playerDataManager.getStatsByUUID(killerUUID);
        Level level = configManager.getLevelById(stats.getLevel());

        /* KILLS */

        boolean isPassive = isPassive(entity);
        boolean isPlayerKill = entity instanceof Player;
        boolean isAggressiveNonPlayer = isAggressiveAndNonPlayer(entity);

        stats.incrementPlayerKills(isPlayerKill);
        stats.incrementAggressiveKills(isAggressiveNonPlayer);
        stats.incrementPassiveKills(isPassive);

        /* POINTS */

        boolean shouldOverridePoints = overrides.containsKey(entityType);
        int addedPoints = shouldOverridePoints ? overrides.get(entityType) : getDefaultPointsForKilling(entity);

        int updatedPoints = stats.getPoints() + addedPoints;
        int pointsToLevelUp = level.pointsToLevelUp();
        boolean levelUp = updatedPoints >= pointsToLevelUp;

        if (levelUp) {
            stats.incrementLevel(true);
            updatedPoints -= pointsToLevelUp;
        }

        stats.setPoints(updatedPoints);

        /* NOTIFICATION AREA */

        if (levelUp) {
            // notify via a title
        }

        // showcase the point difference via an actionbar

        /* SAVING DATA */

        playerDataManager.savePlayerData();
    }

}
