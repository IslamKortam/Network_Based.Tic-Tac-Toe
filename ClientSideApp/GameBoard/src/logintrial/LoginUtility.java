/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logintrial;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author a7med
 */
public class LoginUtility {

    private static Scene scene;
    private static Label errorMsg;
    
    public static Parent ref;
    static void setErrorLabel(Label error) {
        errorMsg = error;
    }

    public static void displayLoginError(int ch) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (ch == 1) {
                    errorMsg.setText("Success");
                } else {
                    errorMsg.setText("Invalid username or password.");
                }
            }
        });
    }

    public static void initScene() throws IOException {
        Parent root = FXMLLoader.load((logintrial.LoginUtility.class).getResource("Login.fxml"));
        ref=root;
        scene = new Scene(root);
    }

    public static Scene getScene() {
        return scene;
    }
}
