package codes.antti.randomspawn;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import codes.antti.randomspawn.FindSafeSpawn;

import io.papermc.lib.PaperLib;

public class PlayerJoinHandler implements Listener {
    FindSafeSpawn spawnFinder = new FindSafeSpawn();
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        boolean played = Bukkit.getServer().getOfflinePlayer(event.getPlayer().getUniqueId()).hasPlayedBefore();
        if (!played) {
            PaperLib.teleportAsync(event.getPlayer(), spawnFinder.randomSafeSpawn());
        }
    }
}