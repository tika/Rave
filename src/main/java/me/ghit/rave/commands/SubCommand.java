package me.ghit.rave.commands;

import org.bukkit.entity.Player;

import java.util.List;

/*
    Credit to Kody Simpson for creating this class (modification made by me)
    YouTube: https://www.youtube.com/channel/UC_LtbK9pzAEI-4yVprLOcyA (Kody Simpson YouTube Channel)
 */
public abstract class SubCommand {

    //name of the subcommand e.g /prank <subcommand>
    public abstract String getName();

    //e.g "This adds a mine"
    public abstract String getDescription();

    //How to use command e.g /mine add <label>
    public abstract String getSyntax();

    //code for the subcommand
    public abstract void perform(Player player, String args[]);

    public abstract List<String> getSubcommandArguments(Player player, String args[]);

}
