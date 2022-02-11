/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadGame;

import java.io.IOException;
import java.sql.Date;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Salma
 */
public class loadGame extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loadGame.fxml"));
        
        Scene scene = new Scene(root);
     //testing
            loadGameUtility.appendData(0, new Date(1,5,2020), "ahmed","ismaeel");
            loadGameUtility.appendData(1, new Date(1,5,2020), "salma","ismaeel");
            loadGameUtility.appendData(2, new Date(1,5,2020), "bassant","ismaeel");
            loadGameUtility.appendData(3, new Date(1,5,2020), "mona","ismaeel");
            loadGameUtility.clickOnButton();
  
        stage.setScene(scene);
        stage.show();
    }
        
       
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
