import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bomb.java
 * 
 * Represents a bomb placed by the player within the Shadow Escape game.
 * Each bomb has a countdown timer and explodes after a specific number
 * of turns, producing explosions that destroy nearby soft walls and
 * can damage enemies or the player.
 * 
 * In this MCO2 version, {@code Bomb} extends {@link Entity} and implements
 * {@link Serializable}, supporting save/load functionality and reusability.
 * Bombs now also feature a configurable explosion range determined by
 * the player’s power-ups.
 * 
 * Upon detonation, the bomb generates {@link Explosion} objects in
 * the four cardinal directions (up, down, left, right), up to its range.
 * 
 */
public class Bomb extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Turns remaining before explosion. */
    private int timer;
    /** The radius of the explosion. */
    private int range;

    /**
     * Constructs a standard Bomb.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public Bomb(int x, int y) {
        super(x, y);
        this.timer = 6; // Default timer
        this.range = 2; // Default range
    }

    /**
     * Constructs a Bomb with custom settings (for powerups/loading).
     *
     * @param x     the X coordinate
     * @param y     the Y coordinate
     * @param timer turns until explosion
     * @param range radius of explosion
     */
    public Bomb(int x, int y, int timer, int range) {
        super(x, y);
        this.timer = timer;
        this.range = range;
    }

    /**
     * Decrements the fuse timer by one.
     */
    public void tick() {
        timer--;
    }

    /**
     * Gets the current timer value.
     * @return int representing turns left.
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Calculates the spread of the explosion based on the map layout.
     * Stops at Hard Walls, destroys Soft Walls.
     *
     * @param level the current Level object to check for walls
     * @return a List of Explosion objects representing the blast area
     */
    public List<Explosion> explode(Level level) {
        List<Explosion> list = new ArrayList<>();
        // Center of explosion
        list.add(new Explosion(getX(), getY()));

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        
        // Raycast in 4 directions
        for (int[] d : dirs) {
            for (int i = 1; i <= range; i++) {
                int nx = getX() + d[0] * i;
                int ny = getY() + d[1] * i;

                if (nx < 0 || ny < 0 || nx >= level.getWidth() || ny >= level.getHeight()) break;

                list.add(new Explosion(nx, ny));

                Tile tile = level.getTile(nx, ny);
                if (tile.getType() == Tile.Type.HARD_WALL) break;
                if (tile.getType() == Tile.Type.SOFT_WALL) {
                    level.setTile(nx, ny, Tile.Type.FLOOR); // Destroy the wall
                    break; // Stop expansion after hitting a wall
                }
            }
        }
        return list;
    }
}