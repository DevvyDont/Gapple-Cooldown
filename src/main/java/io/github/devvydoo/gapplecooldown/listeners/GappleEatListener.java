package io.github.devvydoo.gapplecooldown.listeners;

import io.github.devvydoo.gapplecooldown.GappleCooldown;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class GappleEatListener implements Listener {

    private GappleCooldown plugin;

    public GappleEatListener(GappleCooldown plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onGappleEat(PlayerItemConsumeEvent event){

        Player player = event.getPlayer();
        ItemStack foodEaten = event.getItem();

        // Is the item being eaten a Golden Apple?
        if (!(foodEaten.getType().equals(Material.GOLDEN_APPLE)
                || foodEaten.getType().equals(Material.ENCHANTED_GOLDEN_APPLE))){ return; }

        // Is the player on cooldown already?
        if (plugin.isPlayerOnCooldown(player)){
            event.setCancelled(true);  // Don't let them eat the gapple

            // Get the message defined in the config, then replace our time left place holder
            String message = replaceTimePlaceholder(plugin.getConfig().getString("onCooldownMessage"),
                    plugin.getCooldownTime(player));

            // Tell the player
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));  // Send message

        } else {
            // This will run if our player is all good to eat a gapple
            plugin.addPlayerToCooldown(player);

            // If our config wants us to tell players they are on a cooldown
            if (plugin.getConfig().getBoolean("wantEnterCooldownMessage")){
                String message = replaceTimePlaceholder(plugin.getConfig().getString("enterCooldownMessage"),
                        plugin.getConfig().getInt("cooldown"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }


        }

    }

    private String replaceTimePlaceholder(String originalMessage, int timeLeft){
        return originalMessage.replace("{TIME_LEFT}", String.valueOf(timeLeft));
    }

}
