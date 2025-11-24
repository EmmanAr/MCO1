
import java.io.Serializable;

/**
 * Explosion.java
 * 
 * Represents a single tile affected by a bomb explosion
 * in the Shadow Escape game. Each {@code Explosion}
 * corresponds to a single grid cell where an explosion
 * occurs, which may damage enemies, the player, or destroy
 * soft walls within the {@link Level}.
 * 
 * In this MCO2 version, {@code Explosion} extends
 * {@link Entity} and implements {@link Serializable},
 * allowing explosions to be stored and rendered consistently
 * within the game state during save/load operations.
 * 
 */
public class Explosion extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** The duration (in turns) before the explosion disappears. */
    private int duration;

    /**
     * Constructs a new {@code Explosion} at the specified position
     * with a default visible duration of 1 turn.
     * 
     * @param x the X-coordinate of the explosion
     * @param y the Y-coordinate of the explosion
     */
    public Explosion(int x, int y) {
        super(x, y);
        this.duration = 1;
    }

    /**
     * Constructs a new {@code Explosion} with a custom duration.
     * 
     * @param x the X-coordinate of the explosion
     * @param y the Y-coordinate of the explosion
     * @param duration the number of turns the explosion remains active
     */
    public Explosion(int x, int y, int duration) {
        super(x, y);
        this.duration = duration;
    }

    /**
     * Reduces the explosion’s lifetime by one turn.
     * Once the duration reaches zero, the explosion
     * is considered expired and can be removed.
     */
    public void tick() {
        duration--;
    }

    /**
     * Checks whether this explosion is still active.
     * 
     * @return {@code true} if duration > 0, {@code false} if expired
     */
    public boolean isActive() {
        return duration > 0;
    }

    /**
     * Retrieves the remaining active duration of the explosion.
     * 
     * @return the number of turns remaining
     */
    public int getDuration() {
        return duration;
    }
}
