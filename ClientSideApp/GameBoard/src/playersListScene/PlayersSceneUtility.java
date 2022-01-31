/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package playersListScene;

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
public class PlayersSceneUtility {
    static ArrayList<PlayerElements> playersElementsArray = new ArrayList<PlayerElements>();
    static Vector<Player> Players = new Vector<>();
    private static Scene scene;
    static FlowPane playersFlowPane;

    public static void setPlayers(Vector<Player> Players) {
        PlayersSceneUtility.Players = Players;
    }
    public static void setPlayersFlowPane(FlowPane playersFlowPane) {
        PlayersSceneUtility.playersFlowPane = playersFlowPane;
    }
    
    static void goSceneBack(){
        System.out.println("Back Button Pressed.....");
    } 

    static void setButtonForPlayers(Vector<Player> PlayersVector){
        Players = PlayersVector;
        for(Player Player:PlayersVector )
        {
            PlayerElements newPlayer = new PlayerElements();

            Image img = new Image(playersListScene.PlayersScene.class.getResourceAsStream("/resources/AvatarImgs/"+Integer.toString(Player.getIconIndex())+".png"));
            ImageView usrImgView = new ImageView(img);
            Label usrNameLabel = new Label(Player.getUserName());
            Label scoreLabel = new Label(Integer.toString(Player.getScore()));
            Label statusLabel = new Label(Player.getStatus().toString());
            Button invite = new Button("challenge");

            newPlayer.setImg(img);
            newPlayer.setImgView(usrImgView);
            newPlayer.setUsrName(usrNameLabel);
            newPlayer.setScore(scoreLabel);
            newPlayer.setStatus(statusLabel);
            newPlayer.setInviteBtn(invite);
            newPlayer.setUserId(Player.getId());

            FlowPane playerPane = new FlowPane();
            playerPane.getChildren().addAll(usrImgView , usrNameLabel  ,new Label("  "), scoreLabel , new Label("  ") ,statusLabel , newPlayer.getInviteBtn());
            
            playersFlowPane.getChildren().add(playerPane);
            // Adding the newPlayer object to the ArrayList Which contains elements for each new player 
            playersElementsArray.add(newPlayer);
            inviteBtnHandler();
        }
    }

    static void addNewPlayer(int userID)
    {
        for(Player Player:Players)
        {
            if(Player.getId()==userID)
            {
                PlayerElements newPlayer = new PlayerElements();
                newPlayer.setImgView(new ImageView(new Image(playersListScene.PlayersScene.class.getResourceAsStream("/resources/AvatarImgs/"+Integer.toString(Player.getIconIndex())+".png"))));
                newPlayer.setUsrName(new Label(Player.getUserName()));
                newPlayer.setScore(new Label(Integer.toString(Player.getScore())));
                newPlayer.setStatus(new Label(Player.getStatus().toString()));
                newPlayer.setInviteBtn(new Button("challenge"));

            }
        }
    }
    static void updatePlayer(int userID)
    {
        PlayerElements playerElement;
        for(PlayerElements element:playersElementsArray){
            if(element.getUserId() == userID){
                playerElement= element;
            for(Player Player:Players)
                {
                    if(Player.getId()==userID)
                    {
                        playerElement.setScore(new Label(Integer.toString(Player.getScore())));
                        playerElement.setStatus(new Label(Player.getStatus().toString()));

                    }
                }
            }
        }
    }

    static void inviteBtnHandler(){
    //To handle actions on the invite button for each player
        playersElementsArray.forEach(player -> 
        player.getInviteBtn().setOnMouseClicked(mouseEvent -> {
        System.out.println("user pressed "+player.getUserId());
        }));
    }

    public static void initScene()throws IOException{
        Parent root = FXMLLoader.load((playersListScene.PlayersSceneUtility.class).getResource("PlayersSceneFXML.fxml"));
        scene = new Scene(root);
    }    

    public static Scene getScene(){
        return scene;
    }        
}
