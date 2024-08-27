package me.xiaojibazhanshi.victorypointsystem.commands;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.guis.StatsGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VPCommand implements CommandExecutor {

    private VPSystem main;

    public VPCommand(VPSystem main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

        StatsGui gui = new StatsGui();
        gui.openGui(main, player);

        return true;
    }
}
