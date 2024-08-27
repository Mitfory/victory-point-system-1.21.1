package me.xiaojibazhanshi.victorypointsystem.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.createItem;

public class StatsGuiHelper {

    protected StatsGuiHelper() {

    }

    protected GuiItem getKDButton(Player player, double kdRatio) {
        DecimalFormat df = new DecimalFormat("0.00");
        String kdr = df.format(kdRatio);

        Material material = Material.DIAMOND;
        String name = "&aK/D Ratio";
        List<String> lore = new ArrayList<>(List.of(
                "",
                "&7&bYour K/D Ratio is &b&l" + kdr,
                "",
                "&7&oClick me to show it off on the chat!"
        ));

        ItemStack item = createItem(material, name, lore);

        GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {

        });

        return guiItem;
    }

}
