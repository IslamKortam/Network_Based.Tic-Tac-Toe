/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package playersListScene;

import controllerPackage.Player;
import controllerPackage.PlayerStatus;
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
          Player p4 = new Player(3, "Islam", "ISLAM KORTAM", "imkortam@gmail.com", 100, 1, PlayerStatus.ONLINE);
        Player p5 = new Player(4, "Alaa", "Alaa KORTAM", "Alaa@gmail.com", 200, 0, PlayerStatus.OFFLINE);
        Player p6 = new Player(5, "Ahmed", "Ahmed KORTAM", "Ahmed@gmail.com", 300, 2, PlayerStatus.IN_MULTIPLAYER_GAME);
        PlayersVector.add(p4);
        PlayersVector.add(p5);
        PlayersVector.add(p6);
        
        PlayersVector.add(p1);

        PlayersVector.add(p2);
        PlayersVector.add(p3);


//PlayersSceneUtility.setPlayers(PlayersVector);
//PlayersSceneUtility.addNewPlayer(0);
//PlayersSceneUtility.addNewPlayer(1);
//PlayersSceneUtility.addNewPlayer(2);
//PlayersSceneUtility.addNewPlayer(3);

        PlayersSceneUtility.appendNewPlayer(PlayersVector);

//        PlayersSceneUtility.updatePlayer(1);

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
