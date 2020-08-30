package me.ghit.rave.commands.subcommands;

import me.ghit.rave.commands.SubCommand;
import me.ghit.rave.templates.Party;
import me.ghit.rave.utils.Chat;
import me.ghit.rave.utils.PartyUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class ListCMD extends SubCommand {
    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "lists the players in your party";
    }

    @Override
    public String getSyntax() {
        return "/rave list";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("rave.list") || !player.hasPermission("rave.*")) {
            player.sendMessage(Chat.toColor("&cYou do not have permission to use this command!"));
            return;
        }

        Party party = PartyUtils.findParty(player.getUniqueId());

        if (party == null) {
            player.sendMessage(Chat.toColor("&cYou must be in a party to use this command!"));
            return;
        }

        player.sendMessage(Chat.toColor("&7-------------"));
        player.sendMessage(Chat.toColor(String.format("&aLeader: %s", Bukkit.getOfflinePlayer(party.getLeader()).getName())));
        player.sendMessage(Chat.toColor("&7-- &dMembers &7--"));

        for (UUID member : party.getMembers()) {
            if (party.getLeader() != member)
                player.sendMessage(Chat.toColor(String.format("&d%s", Bukkit.getOfflinePlayer(member).getName())));
        }

        player.sendMessage(Chat.toColor("&7-------------"));
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
