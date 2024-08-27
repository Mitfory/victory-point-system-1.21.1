package me.xiaojibazhanshi.victorypointsystem;

import me.xiaojibazhanshi.victorypointsystem.commands.VPCommand;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.data.config.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.listeners.DeathListener;
import me.xiaojibazhanshi.victorypointsystem.listeners.KillListener;
import me.xiaojibazhanshi.victorypointsystem.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class VPSystem extends JavaPlugin {

    private VPSystem instance;
    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        instance = this;

        configManager = new ConfigManager(instance);
        playerDataManager = new PlayerDataManager(instance);

        Bukkit.getPluginManager().registerEvents(new DeathListener(playerDataManager), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(playerDataManager), instance);
        Bukkit.getPluginManager().registerEvents(new KillListener(playerDataManager, configManager), instance);

        getCommand("victorypoints").setExecutor(new VPCommand());
    }

    @Override
    public void onDisable() {
        playerDataManager.savePlayerData(); // failsafe
    }

    public VPSystem getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}
