package me.ghit.rave.commands.subcommands;

import me.ghit.rave.commands.SubCommand;
import me.ghit.rave.templates.Party;
import me.ghit.rave.utils.Chat;
import me.ghit.rave.utils.PartyUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KickCMD extends SubCommand {
    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "kick a member from your party";
    }

    @Override
    public String getSyntax() {
        return "/rave kick <member>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("rave.kick") || !player.hasPermission("rave.*")) {
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
            player.sendMessage(Chat.toColor("&cYou cannot kick yourself!"));
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

        party.leaveParty(mentioned.getUniqueId());
        party.message(Chat.toColor(String.format("&3%s &bhas been kicked from the party!", mentioned.getName())), true);
        mentioned.sendMessage(Chat.toColor(String.format("&cYou have been kicked from %s's party!", player.getName())));
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2 && PartyUtils.isInParty(player.getUniqueId())) {
            List<String> membersList = new ArrayList<>();
            PartyUtils.findParty(player.getUniqueId()).getMembers().forEach(member -> membersList.add(Bukkit.getOfflinePlayer(member).getName()));
            return membersList;
        }

        return null;
    }
}
