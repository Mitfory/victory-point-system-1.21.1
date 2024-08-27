package me.xiaojibazhanshi.victorypointsystem.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static me.xiaojibazhanshi.victorypointsystem.util.KillListenerUtil.getDefaultPointsForKilling;

public class KillListener implements Listener {

    @EventHandler
    public void onPlayerKill(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        EntityType entityType = entity.getType();
        int points = getDefaultPointsForKilling(entity);


    }

}
