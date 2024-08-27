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

        Material material = Material.DIAMOND;
        String name = "&6&lK/D Ratio";
        List<String> lore = new ArrayList<>(List.of(
                "",
                "&7&lYour &a&lK/D Ratio &7&lis &b&l" + kdr,
                "",
                "&7&oClick me to show it off on the chat!"
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

    protected GuiItem getBasicFiller() {
        ItemStack item = createItem(Material.GRAY_STAINED_GLASS_PANE, "", null);
        GuiItem guiItem = ItemBuilder.from(item).asGuiItem();

        return guiItem;
    }

}
