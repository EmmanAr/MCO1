import java.io.Serializable;

/**
 * Player.java
 * 
 * Represents the main hero of the Shadow Escape game.
 * The player can move around the map, place bombs, and lose lives when
 * colliding with enemies or explosions. The player has a limited number
 * of bombs that can be placed and recovered after they explode.
 * 
 * This class interacts closely with the {@link Level} class for movement
 * validation and bomb placement logic. In MCO2, the Player now extends
 * {@link Entity} and implements {@link Movable} to demonstrate inheritance
 * and polymorphism in an object-oriented design.
 * 
 */
public class Player extends Entity implements Movable, Serializable {
    private static final long serialVersionUID = 1L;

    private int lives;
    private int availableBombs;
    private int score;

    /**
     * Constructs a new Player at the given coordinates.
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Player(int x, int y) {
        super(x, y);
        this.lives = 3;
        this.availableBombs = 1;
        this.score = 0;
    }

    /**
     * Moves the player if the target tile is walkable (Not a Wall).
     *
     * @param dx    Direction X (-1, 0, 1)
     * @param dy    Direction Y (-1, 0, 1)
     * @param level The level for collision checking
     */
    @Override
    public void move(int dx, int dy, Level level) {
        int nx = getX() + dx;
        int ny = getY() + dy;
        Tile tile = level.getTile(nx, ny);
        
        if (tile.getType() != Tile.Type.HARD_WALL && tile.getType() != Tile.Type.SOFT_WALL) {
            setPosition(nx, ny);
        }
    }

    /**
     * Checks if the player has bomb ammo left.
     * @return true if ammo > 0
     */
    public boolean canPlaceBomb() {
        return availableBombs > 0;
    }

    /**
     * Decrements the available bomb count.
     */
    public void placeBomb() {
        availableBombs--;
    }

    /**
     * Replenishes bomb count (usually called after a bomb explodes).
     */
    public void recoverBomb() {
        availableBombs++;
    }

    /**
     * Decrements player lives.
     */
    public void loseLife() {
        lives--;
    }

    public int getLives() { 
        return lives; 
    }

    public int getAvailableBombs() { 
        return availableBombs; 
    }
}