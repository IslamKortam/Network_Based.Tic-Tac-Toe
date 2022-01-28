/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package logintrial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author Bahaa eldin
 */
public class LoginTrial extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        LoginUtility.initScene();
        stage.setScene(LoginUtility.getScene());
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        launch(args);
        System.out.println("4334565");

Thread.sleep(5000);
LoginUtility.displayLoginError(0);

        System.out.println("trttgtr");
    }
    
}
