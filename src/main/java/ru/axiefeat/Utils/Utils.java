package ru.axiefeat.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.axiefeat.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String getMentionColor (Player p) {
        File file = new File(Main.getInstance().getDataFolder(), "data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        String colorName = cfg.getString("Players." + p.getName());

        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Mentions.Colors." + colorName)));
    }

    public static void setMentionColor (String name, Player p) {
        File file = new File(Main.getInstance().getDataFolder(), "data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if (cfg.getString("Players." + p.getName()).equals(name)) return;
        cfg.set("Players." + p.getName(), name);

        try {
            cfg.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String setMentions (String msg) {
        if (Main.getInstance().getConfig().getBoolean("Mentions.Enabled")) {
            return msg;
        }

        Pattern pattern = Pattern.compile(Objects.requireNonNull(Main.getInstance().getConfig().getString("Mentions.Pattern")));
        Matcher matcher = pattern.matcher(msg);

        while (matcher.find()) {
            String targetName = matcher.group(1);
            Player target = Bukkit.getPlayerExact(targetName);

            if (target != null) {
                runActions(target);

                msg = msg.replace(target.getName(), getMentionColor(target) + target.getName() + ChatColor.RESET);
            }

        }
        return msg;
    }

    private static void runActions(Player p) {
        ArrayList<String> list = (ArrayList<String>) new ArrayList<>(Main.getInstance().getConfig().getList("Mentions.Actions"));

        int i = 0;
        while (i < list.size()) {
            if (list.get(i).startsWith("[MESSAGE] ")) {
                String msg = ChatColor.translateAlternateColorCodes('&', list.get(i).replace("[MESSAGE] ", ""));
                p.sendMessage(msg);
            } else if (list.get(i).startsWith("[SOUND] ")) {
                Sound sound = Sound.valueOf(list.get(i).replace("[SOUND] ", ""));
                p.playSound(p.getLocation(), sound, 500.0f, 1.0f);
            } else if (list.get(i).startsWith("[TITLE] ")) {
                if (list.get(i).contains("\n")) {
                    String msg = ChatColor.translateAlternateColorCodes('&', list.get(i).replace("[TITLE] ", ""));
                    String[] split = msg.split("\n");
                    String title = split[0];
                    String subtitle = split[1];

                    p.sendTitle(title, subtitle);
                } else {
                    String msg = ChatColor.translateAlternateColorCodes('&', list.get(i).replace("[TITLE] ", ""));
                    p.sendTitle(msg, "");
                }
            }
            i++;
        }
    }

    public static boolean isValidColorName (String color) {
        if (color == null) return false;

        ArrayList<String> list = new ArrayList<>(Main.getInstance().getConfig().getConfigurationSection("Mentions.Colors").getKeys(false));

        return list.contains(color);
    }

    public static boolean isMentionsEnabled () {
        return Main.getInstance().getConfig().getBoolean("Mentions.Enabled");
    }
}
