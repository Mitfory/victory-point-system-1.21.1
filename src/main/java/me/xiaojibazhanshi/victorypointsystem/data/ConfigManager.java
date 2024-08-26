package me.xiaojibazhanshi.victorypointsystem.data;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final FileConfiguration config;
    private String placeholder;

    public ConfigManager(VPSystem main) {
        main.saveDefaultConfig();
        main.getConfig().options().copyDefaults(true);

        config = main.getConfig();

        initializeVariables();
    }

    private void initializeVariables() {
        placeholder = config.getString("placeholder");
    }

    public void doSomething() {
        System.out.println(placeholder);
    }

}
