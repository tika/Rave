package me.ghit.rave.listeners;

import me.ghit.rave.templates.Party;
import me.ghit.rave.utils.Chat;
import me.ghit.rave.utils.ChatUtils;
import me.ghit.rave.utils.PartyUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        if (!PartyUtils.isInParty(player.getUniqueId()) && ChatUtils.hasChatEnabled(player.getUniqueId()))  {
            ChatUtils.setPartyChatEnabled(player.getUniqueId(), false);
            player.sendMessage(Chat.toColor("&bParty chat has been &cdisabled &bbecause you are no longer in a party!"));
            return;
        }

        if (ChatUtils.hasChatEnabled(player.getUniqueId())) {
            Party party = PartyUtils.findParty(player.getUniqueId());

            party.message("&3" + player.getName() + "&7: " + e.getMessage());
            e.setCancelled(true);

            // Log to console
            System.out.println("[Party Chat]: " + player.getName() + ": " + e.getMessage());
        }
    }
}
