package me.xiaojibazhanshi.victorypointsystem.commands.vpcommand;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class VPCommandTabCompleter implements TabCompleter {

    private final ConfigManager configManager;

    public VPCommandTabCompleter(VPSystem main) {
        this.configManager = main.getConfigManager();
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            return handleFirstArgument(sender, args[0]);
        }

        if (args.length == 2
            && args[0].equalsIgnoreCase("reset")
            && sender instanceof ConsoleCommandSender) {

            return handleSecondArgumentForReset(args[1]);
        }

        return suggestions;
    }

    private List<String> handleFirstArgument(@NotNull CommandSender sender, String firstArgument) {
        String statsPermission = configManager.getStatsPermission();
        boolean hasStatsPerms = sender.hasPermission(statsPermission);

        String reloadPermission = configManager.getReloadPermission();
        boolean hasReloadPerms = sender.hasPermission(reloadPermission);

        List<String> suggestions = new ArrayList<>();

        if (sender instanceof ConsoleCommandSender) {
            suggestions.add("reset");
        }

        if (sender instanceof Player) {
            if (hasStatsPerms) {
                suggestions.add("stats");
            }

            if (hasReloadPerms) {
                suggestions.add("reload");
            }
        }

        return StringUtil.copyPartialMatches(firstArgument, suggestions, new ArrayList<>());
    }

    // not done using #copyPartialMatches because it could bomb the main thread
    private List<String> handleSecondArgumentForReset(String secondArgument) {
        List<String> matches = new ArrayList<>();

        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            String name = offlinePlayer.getName();

            if (name != null && name.toLowerCase().startsWith(secondArgument.toLowerCase())) {
                matches.add(name);
            }

            if (matches.size() >= 24) { // 4 console lines max
                break;
            }
        }

        return matches;
    }
}
