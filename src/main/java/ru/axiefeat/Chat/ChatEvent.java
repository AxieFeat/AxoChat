package ru.axiefeat.Chat;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.axiefeat.Main;
import ru.axiefeat.Utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class ChatEvent implements Listener {
    @EventHandler
    public void AsyncChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();

        int i = 0;
        while (i < Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("Chats")).getKeys(false).size()) {
            ArrayList<String> keys = new ArrayList<>(Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("Chats")).getKeys(false));
            if (msg.startsWith(Objects.requireNonNull(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Start-char")))) {
                if (!p.hasPermission(Objects.requireNonNull(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Perm")))) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Perm-error"))));
                    e.setCancelled(true);
                    return;
                }

                int Int = 0;
                while (Int < Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("Chats." + keys.get(i) + ".Groups")).getKeys(false).size()) {
                    ArrayList<String> list = new ArrayList<>(Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("Chats." + keys.get(i) + ".Groups")).getKeys(false));

                    if (p.hasPermission(list.get(Int))) {
                        if (msg.length() > Main.getInstance().getConfig().getInt("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Max-length")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Max-length-error"))));
                            e.setCancelled(true);
                            return;
                        }

                        if (Main.getInstance().getConfig().getInt("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Range") == 0) {
                            if (!Main.getInstance().getConfig().getBoolean("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Json")) {
                                if (Objects.equals(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Start-char"), "")) {
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        if (player.hasPermission(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Perm-see"))) {
                                            String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Format")).replace("%message%", Utils.setMentions(msg))));
                                            player.sendMessage(message);
                                            e.setCancelled(true);
                                            break;
                                        }
                                    }
                                } else {
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        if (player.hasPermission(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Perm-see"))) {
                                            String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Format")).replace("%message%", Utils.setMentions(msg.replaceFirst(".", "")))));
                                            player.sendMessage(message);
                                            e.setCancelled(true);
                                            break;
                                        }
                                    }
                                }
                            } else {
                                if (Objects.equals(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Start-char"), "")) {
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        if (player.hasPermission(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Perm-see"))) {
                                            String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Format")).replace("%message%", Utils.setMentions(msg))));
                                            player.spigot().sendMessage(ChatMessageType.CHAT, TextComponent.fromLegacyText(message));
                                            e.setCancelled(true);
                                            break;
                                        }
                                    }
                                } else {
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        if (player.hasPermission(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Perm-see"))) {
                                            String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Format")).replace("%message%", Utils.setMentions(msg.replaceFirst(".", "")))));
                                            player.spigot().sendMessage(ChatMessageType.CHAT, TextComponent.fromLegacyText(message));
                                            e.setCancelled(true);
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {
                            if (!Main.getInstance().getConfig().getBoolean("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Json")) {
                                if (Objects.equals(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Start-char"), "")) {
                                    e.setCancelled(true);
                                    for (Player player : p.getWorld().getPlayers()) {
                                        if (player != p && player.getLocation().distance(p.getLocation()) <= Main.getInstance().getConfig().getInt("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Range")) {
                                            if (player.hasPermission(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Perm-see"))) {
                                                String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Format")).replace("%message%", Utils.setMentions(msg))));
                                                player.sendMessage(message);
                                            }
                                        }
                                    }
                                    break;
                                } else {
                                    e.setCancelled(true);
                                    for (Player player : p.getWorld().getPlayers()) {
                                        if (player != p && player.getLocation().distance(p.getLocation()) <= Main.getInstance().getConfig().getInt("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Range")) {
                                            if (player.hasPermission(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Perm-see"))) {
                                                String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Format")).replace("%message%", Utils.setMentions(msg.replaceFirst(".", "")))));
                                                player.sendMessage(message);
                                            }
                                        }
                                    }
                                    break;
                                }
                            } else {
                                if (Objects.equals(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Start-char"), "")) {
                                    e.setCancelled(true);
                                    for (Player player : p.getWorld().getPlayers()) {
                                        if (player != p && player.getLocation().distance(p.getLocation()) <= Main.getInstance().getConfig().getInt("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Range")) {
                                            if (player.hasPermission(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Perm-see"))) {
                                                String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Format")).replace("%message%", Utils.setMentions(msg))));
                                                player.spigot().sendMessage(ChatMessageType.CHAT, TextComponent.fromLegacyText(message));
                                            }
                                        }
                                    }
                                    break;
                                } else {
                                    e.setCancelled(true);
                                    for (Player player : p.getWorld().getPlayers()) {
                                        if (player != p && player.getLocation().distance(p.getLocation()) <= Main.getInstance().getConfig().getInt("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Range")) {
                                            if (player.hasPermission(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Perm-see"))) {
                                                String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(Main.getInstance().getConfig().getString("Chats." + keys.get(i) + ".Groups." + list.get(Int) + ".Format")).replace("%message%", Utils.setMentions(msg.replaceFirst(".", "")))));
                                                player.spigot().sendMessage(ChatMessageType.CHAT, TextComponent.fromLegacyText(message));
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    Int++;
                }
                break;
            }
            i++;
        }
    }
}
