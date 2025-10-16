import java.util.Random;

/**
 * Enemy.java
 * 
 * Represents a simple AI-controlled enemy that moves randomly.
 * Colliding with the player reduces the player’s lives.
 */
public class Enemy {
    private int x, y;
    private boolean dead;
    private Random rnd = new Random();

    /**
     * Constructs a new enemy at a given position.
     */
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.dead = false;
    }

    /**
     * Moves randomly to one of the four directions if walkable.
     */
    public void moveRandom(Level level) {
        int dx = 0, dy = 0;
        switch (rnd.nextInt(4)) {
            case 0: dx = 1; break;
            case 1: dx = -1; break;
            case 2: dy = 1; break;
            case 3: dy = -1; break;
        }
        int nx = x + dx;
        int ny = y + dy;
        if (level.isWalkable(nx, ny)) {
            x = nx;
            y = ny;
        }
    }

    // Getters & Setters
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isDead() { return dead; }
    public void setDead(boolean d) { dead = d; }
}
