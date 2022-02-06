/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package logintrial;

import controllerPackage.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import stagemanager.StageManager;


/**
 *
 * @author Bahaa eldin
 */
public class LoginTrial extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        StageManager stageManger = new StageManager(stage);
        MainController.getRef().setStageMagner(stageManger);
    }

    /**
     * @param args the command line arguments
     */
    
    
}
