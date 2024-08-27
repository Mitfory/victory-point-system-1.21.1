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

        boolean isPassiveKill = isPassive(entity);
        boolean isPlayerKill = entity instanceof Player;
        boolean isAggressiveNonPlayer = isAggressiveAndNonPlayer(entity);
        boolean shouldOverridePoints = overrides.containsKey(entityType);
        int addedPoints = shouldOverridePoints ? overrides.get(entityType) : getDefaultPointsForKilling(entity);

        int updatedPoints = stats.getPoints() + addedPoints;
        int pointsToLvlUp = level.pointsToLvlUp();

        if (updatedPoints > pointsToLvlUp) {
            // notify the player

            stats.setLevel(stats.getLevel() + 1);
            updatedPoints -= pointsToLvlUp;
        }

        stats.setPlayerKills(isPlayerKill ? stats.getPlayerKills() + 1 : stats.getPlayerKills());
        stats.setAggressiveKills();


    }

}
