/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package gameboard;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import stagemanager.StageManager;
import logintrial.LoginUtility;
import stagemanager.StageManager.SceneName;
import static stagemanager.StageManager.SceneName.GAMEMODE;
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
        
      //  GameBoardUtility.initScene();

        //stage.setScene(GameBoardUtility.getScene());
//         GameBoardUtility.setPlyer("salma", "4", 0, "Islam", "3", 5);

//        LoginUtility.initScene();
//        SignUpUtility.initScene();
//        modes.ModesUtility.initScene();
//        
//        stage.setScene(modes.ModesUtility.getScene());
        

      StageManager s; 
        s= new StageManager(stage);
        
      //  s.displayScene(StageManager.SceneName.SIGNUP);
        //  s.resetStage();
        //testing stage manager
        Thread th=new Thread(new Runnable(){
            @Override
            public void run() {
                for(SceneName i:SceneName.values()){
                    try {
                        s.displayScene(i);
                      Thread.sleep(2000);
                    } catch (Exception ex) {
                        Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                }
            }
        
        
        }
               
        );
        th.start();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
