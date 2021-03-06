package me.ghit.rave.commands.subcommands;

import me.ghit.rave.commands.SubCommand;
import me.ghit.rave.templates.Invite;
import me.ghit.rave.utils.Chat;
import me.ghit.rave.utils.InviteUtils;
import me.ghit.rave.utils.PartyUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JoinCMD extends SubCommand {
    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "accepts a players invite and joins their party";
    }

    @Override
    public String getSyntax() {
        return "/rave join <player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("rave.join") || !player.hasPermission("rave.*")) {
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
             player.sendMessage(Chat.toColor("&cYou cannot join your own party!"));
             return;
        }

        Invite invite = InviteUtils.findInvite(mentioned.getUniqueId(), player.getUniqueId());

        // Already in party
        if (PartyUtils.isInParty(player.getUniqueId())) {
            if (PartyUtils.findParty(player.getUniqueId()).getMembers().contains(mentioned.getUniqueId())) {
                player.sendMessage(Chat.toColor("&cYou are already in that party!"));
                return;
            }
        }

        // If not invited
        if (invite == null) {
            player.sendMessage(Chat.toColor("&cYou have not been invited to that party!"));
            return;
        }

        invite.accepted();
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2) {
            List<String> playersList = new ArrayList<>();
            Bukkit.getServer().getOnlinePlayers().forEach(p -> playersList.add(p.getName()));
            return playersList;
        }
        return null;
    }
}
