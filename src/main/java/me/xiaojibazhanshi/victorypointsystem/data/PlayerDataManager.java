package me.xiaojibazhanshi.victorypointsystem.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final Map<UUID, Stats> playerData = new HashMap<>();
    private final VPSystem main;
    private final File dataFile;
    private final Gson gson;

    public PlayerDataManager(VPSystem main) {
        this.main = main;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.dataFile = new File(main.getDataFolder(), "playerdata.json");

        loadPlayerDataAsync();
    }

    public void savePlayerDataAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            try (FileWriter writer = new FileWriter(dataFile)) {
                gson.toJson(playerData, writer);

            } catch (IOException e) {
                String stackTrace = Arrays.toString(e.getStackTrace());
                Bukkit.getLogger().severe("Failed to save player data: " + stackTrace);
            }
        });
    }

    public void loadPlayerDataAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            if (!isJsonFileReadable()) {
                Bukkit.getLogger().warning("Player data file not found or unreadable, starting with an empty file.");
                return;
            }

            try (FileReader reader = new FileReader(dataFile)) {
                Type mapType = new TypeToken<Map<UUID, Stats>>() {
                }.getType();
                Map<UUID, Stats> loadedData = gson.fromJson(reader, mapType);

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
        });
    }

    private boolean isJsonFileReadable() {
        return dataFile.exists() && dataFile.canRead();
    }

    public Stats getStatsByUUID(UUID uuid) {
        return playerData.get(uuid);
    }

    public void resetStats(UUID uuid) {
        playerData.remove(uuid);
        playerData.put(uuid, new Stats(1, 0, 0, 0, 0, 0));
    }

    public void addPlayerIfAbsent(UUID uuid) {
        playerData.computeIfAbsent
                (uuid, id -> new Stats(1, 0, 0, 0, 0, 0));
    }
}
