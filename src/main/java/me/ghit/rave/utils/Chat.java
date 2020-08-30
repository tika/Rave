package me.ghit.rave.utils;

import org.bukkit.ChatColor;

public class Chat {

    // Shorthand method of "ChatColor.translateAlternateColorCodes('&', MESSAGE);"
    public static String toColor(String raw) {
        return ChatColor.translateAlternateColorCodes('&', raw);
    }
}