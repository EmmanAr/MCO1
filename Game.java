import java.util.*;

/**
 * Game.java
 * 
 * Controls the overall flow of the Shadow Escape console version.
 * Handles user input, updating bombs, enemies, and map display.
 */
public class Game {
    private Level level;
    private Player player;
    private List<Enemy> enemies;
    private List<Bomb> bombs;
    private boolean gameOver;
    private int turnCounter;
    private Scanner sc;

    /**
     * Constructor initializes the game state and creates the map.
     */
    public Game() {
        sc = new Scanner(System.in);
        initialize();
    }

    /**
     * Initializes a new level, player, and enemies.
     */
    private void initialize() {
        level = new Level(13, 11);
        player = new Player(1, 1);
        enemies = new ArrayList<>();
        bombs = new ArrayList<>();
        gameOver = false;
        turnCounter = 0;

        // Add sample enemies
        enemies.add(new Enemy(5, 5));
        enemies.add(new Enemy(8, 3));
        enemies.add(new Enemy(10, 7));
    }

    /**
     * Main game loop. Handles user input and turn updates.
     */
    public void start() {
        while (!gameOver) {
            printBoard();
            System.out.println("Lives: " + player.getLives() + 
                               " | Bombs: " + player.getAvailableBombs() +
                               " | Turn: " + turnCounter);
            System.out.print("Move (WASD), B=Bomb, Q=Quit: ");
            String input = sc.nextLine().trim().toUpperCase();

            if (input.equals("Q")) {
                System.out.println("You quit the game.");
                break;
            }

            processInput(input);
            updateGame();
            checkGameState();
            turnCounter++;
        }
    }

    /**
     * Processes player keyboard input.
     */
    private void processInput(String input) {
        int dx = 0, dy = 0;
        switch (input) {
            case "W": dy = -1; break;
            case "S": dy = 1; break;
            case "A": dx = -1; break;
            case "D": dx = 1; break;
            case "B":
                placeBomb();
                return;
            default:
                System.out.println("Invalid input!");
                return;
        }
        player.move(dx, dy, level);
    }

    /**
     * Handles bomb placement by player.
     */
    private void placeBomb() {
        if (player.canPlaceBomb()) {
            bombs.add(new Bomb(player.getX(), player.getY(), 6));
            player.placeBomb();
        } else {
            System.out.println("No bombs available!");
        }
    }

    /**
     * Updates all entities and resolves interactions (explosions, enemies, etc.).
     */
    private void updateGame() {
        List<Explosion> explosions = new ArrayList<>();

        // Bomb timer countdown and explosion
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

        // Handle explosion effects
        for (Explosion e : explosions) {
            if (player.getX() == e.x && player.getY() == e.y) {
                player.loseLife();
                System.out.println("💥 You got hit by an explosion!");
            }
            for (Enemy enemy : enemies) {
                if (enemy.getX() == e.x && enemy.getY() == e.y) {
                    enemy.setDead(true);
                }
            }
        }

        // Remove dead enemies
        enemies.removeIf(Enemy::isDead);

        // Enemy movement and collision
        for (Enemy enemy : enemies) {
            enemy.moveRandom(level);
            if (enemy.getX() == player.getX() && enemy.getY() == player.getY()) {
                player.loseLife();
                System.out.println("❌ You touched an enemy!");
            }
        }
    }

    /**
     * Checks whether the player wins or loses.
     */
    private void checkGameState() {
        if (player.getLives() <= 0) {
            gameOver = true;
            System.out.println("Game Over! You lost all lives.");
        }

        Tile t = level.getTile(player.getX(), player.getY());
        if (t.getType() == Tile.Type.EXIT) {
            System.out.println("🏁 You reached the exit! You win!");
            gameOver = true;
        }
    }

    /**
     * Prints the current state of the map to the console.
     */
    private void printBoard() {
        char[][] grid = new char[level.getHeight()][level.getWidth()];

        // Draw terrain
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                Tile t = level.getTile(x, y);
                switch (t.getType()) {
                    case FLOOR: grid[y][x] = '.'; break;
                    case SOFT_WALL: grid[y][x] = 'S'; break;
                    case HARD_WALL: grid[y][x] = '#'; break;
                    case EXIT: grid[y][x] = 'E'; break;
                }
            }
        }

        // Draw entities
        for (Enemy e : enemies) grid[e.getY()][e.getX()] = 'X';
        for (Bomb b : bombs) grid[b.getY()][b.getX()] = 'O';
        grid[player.getY()][player.getX()] = 'P';

        // Print grid
        System.out.println();
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
