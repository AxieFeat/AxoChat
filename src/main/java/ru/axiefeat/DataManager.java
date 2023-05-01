package ru.axiefeat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import ru.axiefeat.Chat.ChatEvent;
import ru.axiefeat.Commands.MentionsCMD;
import ru.axiefeat.Commands.MentionsTabCompliter;
import ru.axiefeat.Utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DataManager {
    private final Main plugin;

    public DataManager(Main plugin) {
        this.plugin = plugin;
    }

    public void setupPlugin() {
        long startTime = System.currentTimeMillis();
        long time;

        Logger.log("");
        Logger.log("    &aAxoChat &fЗагрузка плагина...");
        Logger.log("    &fВерсия &a" + plugin.getDescription().getVersion() + "&f!");
        Logger.log("    &fВерсия ядра: &a" + Main.getInstance().getServer().getVersion());

        time = System.currentTimeMillis();
        plugin.saveDefaultConfig();

        createData();

        Logger.log("    &fКонфиг файлы загружены! &7(&a" + (System.currentTimeMillis() - time) + "ms&7)");
        refactorConfig();

        time = System.currentTimeMillis();
        registerEvents();
        Logger.log("    &fРегистрация ивентов завершена! &7(&a" + (System.currentTimeMillis() - time) + "ms&7)");

        time = System.currentTimeMillis();
        setupCommands();
        Logger.log("    &fЗагрузка команд завершена! &7(&a" + (System.currentTimeMillis() - time) + "ms&7)");
        Logger.log("    &aГотово! &7(&fВсего &a" + (System.currentTimeMillis() - startTime) + "ms&7)");
        Logger.log("");
    }

    public void shutdownPlugin() {
        plugin.reloadConfig();
        Logger.log("");
        Logger.log("    &aAxoChat &fвыключен!");
        Logger.log("");
    }

    private void registerEvents() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new ChatEvent(), plugin);
        pluginManager.registerEvents(new JoinEvent(), plugin);
    }

    private void setupCommands() {
        Objects.requireNonNull(plugin.getCommand("mentionscolor")).setExecutor(new MentionsCMD());
        Objects.requireNonNull(plugin.getCommand("mentionscolor")).setTabCompleter(new MentionsTabCompliter());
    }

    private void refactorConfig () {
        FileConfiguration config = Main.getInstance().getConfig();
        int i = 0;
        while (i < Objects.requireNonNull(config.getConfigurationSection("Chats")).getKeys(false).size()) {
            ArrayList<String> keys = new ArrayList<>(Objects.requireNonNull(config.getConfigurationSection("Chats")).getKeys(false));
            int Int = 0;
            while (Int < Objects.requireNonNull(config.getConfigurationSection("Chats." + keys.get(i) + ".Groups")).getKeys(false).size()) {
                ArrayList<String> list = new ArrayList<>(Objects.requireNonNull(config.getConfigurationSection("Chats." + keys.get(i) + ".Groups")).getKeys(false));
                if (config.getInt("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Max-length") == -1) {
                    config.set("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Max-length", 2147483647);
                    Main.getInstance().saveConfig();
                }
            }
        }
    }

    private void createData() {
        File file = new File(Main.getInstance().getDataFolder(), "data.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                cfg.createSection("Players");
                cfg.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
