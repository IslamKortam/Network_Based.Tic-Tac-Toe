/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playersOnServer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Salma
 */
public class PlayerContainerController implements Initializable {

    

    @FXML
    private Label name;

    @FXML
    private ImageView playerImg;

    @FXML
    private Label score;

    @FXML
    private Label status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
     PlayersOnServerUtility.setContainerNodes(playerImg,name,score,status);
    }    
    
}
