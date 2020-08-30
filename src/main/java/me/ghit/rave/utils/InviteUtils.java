package me.ghit.rave.utils;

import me.ghit.rave.Rave;
import me.ghit.rave.templates.Invite;

import java.util.UUID;

public class InviteUtils {

    private static final Rave plugin = Rave.getInstance();

    private InviteUtils() {}

    public static Invite findAnyInvite(UUID player) {
        Invite invite = null;

        for (Invite it : plugin.getInvites()) {
            if (it.getInviter() == player || it.getInvitedPlayer() == player) {
                invite = it;
                break;
            }
        }

        return invite;
    }

    public static Invite findInvite(UUID inviter, UUID invitedPlayer) {
        Invite invite = null;

        for (Invite it : plugin.getInvites()) {
            if (it.getInviter() == inviter && it.getInvitedPlayer() == invitedPlayer) {
                invite = it;
                break;
            }
        }

        return invite;
    }

}
