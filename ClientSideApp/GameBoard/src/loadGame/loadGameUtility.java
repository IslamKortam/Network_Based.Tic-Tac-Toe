/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadGame;

import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;


/**
 *
 * @author Salma
 */
class loadGameUtility {

    private static TableColumn<Game, Date> date;
    private static TableColumn<Game, Integer> gameId;
    private static TableColumn<Game, String> player2Name;
    private static TableColumn<Game, String> player2Status;
    private static TableView<Game> table;
    private static TableColumn load;
    private static  ObservableList<Game> data = FXCollections.observableArrayList();
     private static Game g;
    
    public static void setNodes(TableView<Game> table, TableColumn<Game, Integer> gameId, TableColumn<Game, Date> date, TableColumn<Game, String> player2Name, TableColumn<Game, String> player2Status, ObservableList<Game> data,TableColumn<Game, Button> load) {
       loadGameUtility.date = date;
        loadGameUtility.gameId = gameId;
        loadGameUtility.player2Name = player2Name;
        loadGameUtility.player2Status = player2Status;
         loadGameUtility.table = table;
         loadGameUtility.data = data;
         loadGameUtility.load = load;}

public static void appendData(int gameId,Date date,String p2name,String p2status){
     loadGameUtility. g = new Game(gameId, p2name, p2status, date,"");
     data.add(g);
     table.setItems(data);
    
    }
public static void clickOnButton(){
      
       for (Game g:table.getItems()){
                 Button load=g.getLoad();
                 load.setOnAction((event) -> {
                     System.out.println(g.getPlayer2Name());
                 });
       
       }}
       
       public static void reset(){
          table.getItems().clear();
       }

     
}


