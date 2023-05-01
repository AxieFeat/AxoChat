package ru.axiefeat.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.axiefeat.Main;
import ru.axiefeat.Utils.Utils;

import java.util.Objects;

public class MentionsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Not-Player"))));
                return true;
            }
            if (!sender.hasPermission("AxoChat.MentionsColor")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Perm-Error"))));
                return true;
            }
            if (!Utils.isMentionsEnabled()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Mentions-Disabled"))));
                return true;
            }
            if (!Utils.isValidColorName(args[0])) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Color-Error"))));
                return true;
            }
            Utils.setMentionColor(args[0], (Player) sender);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Success").replace("%color_name%", Utils.getMentionColor((Player) sender) + args[0]))));
            return true;
        }
        else if (args.length > 1 && args[0].equals("admin") && args.length <= 3) {
            if ((sender instanceof Player)) {
                if (!sender.hasPermission("AxoChat.Admin.MentionsColor")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Perm-Error"))));
                    return true;
                }
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Player-Error"))));
                    return true;
                }
                if (!Utils.isMentionsEnabled()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Mentions-Disabled"))));
                    return true;
                }
                if (!Utils.isValidColorName(args[2])) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Color-Error"))));
                    return true;
                }
                Utils.setMentionColor(args[1], target);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Success-Other").replace("%color_name%", Utils.getMentionColor((Player) sender) + args[1]).replace("%player%", target.getName()))));
                return true;
            } else {
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Player-Error"))));
                    return true;
                }
                if (!Utils.isMentionsEnabled()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Mentions-Disabled"))));
                    return true;
                }
                if (!Utils.isValidColorName(args[2])) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Color-Error"))));
                    return true;
                }
                Utils.setMentionColor(args[1], target);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Success-Other").replace("%color_name%", Utils.getMentionColor((Player) sender) + args[1]).replace("%player%", target.getName()))));
                return true;
            }
        }
        else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Messages.Not-Found"))));
            return true;
        }
    }
}
