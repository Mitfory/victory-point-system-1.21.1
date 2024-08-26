package me.xiaojibazhanshi.victorypointsystem.data.config;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.*;

import static me.xiaojibazhanshi.victorypointsystem.data.config.ConfigUtil.nullCheck;
import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;

public class ConfigManager {

    private final FileConfiguration config;

    private String statsCmdPermission;
    private String statsGuiTitle;

    private boolean arePerksCumulative;

    private List<Level> availableLevels;

    private Map<EntityType, Integer> pointsPerKillOverrides;

    public ConfigManager(VPSystem main) {
        main.saveDefaultConfig();
        main.getConfig().options().copyDefaults(true);

        config = main.getConfig();

        initializeVariables();
    }

    private void initializeVariables() {
        statsCmdPermission = nullCheck(config, "stats-command.permission", String.class, "vpsystem.stats");
        statsGuiTitle = nullCheck(config, "stats-command.gui-title", String.class, color("&8Statistics"));

        arePerksCumulative = nullCheck(config, "are-perks-cumulative", Boolean.class, true);

        availableLevels = retrieveLevels();

        pointsPerKillOverrides = retrievePointsPerKillOverrides();
    }

    @SuppressWarnings("unchecked")
    private Map<EntityType, Integer> retrievePointsPerKillOverrides() {

        Map<EntityType, Integer> overrides = new HashMap<>();
        List<String> overrideList = (List<String>) nullCheck
                (config, "points-per-kill-list-override", List.class, Collections.emptyList());

        if (overrideList.isEmpty()) return overrides;

        for (String override : overrideList) {
            String[] unmapped = override.split(":");

            String entityType = unmapped[0];
            int points;

            try {
                points = Integer.parseInt(unmapped[1]);
            } catch(NumberFormatException nfe) {
                points = 10; // default value - points for killing passive mobs
            }

            overrides.put(EntityType.valueOf(entityType), points);
        }

        return overrides;
    }

    private List<Level> retrieveLevels() {
        List<Level> levels = new ArrayList<>();

        ConfigurationSection levelSection = nullCheck(config, "levels", ConfigurationSection.class, null);
        if (levelSection == null) return levels;

        for (String path : levelSection.getKeys(true)) {
            int id = Integer.parseInt((path.split("."))[1]); // example: levels.12 -> gets 12
            String title = nullCheck(config, path + ".title", String.class, "");

            int pointsToLvlUp = nullCheck(config, path + ".points.to-level-up", Integer.class, 0);
            int pointsOnDeath = nullCheck(config, path + ".points.on-death", Integer.class, 0);

            double dmgPerk = nullCheck(config, path + ".perks.damage", Double.class, 0.0);
            double hpPerk = nullCheck(config, path + ".perks.health", Double.class, 0.0);

            levels.add(new Level(id, title, pointsToLvlUp, pointsOnDeath, dmgPerk, hpPerk));
        }

        return levels;
    }

    public Level getLevelById(int id) {
        return availableLevels.get(id -1);
    }

    public Boolean getArePerksCumulative() {
        return arePerksCumulative;
    }

    public Map<EntityType, Integer> getPointsPerKillOverrides() {
        return pointsPerKillOverrides;
    }

    public String getStatsCmdPermission() {
        return statsCmdPermission;
    }

    public String getStatsGuiTitle() {
        return statsGuiTitle;
    }

    public List<Level> getAllLevels() {
        return availableLevels;
    }



}
