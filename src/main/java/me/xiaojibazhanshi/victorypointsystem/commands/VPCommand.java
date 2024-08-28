package me.xiaojibazhanshi.victorypointsystem.commands;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.guis.StatsGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;

public class VPCommand implements CommandExecutor {

    private final VPSystem main;
    private final ConfigManager configManager;
    private final PlayerDataManager playerDataManager;

    public VPCommand(VPSystem main) {
        this.main = main;
        this.configManager = main.getConfigManager();
        this.playerDataManager = main.getPlayerDataManager();
    }

    @Override
    @SuppressWarnings("deprecation") // the warning is invalid as I'm not persistently storing the offlinePlayer
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // CONSOLE-SPECIFIC

        if (sender instanceof ConsoleCommandSender console) {
            if (args.length != 2 && !args[0].equalsIgnoreCase("reset")) {
                console.sendMessage(color("&cUsage: /vp reset <nickname>"));

                return true;
            }

            String playerName = args[1];
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);

            if (!offlinePlayer.hasPlayedBefore()) {
                console.sendMessage(color("&cSpecified player has never player before!"));

                return true;
            }

            UUID uuid = offlinePlayer.getUniqueId();

            playerDataManager.resetStats(uuid);
            playerDataManager.savePlayerDataAsync();

            console.sendMessage(color("&aSuccessfully reset &c" + playerName + "&a's stats!"));

            return true;
        }

        // PLAYER-SPECIFIC

        if (!(sender instanceof Player player)) {
            sender.sendMessage(color("&cThis command can only be executed by a player!"));

            return true;
        }

        String statsPermission = configManager.getStatsPermission();
        boolean hasCommandPerms = player.hasPermission(statsPermission);

        if (!(hasCommandPerms)) {
            player.sendMessage(color("&cNo permission!"));

            return true;
        }

        String reloadPermission = configManager.getReloadPermission();
        boolean hasReloadPerms = player.hasPermission(reloadPermission);

        if (args.length < 1) {
            String usage = color("&cUsage: /vp stats" + (hasReloadPerms ? "/reload" : ""));
            player.sendMessage(usage);

            return true;
        }

        String subcommand = args[0].toLowerCase();

        if ()



        StatsGui gui = new StatsGui();
        gui.openGui(main, player);

        return true;
    }
}
