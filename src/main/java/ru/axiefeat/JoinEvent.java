package ru.axiefeat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class JoinEvent implements Listener {
    @EventHandler
    public void onJoinEvent (PlayerJoinEvent e) {
        File file = new File(Main.getInstance().getDataFolder(), "data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if (cfg.getConfigurationSection("Players").getKeys(false).contains(e.getPlayer().getName())) return;
        cfg.createSection("Players." + e.getPlayer().getName());
        cfg.set("Players." + e.getPlayer().getName(), "Default");
        try {
            cfg.save(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
