package me.ghit.rave.utils;

import me.ghit.rave.Rave;
import me.ghit.rave.templates.Party;

import java.util.UUID;

public class PartyUtils {

    private static final Rave plugin = Rave.getInstance();

    private PartyUtils() {}
    
    public static Party findParty(UUID player) {
        Party party = null;

        for (Party it : plugin.getParties()) {
            if (it.getMembers().contains(player)) {
                party = it;
                break;
            }
        }

        return party;
    }

    public static boolean isInParty(UUID player) {
        return findParty(player) != null;
    }
}
