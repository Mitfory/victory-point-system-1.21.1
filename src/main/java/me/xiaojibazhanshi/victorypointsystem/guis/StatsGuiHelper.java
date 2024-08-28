package me.xiaojibazhanshi.victorypointsystem.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;
import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.createItem;
import static me.xiaojibazhanshi.victorypointsystem.util.PlayerChatUtil.replaceStatPlaceholders;

public class StatsGuiHelper {

    protected StatsGuiHelper() {

    }

    protected GuiItem getKDButton(VPSystem main, Player player, double kdRatio) {

        DecimalFormat df = new DecimalFormat("0.00");
        String kdr = df.format(kdRatio);

        Material material = Material.NETHER_STAR;
        String name = "&6&lK/D Ratio";
        List<String> lore = new ArrayList<>(List.of(
                "",
                "&7&lYour &a&lK/D Ratio &7&lis &b&l" + kdr,
                "",
                "&7&oClick me to show it off in the chat!"
        ));

        ItemStack item = createItem(material, name, lore);

        GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {
            String targetText = "%my_kd%";
            String finalText = replaceStatPlaceholders(main, targetText, player);

            player.closeInventory();
            player.chat(finalText);
        });

        return guiItem;
    }

    protected GuiItem getPlayerKillsButton(VPSystem main, Player player, int kills) {

        Material material = Material.DIAMOND_SWORD;
        String name = "&6&lPlayer Kills";
        List<String> lore = new ArrayList<>(List.of(
                "",
                "&7&lYour &a&lPlayer Kill &7&lcount is &b&l" + kills,
                "",
                "&7&oClick me to show it off in the chat!"
        ));

        ItemStack item = createItem(material, name, lore);

        GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {
            String targetText = "%my_player_kills%";
            String finalText = replaceStatPlaceholders(main, targetText, player);

            player.closeInventory();
            player.chat(finalText);
        });

        return guiItem;
    }

    protected GuiItem getAllKillsButton(VPSystem main, Player player, int kills) {

        Material material = Material.IRON_SWORD;
        String name = "&6&lAll Kills";
        List<String> lore = new ArrayList<>(List.of(
                "",
                "&7&lYour overall &a&lKill &7&lcount is &b&l" + kills,
                "",
                "&7&oClick me to show it off in the chat!"
        ));

        ItemStack item = createItem(material, name, lore);

        GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {
            String targetText = "%my_kills%";
            String finalText = replaceStatPlaceholders(main, targetText, player);

            player.closeInventory();
            player.chat(finalText);
        });

        return guiItem;
    }

    protected GuiItem getPassiveKillsButton(VPSystem main, Player player, int kills) {

        Material material = Material.FEATHER;
        String name = "&6&lPassive Mob Kills";
        List<String> lore = new ArrayList<>(List.of(
                "",
                "&7&lYour &a&lPassive Mob Kill &7&lcount is &b&l" + kills,
                "",
                "&7&oClick me to show it off in the chat!"
        ));

        ItemStack item = createItem(material, name, lore);

        GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {
            String targetText = "%my_passive_kills%";
            String finalText = replaceStatPlaceholders(main, targetText, player);

            player.closeInventory();
            player.chat(finalText);
        });

        return guiItem;
    }
    protected GuiItem getAggressiveKillsButton(VPSystem main, Player player, int kills) {

        Material material = Material.BLAZE_ROD;
        String name = "&6&lAggressive Mob Kills";
        List<String> lore = new ArrayList<>(List.of(
                "",
                "&7&lYour &a&lAggressive Mob Kill &7&lcount is &b&l" + kills,
                "",
                "&7&oClick me to show it off in the chat!"
        ));

        ItemStack item = createItem(material, name, lore);

        GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {
            String targetText = "%my_aggressive_kills%";
            String finalText = replaceStatPlaceholders(main, targetText, player);

            player.closeInventory();
            player.chat(finalText);
        });

        return guiItem;
    }

    protected GuiItem getDeathsButton(VPSystem main, Player player, int deaths) {

        Material material = Material.SKELETON_SKULL;
        String name = "&6&lDeaths";
        List<String> lore = new ArrayList<>(List.of(
                "",
                "&7&lYour &a&lDeath &7&lcount is &b&l" + deaths,
                "",
                "&7&oClick me to show it off in the chat!"
        ));

        ItemStack item = createItem(material, name, lore);

        GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {
            String targetText = "%my_deaths%";
            String finalText = replaceStatPlaceholders(main, targetText, player);

            player.closeInventory();
            player.chat(finalText);
        });

        return guiItem;
    }

    protected GuiItem getPointsButton(VPSystem main, Player player, int points) {

        Material material = Material.EMERALD;
        String name = "&6&lPoints";
        List<String> lore = new ArrayList<>(List.of(
                "",
                "&7&lYour &a&lAccumulated Point &7&lcount is &b&l" + points,
                "",
                "&7&oClick me to show it off in the chat!"
        ));

        ItemStack item = createItem(material, name, lore);

        GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {
            String targetText = "%my_points%";
            String finalText = replaceStatPlaceholders(main, targetText, player);

            player.closeInventory();
            player.chat(finalText);
        });

        return guiItem;
    }

    protected GuiItem getLevelButton(VPSystem main, Player player, int level) {

        Material material = Material.EXPERIENCE_BOTTLE;
        String name = "&6&lLevel";
        List<String> lore = new ArrayList<>(List.of(
                "",
                "&7&lYour &a&lLevel &7&lis &b&l" + level,
                "",
                "&7&oClick me to show it off in the chat!"
        ));

        ItemStack item = createItem(material, name, lore);

        GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {
            String targetText = "%my_level%";
            String finalText = replaceStatPlaceholders(main, targetText, player);

            player.closeInventory();
            player.chat(finalText);
        });

        return guiItem;
    }

    protected GuiItem getBasicFiller() {
        ItemStack item = createItem(Material.GRAY_STAINED_GLASS_PANE, " ", null);
        GuiItem guiItem = ItemBuilder.from(item).asGuiItem();

        return guiItem;
    }

}
