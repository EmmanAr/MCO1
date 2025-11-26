
import java.io.Serializable;
import java.util.Random;

/**
 * Level.java
 *
 * Represents the game world of Shadow Escape as a 2D grid of {@link Tile} objects.
 * Responsible for generating the map layout, checking tile walkability, and
 * handling destruction events such as bomb explosions.
 *
 * In MCO2, this class implements {@link Serializable}, allowing level states
 * to be saved and restored during gameplay. It serves as a concrete environment
 * container that interacts with movable entities like {@link Player}, {@link Enemy},
 * and {@link Bomb}.
 *
 * Each level includes:
 *   Indestructible hard walls (borders)
 *   Randomly placed destructible soft walls
 *   Walkable floor tiles
 *   One exit tile leading to victory
 * 
 */
public class Level implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 2D array of {@link Tile} objects representing the level map. */
    private Tile[][] map;

    /** Width of the level in tiles. */
    private int width;

    /** Height of the level in tiles. */
    private int height;

    /** Random number generator for map layout variation. */
    private transient Random rnd;

    /**
     * Constructs a new {@code Level} with the specified dimensions.
     * Automatically generates a randomized layout with hard walls, soft walls,
     * and a single exit.
     *
     * @param width  number of tiles horizontally
     * @param height number of tiles vertically
     */
    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.rnd = new Random();
        this.map = new Tile[width][height];
        generate();
    }

    /**
     * Randomly generates the level layout.
     * - Outer borders are {@code HARD_WALL}.
     * - Inner spaces are mostly {@code FLOOR}, with random {@code SOFT_WALL}.
     * - The bottom-right area contains one {@code EXIT} tile.
     */
    private void generate() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    map[x][y] = new Tile(Tile.Type.HARD_WALL);
                } else {
                    map[x][y] = new Tile(Tile.Type.FLOOR);
                }
            }
        }

        for (int j = 1; j < height - 2; j++) {
            for(int k = 1; k < width - 2; k++){
                if ((j % 2 == 0) && (k % 2 == 0)) {
                    map[k][j] = new Tile(Tile.Type.HARD_WALL);
                }
            }
        }

        /* 
        // Randomly place soft walls in open spaces
        for (int i = 0; i < width * height / 6; i++) {
            int x = rnd.nextInt(width);
            int y = rnd.nextInt(height);
            if (map[x][y].getType() == Tile.Type.FLOOR)
                map[x][y] = new Tile(Tile.Type.SOFT_WALL);
        }
        */

        int i = 0;
        while (i < 10) {
            int x = rnd.nextInt(width);
            int y = rnd.nextInt(height);
            if (map[x][y].getType() == Tile.Type.FLOOR){
            map[x][y] = new Tile(Tile.Type.SOFT_WALL);
            i++;
            }
        }

        // Place exit near bottom-right corner
        map[width - 2][height - 2] = new Tile(Tile.Type.EXIT);
    }

    /**
     * Determines if a given tile can be traversed by an entity.
     * Only {@code FLOOR} and {@code EXIT} tiles are walkable.
     *
     * @param x the X-coordinate of the tile
     * @param y the Y-coordinate of the tile
     * @return {@code true} if walkable, {@code false} otherwise
     */
    public boolean isWalkable(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return false;
        Tile.Type t = map[x][y].getType();
        return t == Tile.Type.FLOOR || t == Tile.Type.EXIT;
    }

    /**
     * Destroys a soft wall tile if it is within bounds and destructible.
     * Converts the tile to {@code FLOOR} upon destruction.
     *
     * @param x the X-coordinate of the tile
     * @param y the Y-coordinate of the tile
     * @return {@code true} if tile was valid (even if indestructible),
     *         {@code false} if coordinates were out of bounds
     */
    public boolean destroyTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return false;
        Tile t = map[x][y];
        if (t.getType() == Tile.Type.SOFT_WALL) {
            map[x][y] = new Tile(Tile.Type.FLOOR);
        }
        return true;
    }

    /**
     * Retrieves the {@link Tile} at a specific coordinate.
     *
     * @param x the X-coordinate
     * @param y the Y-coordinate
     * @return the {@link Tile} at that position
     */
    public Tile getTile(int x, int y) {
        return map[x][y];
    }

    /**
     * Updates the tile at the given position with a new type.
     *
     * @param x the X-coordinate of the tile
     * @param y the Y-coordinate of the tile
     * @param type the new {@link Tile.Type} to assign
     */
    public void setTile(int x, int y, Tile.Type type) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            map[x][y].setType(type);
        }
    }

    /** @return width of the level */
    public int getWidth() { 
        return width; 
    }

    /** @return height of the level */
    public int getHeight() { 
        return height; 
    }
}
