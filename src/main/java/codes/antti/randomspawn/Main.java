package codes.antti.randomspawn;

import org.bukkit.plugin.java.JavaPlugin;
import codes.antti.randomspawn.*;

public class Main extends JavaPlugin {
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerJoinHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathHandler(), this);
        getLogger().info("Enabled RandomSpawn!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled RandomSpawn!");
    }
}