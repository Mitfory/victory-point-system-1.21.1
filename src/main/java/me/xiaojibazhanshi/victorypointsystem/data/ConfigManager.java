package me.xiaojibazhanshi.victorypointsystem.data;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.*;

import static me.xiaojibazhanshi.victorypointsystem.util.ConfigUtil.nullCheck;

public class ConfigManager {

    private FileConfiguration config;

    private boolean arePerksCumulative;

    private String statsPermission;
    private String reloadPermission;

    private List<Level> availableLevels;

    private Map<EntityType, Integer> pointsPerKillOverrides;

    public ConfigManager(VPSystem main) {
        main.saveDefaultConfig();
        main.getConfig().options().copyDefaults(true);

        config = main.getConfig();

        initializeVariables();
    }

    public void reload(VPSystem main) {
        main.reloadConfig();

        config = main.getConfig();

        initializeVariables();
    }

    private void initializeVariables() {
        arePerksCumulative = nullCheck(config, "are-perks-cumulative", Boolean.class, true);

        statsPermission = nullCheck
                (config, "vp-command.stats-permission", String.class, "vpsystem.stats");

        reloadPermission = nullCheck
                (config, "vp-command.reload-permission", String.class, "vpsystem.reload");

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
            } catch (NumberFormatException nfe) {
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
        String path = "levels.";

        for (String level : levelSection.getKeys(false)) {
            String pathCopy = path + level;

            int id = Integer.parseInt(level); // example: levels.12 -> gets 12
            String title = nullCheck(config, pathCopy + ".title", String.class, "");

            int pointsToLvlUp = nullCheck(config, pathCopy + ".points.to-level-up", Integer.class, 0);
            int pointsOnDeath = nullCheck(config, pathCopy + ".points.on-death", Integer.class, 0);

            double dmgPerk = nullCheck(config, pathCopy + ".perks.damage", Double.class, 0.0);
            double hpPerk = nullCheck(config, pathCopy + ".perks.health", Double.class, 0.0);

            levels.add(new Level(id, title, pointsToLvlUp, pointsOnDeath, dmgPerk, hpPerk));
        }

        return levels;
    }

    public int getAllLvlUpPointsTilLevel(int level, boolean includeLastLevelsPoints) {
        int points = 0;

        for (Level lvl : availableLevels) {
            if (lvl.id() -(includeLastLevelsPoints ? 1 : 0) == level) break;

            int lvlPoints = lvl.pointsToLevelUp();
            points += lvlPoints;
        }

        return points;
    }

    public Level getLevelById(int id) { return availableLevels.get(id - 1); }

    public Boolean getArePerksCumulative() { return arePerksCumulative; }

    public Map<EntityType, Integer> getPointsPerKillOverrides() { return pointsPerKillOverrides; }

    public String getStatsPermission() { return statsPermission; }

    public List<Level> getAllLevels() { return availableLevels; }

    public String getReloadPermission() { return reloadPermission; }

}
