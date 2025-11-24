package mco1;
import java.io.Serializable;

/**
 * Entity.java
 *
 * Abstract superclass representing any object that occupies
 * a position on the game grid (e.g., {@link Player}, {@link Enemy}).
 * Provides shared attributes such as position and common behavior.
 *
 */
public abstract class Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** X-coordinate on the map grid. */
    protected int x;

    /** Y-coordinate on the map grid. */
    protected int y;

    /**
     * Constructs an {@code Entity} at the given coordinates.
     *
     * @param x the X-coordinate
     * @param y the Y-coordinate
     */
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** @return the entity’s X-coordinate */
    public int getX() { 
        return x; 
    }

    /** @return the entity’s Y-coordinate */
    public int getY() { 
        return y; 
    }

    /**
     * Updates the entity’s position.
     *
     * @param x new X-coordinate
     * @param y new Y-coordinate
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
