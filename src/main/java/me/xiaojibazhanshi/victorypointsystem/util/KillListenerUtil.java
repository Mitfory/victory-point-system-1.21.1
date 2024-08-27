package me.xiaojibazhanshi.victorypointsystem.util;

import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Level;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;

import java.util.List;

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

    @SuppressWarnings("DataFlowIssue")
    public static void handleAttributeChange(Player player, Level nextLevel, List<Level> previous, boolean cumulative) {
        AttributeInstance damageAttribute = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        AttributeInstance healthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        double damagePerkStack = 0.0;
        double healthPerkStack = 0.0;

        for (Level level : previous) {
            damagePerkStack += level.dmgPerk();
            healthPerkStack += level.hpPerk();
        }

        double baseDamage = damageAttribute.getDefaultValue();
        double baseHealth = healthAttribute.getDefaultValue();

        double cumulatedDamage = baseDamage + damagePerkStack;
        double cumulatedHealth = baseHealth + healthPerkStack;

        double damagePerk = nextLevel.dmgPerk();
        double healthPerk = nextLevel.hpPerk();

        damageAttribute.setBaseValue((cumulative ? cumulatedDamage : baseDamage) + damagePerk);
        healthAttribute.setBaseValue((cumulative ? cumulatedHealth : baseHealth) + healthPerk);
    }

    public static List<Level> getPreviousLevels(ConfigManager configManager, Level previousTo) {
        return configManager.getAllLevels()
                .stream()
                .filter(level -> level.id() < previousTo.id())
                .toList();
    }

}
