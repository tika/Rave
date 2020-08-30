package me.ghit.rave.templates;

import me.ghit.rave.Rave;
import me.ghit.rave.utils.Chat;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {

    private final Rave plugin = Rave.getInstance();

    private final UUID leader;
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
}
