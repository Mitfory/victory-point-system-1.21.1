package me.xiaojibazhanshi.victorypointsystem.util;

import org.bukkit.entity.*;

public class KillListenerUtil {

    private KillListenerUtil() {

    }

    public static boolean isPassive(Entity entity) {
        return (!(entity instanceof Enemy));
    }

    public static boolean isAggressiveAndNonPlayer(Entity entity) {
        return (entity instanceof Enemy) && !(entity instanceof Player);
    }

    public static int getDefaultPointsForKilling(Entity entity) {
        return (entity instanceof Warden) ? 500
                : (entity instanceof Wither || entity instanceof EnderDragon) ? 400
                : (!isPassive(entity)) ? 20
                : 10;
    }

}
