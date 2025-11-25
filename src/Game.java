import java.io.*;
import java.util.*;

/**
 * Game.java
 * 
 * The central logic engine for Shadow Escape.
 * This class maintains the {@link GameState}, processes inputs from the controller,
 * and executes game logic updates (such as bomb timers and enemy movement).
 * 
 * It is decoupled from the UI to allow for easier testing and cleaner architecture.
 * 
 */
public class Game {
    private GameState state;
    private boolean gameOver;
    private boolean victory;

    /**
     * Constructs a new Game instance and initializes the level and entities.
     */
    public Game() {
        initialize();
    }

    /**
     * Sets up the map, player, enemies, and empty bomb lists.
     */
    private void initialize() {
        Level level = new Level(13, 11);
        Player player = new Player(1, 1);
        List<Enemy> enemies = new ArrayList<>();
        List<Bomb> bombs = new ArrayList<>();

        // Add Entities polymorphically
        enemies.add(new Shadowling(5, 5));
        enemies.add(new Shadowling(6, 8));
        enemies.add(new Charger(10, 7));
        enemies.add(new Wraith(8, 3)); 

        state = new GameState(level, player, enemies, bombs, 0);
        gameOver = false;
        victory = false;
    }

    /**
     * Processes raw input strings from the UI Controller.
     * Handles movement (WASD) and actions (Bomb, Save, Load).
     *
     * @param input the string representation of the key pressed (e.g., "W", "B")
     */
    public void processPlayerInput(String input) {
        if (gameOver) return;

        Player player = state.getPlayer();
        Level level = state.getLevel();

        int dx = 0, dy = 0;
        switch (input) {
            case "W": dy = -1; break;
            case "S": dy = 1; break;
            case "A": dx = -1; break;
            case "D": dx = 1; break;
            case "B": placeBomb(); break;
            case "Z": System.out.println("Saving..."); break; // Placeholder for persistence
            case "X": System.out.println("Loading..."); break; // Placeholder for persistence
        }

        if (dx != 0 || dy != 0) {
            player.move(dx, dy, level);
        }
        checkGameState();
    }

    /**
     * Advances the game logic by one "tick".
     * 
     * This method is called by the UI Timer. It:
     * 
     * Decrements bomb timers and handles explosions.
     * Applies damage to players and enemies.
     * Moves all active enemies.
     * Increments the turn counter.
     * 
     */
    public void updateGameLogic() {
        if (gameOver) return;

        Player player = state.getPlayer();
        Level level = state.getLevel();
        List<Enemy> enemies = state.getEnemies();
        List<Bomb> bombs = state.getBombs();
        List<Explosion> explosions = new ArrayList<>();

        // Update Bombs
        Iterator<Bomb> it = bombs.iterator();
        while (it.hasNext()) {
            Bomb b = it.next();
            b.tick(); // Reduces timer
            if (b.getTimer() <= 0) {
                explosions.addAll(b.explode(level));
                player.recoverBomb(); 
                it.remove();
            }
        }

        // Process Explosion Damage
        for (Explosion e : explosions) {
            if (player.getX() == e.getX() && player.getY() == e.getY()) {
                player.loseLife();
            }
            // Remove enemies hit by explosion (Functional style removal)
            enemies.removeIf(en -> en.getX() == e.getX() && en.getY() == e.getY());
        }

        // Update Enemies
        for (Enemy enemy : enemies) {
            enemy.update(level, player);
            if (enemy.getX() == player.getX() && enemy.getY() == player.getY()) {
                player.loseLife();
            }
        }

        state.incrementTurn();
        checkGameState();
    }

    /**
     * Attempts to place a bomb at the player's current location.
     * Validates if the player has ammo before placing.
     */
    private void placeBomb() {
        Player player = state.getPlayer();
        List<Bomb> bombs = state.getBombs();
        if (player.canPlaceBomb()) {
            bombs.add(new Bomb(player.getX(), player.getY())); 
            player.placeBomb(); 
        }
    }

    /**
     * Checks win/loss conditions.
     * Win: Player stands on Exit.
     * Loss: Player lives reach 0.
     */
    private void checkGameState() {
        Player player = state.getPlayer();
        Level level = state.getLevel();

        if (player.getLives() <= 0) {
            gameOver = true;
            victory = false;
        }

        Tile t = level.getTile(player.getX(), player.getY());
        if (t.getType() == Tile.Type.EXIT) {
            gameOver = true;
            victory = true;
        }
    }

    public GameState getState() { 
        return state; 
    }

    public boolean isGameOver() { 
        return gameOver; 
    }

    public boolean isVictory() { 
        return victory; 
    }

}