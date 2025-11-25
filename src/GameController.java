import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * GameController.java
 * 
 * Manages the interaction between the User Interface (View) and the Game Logic (Model).
 * This class handles:
 * 
 * Transitioning from Menu to Game.
 * Capturing Keyboard Input (WASD/Arrows).
 * Running the Game Loop (Timer).
 * Rendering the Game State to the Canvas.
 * 
 */
public class GameController {

    @FXML
    private Button PlayGameButton;

    /** The core game logic instance. */
    private Game game;

    /** The canvas used to draw the game graphics. */
    private Canvas canvas;

    /** The size of one grid tile in pixels (40x40). */
    private static final int TILE_SIZE = 40;

    /**
     * Triggered when the "Play Game" button is clicked in the Menu.
     * Initializes the game engine, sets up the game scene, starts the loop,
     * and switches the window content.
     *
     * @param event the click event captured by JavaFX
     */
    @FXML
    void PGButtonPressed(ActionEvent event) {
        // Initialize the Game Logic
        game = new Game();

        // Setup the Game View (Canvas)
        // Level size is 13x11 based on Level.java dimensions
        int width = 13 * TILE_SIZE; 
        int height = 11 * TILE_SIZE;
        
        BorderPane root = new BorderPane();
        canvas = new Canvas(width, height);
        root.setCenter(canvas);
        
        Scene gameScene = new Scene(root);

        // Setup Input Handling (Keyboard)
        gameScene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            switch (code) {
                case W: game.processPlayerInput("W"); break;
                case S: game.processPlayerInput("S"); break;
                case A: game.processPlayerInput("A"); break;
                case D: game.processPlayerInput("D"); break;
                case B: game.processPlayerInput("B"); break; // Place Bomb
                case Z: game.processPlayerInput("Z"); break; // Save
                case X: game.processPlayerInput("X"); break; // Load
                default: break;
            }
            render(); // Redraw immediately after input for responsiveness
        });

        // Setup Game Loop (Timer)
        // Runs logic every 1.0 seconds. Adjust Duration.seconds(X) to change game speed.
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.seconds(1.0), e -> {
            if (!game.isGameOver()) {
                game.updateGameLogic();
                render();
            }
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

        // Switch the Window (Stage) to the Game Scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(gameScene);
        stage.centerOnScreen();
        
        // Initial Draw
        render();
    }

    /**
     * Draws the current state of the Game object to the JavaFX Canvas.
     * This method clears the screen and redraws the grid, entities, and HUD
     * based on the latest data from {@code GameState}.
     */
    private void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GameState state = game.getState();
        Level level = state.getLevel();
        Player p = state.getPlayer();

        // Clear background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw Tiles
        for (int x = 0; x < 13; x++) {
            for (int y = 0; y < 11; y++) {
                Tile tile = level.getTile(x, y);
                drawTile(gc, x, y, tile);
            }
        }

        // Draw Bombs
        for (Bomb b : state.getBombs()) {
            gc.setFill(Color.BLACK);
            gc.fillOval(b.getX() * TILE_SIZE + 5, b.getY() * TILE_SIZE + 5, TILE_SIZE - 10, TILE_SIZE - 10);
            // Draw Fuse Timer text on top of bomb
            gc.setFill(Color.WHITE);
            gc.fillText(String.valueOf(b.getTimer()), b.getX() * TILE_SIZE + 15, b.getY() * TILE_SIZE + 25);
        }

        // Draw Enemies (Color coded by type)
        for (Enemy e : state.getEnemies()) {
            if (e instanceof Charger) gc.setFill(Color.ORANGERED);
            else if (e instanceof Wraith) gc.setFill(Color.PURPLE);
            else gc.setFill(Color.RED); // Default Shadowling
            
            gc.fillRect(e.getX() * TILE_SIZE + 5, e.getY() * TILE_SIZE + 5, TILE_SIZE - 10, TILE_SIZE - 10);
        }

        // Draw Player
        gc.setFill(Color.CYAN);
        gc.fillRect(p.getX() * TILE_SIZE + 5, p.getY() * TILE_SIZE + 5, TILE_SIZE - 10, TILE_SIZE - 10);

        // Draw HUD / Game Over Screen overlay
        if (game.isGameOver()) {
            gc.setFill(new Color(0, 0, 0, 0.7)); // Semi-transparent black overlay
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.setFill(Color.WHITE);
            gc.setFont(new Font(30));
            String msg = game.isVictory() ? "VICTORY!" : "GAME OVER";
            gc.fillText(msg, canvas.getWidth() / 2 - 80, canvas.getHeight() / 2);
        } else {
            // Live HUD
            gc.setFill(Color.WHITE);
            gc.setFont(new Font(14));
            gc.fillText("Lives: " + p.getLives(), 10, 20);
            gc.fillText("Turn: " + state.getTurnCounter(), 80, 20);
        }
    }

    /**
     * Helper method to draw a specific tile type at grid coordinates.
     *
     * @param gc   the GraphicsContext used for drawing
     * @param x    the grid X coordinate
     * @param y    the grid Y coordinate
     * @param tile the Tile object containing type information
     */
    private void drawTile(GraphicsContext gc, int x, int y, Tile tile) {
        if (tile == null) return;
        switch (tile.getType()) {
            case HARD_WALL:
                gc.setFill(Color.DARKGRAY);
                gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                break;
            case SOFT_WALL:
                gc.setFill(Color.SADDLEBROWN);
                gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                // Add a border to soft walls for detail
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                break;
            case FLOOR:
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                break;
            case EXIT:
                gc.setFill(Color.GOLD);
                gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                break;
        }
    }
}