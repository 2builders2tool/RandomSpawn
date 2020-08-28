package codes.antti.randomspawn;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerRespawnEvent;
import codes.antti.randomspawn.FindSafeSpawn;
import org.bukkit.configuration.file.FileConfiguration;

public class PlayerDeathHandler implements Listener {
    private FileConfiguration config = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig();
    private int respawnRadius = config.getInt("respawnRadius");
    FindSafeSpawn spawnFinder = new FindSafeSpawn();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if(event.getPlayer().getBedSpawnLocation() == null && event.getPlayer().getLocation().getWorld().getEnvironment().toString().toLowerCase().equals("normal")) {
            event.setRespawnLocation(spawnFinder.randomSafeSpawn(event.getPlayer().getLocation(),-respawnRadius,respawnRadius,-respawnRadius,respawnRadius));
        }
    }
}