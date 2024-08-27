package me.xiaojibazhanshi.victorypointsystem;

import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.data.config.ConfigManager;
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
