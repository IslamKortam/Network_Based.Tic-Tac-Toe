/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package playersOnServer;

import controllerPackage.Player;
import controllerPackage.PlayerStatus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
/**
 *
 * @author Bahaa eldin
 */
public class PlayersOnServerUtility {
    static ArrayList<PlayerElements> playersElementsArray = new ArrayList<PlayerElements>();
    static Vector<Player> Players = new Vector<>();
    private static Scene scene;
    static FlowPane playersFlowPane;

    public static void setPlayers(Vector<Player> Players) {
        PlayersOnServerUtility.Players = Players;
    }
    public static void setPlayersFlowPane(FlowPane playersFlowPane) {
        PlayersOnServerUtility.playersFlowPane = playersFlowPane;
    }
    static void goSceneBack(){
        System.out.println("Back Button Pressed.....");
    }


static void setButtonForPlayers(Vector<Player> PlayersVector){
        Players = PlayersVector;
        for(Player Player:PlayersVector )
        {
            PlayerElements newPlayer = new PlayerElements();

            Image img = new Image(playersOnServer.PlayersOnServer.class.getResourceAsStream("/resources/AvatarImgs/"+Integer.toString(Player.getIconIndex())+".png"));
            ImageView usrImgView = new ImageView(img);
            Label usrNameLabel = new Label(Player.getUserName());
            Label scoreLabel = new Label(Integer.toString(Player.getScore()));
            Label statusLabel = new Label(Player.getStatus().toString());

            newPlayer.setImg(img);
            newPlayer.setImgView(usrImgView);
            newPlayer.setUsrName(usrNameLabel);
            newPlayer.setScore(scoreLabel);
            newPlayer.setStatus(statusLabel);
            newPlayer.setUserId(Player.getId());

            FlowPane playerPane = new FlowPane();
            playerPane.getChildren().addAll(usrImgView , usrNameLabel  ,new Label("  "), scoreLabel , new Label("  ") ,statusLabel);
            
            playersFlowPane.getChildren().add(playerPane);
            // Adding the newPlayer object to the ArrayList Which contains elements for each new player 
            playersElementsArray.add(newPlayer);
//            inviteBtnHandler();
        }
    }

    public static void initScene()throws IOException{
        Parent root = FXMLLoader.load((playersOnServer.PlayersOnServer.class).getResource("PlayersOnServer.fxml"));
        scene = new Scene(root);
    }    

    public static Scene getScene(){
        return scene;
    }        





}
