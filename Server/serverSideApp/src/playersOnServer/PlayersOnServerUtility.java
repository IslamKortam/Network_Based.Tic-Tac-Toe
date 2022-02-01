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
public class PlayersOnServerUtility {
    static ArrayList<PlayerElements> playersElementsArray = new ArrayList<PlayerElements>();
    static Vector<Player> Players;
    private static Scene scene;
    private static Label name;
    private static ImageView playerImg;
    private static Label score;
    private static Label status;
    private static VBox area;
    private static ArrayList<Node> nodes;


    public static void setPlayers(Vector<Player> Players) {
        PlayersOnServerUtility.Players = Players;
    }
    static void goSceneBack(){
        System.out.println("Back Button Pressed.....");
    }

    static void createNodes(Player p) throws IOException{
        Image img = new Image(playersOnServer.PlayersOnServer.class.getResourceAsStream("/resources/AvatarImgs/" + Integer.toString(p.getIconIndex()) + ".png"));
        Node n = FXMLLoader.load(playersOnServer.PlayersOnServerController.class.getResource("playerContainer.fxml"));
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
        newPlayer.setUserId(p.getId());
        playersElementsArray.add(newPlayer);
        
    }
    static void appendNewPlayer(Vector<Player> PlayersVector) throws IOException {
        Players = PlayersVector;
        System.out.println(Players.size());
        for (Player p : Players) {
            createNodes(p);
        }
        System.out.println(nodes.size());
    }
static void setContainerNodes(ImageView playerImg, Label name, Label score, Label status) {
        PlayersOnServerUtility.playerImg = playerImg;
        PlayersOnServerUtility.name = name;
        PlayersOnServerUtility.score = score;
        PlayersOnServerUtility.status = status;
    }

    static void setParentContainer(ArrayList<Node> nodes, Vector<Player> playersVector, VBox area) {
        PlayersOnServerUtility.Players = playersVector;
        PlayersOnServerUtility.area = area;
        PlayersOnServerUtility.nodes = nodes;
    }

    public static void initScene()throws IOException{
        Parent root = FXMLLoader.load((playersOnServer.PlayersOnServer.class).getResource("PlayersOnServer.fxml"));
        scene = new Scene(root);
    }    

    public static Scene getScene(){
        return scene;
    }        





}
