package me.xiaojibazhanshi.victorypointsystem.runnables;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionbarRunnable extends BukkitRunnable {

    private final Player player;

    public ActionbarRunnable(Player player) { // add more data if needed
        this.player = player;
    }

    @Override
    public void run() {

    }

    private void sendActionbarUpdate() {

    }

}
