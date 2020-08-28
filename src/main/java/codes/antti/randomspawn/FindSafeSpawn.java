package codes.antti.randomspawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Chunk;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.lang.InterruptedException;

import com.palmergames.bukkit.towny.TownyAPI;

import io.papermc.lib.PaperLib;

public class FindSafeSpawn {
    private FileConfiguration config = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig();
    private int minX = config.getInt("min-x");
    private int maxX = config.getInt("max-x");
    private int minY = config.getInt("min-y");
    private int maxY = config.getInt("max-y");
    private int minZ = config.getInt("min-z");
    private int maxZ = config.getInt("max-z");
    private int tries = config.getInt("tries");
    private List<String> safeBlocks = config.getStringList("safe-blocks");
    private String worldName = config.getString("world");
    private World world = Bukkit.getWorld(worldName);
    Random r = new Random();

    // random spawn by config
    public Location randomSpawn() {
        int x = r.nextInt(maxX-minX) + minX;
        int z = r.nextInt(maxZ-minZ) + minZ;
        return new Location(world, x, 256, z);
    }

    // random spawn by args
    public Location randomSpawn(int minX2, int maxX2, int minZ2, int maxZ2) {
        int x = r.nextInt(maxX2-minX2) + minX2;
        int z = r.nextInt(maxZ2-minZ2) + minZ2;
        return new Location(world, x, 256, z);
    }

    // get landing pos for location
    public int getHeight(Location loc) {
        if (!TownyAPI.getInstance().isWilderness(loc)) {
            return -1;
        }
        Future<Chunk> future;
        try {
            future = PaperLib.getChunkAtAsync(loc);
            try {
                future.get();
            } catch (ExecutionException e) {
                Bukkit.getLogger().severe("RandomSpawn PaperLib chunk async loading error");
            }
        } catch(InterruptedException e) {
            Bukkit.getLogger().severe("RandomSpawn PaperLib chunk async loading error");
        }
        int y = maxY;
        while(true) {
            loc.setY(y);
            Material type = loc.getBlock().getType();
            if(type != Material.VOID_AIR && type != Material.CAVE_AIR && type != Material.AIR) {
                if(safeBlocks.contains(type.toString().toLowerCase())) {
                    return y;
                } else {
                    Bukkit.getLogger().info("Unsafe block");
                    return -1;
                }
            } else {
                y--;
                if(y < minY) {
                    Bukkit.getLogger().info("Less than miny");
                    return -1;
                }
            }
        }
    }

    // find safe spawn by config
    public Location randomSafeSpawn() {
        for(int i = 0; i < tries; i++) {
            Location random = randomSpawn();
            int height = getHeight(random);
            if(height > 0) {
                random.setY(height+1);
                return random;
            }
        }
        return world.getSpawnLocation();
    }

    // find safe spawn by args
    public Location randomSafeSpawn(Location loc, int minX2, int maxX2, int minZ2, int maxZ2) {
        for(int i = 0; i < tries; i++) {
            Location random = randomSpawn(minX2+(int)loc.getX(), maxX2+(int)loc.getX(), minZ2+(int)loc.getZ(), maxZ2+(int)loc.getZ());
            int height = getHeight(random);
            if(height > 0) {
                random.setY(height+1);
                return random;
            }
        }
        return world.getSpawnLocation();
    }
}