/**
 * Wraith.java
 *
 * A ghost-like enemy that moves toward the player’s position
 * using basic directional tracking.
 * 
 */
public class Wraith extends Enemy {
    private static final long serialVersionUID = 1L;

    public Wraith(int x, int y) {
        super(x, y);
        logo = 'W';
    }

    @Override
    public void update(Level level, Player player) {
        int dx = 0, dy = 0;

        if (player.getX() > getX()) dx = 1;
        else if (player.getX() < getX()) dx = -1;

        if (player.getY() > getY()) dy = 1;
        else if (player.getY() < getY()) dy = -1;

        move(dx, dy, level);
    }
}
