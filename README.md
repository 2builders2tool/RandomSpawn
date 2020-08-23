# RandomSpawn

Minecraft random spawning plugin.

Works in 1.15.2+ versions.

## Building

Just use maven. `mvn install -f pom.xml`

## Config

```yaml
min-x: -5 # these 4 values make up the boundaries of first time random spawning 
max-x: 5 
min-z: -5
max-z: 5
world: world # this is the world name of the world where players will spawn
respawnRadius: 20 # how far should player respawn from their death location if no bed spawn is set
safe-blocks: # safe blocks to spawn on
  - grass_block
```