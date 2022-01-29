/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package controllerPackage;

import java.util.Vector;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Bahaa eldin
 */
public class PlayersScene extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        PlayersSceneUtility.initScene();
        Vector<Player> PlayersVector = new Vector<>();
        Player p1 = new Player(0, "Islam", "ISLAM KORTAM", "imkortam@gmail.com", 100, 1, PlayerStatus.ONLINE);
        Player p2 = new Player(1, "Alaa", "Alaa KORTAM", "Alaa@gmail.com", 200, 0, PlayerStatus.OFFLINE);
        Player p3 = new Player(2, "Ahmed", "Ahmed KORTAM", "Ahmed@gmail.com", 300, 2, PlayerStatus.IN_MULTIPLAYER_GAME);
        PlayersVector.add(p1);
        PlayersVector.add(p2);
        PlayersVector.add(p3);
        
        
        PlayersSceneUtility.setButtonForPlayers(PlayersVector);
        
        stage.setScene(PlayersSceneUtility.getScene());
    
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
