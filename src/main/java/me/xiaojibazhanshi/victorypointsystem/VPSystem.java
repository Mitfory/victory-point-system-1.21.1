package me.xiaojibazhanshi.victorypointsystem;

import lombok.Getter;
import me.xiaojibazhanshi.victorypointsystem.data.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class VPSystem extends JavaPlugin {

    private VPSystem instance;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;

        configManager = new ConfigManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
