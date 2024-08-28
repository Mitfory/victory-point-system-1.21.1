package me.xiaojibazhanshi.victorypointsystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VPCommandHelper {

    protected VPCommandHelper() {

    }

    public void sendAlert() {
        List<Player> onlinePlayers = new ArrayList<>(List.copyOf(Bukkit.getOnlinePlayers()));

        for (Player player : onlinePlayers) {
            player.sendMessage();

            // to be implemented
        }
    }

}
