package me.ghit.rave.commands.subcommands;

import me.ghit.rave.commands.SubCommand;
import me.ghit.rave.templates.Party;
import me.ghit.rave.utils.Chat;
import me.ghit.rave.utils.PartyUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class PromoteCMD extends SubCommand {
    @Override
    public String getName() {
        return "promote";
    }

    @Override
    public String getDescription() {
        return "Promotes a party member to leader";
    }

    @Override
    public String getSyntax() {
        return "/rave promote <member>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("rave.promote") || !player.hasPermission("rave.*")) {
            player.sendMessage(Chat.toColor("&cYou do not have permission to use this command!"));
            return;
        }

        if (args.length < 2) {
            player.sendMessage(Chat.toColor("&cYou have not entered enough arguments!"));
            return;
        }

        Player mentioned = Bukkit.getPlayer(args[1]);

        if (mentioned == null) {
            player.sendMessage(Chat.toColor("&cYou have inputted an invalid player!"));
            return;
        }

        if (mentioned == player) {
            player.sendMessage(Chat.toColor("&cYou cannot promote yourself!"));
            return;
        }

        if (!PartyUtils.isInParty(player.getUniqueId())) {
            player.sendMessage(Chat.toColor("&cYou must be in a party to use this command!"));
            return;
        }

        Party party = PartyUtils.findParty(player.getUniqueId());

        if (party.getLeader() != player.getUniqueId()) {
            player.sendMessage(Chat.toColor("&cYou must be the leader of the party to use this command!"));
            return;
        }

        if (!party.getMembers().contains(mentioned.getUniqueId())) {
            player.sendMessage(Chat.toColor("&cYou have inputted a player not in your party!"));
            return;
        }

        party.promote(mentioned.getUniqueId());
        party.message(Chat.toColor(String.format("&a%s has been promoted to the leader of the party!", mentioned.getName())));
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}