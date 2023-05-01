package ru.axiefeat.Utils;

import org.bukkit.Bukkit;

public class Logger {
    public static void info(String msg) {
        if (msg == null) msg = "null";
        log(Chat.prefix + " &a[I] &f" + msg);
    }
    public static void warn(String msg) {
        if (msg == null) msg = "null";
        log(Chat.prefix + " &e[W] &f" + msg);
    }
    public static void error(String msg) {
        if (msg == null) msg = "null";
        log(Chat.prefix + " &c[E] &f" + msg);
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().getServer().getConsoleSender().sendMessage(Chat.color(message));
    }
}
