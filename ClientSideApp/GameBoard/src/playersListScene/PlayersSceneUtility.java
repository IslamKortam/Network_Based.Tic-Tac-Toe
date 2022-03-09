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
import gameboard.GameBoardController;
import gameboard.GameBoardUtility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import stagemanager.StageManager;

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
        
    }

    static void appendNewPlayer() throws IOException {
        Vector<Player> Players = Player.allPlayers;
        
        for (Player p : Players) {
            createNodes(p);
        }
//        System.out.println(nodes.size());
    }

    static void createNodes(Player p) {
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
                    //inviteBtnHandler();
                    if ((p.getStatus() != PlayerStatus.ONLINE)) {
                        //System.out.println(p.getStatus().toString());
                        invite.setDisable(true);
                        //invite.focusTraversableProperty();  
                    }

                    newPlayer.setUserId(p.getId());
                    playersElementsArray.add(newPlayer);
                    inviteBtnHandler(newPlayer.getUserId());

                } catch (IOException ex) {
                    Logger.getLogger(PlayersSceneUtility.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public static void initScene() throws IOException {
        Parent root = FXMLLoader.load((playersListScene.PlayersSceneUtility.class).getResource("PlayersSceneFXML.fxml"));
        scene = new Scene(root);
        ref = root;
        area.getChildren().add(FXMLLoader.load(playersListScene.PlayersSceneController.class.getResource("header.fxml")));
        
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

    static void inviteBtnHandler(int userId) {
        for (PlayerElements playerElement : playersElementsArray) {
            if (playerElement.getUserId() == userId) {
                playerElement.getInviteBtn().setOnMouseClicked(mouseEvent -> {
                    Invitation invitation = new Invitation(Player.getThisPlayer().getId(), playerElement.getUserId(), Invitation.InvitationType.NEW_GAME);
                    CommunicationMassege invitationmsg = new CommunicationMassege(CommunicationMassegeType.INVITATION, Parser.gson.toJson(invitation));
                    ClientConnectionHandler.ref.sendCommMsgToServer(invitationmsg);
                });
            }
        }
        /*
//    //To handle actions on the invite button for each player
        playersElementsArray.forEach(player
                -> player.getInviteBtn().setOnMouseClicked(mouseEvent -> {
//                    System.out.println("user pressed " + player.getUserId());
////////// here we used a dummy senderID , 
                    Invitation invitation = new Invitation(Player.getThisPlayer().getId(), player.getUserId(), Invitation.InvitationType.NEW_GAME);
                    CommunicationMassege invitationmsg = new CommunicationMassege(CommunicationMassegeType.INVITATION, Parser.gson.toJson(invitation));
                    ClientConnectionHandler.ref.sendCommMsgToServer(invitationmsg);
                }));
        */
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
                        Player player = Player.getPlayerByID(userID);
                        if(player == null)
                            return;
                        playerElement.getScore().setText(Integer.toString(player.getScore()));

                        playerElement.getStatus().setText(player.getStatus().toString());
                        if (player.getStatus() == PlayerStatus.ONLINE) {

                            playerElement.getInviteBtn().setDisable(false);
                            //playerElement.getInviteBtn().focusTraversableProperty();  
                        } else {
                            playerElement.getInviteBtn().setDisable(true);
                        }
                    }
                }
            }
        });

    }

    public static void addPlayerToVector(int id) throws IOException {
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
            }
        }
    }
    /*
    public static void setPlayers(Vector<Player> Players) {
        PlayersSceneUtility.Players = Players;
        System.out.println(Players.size());
    }
    
     */
    
    public static void resetScene(){
        Vector<Player> Players = Player.allPlayers;
        Players.clear();
        area.getChildren().clear();
        nodes.clear();
        playersElementsArray.clear();
        
    }
    
    
    public static void notifyNewUserSignedIn(int playerID) {
        Player player = Player.getPlayerByID(playerID);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setHeaderText("Notification");
                a.setContentText("Player: " + player.getUserName() + " is online!\n");
                a.show();
            }
        });
    }

}

// 
//   
//   

