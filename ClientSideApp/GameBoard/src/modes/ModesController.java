/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package modes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 *
 * @author a7med
 */
public class ModesController {

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.show();
    }

    @FXML
    private void handleButtonAction2(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.show();

    }
}
