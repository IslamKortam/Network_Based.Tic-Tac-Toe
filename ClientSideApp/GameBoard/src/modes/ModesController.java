/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package modes;

import controllerPackage.MainController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 *
 * @author a7med
 */
public class ModesController implements Initializable{
    public static ModesController ref;
    Alert a ;
    @FXML
    private Label label;

    @FXML
    private void handleSinglePlayerButtonAction(ActionEvent event) throws IOException {
        System.out.println("Single Player");
        MainController.getRef().initSinglePlayerGame();
    }

    @FXML
    private void handleMultiPlayerButtonAction(ActionEvent event) throws IOException {
        System.out.println("Multi Player");
        MainController ref= MainController.getRef();
        ref.navigateToplayerScene();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        a   = new Alert(Alert.AlertType.NONE);
        ref = this;
    }
    
    @FXML
    public void showAlert(String msg,AlertType type) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                a.setAlertType(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Alert");
                a.setContentText(msg);
                a.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                        switch(type){
                            case GameLoad:
                                System.err.println("gameload");
                                break;
                            case GameSave:
                                
                                break;
                            case InvitationRequest:
                                
                                break;
                        }
                    }
                    else{
                        switch(type){
                                case GameLoad:
                                    System.err.println("gameload false");
                                    break;
                                case GameSave:

                                    break;
                                case InvitationRequest:

                                    break;
                            }
                    }
                });
            }
        });
    }
}
