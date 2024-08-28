package me.xiaojibazhanshi.victorypointsystem.listeners;

import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static me.xiaojibazhanshi.victorypointsystem.util.PlayerChatUtil.replaceStatPlaceholders;

public class PlayerChatListener implements Listener {

    private final VPSystem main;

    public PlayerChatListener(VPSystem main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();

        String replacedMessage = replaceStatPlaceholders(main, message, player);

        event.setMessage(replacedMessage);
    }

}
