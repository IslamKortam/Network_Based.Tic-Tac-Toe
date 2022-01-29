/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package gameboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import logintrial.LoginUtility;
import xoSignupPkg.SignUpUtility;


/**
 *
 * @author Bahaa eldin
 */
public class GameBoard extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
//        System.out.println(getClass());
        
//        Scene scene = new Scene(root);
        
        GameBoardUtility.initScene();
        LoginUtility.initScene();
        SignUpUtility.initScene();
        modes.ModesUtility.initScene();
        
        stage.setScene(modes.ModesUtility.getScene());
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
