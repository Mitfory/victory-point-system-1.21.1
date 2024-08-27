package me.xiaojibazhanshi.victorypointsystem.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GeneralUtil {

    private GeneralUtil() {

    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static ItemStack createItem(Material material, String name, @Nullable List<String> lore) {
        List<String> coloredLore = new ArrayList<>();

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName(color(name));

        if (lore != null) {
            for (String line : lore) {
                coloredLore.add(color(line));
            }
        }

        meta.setLore(coloredLore);
        item.setItemMeta(meta);

        return item;
    }

}
