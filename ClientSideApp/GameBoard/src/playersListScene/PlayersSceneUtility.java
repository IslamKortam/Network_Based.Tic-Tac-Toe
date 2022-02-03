/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package playersListScene;

import controllerPackage.Player;
import controllerPackage.PlayerStatus;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Bahaa eldin
 */
public class PlayersSceneUtility {

    static ArrayList<PlayerElements> playersElementsArray = new ArrayList<PlayerElements>();
    static Vector<Player> Players;
    private static Scene scene;
    private static Button invite;
    private static Label name;
    private static ImageView playerImg;
    private static Label score;
    private static Label status;
    private static VBox area;
    private static ArrayList<Node> nodes;

    static void goSceneBack() {
        System.out.println("Back Button Pressed.....");
    }

    static void appendNewPlayer(Vector<Player> PlayersVector) throws IOException {
        Players = PlayersVector;
        System.out.println(Players.size());
        for (Player p : Players) {
            createNodes(p);
        }
        System.out.println(nodes.size());
    }
    static void createNodes(Player p) throws IOException{
        Image img = new Image(playersListScene.PlayersScene.class.getResourceAsStream("/resources/AvatarImgs/" + Integer.toString(p.getIconIndex()) + ".png"));
        Node n = FXMLLoader.load(playersListScene.PlayersSceneController.class.getResource("playerContainer.fxml"));
        name.setText(p.getUserName());
        score.setText(Integer.toString(p.getScore()));
        status.setText(p.getStatus().toString());
        playerImg.setImage(img);
        nodes.add(n);
        area.getChildren().add(n);
        PlayerElements newPlayer = new PlayerElements();
        newPlayer.setImg(img);
        newPlayer.setImgView(playerImg);
        newPlayer.setUsrName(name);
        newPlayer.setScore(score);
        newPlayer.setStatus(status);
        newPlayer.setInviteBtn(invite);
        newPlayer.setUserId(p.getId());
        playersElementsArray.add(newPlayer);
        inviteBtnHandler();
        
    }

    public static void initScene() throws IOException {
        Parent root = FXMLLoader.load((playersListScene.PlayersScene.class).getResource("PlayersSceneFXML.fxml"));
        scene = new Scene(root);
    }

    public static Scene getScene() {
        return scene;
    }

    static void setContainerNodes(ImageView playerImg, Label name, Label score, Label status, Button invite) {
        PlayersSceneUtility.playerImg = playerImg;
        PlayersSceneUtility.name = name;
        PlayersSceneUtility.score = score;
        PlayersSceneUtility.status = status;
        PlayersSceneUtility.invite = invite;
    }

    static void setParentContainer(ArrayList<Node> nodes, Vector<Player> playersVector, VBox area) {
        PlayersSceneUtility.Players = playersVector;
        PlayersSceneUtility.area = area;
        PlayersSceneUtility.nodes = nodes;
    }

    static void inviteBtnHandler() {
//    //To handle actions on the invite button for each player
        playersElementsArray.forEach(player
                -> player.getInviteBtn().setOnMouseClicked(mouseEvent -> {
                    System.out.println("user pressed " + player.getUserId());
                }));
    }

    static void updatePlayer(int userID) {
        PlayerElements playerElement;
        for (PlayerElements element : playersElementsArray) {
            if (element.getUserId() == userID) {
                playerElement = element;
                for (Player Player : Players) {
                    if (Player.getId() == userID) {
                        playerElement.getScore().setText(Integer.toString(Player.getScore()));
                        System.out.println(playerElement.getScore().getText());
                        playerElement.getStatus().setText(Player.getStatus().toString());

                    }
                }
            }
        }
    }
//may cause problems
    static void addNewPlayer(int userID) throws IOException {
        for (Player p : Players) {
            if (p.getId() == userID) {
                createNodes(p);
                System.out.println(userID);
            }
        }
    }

    public static void setPlayers(Vector<Player> Players) {
        PlayersSceneUtility.Players = Players;
    }

}

// 
//   
//   

