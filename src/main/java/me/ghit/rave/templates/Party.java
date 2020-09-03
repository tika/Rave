package me.ghit.rave.templates;

import me.ghit.rave.Rave;
import me.ghit.rave.utils.Chat;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {

    private final Rave plugin = Rave.getInstance();

    private UUID leader;
    private final List<UUID> members = new ArrayList<>();
    private final long createdms;

    public Party(UUID leader, List<UUID> members) {
        this.leader = leader;
        this.members.addAll(members);
        this.createdms = System.currentTimeMillis();
    }

    public UUID getLeader() {
        return leader;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public long getCreatedTime() {
        return createdms;
    }

    public void joinParty(UUID player) {
        members.add(player);
        message("&d" + Bukkit.getOfflinePlayer(player).getName() + " has joined the party.");
    }

    public void leaveParty(UUID player) {
        members.remove(player);

        if (members.size() == 1) {
            message("&bThe party has been disbanded as all the members have left");
            disband();
            return;
        }

        // If the player that leaves is the leader
        if (leader == player) {
            // promote first in members list
            promote(members.get(0));
        }
    }

    public void create() {
        plugin.getParties().add(this);
    }

    public void message(String raw) {
        String colored = Chat.toColor(String.format("&a&lPARTY > &7%s", raw));
        members.forEach(member -> Bukkit.getPlayer(member).sendMessage(colored));
    }

    public void disband() {
        plugin.getParties().remove(this);
    }

    public void promote(UUID player) {
        leader = player;
        message("&a" + Bukkit.getOfflinePlayer(player).getName() + " is now the party leader");
    }
}
