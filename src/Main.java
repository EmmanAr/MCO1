
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main.java
 * 
 * Entry point for the Shadow Escape console game.
 * 
 * This class simply initializes and starts the {@link Game} object,
 * which manages all setup, input handling, and game progression logic.
 * 
 * Authors: Arana, John Emmanuel M. and Sanchez, Arkin Julian C.
 * Date: October 2025
 * 
 * @version 15.6
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GameMenuView.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Shadow Escape");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Failed to load the main menu. Make sure GameMenuView.fxml is in the correct location.");
            e.printStackTrace();
        }
    }

    /**
     * Launches the Shadow Escape game by creating a {@link Game} instance
     * and invoking its {@link Game#start()} method.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
