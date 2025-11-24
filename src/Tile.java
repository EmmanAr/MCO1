
import java.io.Serializable;

/**
 * Tile.java
 *
 * Represents a single map tile used in the Shadow Escape game.
 * Each tile defines the environment layout and may represent
 * a walkable area, destructible obstacle, indestructible wall,
 * or the level exit.
 *
 * <p>In MCO2, {@code Tile} implements {@link Serializable} to allow
 * game levels to be saved and reloaded for persistence.
 * The class remains lightweight and immutable in purpose,
 * primarily used by {@link Level} to construct and manage
 * the map grid.</p>
 *
 */
public class Tile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Enumeration representing the possible tile types in the level map.
     *   {@code FLOOR} – A walkable tile where entities can move.
     *   {@code SOFT_WALL} – A destructible wall that bombs can destroy.
     *   {@code HARD_WALL} – An indestructible barrier that blocks movement.
     *   {@code EXIT} – The goal tile that ends the level when reached.
     * 
     */
    public enum Type { FLOOR, SOFT_WALL, HARD_WALL, EXIT }

    /** The current type of this tile. */
    private Type type;

    /**
     * Constructs a {@code Tile} with the given {@link Type}.
     *
     * @param type the type of the tile
     */
    public Tile(Type type) {
        this.type = type;
    }

    /**
     * Retrieves the {@link Type} of this tile.
     *
     * @return the type of this tile
     */
    public Type getType() {
        return type;
    }

    /**
     * Updates this tile’s {@link Type} (e.g., when a soft wall is destroyed).
     *
     * @param type the new tile type to assign
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Returns a character symbol for rendering this tile in text-based maps.
     *
     * @return a single-character representation of the tile type
     */
    public char getSymbol() {
        switch (type) {
            case FLOOR: return '.';
            case SOFT_WALL: return 'S';
            case HARD_WALL: return '#';
            case EXIT: return 'E';
            default: return '?';
        }
    }
}
