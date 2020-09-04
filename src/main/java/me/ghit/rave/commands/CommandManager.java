package me.ghit.rave.commands;

import me.ghit.rave.commands.subcommands.*;
import me.ghit.rave.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager(){
        subcommands.add(new InviteCMD());
        subcommands.add(new JoinCMD());
        subcommands.add(new ListCMD());
        subcommands.add(new LeaveCMD());
        subcommands.add(new ChatCMD());
        subcommands.add(new KickCMD());
        subcommands.add(new PromoteCMD());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length > 0){
                for (int i = 0; i < getSubCommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                        getSubCommands().get(i).perform(p, args);
                    }
                }
            } else { // Only command is ran
                p.sendMessage(Chat.toColor("&7---------------- &a&lR&b&lA&c&lV&d&lE &7----------------"));
                p.sendMessage(" ");
                p.sendMessage(Chat.toColor("&7Rave is a fully-fledged parties plugin, inspired by Hypixel, made for the community."));
                p.sendMessage(" ");
                p.sendMessage(Chat.toColor("&dCommands:"));

                for (SubCommand subCommand : getSubCommands()) {
                    p.sendMessage(Chat.toColor(String.format("&c%s &8- &a%s", subCommand.getSyntax(), subCommand.getDescription())));
                }
            }
        }


        return true;
    }

    public ArrayList<SubCommand> getSubCommands(){
        return subcommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1){ //prank <subcommand> <args>
            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubCommands().size(); i++){
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }

            return subcommandsArguments;

        }else if(args.length >= 2){

            for (int i = 0; i < getSubCommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    return getSubCommands().get(i).getSubcommandArguments((Player) sender, args);
                }
            }

        }

        return null;
    }
}
