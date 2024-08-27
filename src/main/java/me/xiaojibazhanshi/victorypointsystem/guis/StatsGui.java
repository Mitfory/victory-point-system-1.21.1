package me.xiaojibazhanshi.victorypointsystem.guis;

import dev.triumphteam.gui.guis.Gui;
import me.xiaojibazhanshi.victorypointsystem.VPSystem;
import me.xiaojibazhanshi.victorypointsystem.data.ConfigManager;
import me.xiaojibazhanshi.victorypointsystem.data.PlayerDataManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;

public class StatsGui {

    private final ConfigManager configManager;
    private final PlayerDataManager playerDataManager;

    public StatsGui(VPSystem main) {
        this.configManager = main.getConfigManager();
        this.playerDataManager = main.getPlayerDataManager();
    }

    public void openGui(Player player) {
        String guiName = color(configManager.getStatsGuiTitle());

        Gui gui = Gui.gui()
                .rows(3)
                .title(Component.text(guiName))
                .create();

        // further impl
    }

}
