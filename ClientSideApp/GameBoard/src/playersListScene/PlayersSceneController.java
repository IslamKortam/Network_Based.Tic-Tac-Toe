/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package playersListScene;

import controllerPackage.Player;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PlayersSceneController implements Initializable{

    @FXML
      private VBox area;

    @FXML
    private Button backBtn;
    Vector<Player> playersVector;
   ArrayList<Node> nodes;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        //playersVector = new Vector<Player>();
        nodes = new ArrayList<>();
        PlayersSceneUtility.setParentContainer(nodes, area);

    }

    @FXML
    void handleBackBtn(ActionEvent event) {
        PlayersSceneUtility.goSceneBack();
//        PlayersSceneUtility.setButtonForPlayers(playersVector);
    }

}
