/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Bahaa eldin
 */
public class PlayersSceneUtility {
    private static Scene scene;

    static String score;
    static String userName;
    static PlayerStatus status;

    static FlowPane   playersFlowPane;

    public static void setPlayersFlowPane(FlowPane playersFlowPane) {
        PlayersSceneUtility.playersFlowPane = playersFlowPane;
    }
    
    static void goSceneBack(){
        System.out.println("Back Button Pressed.....");
    } 
    static void setButtonForPlayers(Vector<Player> PlayersVector){
        for(Player Player:PlayersVector )
        {
            score = Integer.toString(Player.getScore());
            userName = Player.getUserName();
            status = Player.getStatus();

            Button usrNameBtn = new Button(userName);
            Label scoreLabel = new Label(score);
            Label statusLabel = new Label(status.toString());

            FlowPane playerPane = new FlowPane();
            playerPane.getChildren().addAll(usrNameBtn  , new Label("  "), scoreLabel , new Label("  ") , statusLabel);
            
            playersFlowPane.getChildren().add(playerPane);
        }
    }
    public static void initScene()throws IOException{
        Parent root = FXMLLoader.load((controllerPackage.PlayersSceneUtility.class).getResource("PlayersSceneFXML.fxml"));
        scene = new Scene(root);
    }    
    public static Scene getScene(){
        return scene;
    }    


    
}
