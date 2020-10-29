package me.ghit.rave.templates;

import me.ghit.rave.Rave;
import org.bukkit.Bukkit;

public abstract class RepeatingTask implements Runnable {
    private final int taskId;

    /**
     * Repeating task
     * @param arg1 Delay
     * @param arg2 Period
     */
    public RepeatingTask(int arg1, int arg2) {
        Rave plugin = Rave.getInstance();
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, arg1, arg2);
    }

    /**
     * Cancel the task
     */
    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
