/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package playersOnServer;

import controllerPackage.Player;
import controllerPackage.PlayerStatus;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import serverdao.Dao;

/**
 *
 * @author Bahaa eldin
 */
public class PlayersOnServer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        PlayersOnServerUtility.initScene();
        Vector<Player> PlayersVector = new Vector<>();
        Player p1 = new Player(0, "Islam", "ISLAM KORTAM", "imkortam@gmail.com", 100, 1, PlayerStatus.ONLINE);
        Player p2 = new Player(1, "Alaa", "Alaa KORTAM", "Alaa@gmail.com", 200, 0, PlayerStatus.OFFLINE);
        Player p3 = new Player(2, "Ahmed", "Ahmed KORTAM", "Ahmed@gmail.com", 300, 2, PlayerStatus.IN_MULTIPLAYER_GAME);
        PlayersVector.add(p1);
        PlayersVector.add(p2);
        PlayersVector.add(p3);
           
        PlayersOnServerUtility.appendNewPlayer(PlayersVector);
        stage.setScene(PlayersOnServerUtility.getScene());
    
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        
        launch(args);
 
    }
    
}

