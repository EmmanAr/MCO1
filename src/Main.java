import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Loads the User's provided Menu FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameMenuView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Shadow Escape");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Error loading GameMenuView.fxml. Ensure it is in the same folder as Main.java");
            e.printStackTrace();
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be launched
     * through deployment artifacts, e.g., in IDEs with limited FX support.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
