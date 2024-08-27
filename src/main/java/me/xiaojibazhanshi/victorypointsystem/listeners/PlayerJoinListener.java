package me.xiaojibazhanshi.victorypointsystem.listeners;

import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final PlayerDataManager playerDataManager;

    public PlayerJoinListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        playerDataManager.addPlayerIfAbsent(uuid);
        playerDataManager.savePlayerData();
    }

}
