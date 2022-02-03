package playersOnServer;

import controllerPackage.Player;
import controllerPackage.PlayerStatus;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PlayersOnServerController implements Initializable{

    @FXML
      private VBox area;

    @FXML
    private Button backBtn;
    Vector<Player> playersVector;
   ArrayList<Node> nodes;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        playersVector = new Vector<Player>();
        nodes = new ArrayList<>();
        PlayersOnServerUtility.setParentContainer(nodes, playersVector, area);
        
    }
@FXML
    void handleBackBtn(ActionEvent event) {
        PlayersOnServerUtility.goSceneBack();
    }

}
