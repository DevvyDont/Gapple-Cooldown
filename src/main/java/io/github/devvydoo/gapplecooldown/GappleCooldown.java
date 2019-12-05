package io.github.devvydoo.gapplecooldown;

import io.github.devvydoo.gapplecooldown.commands.ReloadCommand;
import io.github.devvydoo.gapplecooldown.listeners.GappleEatListener;
import io.github.devvydoo.gapplecooldown.tasks.RemovePlayerFromCooldownTask;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class GappleCooldown extends JavaPlugin {

    private HashMap<Player, Integer> onCooldown = new HashMap<>();

    public void addPlayerToCooldown(Player player){
        onCooldown.put(player, (int) System.currentTimeMillis() / 1000);
        new RemovePlayerFromCooldownTask(this, player)
                .runTaskLater(this, 20 * getConfig().getInt("cooldown"));
    }

    public void removePlayerFromCooldown(Player player){
        onCooldown.remove(player);
    }

    public boolean isPlayerOnCooldown(Player player){
        return onCooldown.containsKey(player);
    }

    public int getCooldownTime(Player player){
        int totalCooldown = getConfig().getInt("cooldown");
        int secondsPassed = ( (int) System.currentTimeMillis() / 1000 ) - onCooldown.get(player);
        return totalCooldown - secondsPassed;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        FileConfiguration config = getConfig();  // Get our config

        // Construct our default config values
        config.addDefault("cooldown", 30);
        config.addDefault("onCooldownMessage",
                "&cGapples are on cooldown! Please wait &f{TIME_LEFT} &cseconds!");
        config.addDefault("wantEnterCooldownMessage", false);
        config.addDefault("enterCooldownMessage",
                "&6You ate a gapple! You cannot eat another for &f{TIME_LEFT} &6seconds!");

        // Save our config
        config.options().copyDefaults();
        saveDefaultConfig();

        // Register events and commands
        getServer().getPluginManager().registerEvents(new GappleEatListener(this), this);
        getCommand("gcreload").setExecutor(new ReloadCommand(this));

    }
}
