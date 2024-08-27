package me.xiaojibazhanshi.victorypointsystem.listeners;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;
import static me.xiaojibazhanshi.victorypointsystem.util.KillListenerUtil.*;

public class KillListener implements Listener {

    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;
    private final VPSystem main;

    public KillListener(VPSystem main) {
        this.main = main;
        this.playerDataManager = main.getPlayerDataManager();
        this.configManager = main.getConfigManager();
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent event) {
        if (!(event.getEntity().getKiller() instanceof Player killer)) return;

        /* DATA */

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

        /* POINTS AND NOTIFYING */

        boolean shouldOverridePoints = overrides.containsKey(entityType);
        int addedPoints = shouldOverridePoints ? overrides.get(entityType) : getDefaultPointsForKilling(entity);

        int updatedPoints = stats.getPoints() + addedPoints;
        int pointsToLevelUp = level.pointsToLevelUp();
        boolean levelUp = updatedPoints >= pointsToLevelUp;

        stats.setPoints(updatedPoints);

        if (levelUp) {
            if (level.id() == configManager.getAllLevels().size()) return;
            Level nextLevel;

            do {
                nextLevel = configManager.getAllLevels().get(level.id());
                boolean arePerksCumulative = configManager.getArePerksCumulative();

                List<Level> previousLevels = getPreviousLevels(configManager, nextLevel);
                handleAttributeChange(killer, nextLevel, previousLevels, arePerksCumulative);

                stats.incrementLevel(true);
                updatedPoints -= pointsToLevelUp;
            } while (updatedPoints >= pointsToLevelUp);

            killer.sendTitle(color(nextLevel.title()),
                    color( "&7is your new title!"),
                    5, 25, 5);

            killer.playSound(killer, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        }

        stats.setPoints(updatedPoints);

        /* SAVING DATA */

        playerDataManager.savePlayerData();

        /* VISUAL EFFECT (FLOATING +XX POINTS) */

        Location topOfTheHead = entity.getLocation().add(0, entity.getHeight(), 0);

        ArmorStand pointHologram = generateHologram(topOfTheHead, color("&a&l+" + addedPoints));

        Bukkit.getScheduler().runTaskLater(main, pointHologram::remove, 30);
    }


}
