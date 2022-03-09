/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverhome;

import ServerLogicClasses.UserHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author Salma
 */
public class ServerHomeController implements Initializable {
    private static ServerHomeController ref = null;
      
   @FXML
    private ImageView playerList;

    @FXML
    private Button startServer;

    @FXML
    private ImageView statusImg;

    @FXML
    private Text statusText;

    @FXML
    private Button stopSever;
    @FXML
    private TextArea logs;


    @FXML
    void beOffline(ActionEvent event) {
        ServerLogicClasses.ServerMulti.stopServer();
        UserHandler.stopAllUsers();
             ServerHomeUtility.changeStatusToOffline();
           startServer.setDisable(false);
             stopSever.setDisable(true);}

    @FXML
    void goLive(MouseEvent event) {
            ServerLogicClasses.ServerMulti.startServer();
           ServerHomeUtility.changeStatusToOnline();
             startServer.setDisable(true);
             stopSever.setDisable(false);
    }

    @FXML
    void showPlayers(MouseEvent event) throws IOException {
      ServerHomeUtility.showPlayerList(event);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ref = this;
        //toOffline();
        toOffline();
        ServerHomeUtility server = new ServerHomeUtility(logs, startServer, stopSever,statusText, statusImg);
    }    
    
    public static void toOnline(){
        ServerHomeUtility.changeStatusToOnline();
        ref.startServer.setDisable(true);
        ref.stopSever.setDisable(false);
    }
    
    public static void toOffline(){
        ServerHomeUtility.changeStatusToOffline();
        ref.startServer.setDisable(false);
        ref.stopSever.setDisable(true);
    }
    
    
    public static void showDB_ConnectionError_Alert()  {
        String alertHeader = "Database connection Error!";
        String body = "Aborting";
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.CONFIRMATION);
                a.setHeaderText(alertHeader);
                a.setContentText(body);
                a.showAndWait();
                Platform.exit();
                System.exit(0);
            }
        });
    }
}
