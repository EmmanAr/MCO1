
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

    /** The number of lives remaining for the player. */
    private int lives;

    /** The number of bombs currently available to the player. */
    private int availableBombs;

    /** The player’s current score. */
    private int score;

    /** The maximum range of the player’s bomb explosions. */
    private int bombRange;

    /**
     * Constructs a {@code Player} object with an initial position
     * and default attributes (3 lives, 1 bomb, range = 1).
     * 
     * @param x the starting X-coordinate of the player
     * @param y the starting Y-coordinate of the player
     */
    public Player(int x, int y) {
        super(x, y);
        this.lives = 3;
        this.availableBombs = 2;
        this.score = 0;
        this.bombRange = 1;
    }

    /**
     * Attempts to move the player by the specified direction vector.
     * The movement is validated using the {@link Level#isWalkable(int, int)} method.
     * 
     * @param dx horizontal movement (-1 for left, 1 for right, 0 for none)
     * @param dy vertical movement (-1 for up, 1 for down, 0 for none)
     * @param level the {@link Level} object representing the map
     */
    @Override
    public void move(int dx, int dy, Level level) {
        int nx = getX() + dx;
        int ny = getY() + dy;
        if (!level.isWalkable(nx, ny)) return;
        setPosition(nx, ny);
    }

    /**
     * Decreases the number of available bombs by one when a bomb is placed.
     */
    public void placeBomb() {
        if (availableBombs > 0) availableBombs--;
    }

    /**
     * Increases the number of available bombs by one when a bomb explodes.
     */
    public void recoverBomb() {
        availableBombs++;
    }

    /**
     * Determines whether the player currently has a bomb available to place.
     * 
     * @return {@code true} if at least one bomb is available; {@code false} otherwise
     */
    public boolean canPlaceBomb() {
        return availableBombs > 0;
    }

    /**
     * Reduces the player's life by one when hit by an enemy or explosion.
     */
    public void loseLife() {
        lives--;
    }

    /**
     * Adds points to the player's score.
     * 
     * @param value the amount of score to add
     */
    public void addScore(int value) {
        score += value;
    }

    /**
     * Increases the player's bomb range when picking up a range power-up.
     */
    public void increaseRange() {
        bombRange++;
    }

    /**
     * Adds an extra life to the player when collecting a life power-up.
     */
    public void addLife() {
        lives++;
    }

    // =======================
    // Getters
    // =======================

    /** @return the number of lives remaining */
    public int getLives() { 
        return lives; 
    }

    /** @return the number of bombs currently available */
    public int getAvailableBombs() { 
        return availableBombs; 
    }

    /** @return the player’s current score */
    public int getScore() { 
        return score; 
    }

    /** @return the bomb range of the player */
    public int getBombRange() { 
        return bombRange; 
    }
}
