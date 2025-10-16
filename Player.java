/**
 * Player.java
 * 
 * Represents the main hero in the game. The player can move,
 * place bombs, and lose lives upon touching enemies or explosions.
 */
public class Player {
    private int x, y;
    private int lives;
    private int availableBombs;

    /**
     * Constructs a player with initial position and default stats.
     */
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.lives = 3;
        this.availableBombs = 1;
    }

    /**
     * Attempts to move the player on the map.
     */
    public void move(int dx, int dy, Level level) {
        int nx = x + dx;
        int ny = y + dy;
        if (!level.isWalkable(nx, ny)) return;
        x = nx;
        y = ny;
    }

    /**
     * Places a bomb and decreases available count.
     */
    public void placeBomb() {
        availableBombs--;
    }

    /**
     * Recovers a bomb after explosion.
     */
    public void recoverBomb() {
        availableBombs++;
    }

    /**
     * Checks if player can place a bomb.
     */
    public boolean canPlaceBomb() {
        return availableBombs > 0;
    }

    /**
     * Reduces player's life count.
     */
    public void loseLife() {
        lives--;
    }

    // Getters
    public int getLives() { return lives; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getAvailableBombs() { return availableBombs; }
}
