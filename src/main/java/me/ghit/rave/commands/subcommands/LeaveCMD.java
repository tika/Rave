package me.ghit.rave.commands.subcommands;

import me.ghit.rave.commands.SubCommand;
import me.ghit.rave.templates.Party;
import me.ghit.rave.utils.Chat;
import me.ghit.rave.utils.PartyUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class LeaveCMD extends SubCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leave the party";
    }

    @Override
    public String getSyntax() {
        return "/rave leave";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("rave.leave") || !player.hasPermission("rave.*")) {
            player.sendMessage(Chat.toColor("&cYou do not have permission to use this command!"));
            return;
        }

        if (!PartyUtils.isInParty(player.getUniqueId())) {
            player.sendMessage(Chat.toColor("&cYou must be in a party to use this command!"));
            return;
        }

        Party p = PartyUtils.findParty(player.getUniqueId());
        String leaderName = Bukkit.getOfflinePlayer(p.getLeader()).getName();
        player.sendMessage(Chat.toColor(String.format("&aYou have left &d%s's &aparty!", leaderName)));
        p.leaveParty(player.getUniqueId());
        p.message(player.getName() + "&b has left the party");
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
