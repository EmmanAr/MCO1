import java.util.ArrayList;
import java.util.List;

/**
 * Bomb.java
 * 
 * Handles the bomb timer and explosion logic.
 * Explosions destroy soft walls and damage nearby enemies or player.
 */
public class Bomb {
    private int x, y;
    private int timer;
    private boolean exploded;

    /**
     * Creates a new bomb at (x, y) with a given fuse time.
     */
    public Bomb(int x, int y, int fuse) {
        this.x = x;
        this.y = y;
        this.timer = fuse;
        this.exploded = false;
    }

    /**
     * Decreases the fuse timer every turn.
     */
    public void tick() {
        timer--;
        if (timer <= 0) exploded = true;
    }

    /**
     * Returns true if the bomb has exploded.
     */
    public boolean hasExploded() {
        return exploded;
    }

    /**
     * Produces explosion tiles and destroys soft walls.
     */
    public List<Explosion> explode(Level level) {
        List<Explosion> list = new ArrayList<>();
        list.add(new Explosion(x, y));

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : dirs) {
            int nx = x + d[0];
            int ny = y + d[1];
            if (level.destroyTile(nx, ny))
                list.add(new Explosion(nx, ny));
        }

        return list;
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
}
