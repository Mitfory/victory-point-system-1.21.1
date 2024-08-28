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
        String guiTitle = color("&8Statistics");

        int level = stats.getLevel();
        double kd = stats.getPlayerKD();
        int allKills = stats.getAllKills();
        int passiveKills = stats.getPassiveKills();
        int aggressiveKills = stats.getAggressiveKills();
        int deaths = stats.getDeaths();
        int levelPoints = configManager.getAllLvlUpPointsTilLevel(level, false);
        int allPoints = stats.getPoints() + levelPoints;
        int playerKills = stats.getPlayerKills();

        GuiItem kdButton = getKDButton(main, player, kd);
        GuiItem levelButton = getLevelButton(main, player, level);
        GuiItem allKillsButton = getAllKillsButton(main, player, allKills);
        GuiItem passiveKillsButton = getPassiveKillsButton(main, player, passiveKills);
        GuiItem aggressiveKillsButton = getAggressiveKillsButton(main, player, aggressiveKills);
        GuiItem playerKillsButton = getPlayerKillsButton(main, player, playerKills);
        GuiItem deathsButton = getDeathsButton(main, player, deaths);
        GuiItem pointsButton = getPointsButton(main, player, allPoints);

        String guiName = color(guiTitle);

        Gui gui = Gui.gui()
                .rows(5)
                .title(Component.text(guiName))
                .create();

        gui.setDefaultClickAction(event -> event.setCancelled(true));

        gui.setItem(2, 2, levelButton);
        gui.setItem(2, 4, allKillsButton);
        gui.setItem(2, 6, kdButton);
        gui.setItem(2, 8, playerKillsButton);
        gui.setItem(4, 2, passiveKillsButton);
        gui.setItem(4, 4, aggressiveKillsButton);
        gui.setItem(4, 6, deathsButton);
        gui.setItem(4, 8, pointsButton);

        gui.getFiller().fill(getBasicFiller());
        gui.open(player);
    }

}
