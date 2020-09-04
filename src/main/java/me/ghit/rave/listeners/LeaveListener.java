package me.ghit.rave.listeners;

import me.ghit.rave.Rave;
import me.ghit.rave.templates.Party;
import me.ghit.rave.templates.RepeatingTask;
import me.ghit.rave.utils.PartyUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {
    private final Rave plugin = Rave.getInstance();

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        int dd = plugin.fetchConfig().getConfig().getInt("disconnect-delay");

        if (!PartyUtils.isInParty(player.getUniqueId())) return;

        Party party = PartyUtils.findParty(player.getUniqueId());

        // 5 minutes to reconnect
        final RepeatingTask leavePartyChecker = new RepeatingTask(0, 20) {
            int timePassed = 0; // In seconds

            @Override
            public void run() {
                if (timePassed > 1) {
                    if (player.isOnline()) {
                        this.cancel();
                    }
                }

                if (timePassed == dd) {
                    // leave party
                    party.message(player.getName() + "&b has left the party due to leaving for more than &a" + dd + "s");
                    party.leaveParty(player.getUniqueId());
                    this.cancel();
                }
                timePassed += 1;
            }
        };

        party.message(player.getName() + "&b has disconnected, this player will be removed from the party in &a" + dd + "s");

        leavePartyChecker.run();
    }
}
