/**
 * Movable.java
 *
 * Interface for all entities that can move on the map.
 * Provides a method contract for movement validation and logic.
 * 
 */
public interface Movable {
    /**
     * Moves the entity by the specified delta values if allowed.
     *
     * @param dx change in X direction
     * @param dy change in Y direction
     * @param level reference to the {@link Level} for movement validation
     * 
     */
    void move(int dx, int dy, Level level);
}
