
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Tile.Type;

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

    /** The number of turns before the bomb explodes. */
    private int timer;

    /** Indicates whether the bomb has exploded. */
    private boolean exploded;

    /** The explosion range (how many tiles the blast extends). */
    private int range;

    /**
     * Constructs a new {@code Bomb} at the specified coordinates with a given fuse duration and range.
     * 
     * @param x the X-coordinate where the bomb is placed
     * @param y the Y-coordinate where the bomb is placed
     * @param fuse the number of turns before the bomb explodes
     * @param range the explosion range (number of tiles in each direction)
     */
    public Bomb(int x, int y, int fuse, int range) {
        super(x, y);
        this.timer = fuse;
        this.range = range;
        this.exploded = false;
    }

    /**
     * Decreases the bomb's fuse timer by one each turn.
     * When the timer reaches zero, the bomb is marked as exploded.
     */
    public void tick() {
        timer--;
        if (timer <= 0) exploded = true;
    }

    /**
     * Checks whether this bomb has exploded.
     * 
     * @return {@code true} if the bomb has exploded, {@code false} otherwise
     */
    public boolean hasExploded() {
        return exploded;
    }

    /**
     * Triggers the explosion of this bomb.
     * Generates {@link Explosion} objects in all four directions (up, down, left, right)
     * up to its range, destroying any {@code SOFT_WALL} tiles it touches.
     * 
     * @param level the {@link Level} on which the bomb is placed
     * @return a list of {@link Explosion} objects representing the blast area
     */
    public List<Explosion> explode(Level level) {
        List<Explosion> list = new ArrayList<>();
        list.add(new Explosion(getX(), getY()));

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : dirs) {
            for (int i = 1; i <= range; i++) {
                int nx = getX() + d[0] * i;
                int ny = getY() + d[1] * i;

                // Stop if out of bounds
                if (nx < 0 || ny < 0 || nx >= level.getWidth() || ny >= level.getHeight()) break;

                // Add explosion tile
                list.add(new Explosion(nx, ny));

                // Stop explosion at hard walls or destroy soft walls
                Tile tile = level.getTile(nx, ny);
                if (tile.getType() == Tile.Type.HARD_WALL) break;
                if (tile.getType() == Tile.Type.SOFT_WALL) {
                    level.setTile(nx, ny, Tile.Type.FLOOR);
                    break;
                }
            }
        }

        return list;
    }

    // =======================
    // Getters
    // =======================

    /** @return the number of turns before explosion */
    public int getTimer() { 
        return timer; 
    }

    /** @return the explosion range */
    public int getRange() { 
        return range; 
    }

    /** @return {@code true} if the bomb has exploded */
    public boolean isExploded() { 
        return exploded; 
    }
}
