package me.ghit.rave.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatUtils {
    private ChatUtils() {}

    private static final List<UUID> partyChatEnabled = new ArrayList<>(); // List of players that have chat enabled

    public static String getChatStatus(UUID player) {
        return partyChatEnabled.contains(player) ? Chat.toColor("&aenabled") : Chat.toColor("&cdisabled") ;
    }

    public static boolean hasChatEnabled(UUID player) {
        return partyChatEnabled.contains(player);
    }

    public static void toggle(UUID player) {
        if (partyChatEnabled.contains(player)) {
            partyChatEnabled.remove(player);
        } else {
            partyChatEnabled.add(player);
        }
    }

    public static void setPartyChatEnabled(UUID player, boolean enabled) {
        if (enabled) {
            partyChatEnabled.add(player);
        } else {
            partyChatEnabled.remove(player);
        }
    }
}
