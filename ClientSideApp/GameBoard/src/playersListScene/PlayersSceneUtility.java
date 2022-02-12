/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package playersListScene;

import CommHandlerPK.ClientConnectionHandler;
import CommunicationMasseges.CommunicationMassege;
import CommunicationMasseges.CommunicationMassegeType;
import CommunicationMasseges.Invitation;
import ParserPackage.Parser;
import controllerPackage.Player;
import controllerPackage.PlayerStatus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author Bahaa eldin
 */
public class PlayersSceneUtility {

    static ArrayList<PlayerElements> playersElementsArray = new ArrayList<PlayerElements>();
    //static Vector<Player> Players ;
    private static Scene scene;
    private static Button invite;
    private static Label name;
    private static ImageView playerImg;
    private static Label score;
    private static Label status;
    private static VBox area;
    private static ArrayList<Node> nodes;
    public static Parent ref;
    //private static ClientConnectionHandler handler;

    
    static void goSceneBack() {
        System.out.println("Back Button Pressed.....");
    }

    static void appendNewPlayer() throws IOException {
        Vector<Player> Players = Player.allPlayers;
        System.out.println(Players.size());
        for (Player p : Players) {
            createNodes(p);
        }
//        System.out.println(nodes.size());
    }
    
    static void createNodes(Player p){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Image img = new Image(playersListScene.PlayersSceneUtility.class.getResourceAsStream("/resources/AvatarImgs/" + Integer.toString(p.getIconIndex()) + ".png"));
                try {
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
                    inviteBtnHandler();
                    if((p.getStatus()==(PlayerStatus.IN_MULTIPLAYER_GAME)) || (p.getStatus()==PlayerStatus.IN_SINGLE_PLAYER_GAME)){
                        System.out.println(p.getStatus().toString());
                        invite.setDisable(true);
                        invite.focusTraversableProperty();  
                    }
                    
                    newPlayer.setUserId(p.getId());
                    playersElementsArray.add(newPlayer);
                    
                } catch (IOException ex) {
                    Logger.getLogger(PlayersSceneUtility.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public static void initScene() throws IOException {
        Parent root = FXMLLoader.load((playersListScene.PlayersSceneUtility.class).getResource("PlayersSceneFXML.fxml"));
        scene = new Scene(root);
        ref=root;
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

    static void setParentContainer(ArrayList<Node> nodes, VBox area) {
        
        PlayersSceneUtility.area = area;
        PlayersSceneUtility.nodes = nodes;
    }

    static void inviteBtnHandler() {
//    //To handle actions on the invite button for each player
        playersElementsArray.forEach(player
                -> player.getInviteBtn().setOnMouseClicked(mouseEvent -> {
//                    System.out.println("user pressed " + player.getUserId());
////////// here we used a dummy senderID , 
                Invitation invitation = new Invitation(Player.getThisPlayer().getId(),player.getUserId(), Invitation.InvitationType.NEW_GAME);
                CommunicationMassege invitationmsg = new CommunicationMassege(CommunicationMassegeType.INVITATION, Parser.gson.toJson(invitation));
                ClientConnectionHandler.ref.sendCommMsgToServer(invitationmsg);
                }));
    }

    public static void updatePlayer(int userID) {
        Vector<Player> Players = Player.allPlayers;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
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
        });
        
    }
    public static void addPlayerToVector(int id) throws IOException{
//        Players.add(new Player(9,"salma","Mohamed","salma@yahoo.com",4,5,PlayerStatus.IN_MULTIPLAYER_GAME));
        //Players.add(p);
        //System.out.println(Players.size() + "vector size");
        //addNewPlayer(id);
//        appendNewPlayer(Players);
    }
//may cause problems
    public static void addNewPlayer(int userID) throws IOException {
        Vector<Player> Players = Player.allPlayers;
        for (Player p : Players) {
            if (p.getId() == userID) {
                createNodes(p);
                System.out.println(userID);
            }
        }
    }
/*
    public static void setPlayers(Vector<Player> Players) {
        PlayersSceneUtility.Players = Players;
        System.out.println(Players.size());
    }
    
    */

}

// 
//   
//   

