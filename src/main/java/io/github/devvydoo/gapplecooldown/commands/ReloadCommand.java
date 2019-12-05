package io.github.devvydoo.gapplecooldown.commands;

import io.github.devvydoo.gapplecooldown.GappleCooldown;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    private GappleCooldown plugin;

    public ReloadCommand(GappleCooldown plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // The plugin.yml handles permission checking for us, no need to worry about that

        if (sender instanceof Player){
            Player player = (Player) sender;

            // Reload the plugin
            plugin.reloadConfig();
            player.sendMessage(ChatColor.GREEN + "Reloaded " + ChatColor.GOLD + "GappleCooldown"
                    + ChatColor.GREEN + "!");
        }

        // Console must be executing the command. Let them
        plugin.reloadConfig();
        System.out.println("Reloaded GappleCooldown!");

        return true;
    }
}
