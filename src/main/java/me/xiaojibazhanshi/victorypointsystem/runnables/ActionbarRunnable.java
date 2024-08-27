package me.xiaojibazhanshi.victorypointsystem.runnables;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static me.xiaojibazhanshi.victorypointsystem.util.RunnableHelper.*;

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
        if (Bukkit.getOnlinePlayers().isEmpty()) return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            Stats stats = playerDataManager.getStatsByUUID(player.getUniqueId());
            Level currentLevel = configManager.getLevelById(stats.getLevel());

            showRegularProgressBar(player, stats, currentLevel);
        }
    }

    public void start() {
        runTaskTimerAsynchronously(main, 1, 20);
    }


}
