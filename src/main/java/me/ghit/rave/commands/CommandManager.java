package me.ghit.rave.commands;

import me.ghit.rave.commands.subcommands.*;
import me.ghit.rave.utils.Chat;
import me.ghit.rave.utils.TextUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final ArrayList<SubCommand> subcommands = new ArrayList<>();

    /**
     * CommandManager manages commands
     */
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
                p.sendMessage(Chat.toColor("&7&m----------------&r &6&lR&8&lA&6&lV&8&lE &7&m----------------"));
                p.sendMessage(" ");
                p.sendMessage(Chat.toColor("&6Rave&8 is a simple to use parties plugin, inspired by the original &6&oHypixel&8 parties system, made for simplicity\n"));
                p.sendMessage(" ");
                p.sendMessage(Chat.toColor("&6&lCommands"));

                for (SubCommand subCommand : getSubCommands()) {
                    TextComponent hoverCommand;

                    hoverCommand = new TextComponent(Chat.toColor(String.format("&6&o%s &8- &6%s", subCommand.getSyntax(), subCommand.getDescription())));
                    hoverCommand.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, subCommand.getSyntax()));

                    // Remove sub arguments
                    if (subCommand.getSyntax().split(" ").length > 2) {
                        hoverCommand.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, subCommand.getSyntax().substring(0, subCommand.getSyntax().lastIndexOf(" "))));
                    }

                    hoverCommand.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
                            Chat.toColor(String.format("&6&l%s\n&8%s", TextUtils.capitalize(subCommand.getName()), subCommand.getDescription()))
                    )));

                    p.spigot().sendMessage(hoverCommand);
                }

                p.sendMessage(" ");
                p.sendMessage(Chat.toColor("&7&m-------------------------------------------"));
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

        } else if(args.length >= 2) {

            for (int i = 0; i < getSubCommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    return getSubCommands().get(i).getSubcommandArguments((Player) sender, args);
                }
            }

        }

        return null;
    }
}
