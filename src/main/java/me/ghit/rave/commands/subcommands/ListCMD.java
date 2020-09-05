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

        long activeTime = ((System.currentTimeMillis() - party.getCreatedTime()) / 1000) / 60;

        player.sendMessage(Chat.toColor("&7&m-------------------------------------------"));

        player.sendMessage(Chat.toColor("&3&oParty has been active for: &b&l" + activeTime + " minute(s)"));

        player.sendMessage(" ");

        player.sendMessage(Chat.toColor(String.format("&6&lLEADER&8: &b%s", Bukkit.getOfflinePlayer(party.getLeader()).getName())));
        player.sendMessage(" ");

        player.sendMessage(Chat.toColor("&3&lMembers"));

        for (UUID member : party.getMembers()) {
            if (party.getLeader() != member)
                player.sendMessage(Chat.toColor(String.format(" &8- &b%s", Bukkit.getOfflinePlayer(member).getName())));
        }

        player.sendMessage(Chat.toColor("&7&m-------------------------------------------"));
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
