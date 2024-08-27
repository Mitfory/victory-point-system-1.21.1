package me.xiaojibazhanshi.victorypointsystem.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.xiaojibazhanshi.victorypointsystem.VPSystem;
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
    private final File dataFile;
    private final Gson gson;

    public PlayerDataManager(VPSystem main) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.dataFile = new File(main.getDataFolder(), "playerData.json");

        loadPlayerData();
    }

    public void savePlayerData() {
        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(playerData, writer);

        } catch (IOException e) {
            String stackTrace = Arrays.toString(e.getStackTrace());
            Bukkit.getLogger().severe("Failed to save player data: " + stackTrace);
        }

    }

    public void loadPlayerData() {
        if (!isJsonFileReadable()) {
            Bukkit.getLogger().warning("Player data file not found or unreadable, starting with an empty file.");
            return;
        }

        try (FileReader reader = new FileReader(dataFile)) {
            Type mapType = new TypeToken<Map<UUID, Level>>() {}.getType();
            Map<UUID, Level> loadedData = gson.fromJson(reader, mapType);

            if (loadedData != null) {
                playerData.clear();
                playerData.putAll(loadedData);
            } else {
                Bukkit.getLogger().warning("No data was loaded; the file may be empty or malformed.");
            }

        } catch (IOException e) {
            String stackTrace = Arrays.toString(e.getStackTrace());
            Bukkit.getLogger().severe("Failed to load player data: " + stackTrace);
        }
    }

    private boolean isJsonFileReadable() {
        return dataFile.exists() && dataFile.canRead();
    }

    public Level getLevelByUUID(UUID uuid) {
        return playerData.get(uuid);
    }

    public void setLevel(UUID uuid, Level level) {
        playerData.put(uuid, level);
    }

    public void removePlayer(UUID uuid) {
        playerData.remove(uuid);
    }
}
