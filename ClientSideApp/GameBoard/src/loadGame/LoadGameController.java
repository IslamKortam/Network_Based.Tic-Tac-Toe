/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadGame;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Salma
 */
public class LoadGameController implements Initializable {

     @FXML
    private TableColumn<Game, Date> date;

    @FXML
    private TableColumn<Game, Integer> gameId;

    @FXML
    private TableColumn<Game, String> player2Name;

    @FXML
    private TableColumn<Game, String> player2Status;

    @FXML
    private TableView<Game> table;
      @FXML
    private Button reset;

       @FXML
    private Button home;
     @FXML
    void goToHome(ActionEvent event) {
         System.out.println("homePage");
    }

    @FXML
    void resetAction(ActionEvent event) {
        loadGameUtility.reset();
    }
   
     private final ObservableList<Game> data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn load=new TableColumn("");
       loadGameUtility.setNodes(table,gameId,date,player2Name,player2Status,data,load);
       table.getColumns().add(load);

        gameId.setCellValueFactory(
                new PropertyValueFactory<>("gameId"));

        player2Name.setCellValueFactory(
                new PropertyValueFactory<>("player2Name"));

        date.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        player2Status.setCellValueFactory(
                new PropertyValueFactory<>("player2Status"));
          load.setCellValueFactory(
                new PropertyValueFactory<>("load"));
          
      
     
        
        
    }    
    
}
