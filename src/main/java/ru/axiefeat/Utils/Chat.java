package ru.axiefeat.Utils;

import org.bukkit.ChatColor;

public class Chat {
    public static String prefix = "&fAxoCurrency";

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
