import java.util.Random;

/**
 * Shadowling.java
 *
 * A weak and unpredictable enemy that moves randomly around the map.
 * It demonstrates simple AI behavior for polymorphism testing.
 * 
 */
public class Shadowling extends Enemy {
    private static final long serialVersionUID = 1L;
    private Random rnd = new Random();

    public Shadowling(int x, int y) {
        super(x, y);
        logo = 'H';
    }

    @Override
    public void update(Level level, Player player) {
        // Random wandering behavior
        int dx = 0, dy = 0;
        switch (rnd.nextInt(4)) {
            case 0: dx = 1; break;
            case 1: dx = -1; break;
            case 2: dy = 1; break;
            case 3: dy = -1; break;
        }
        move(dx, dy, level);
    }
}
