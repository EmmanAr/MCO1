package mco1;
import java.util.Random;

/**
 * Charger.java
 *
 * A fast-moving enemy that aggressively charges in random directions.
 * It moves up to two tiles per turn if the path is clear.
 * 
 */
public class Charger extends Enemy {
    private static final long serialVersionUID = 1L;
    private Random rnd = new Random();

    public Charger(int x, int y) {
        super(x, y);
        logo = 'C';
    }

    @Override
    public void update(Level level, Player player) {
        int[] dirs = { -1, 1 };
        int dx = dirs[rnd.nextInt(2)] * (rnd.nextBoolean() ? 2 : 0);
        int dy = dx == 0 ? dirs[rnd.nextInt(2)] * 2 : 0;

        move(dx, dy, level);
    }
}
