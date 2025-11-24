package mco1;
import java.io.Serializable;
import java.util.List;

/**
 * GameState.java
 *
 * Represents the serializable snapshot of the current game session.
 * Stores all necessary information to resume gameplay at a later time.
 * 
 * This class contains no game logic—it only holds data for persistence.
 * 
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private Level level;
    private Player player;
    private List<Enemy> enemies;
    private List<Bomb> bombs;
    private int turnCounter;

    /**
     * Constructs a new {@code GameState} object.
     *
     * @param level  the current game level
     * @param player the player character
     * @param enemies list of active enemies
     * @param bombs list of active bombs
     * @param turnCounter current turn count
     */
    public GameState(Level level, Player player, List<Enemy> enemies, List<Bomb> bombs, int turnCounter) {
        this.level = level;
        this.player = player;
        this.enemies = enemies;
        this.bombs = bombs;
        this.turnCounter = turnCounter;
    }

    // ---------------- Getters ----------------

    public Level getLevel() { 
        return level; 
    }

    public Player getPlayer() { 
        return player; 
    }
    
    public List<Enemy> getEnemies() { 
        return enemies; 
    }

    public List<Bomb> getBombs() { 
        return bombs; 
    }

    public int getTurnCounter() { 
        return turnCounter; 
    }

    // ---------------- Utility ----------------

    /** Increments the turn counter by one. */
    public void incrementTurn() { 
        turnCounter++; 
    }
}
