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
        message(String.format("&b&o%s &3has joined the party", Bukkit.getOfflinePlayer(player).getName()), true);
    }

    public void leaveParty(UUID player) {
        members.remove(player);

        if (members.size() == 1) {
            message("&7&m-------------------------------------------\n" +
                    "&cThe party has been disbanded as all the members have left!\n" +
                    "&7&m-------------------------------------------", false);
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

    public void message(String raw, boolean prefix) {
        String colored = Chat.toColor(raw);

        if (prefix) { colored = Chat.toColor(String.format("&3&lParty &b> &b%s", raw)); }

        String finalColored = colored; // Java no like non-final variables
        members.stream().filter(member -> Bukkit.getPlayer(member) != null).forEach(member -> Bukkit.getPlayer(member).sendMessage(finalColored));
    }

    public void disband() {
        plugin.getParties().remove(this);
    }

    public void promote(UUID player) {
        leader = player;
        message(String.format(
                "&7&m-------------------------------------------\n" +
                "&b%s &3is now the party leader" +
                "\n&7&m-------------------------------------------",
                Bukkit.getOfflinePlayer(player).getName()), false);
    }
}
