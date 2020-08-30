package me.ghit.rave.commands.subcommands;

import me.ghit.rave.Rave;
import me.ghit.rave.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.List;

/*
    DEVELOPMENT COMMAND
 */
public class CheckCMD extends SubCommand {
    private final Rave plugin = Rave.getInstance();

    @Override
    public String getName() {
        return "check";
    }

    @Override
    public String getDescription() {
        return "Checc";
    }

    @Override
    public String getSyntax() {
        return "/rave check";
    }

    @Override
    public void perform(Player player, String[] args) {
        System.out.println("PARTIES: " + plugin.getParties());
        System.out.println("INVITES: " + plugin.getInvites());
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
