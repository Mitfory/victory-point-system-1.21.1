package me.xiaojibazhanshi.victorypointsystem.runnables;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.victorypointsystem.util.RunnableHelper.showProgressBar;

public class ActionbarRunnable extends BukkitRunnable {

    private final VPSystem main;
    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;

    public ActionbarRunnable(VPSystem main) {
        this.main = main;
        this.playerDataManager = main.getPlayerDataManager();
        this.configManager = main.getConfigManager();
    }

    @Override
    public void run() {
        List<Player> onlinePlayers = new ArrayList<>(List.copyOf(Bukkit.getOnlinePlayers()));

        if (onlinePlayers.isEmpty()) return;

        for (Player player : onlinePlayers) {
            Stats stats = playerDataManager.getStatsByUUID(player.getUniqueId());
            Level currentLevel = configManager.getLevelById(stats.getLevel());

            showProgressBar(player, stats, currentLevel, configManager);
        }
    }

    public void start() {
        runTaskTimerAsynchronously(main, 1, 20);
    }


}
