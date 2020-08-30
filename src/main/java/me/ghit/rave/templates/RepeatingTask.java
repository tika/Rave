package me.ghit.rave.templates;

import me.ghit.rave.Rave;
import org.bukkit.Bukkit;

public abstract class RepeatingTask implements Runnable {
    private final int taskId;

    public RepeatingTask(int arg1, int arg2) {
        Rave plugin = Rave.getInstance();
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, arg1, arg2);
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
