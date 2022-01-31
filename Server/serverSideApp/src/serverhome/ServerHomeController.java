/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverhome;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author Salma
 */
public class ServerHomeController implements Initializable {
      
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
             ServerHomeUtility.changeStatusToOffline();
           startServer.setDisable(false);
             stopSever.setDisable(true);}

    @FXML
    void goLive(MouseEvent event) {
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
    ServerHomeUtility server = new ServerHomeUtility(logs, startServer, stopSever,statusText, statusImg);
    }    
    
    
}
