package me.xiaojibazhanshi.victorypointsystem.data.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtil {

    private ConfigUtil() {

    }

    public static <T> T nullCheck(FileConfiguration config, String path, Class<T> clazz, T defaultValue) {
        Object obj = config.get(path);

        if (obj == null) {
            Bukkit.getLogger().severe
                    ("Necessary config item at path: " + path + " doesn't exist! Returning default value.");
            return defaultValue;
        }

        try {
            return clazz.cast(obj);
        } catch (ClassCastException e) {
            Bukkit.getLogger().severe("Config item at path: " + path + " cannot be cast to "
                    + clazz.getSimpleName() + ". Returning default value.");
            return defaultValue;
        }
    }

}
