package me.ghit.rave.templates;

import me.ghit.rave.Rave;
import me.ghit.rave.utils.Chat;
import me.ghit.rave.utils.PartyUtils;
import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.UUID;

public class Invite {
    private final Rave plugin = Rave.getInstance();

    private final UUID invitedPlayer;
    private final UUID inviter;

    public Invite(UUID invitedPlayer, UUID inviter) {
        this.invitedPlayer = invitedPlayer;
        this.inviter = inviter;
    }

    private final RepeatingTask expiryChecker = new RepeatingTask(0, 20) {
        int timePassed = 0; // In seconds

        @Override
        public void run() {
            if (timePassed == 60) { // 60 seconds
                // Remove the invite
                plugin.getInvites().remove(Invite.this);
                plugin.getParties().remove(PartyUtils.findParty(inviter)); // Remove 1 player party
                expired();

                this.cancel();
            }

            timePassed += 1;
        }
    };

    public UUID getInvitedPlayer() {
        return invitedPlayer;
    }

    public UUID getInviter() {
        return inviter;
    }

    public void accepted() {
        PartyUtils.findParty(inviter).joinParty(invitedPlayer);
        plugin.getInvites().remove(this);
        expiryChecker.cancel();
    }

    public boolean isInvited() {
        boolean isInvited = false;

        for (Invite invite : plugin.getInvites()) {
            if (invite.getInvitedPlayer() == invitedPlayer && invite.getInviter() == inviter) {
                isInvited = true;
                break;
            }
        }

        return isInvited;
    }

    public void create() {
        plugin.getInvites().add(this);
        expiryChecker.run();

        // If party doesn't already exist
        if (!PartyUtils.isInParty(inviter)) {
            Party party = new Party(inviter, Collections.singletonList(inviter));
            party.create();
        }
    }

    private void expired() {
        if (Bukkit.getPlayer(invitedPlayer) != null) Bukkit.getPlayer(invitedPlayer).sendMessage(Chat.toColor(String.format("&dThe invite from &5%s &dhas now expired!", Bukkit.getOfflinePlayer(inviter).getName())));
        if (Bukkit.getPlayer(inviter) != null) Bukkit.getPlayer(inviter).sendMessage(Chat.toColor(String.format("&dThe invite to &5%s &dhas now expired!", Bukkit.getOfflinePlayer(invitedPlayer).getName())));
    }
}
