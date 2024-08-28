package me.xiaojibazhanshi.victorypointsystem;

import me.xiaojibazhanshi.victorypointsystem.commands.vpcommand.VPCommand;
import me.xiaojibazhanshi.victorypointsystem.commands.vpcommand.VPCommandTabCompleter;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.listeners.DeathListener;
import me.xiaojibazhanshi.victorypointsystem.listeners.KillListener;
import me.xiaojibazhanshi.victorypointsystem.listeners.PlayerChatListener;
import me.xiaojibazhanshi.victorypointsystem.listeners.PlayerJoinListener;
import me.xiaojibazhanshi.victorypointsystem.runnables.ActionbarRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class VPSystem extends JavaPlugin {

    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;

    @Override
    @SuppressWarnings("DataFlowIssue") // just so the yellow marking on setExecutor & setTabCompleter disappears
    public void onEnable() {
        configManager = new ConfigManager(this);
        playerDataManager = new PlayerDataManager(this);

        Bukkit.getPluginManager().registerEvents(new KillListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        getCommand("victorypoints").setExecutor(new VPCommand(this));
        getCommand("victorypoints").setTabCompleter(new VPCommandTabCompleter(this));

        ActionbarRunnable runnable = new ActionbarRunnable(this);
        runnable.start();
    }

    @Override
    public void onDisable() { playerDataManager.savePlayerDataAsync(); } // failsafe

    public ConfigManager getConfigManager() { return configManager; }

    public PlayerDataManager getPlayerDataManager() { return playerDataManager; }
}
