package me.xiaojibazhanshi.victorypointsystem.data.config;

import lombok.Getter;
import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static me.xiaojibazhanshi.victorypointsystem.data.config.ConfigUtil.nullCheck;
import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;

public class ConfigManager {

    private final FileConfiguration config;

    private List<Level> availableLevels;

    private String statsCmdPermission;
    private String statsGuiTitle;

    private Map<EntityType, Integer> pointsPerKillOverrides;

    public ConfigManager(VPSystem main) {
        main.saveDefaultConfig();
        main.getConfig().options().copyDefaults(true);

        config = main.getConfig();

        initializeVariables();
    }

    private void initializeVariables() {
        availableLevels = retrieveLevels();

        statsCmdPermission = nullCheck(config, "stats-command.permission", String.class, "vpsystem.stats");
        statsGuiTitle = nullCheck(config, "stats-command.gui-title", String.class, color("&8Statistics"));



    }

    private List<Level> retrieveLevels() {
        List<Level> levels = new ArrayList<>();

        ConfigurationSection levelSection = nullCheck(config, "levels", ConfigurationSection.class, null);
        if (levelSection == null) return levels;


        for (String path : levelSection.getKeys(true)) {
            String title = nullCheck(config, path + ".title", String.class, "");

            int pointsToLvlUp = nullCheck(config, path + ".points.to-level-up", int.class, 0);
            int pointsOnDeath = nullCheck(config, path + ".points.on-death", int.class, 0);

            double dmgPerk = nullCheck(config, path + ".perks.damage", double.class, 0.0);
            double hpPerk = nullCheck(config, path + ".perks.health", double.class, 0.0);

            levels.add(new Level(title, pointsToLvlUp, pointsOnDeath, dmgPerk, hpPerk));
        }

        return levels;
    }

    public List<Level> getAllLevels() {
        return availableLevels;
    }



}
