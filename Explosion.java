/**
 * Explosion.java
 * 
 * Represents a single cell affected by a bomb explosion.
 */
public class Explosion {
    public int x, y;

    /**
     * Creates an explosion effect at a given tile.
     */
    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
