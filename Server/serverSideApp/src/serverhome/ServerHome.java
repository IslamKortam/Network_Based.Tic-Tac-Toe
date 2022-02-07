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
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        
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
