import java.util.Random;

/**
 * Level.java
 * 
 * Stores the 2D grid of tiles and provides helper functions
 * for movement, wall destruction, and map generation.
 */
public class Level {
    private Tile[][] map;
    private int width, height;
    private Random rnd;

    /**
     * Constructs a level of given dimensions.
     */
    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        rnd = new Random();
        map = new Tile[width][height];
        generate();
    }

    /**
     * Generates the initial map layout with random soft walls and one exit.
     */
    private void generate() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1)
                    map[x][y] = new Tile(Tile.Type.HARD_WALL);
                else
                    map[x][y] = new Tile(Tile.Type.FLOOR);
            }
        }

        // Random soft walls
        for (int i = 0; i < width * height / 6; i++) {
            int x = rnd.nextInt(width);
            int y = rnd.nextInt(height);
            if (map[x][y].getType() == Tile.Type.FLOOR)
                map[x][y] = new Tile(Tile.Type.SOFT_WALL);
        }

        // Exit tile at bottom right
        map[width - 2][height - 2] = new Tile(Tile.Type.EXIT);
    }

    /**
     * Checks if a tile can be walked on by player/enemy.
     */
    public boolean isWalkable(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return false;
        Tile.Type t = map[x][y].getType();
        return t == Tile.Type.FLOOR || t == Tile.Type.EXIT;
    }

    /**
     * Destroys soft walls hit by an explosion.
     */
    public boolean destroyTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return false;
        Tile t = map[x][y];
        if (t.getType() == Tile.Type.SOFT_WALL) {
            map[x][y] = new Tile(Tile.Type.FLOOR);
            return true;
        }
        return true;
    }

    // Getters
    public Tile getTile(int x, int y) { return map[x][y]; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
