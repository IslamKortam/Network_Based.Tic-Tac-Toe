package playersOnServer;

import controllerPackage.Player;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class PlayersOnServerController implements Initializable{

    @FXML
    private FlowPane playersPane;

    @FXML
    private Button backBtn;

    

@Override
    public void initialize(URL url, ResourceBundle rb) {
        PlayersOnServerUtility.setPlayersFlowPane(playersPane);
        
    }
@FXML
    void handleBackBtn(ActionEvent event) {
        PlayersOnServerUtility.goSceneBack();
    }

}
