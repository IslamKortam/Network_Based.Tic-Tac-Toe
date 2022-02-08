/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package modes;

import controllerPackage.MainController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
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
public class ModesController implements Initializable {

    public static ModesController ref;
    //Alert a ;
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
        MainController.getRef().navigateToplayerScene();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //a   = new Alert(Alert.AlertType.NONE);
        ref = this;
    }

    @FXML
    public Boolean showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Alert");
        a.setContentText(msg);
        boolean ans = false;
        Optional<ButtonType> result = a.showAndWait();
        if(result.get() == ButtonType.OK)
            ans = true;
        else
            ans = false;
        return ans;
    }
}

