package me.xiaojibazhanshi.victorypointsystem;

import me.xiaojibazhanshi.victorypointsystem.commands.VPCommand;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.listeners.DeathListener;
import me.xiaojibazhanshi.victorypointsystem.listeners.KillListener;
import me.xiaojibazhanshi.victorypointsystem.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class VPSystem extends JavaPlugin {

    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        playerDataManager = new PlayerDataManager(this);

        Bukkit.getPluginManager().registerEvents(new DeathListener(playerDataManager), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(playerDataManager), this);
        Bukkit.getPluginManager().registerEvents(new KillListener(playerDataManager, configManager), this);

        getCommand("victorypoints").setExecutor(new VPCommand());
    }

    @Override
    public void onDisable() {
        playerDataManager.savePlayerData(); // failsafe
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}
