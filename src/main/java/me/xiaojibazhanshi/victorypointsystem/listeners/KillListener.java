package me.xiaojibazhanshi.victorypointsystem.listeners;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
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
        Entity entity = event.getEntity();

        if (!(event.getEntity().getKiller() instanceof Player killer)) return;
        if (killer.equals(entity)) return;

        // MAIN DATA

        EntityType entityType = entity.getType();

        Map<EntityType, Integer> overrides = configManager.getPointsPerKillOverrides();

        UUID killerUUID = killer.getUniqueId();
        Stats stats = playerDataManager.getStatsByUUID(killerUUID);
        Level level = configManager.getLevelById(stats.getLevel());

        // KILLS

        boolean isPassive = isPassive(entity);
        boolean isPlayerKill = entity instanceof Player;
        boolean isAggressiveNonPlayer = isAggressiveAndNonPlayer(entity);

        stats.incrementPlayerKills(isPlayerKill);
        stats.incrementAggressiveKills(isAggressiveNonPlayer);
        stats.incrementPassiveKills(isPassive);

        // POINTS AND LEVEL UP LOGIC & CHECK

        boolean shouldOverridePoints = overrides.containsKey(entityType);
        int addedPoints = shouldOverridePoints ? overrides.get(entityType) : getDefaultPointsForKilling(entity);

        int updatedPoints = stats.getPoints() + addedPoints;
        int pointsToLevelUp = level.pointsToLevelUp();
        boolean levelUp = updatedPoints >= pointsToLevelUp;

        stats.setPoints(updatedPoints);

        if (levelUp && level.id() != configManager.getAllLevels().size()) {
            Level nextLevel;

            do {
                nextLevel = configManager.getAllLevels().get(level.id());
                boolean arePerksCumulative = configManager.getArePerksCumulative();

                List<Level> previousLevels = getPreviousLevels(configManager, nextLevel);
                handleAttributeChange(killer, nextLevel, previousLevels, arePerksCumulative);

                stats.incrementLevel(true);
                updatedPoints -= pointsToLevelUp;
            } while (updatedPoints >= pointsToLevelUp);

            killer.sendTitle(color(nextLevel.title()), color("&7is your new title!"), 5, 25, 5);
            killer.playSound(killer, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        }

        stats.setPoints(updatedPoints);

        // SAVING DATA

        playerDataManager.savePlayerDataAsync();

        // VFX (FLOATING "+XX POINTS")

        double yAdjustment = entity.getHeight() + 0.1;
        Vector up = new Vector(0, 0.5, 0);

        Location aboveTheHead = entity.getLocation().clone().add(0, yAdjustment, 0);
        Location teleportLocation = aboveTheHead.clone().add(up);
        String displayName = color("&a&l+" + addedPoints);

        TextDisplay textDisplay = createHologram(aboveTheHead, displayName);
        textDisplay.setVisibleByDefault(false);
        textDisplay.setTeleportDuration(28);

        killer.showEntity(main, textDisplay);

        Bukkit.getScheduler().runTaskLater(main, () -> textDisplay.teleport(teleportLocation), 2);
        Bukkit.getScheduler().runTaskLater(main, textDisplay::remove, 30);
    }


}
