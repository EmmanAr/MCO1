package mco1;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameController {
    
    private Parent root;
    private Stage stage;
    private Scene scene;
    
    @FXML
    private Button PlayGameButton;

/* 
    @FXML
    void PGButtonPressed(ActionEvent event) throws IOException{

        //sets up the game (both view and its code)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameRunView.fxml"));
        root = loader.load();
        Game game = loader.getController();

        //root = FXMLLoader.load(getClass().getResource("GameRunView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        Thread gameThread = new Thread(() -> {
        game.start();
        });
        gameThread.start();
    
    }
    */

    @FXML
    void PGButtonPressed(ActionEvent event) throws IOException {

        // Load the FXML layout only (no controller)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("C:\\Users\\johne\\OneDrive\\Documents\\GitHub\\MCO1\\bin\\mco1\\GameRunView.fxml"));
        root = loader.load();  // just loads the UI

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Create and start your game logic (Game.java)
        Game game = new Game();
        Thread gameThread = new Thread(() -> game.start());
        gameThread.start();
    }

}
