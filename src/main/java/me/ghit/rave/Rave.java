package me.ghit.rave;

import me.ghit.rave.commands.CommandManager;
import me.ghit.rave.commands.subcommands.ChatCMD;
import me.ghit.rave.listeners.ChatListener;
import me.ghit.rave.listeners.LeaveListener;
import me.ghit.rave.templates.Invite;
import me.ghit.rave.templates.Party;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Rave extends JavaPlugin {

    private final Logger logger = Bukkit.getLogger();
    private final long startms = System.currentTimeMillis();
    private static Rave plugin;

    private final List<Party> parties = new ArrayList<>();
    private final List<Invite> invites = new ArrayList<>();

    @Override
    public void onEnable() {
        Rave.plugin = this;

        getCommand("rave").setExecutor(new CommandManager());
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(), this);

        final long loadms = System.currentTimeMillis() - startms;
        logger.log(Level.INFO, String.format("[Rave] Loaded in %sms", loadms));
    }

    public List<Party> getParties() { return parties; }
    public List<Invite> getInvites() { return invites; }

    public static Rave getInstance() {
        return plugin;
    }
}
