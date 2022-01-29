/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllerPackage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class PlayersSceneController implements Initializable{

    @FXML
    private FlowPane  playersPane;
    @FXML
    private Button backBtn;
    Vector<Player> playersVector;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PlayersSceneUtility.setPlayersFlowPane(playersPane);
        playersVector = new Vector<Player>();
//        playersVector.add(new Player(21 , "Amany" , "Bahaa" , "eee" , 4 ,5 ,PlayerStatus.OFFLINE));
//        playersVector.add(new Player(21 , "Islam Leader" , "Kortam" , "eee" , 4 ,5 ,PlayerStatus.OFFLINE));
    }

    @FXML
    void handleBackBtn(ActionEvent event) {
        PlayersSceneUtility.goSceneBack();
//        PlayersSceneUtility.setButtonForPlayers(playersVector);
    }

}
