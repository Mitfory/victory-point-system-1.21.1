package me.xiaojibazhanshi.victorypointsystem.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class PlayerDataManager {

    private final Map<UUID, Level> playerData = new HashMap<>();
    private static final String FILE_PATH = "playerData.json";
    private final Gson gson;

    public PlayerDataManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void savePlayerData(Map<UUID, Level> playerData) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(playerData, writer);
        } catch (IOException e) {
            String stackTrace = Arrays.toString(e.getStackTrace());
            Bukkit.getLogger().severe(stackTrace);
        }
    }

    public Map<UUID, Level> loadPlayerData() {
        if (!isJsonFileReadable()) {
            return null;
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {

            Type mapType = new TypeToken<Map<UUID, Level>>() {}.getType();

            return gson.fromJson(reader, mapType);
        } catch (IOException e) {
            String stackTrace = Arrays.toString(e.getStackTrace());
            Bukkit.getLogger().severe(stackTrace);

            return null;
        }
    }

    private boolean isJsonFileReadable() {
        File file = new File(FILE_PATH);
        return file.exists() && file.canRead();
    }

    public Level getLevelByUUID(UUID uuid) {
        return playerData.get(uuid);
    }

}
