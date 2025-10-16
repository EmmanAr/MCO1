/**
 * Tile.java
 * 
 * Represents a single map tile (floor, wall, or exit).
 */
public class Tile {
    public enum Type { FLOOR, SOFT_WALL, HARD_WALL, EXIT }
    private Type type;

    /**
     * Constructs a tile with a given type.
     */
    public Tile(Type type) {
        this.type = type;
    }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
}
