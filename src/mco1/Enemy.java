package mco1;
import java.io.Serializable;

/**
 * Enemy.java
 *
 * Abstract base class for all enemy types in the Shadow Escape game.
 * Each enemy has a position and movement logic determined by its subclass.
 * Enemies interact with the {@link Level} and {@link Player} classes,
 * and can be defeated by explosions.
 *
 * In this MCO2 version, {@code Enemy} extends {@link Entity}
 * and implements {@link Movable}, allowing the creation of
 * polymorphic enemy subclasses such as {@link Shadowling},
 * {@link Wraith}, and {@link Charger}.
 *
 * All subclasses must implement the {@link #update(Level, Player)} method,
 * which defines how the enemy behaves on each game turn (e.g., random movement,
 * chasing the player, or patrolling).
 *
 */
public abstract class Enemy extends Entity implements Movable, Serializable {
    private static final long serialVersionUID = 1L;

    protected char logo;

    /** Indicates whether this enemy has been defeated or destroyed. */
    protected boolean dead;

    /**
     * Constructs a new {@code Enemy} at the specified coordinates.
     *
     * @param x the X-coordinate where the enemy spawns
     * @param y the Y-coordinate where the enemy spawns
     */
    public Enemy(int x, int y) {
        super(x, y);
        this.dead = false;
    }

    /**
     * Defines the enemy’s behavior for each game turn.
     * Must be implemented by subclasses to provide unique AI.
     *
     * @param level  the {@link Level} in which the enemy exists
     * @param player the {@link Player} instance (used for detection and chasing)
     */
    public abstract void update(Level level, Player player);

    /**
     * Moves the enemy by the given offset if the destination tile is walkable.
     * Prevents movement through walls and invalid positions.
     *
     * @param dx the change in X-coordinate
     * @param dy the change in Y-coordinate
     * @param level the current {@link Level} instance for movement validation
     */
    @Override
    public void move(int dx, int dy, Level level) {
        int nx = getX() + dx;
        int ny = getY() + dy;

        if (level.isWalkable(nx, ny)) {
            setPosition(nx, ny);
        }
    }

    /**
     * Checks if this enemy has been defeated (e.g., by an explosion).
     *
     * @return {@code true} if this enemy is dead; {@code false} otherwise
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Sets whether this enemy is defeated.
     *
     * @param d {@code true} to mark as defeated, {@code false} to mark as active
     */
    public void setDead(boolean d) {
        this.dead = d;
    }

    public char getLogo(){
        return logo;
    }
}
