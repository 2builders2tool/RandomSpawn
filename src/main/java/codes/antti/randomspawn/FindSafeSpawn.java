package codes.antti.randomspawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;
import java.util.Random;

public class FindSafeSpawn {
    private FileConfiguration config = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig();
    private int minX = config.getInt("min-x");
    private int maxX = config.getInt("max-x");
    private int minZ = config.getInt("min-z");
    private int maxZ = config.getInt("max-z");
    private List<String> safeBlocks = config.getStringList("safe-blocks");
    private String world = config.getString("world");

    public Location randomSpawn() {
        Random r = new Random();
        int x = r.nextInt(maxX-minX) + minX;
        int z = r.nextInt(maxZ-minZ) + minZ;
        return new Location(Bukkit.getWorld(world), x, 256, z);
    }

    public int getHeight(Location loc) {
        int y = 256;
        while(true) {
            loc.setY(y);
            Block block = loc.getBlock();
            Material type = block.getType();
            if(type != Material.VOID_AIR && type != Material.CAVE_AIR && type != Material.AIR) {
                if(safeBlocks.contains(type.toString().toLowerCase())) {
                    return y;
                } else {
                    return -1;
                }
            } else {
                y--;
                if(y<0) {
                    return -1;
                }
            }
        }
    }

    public Location randomSafeSpawn() {
        for(int i = 0; i<500; i++) {
            Location random = randomSpawn();
            int height = getHeight(random);
            if(height>0) {
                random.setY(height+1);
                return random;
            }
        }
        return Bukkit.getWorld(world).getSpawnLocation();
    }

    public Location randomSpawn(int minX2, int maxX2, int minZ2, int maxZ2) {
        Random r = new Random();
        int x = r.nextInt(maxX2-minX2) + minX2;
        int z = r.nextInt(maxZ2-minZ2) + minZ2;
        return new Location(Bukkit.getWorld(world), x, 256, z);
    }

    public Location randomSafeSpawn(Location loc, int minX2, int maxX2, int minZ2, int maxZ2) {
        for(int i = 0; i<500; i++) {
            Location random = randomSpawn(minX2+(int)loc.getX(), maxX2+(int)loc.getX(), minZ2+(int)loc.getZ(), maxZ2+(int)loc.getZ());
            int height = getHeight(random);
            if(height>0) {
                random.setY(height+1);
                return random;
            }
        }
        return Bukkit.getWorld(world).getSpawnLocation();
    }
}