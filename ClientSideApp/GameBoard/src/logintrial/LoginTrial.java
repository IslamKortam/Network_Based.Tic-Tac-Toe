/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package logintrial;

import controllerPackage.MainController;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modes.ModesController;
import stagemanager.StageManager;

/**
 *
 * @author Bahaa eldin
 */
public class LoginTrial extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        StageManager stageManger = new StageManager(stage);
        MainController.getRef().setStageMagner(stageManger);
        ScheduledThreadPoolExecutor th = new ScheduledThreadPoolExecutor(1);
       
    }

    /**
     * @param args the command line arguments
     */
}
