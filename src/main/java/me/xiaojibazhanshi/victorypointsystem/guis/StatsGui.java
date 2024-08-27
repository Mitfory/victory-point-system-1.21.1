package me.xiaojibazhanshi.victorypointsystem.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import me.xiaojibazhanshi.victorypointsystem.objects.Stats;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;

public class StatsGui extends StatsGuiHelper {

    public void openGui(VPSystem main, Player player) {
        ConfigManager configManager = main.getConfigManager();
        PlayerDataManager playerDataManager = main.getPlayerDataManager();

        Stats stats = playerDataManager.getStatsByUUID(player.getUniqueId());
        String guiTitle = configManager.getStatsGuiTitle();

        double kd = stats.getPlayerKD();
        int allKills = stats.getAllKills();
        int passiveKills = stats.getPassiveKills();
        int aggressiveKills = stats.getAggressiveKills();
        int deaths = stats.getDeaths();
        int points = stats.getPoints();

        GuiItem kdButton = getKDButton(player, kd);

        String guiName = color(guiTitle);

        Gui gui = Gui.gui()
                .rows(3)
                .title(Component.text(guiName))
                .create();

        gui.setDefaultClickAction(event -> event.setCancelled(true));



        gui.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem());
        gui.open(player);
    }

}
