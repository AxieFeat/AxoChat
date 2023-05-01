package ru.axiefeat;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        DataManager manager = new DataManager(this);
        manager.setupPlugin();
    }

    @Override
    public void onDisable() {
        DataManager manager = new DataManager(this);
        manager.shutdownPlugin();
    }

    public static Main getInstance() {
        return instance;
    }
}
