package io.github.devvydoo.gapplecooldown.tasks;

import io.github.devvydoo.gapplecooldown.GappleCooldown;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RemovePlayerFromCooldownTask extends BukkitRunnable {

    private GappleCooldown plugin;
    private Player playerToRemove;

    public RemovePlayerFromCooldownTask(GappleCooldown plugin, Player playerToRemove){
        this.plugin = plugin;
        this.playerToRemove = playerToRemove;
    }

    @Override
    public void run() {
        plugin.removePlayerFromCooldown(playerToRemove);
    }
}
