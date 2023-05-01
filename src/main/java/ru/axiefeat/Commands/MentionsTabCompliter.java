package ru.axiefeat.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.axiefeat.Main;

import java.util.ArrayList;
import java.util.List;

public class MentionsTabCompliter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            ArrayList<String> list = new ArrayList<>();
            if (sender.hasPermission("AxoChat.MentionsColor")) {
                return Main.getInstance().getConfig().getConfigurationSection("Mentions.Colors").getKeys(false).stream().toList();
            }
            if (sender.hasPermission("AxoChat.Admin.MentionsColor")) {
                list.add("admin");
            }
            return list;
        } else if (args.length == 2) {
            ArrayList<String> list = new ArrayList<>();
            if (sender.hasPermission("AxoChat.Admin.MentionsColor") && args[0].equals("admin")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    list.add(p.getName());
                };
            }
            return list;
        } else if (args.length == 3) {
            ArrayList<String> list = new ArrayList<>();
            if (sender.hasPermission("AxoChat.Admin.MentionsColor") && args[0].equals("admin")) {
                return Main.getInstance().getConfig().getConfigurationSection("Mentions.Colors").getKeys(false).stream().toList();
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }
}
