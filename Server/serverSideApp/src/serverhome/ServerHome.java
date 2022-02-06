/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverhome;

import controllerPackage.Player;
import controllerPackage.PlayerHandler;
import controllerPackage.PlayerStatus;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import playersOnServer.PlayersOnServerUtility;

/**
 *
 * @author Salma
 */
public class ServerHome extends Application {
   
   

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("serverHome.fxml"));
       
        Scene scene = new Scene(root);
        ServerHomeUtility.setScene(scene);
        PlayersOnServerUtility.setMainStage(stage);
        stage.setScene(scene);
        stage.show();
        logPlayers();
        
    }
    
    private void logPlayers(){
        for(PlayerHandler player : PlayerHandler.getPlayers()){
            ServerHomeUtility.updateLogs("+ Added: " + player.getUserName() + ":" + player.getStatus());
        }
    }

    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String[] args) {
        launch(args);
    }

    */
    
}
