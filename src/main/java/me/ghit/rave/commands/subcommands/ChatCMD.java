package me.ghit.rave.commands.subcommands;

import me.ghit.rave.commands.SubCommand;
import me.ghit.rave.utils.Chat;
import me.ghit.rave.utils.ChatUtils;
import me.ghit.rave.utils.PartyUtils;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatCMD extends SubCommand {
    @Override
    public String getName() {
        return "chat";
    }

    @Override
    public String getDescription() {
        return "Toggles party chat";
    }

    @Override
    public String getSyntax() {
        return "/party chat [<enable, disable>]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("rave.chat") || !player.hasPermission("rave.*")) {
            player.sendMessage(Chat.toColor("&cYou do not have permission to use this command!"));
            return;
        }

        if (!PartyUtils.isInParty(player.getUniqueId())) {
            player.sendMessage(Chat.toColor("&cYou must be in a party to use this command!"));
            return;
        }

        ChatUtils.toggle(player.getUniqueId());
        player.sendMessage(Chat.toColor("&bParty chat is now " + ChatUtils.getChatStatus(player.getUniqueId())));
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}