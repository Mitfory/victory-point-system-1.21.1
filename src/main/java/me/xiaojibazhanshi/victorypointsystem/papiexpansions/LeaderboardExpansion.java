package me.xiaojibazhanshi.victorypointsystem.papiexpansions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class LeaderboardExpansion extends PlaceholderExpansion {

    private final PlayerDataManager playerDataManager;

    public LeaderboardExpansion(VPSystem main) {
        this.playerDataManager = main.getPlayerDataManager();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "vp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "XiaoJibaZhanshi";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        String[] parts = params.split("_", 4);

        if (parts.length < 3) {
            return null;
        }

        int position;
        String sortMethod = parts[2];
        String requestType = parts.length > 3 ? parts[3] : "amount";

        try {
            position = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return null;
        }

        if (position <= 0) {
            return null;
        }

        List<Map.Entry<UUID, Stats>> sortedEntries = getSortedPlayerEntries(sortMethod);

        if (position > sortedEntries.size()) {
            return "N/A";
        }

        Map.Entry<UUID, Stats> entry = sortedEntries.get(position - 1);
        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(entry.getKey());

        if ("name".equalsIgnoreCase(requestType)) {
            return targetPlayer.getName() != null ? targetPlayer.getName() : "NONE";

        } else if ("amount".equalsIgnoreCase(requestType)) {
            Stats stats = entry.getValue();

            return switch (sortMethod.toLowerCase()) {
                case "level" -> String.valueOf(stats.getLevel());
                case "points" -> String.valueOf(stats.getPoints());
                case "all-kills" -> String.valueOf(stats.getAllKills());
                case "player-kills" -> String.valueOf(stats.getPlayerKills());
                case "deaths" -> String.valueOf(stats.getDeaths());
                case "kd" -> String.format("%.2f", stats.getPlayerKD());
                case "passive-mob-kills" -> String.valueOf(stats.getPassiveKills());
                case "aggressive-mob-kills" -> String.valueOf(stats.getAggressiveKills());
                default -> "N/A";
            };
        }

        return "N/A";
    }

    private List<Map.Entry<UUID, Stats>> getSortedPlayerEntries(String sortMethod) {
        Map<UUID, Stats> playerData = playerDataManager.getPlayerData();

        List<Map.Entry<UUID, Stats>> sortedEntries = new ArrayList<>(playerData.entrySet());

        switch (sortMethod.toLowerCase()) {
            case "level" -> sortedEntries.sort(Comparator.comparingInt(entry -> entry.getValue().getLevel()));
            case "points" -> sortedEntries.sort(Comparator.comparingInt(entry -> entry.getValue().getPoints()));
            case "all-kills" -> sortedEntries.sort(Comparator.comparingInt(entry -> entry.getValue().getAllKills()));
            case "player-kills" -> sortedEntries.sort(Comparator.comparingInt(entry -> entry.getValue().getPlayerKills()));
            case "deaths" -> sortedEntries.sort(Comparator.comparingInt(entry -> entry.getValue().getDeaths()));
            case "kd" -> sortedEntries.sort(Comparator.comparingDouble(entry -> entry.getValue().getPlayerKD()));
            case "passive-mob-kills" -> sortedEntries.sort(Comparator.comparingInt(entry -> entry.getValue().getPassiveKills()));
            case "aggressive-mob-kills" -> sortedEntries.sort(Comparator.comparingInt(entry -> entry.getValue().getAggressiveKills()));
            default -> {} // ignore sorting if none of the cases match I guess
        }

        Collections.reverse(sortedEntries);

        return sortedEntries;
    }
}
