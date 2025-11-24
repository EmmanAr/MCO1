import java.io.*;
import java.util.*;

/**
 * Game.java
 *
 * Controls the main flow of the Shadow Escape console game.
 * Manages player actions, bombs, enemies, and map updates.
 * This version uses {@link GameState} for save/load persistence.
 *
 * Implements clean OOP separation of concerns:
 *   {@link Game} handles logic, input, and progression.
 *   {@link GameState} handles serializable data storage.
 *
 */
public class Game implements Runnable{
    /** Current game state containing level, player, and entities. */
    private GameState state;

    /** Flag to indicate if the game is over. */
    private boolean gameOver;

    /** Input scanner for console commands (not serialized). */
    private transient Scanner sc;

    Thread gameRun = new Thread(this);

    /**
     * Constructs a new {@code Game} instance and initializes the game.
     */
    public Game() {
        sc = new Scanner(System.in);
        initialize();
    }

    /**
     * Sets up the initial game configuration.
     * Creates a level, player, and polymorphic enemies.
     */
    private void initialize() {
        Level level = new Level(13, 11);
        Player player = new Player(1, 1);
        List<Enemy> enemies = new ArrayList<>();
        List<Bomb> bombs = new ArrayList<>();

        enemies.add(new Shadowling(5, 5));
        enemies.add(new Shadowling(6, 8)); //temp replacement
        //enemies.add(new Wraith(8, 3));
        //TODO you too OP bruh we gonna remove you for now
        enemies.add(new Charger(10, 7));

        state = new GameState(level, player, enemies, bombs, 0);
        gameOver = false;
    }

    /**
     * Starts the main game loop.
     * Processes input, updates the map, and renders the current state.
     */
    public void start() {
        gameRun.start();

        while (!gameOver) {
            printBoard();
            System.out.println("Lives: " + state.getPlayer().getLives() +
                            " | Bombs: " + state.getPlayer().getAvailableBombs() +
                            " | Turn: " + state.getTurnCounter());
            System.out.print("[WASD] Move | B Bomb | Z Save | X Load | Q Quit: ");
            String input = sc.nextLine().trim().toUpperCase();

            switch (input) {
                case "Q":
                    System.out.println("You quit the game.");
                    gameOver = true;
                    break;
                case "Z": // ✅ Save
                    saveGame();
                    break;
                case "X": // ✅ Load
                    loadGame();
                    break;
                default:
                    processInput(input);

                    //put in GameRun
                    /* 
                    updateGame();
                    checkGameState();
                    state.incrementTurn();
                    */
                    checkGameState();

                    break;
            }
        }
    }

    /**
     * Handles player input and movement.
     *
     * @param input user command
     */
    private void processInput(String input) {
        Player player = state.getPlayer();
        Level level = state.getLevel();

        int dx = 0, dy = 0;
        switch (input) {
            case "W": dy = -1; break;
            case "S": dy = 1; break;
            case "A": dx = -1; break;
            case "D": dx = 1; break;
            case "B": placeBomb(); return;
            case "Z": saveGame(); return;
            case "X": loadGame(); return;
            case "Q": System.out.println("You quit the game."); gameOver = true; return;
            default: System.out.println("Invalid input!"); return;
        }
        player.move(dx, dy, level);
    }

    /**
     * Places a bomb if available.
     */
    private void placeBomb() {
        Player player = state.getPlayer();
        List<Bomb> bombs = state.getBombs();

        if (player.canPlaceBomb()) {
            bombs.add(new Bomb(player.getX(), player.getY(), 6, 1));
            player.placeBomb();
        } else {
            System.out.println("No bombs available!");
        }
    }

    /**
     * Updates bombs, explosions, and enemies.
     */
    private void updateGame() {
        Player player = state.getPlayer();
        Level level = state.getLevel();
        List<Enemy> enemies = state.getEnemies();
        List<Bomb> bombs = state.getBombs();

        List<Explosion> explosions = new ArrayList<>();

        // Bomb updates
        Iterator<Bomb> it = bombs.iterator();
        while (it.hasNext()) {
            Bomb b = it.next();
            b.tick();
            if (b.hasExploded()) {
                explosions.addAll(b.explode(level));
                player.recoverBomb();
                it.remove();
            }
        }

        // Explosion damage
        for (Explosion e : explosions) {
            if (player.getX() == e.x && player.getY() == e.y) {
                player.loseLife();
                System.out.println("You got hit by an explosion!");
            }
            for (Enemy enemy : enemies) {
                if (enemy.getX() == e.x && enemy.getY() == e.y) {
                    enemy.setDead(true);
                    System.out.println(enemy.getClass().getSimpleName() + " was destroyed!");
                }
            }
        }

        enemies.removeIf(Enemy::isDead);

        // Enemy AI behavior
        for (Enemy enemy : enemies) {
            enemy.update(level, player);
            if (enemy.getX() == player.getX() && enemy.getY() == player.getY()) {
                player.loseLife();
                System.out.println("❌ " + enemy.getClass().getSimpleName() + " caught you!");
            }
        }
    }

    /**
     * Checks for win/loss conditions.
     */
    private void checkGameState() {
        Player player = state.getPlayer();
        Level level = state.getLevel();

        if (player.getLives() <= 0) {
            gameOver = true;
            System.out.println("Game Over! You lost all your lives.");
        }

        Tile t = level.getTile(player.getX(), player.getY());
        if (t.getType() == Tile.Type.EXIT) {
            System.out.println("You reached the exit! You win!");
            gameOver = true;
        }
    }

    /**
     * Renders the map, entities, and player using ASCII symbols.
     */
    private void printBoard() {
        Level level = state.getLevel();
        Player player = state.getPlayer();
        List<Enemy> enemies = state.getEnemies();
        List<Bomb> bombs = state.getBombs();

        char[][] grid = new char[level.getHeight()][level.getWidth()];

        // Draw terrain
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                grid[y][x] = level.getTile(x, y).getSymbol();
            }
        }

        // Draw entities
        for (Enemy e : enemies) grid[e.getY()][e.getX()] = e.getLogo();
        for (Bomb b : bombs) grid[b.getY()][b.getX()] = 'O';
        grid[player.getY()][player.getX()] = 'P';

        // Print
        System.out.println();
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // ---------------- SAVE / LOAD ----------------

    /**
     * Saves the current {@link GameState} to disk.
     */
    private void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savegame.dat"))) {
            out.writeObject(state);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Failed to save game: " + e.getMessage());
        }
    }

    /**
     * Loads a previously saved {@link GameState} from disk.
     */
    private void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            state = (GameState) in.readObject();
            System.out.println("Game loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load game: " + e.getMessage());
        }
    }

    public void run(){
        /*
        for(int i = 1; i <= 3; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(i);

            if (i == 3) {
                System.out.println("Times up!");
                System.exit(0);
                //ends both this and main thread
            }
        }
        */

        while (!gameOver) {
            try {
                Thread.sleep(5000);
                //game updates itself every 5 seconds (placeholder time)
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }

            updateGame();
            checkGameState();
            state.incrementTurn();
            printBoard();
            System.out.println("Lives: " + state.getPlayer().getLives() +
                            " | Bombs: " + state.getPlayer().getAvailableBombs() +
                            " | Turn: " + state.getTurnCounter());
            System.out.print("[WASD] Move | B Bomb | Z Save | X Load | Q Quit: ");

            if (gameOver){
                System.exit(0);
            }
        }
    }
}
