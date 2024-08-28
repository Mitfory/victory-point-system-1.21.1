package me.xiaojibazhanshi.victorypointsystem.listeners;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;
import java.util.UUID;

import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;
import static me.xiaojibazhanshi.victorypointsystem.util.KillListenerUtil.getPreviousLevels;
import static me.xiaojibazhanshi.victorypointsystem.util.KillListenerUtil.handleAttributeChange;

public class DeathListener implements Listener {

    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;

    public DeathListener(VPSystem main) {
        this.playerDataManager = main.getPlayerDataManager();
        this.configManager = main.getConfigManager();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID uuid = player.getUniqueId();

        Stats stats = playerDataManager.getStatsByUUID(uuid);
        int level = stats.getLevel();
        stats.incrementDeaths(true);

        int currentPoints = stats.getPoints();
        Level currentLevel = configManager.getLevelById(level);
        int pointsOnDeath = currentLevel.pointsOnDeath();

        List<Level> previousLevels = getPreviousLevels(configManager, currentLevel);
        Level previousLevel = previousLevels.getLast();
        boolean arePerksCumulative = configManager.getArePerksCumulative();
        int previousLevelPoints = previousLevel.pointsToLevelUp();

        boolean levelDown = currentPoints - pointsOnDeath < 0;

        stats.setPoints(levelDown
                ? previousLevelPoints - currentPoints
                : currentPoints - Math.abs(pointsOnDeath));

        stats.decrementLevel(levelDown);
        String previousTitle = previousLevel.title();

        if (levelDown) {
            handleAttributeChange(player, previousLevel, previousLevels, arePerksCumulative);

            player.sendMessage(color("&cYou've died and lost a level!"));
            player.sendMessage(color(previousTitle + " &7is your new title!"));
        }

        playerDataManager.savePlayerData();
    }

}
