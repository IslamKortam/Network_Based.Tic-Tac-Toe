/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverhome;

import controllerPackage.Player;
import controllerPackage.PlayerStatus;
import java.io.IOException;
import java.util.Vector;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import playersOnServer.PlayersOnServerUtility;
import serverhome.ServerHomeController;

/**
 *
 * @author Salma
 */
public class ServerHomeUtility {
   static TextArea logs;
   static Button startServer;
   static Button stopServer;
   static Text status;
    static ImageView statusImg;

    public ServerHomeUtility(TextArea logs, Button startServer, Button stopServer, Text status, ImageView statusImg) {
        ServerHomeUtility.logs = logs;
        ServerHomeUtility.startServer = startServer;
        ServerHomeUtility.stopServer = stopServer;
        ServerHomeUtility.status = status;
        ServerHomeUtility.statusImg = statusImg;
    }
    
    
    
   public static  void changeStatusToOnline(){
       
       
       Platform.runLater(new Runnable() {
           @Override
           public void run() {
               status.setText("server is online");
               status.setFill(Color.GREEN);
               Image online = new Image(ServerHomeController.class.getResourceAsStream("/resources/online.png"));
               statusImg.setImage(online);
               logs.appendText("--------- server is now live ------\n");
           }
       });
       
         
   } 
   public static void changeStatusToOffline(){
          
        Platform.runLater(new Runnable(){
        
            @Override
            public void run() {
                 status.setText("server is offline");
         status.setFill(Color.RED);
           Image offline = new Image(ServerHomeController.class.getResourceAsStream("/resources/offline.png"));
               statusImg.setImage(offline);
         logs.appendText("\n------- server is turned off ------\n");

            }
       });
       
   } 
    public static void showPlayerList(Event e) throws IOException{
            PlayersOnServerUtility.initScene();
            Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
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
    
    public static void updateLogs(String update){
         logs.appendText("\n"+update);
    }
    public static void resetLogs(){
      logs.setText("");
    }
    
}
