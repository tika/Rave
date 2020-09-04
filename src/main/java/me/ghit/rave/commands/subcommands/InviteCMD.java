package me.ghit.rave.commands.subcommands;

import me.ghit.rave.Rave;
import me.ghit.rave.commands.SubCommand;
import me.ghit.rave.templates.Invite;
import me.ghit.rave.utils.Chat;
import me.ghit.rave.utils.PartyUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class InviteCMD extends SubCommand {
    private final Rave plugin = Rave.getInstance();

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "invites a specified player";
    }

    @Override
    public String getSyntax() {
        return "/rave invite <player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("rave.invite") || !player.hasPermission("rave.*")) {
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
             player.sendMessage(Chat.toColor("&cYou cannot invite yourself!"));
             return;
         }

         if (PartyUtils.isInParty(player.getUniqueId())) {
             if (PartyUtils.findParty(player.getUniqueId()) == PartyUtils.findParty(mentioned.getUniqueId())) {
                 player.sendMessage(Chat.toColor("&cThat player is already in your party!"));
                 return;
             }
         }

         Invite invite = new Invite(mentioned.getUniqueId(), player.getUniqueId());

         // If already invited
         if (invite.isInvited()) {
            player.sendMessage(Chat.toColor("&cYou have already invited this player!"));
         } else {
             int seconds = plugin.fetchConfig().getConfig().getInt("invite-expiry");

             player.sendMessage(Chat.toColor(String.format("&bYou invited &3%s &bto the party, they have %s seconds to join!", mentioned.getName(), seconds)));

             TextComponent inviteMessage = new TextComponent(Chat.toColor(String.format("&3%s &binvited you to join their party! Type /rave join %s", player.getName(), player.getName())));
             inviteMessage.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rave join " + player.getName() ) );
             inviteMessage.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new Text( Chat.toColor("&3Click to join &b" + player.getName() + "'s &3party") ) ) );

             mentioned.spigot().sendMessage(inviteMessage);
             invite.create();
         }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
