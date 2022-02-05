package logintrial;

import controllerPackage.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.util.ResourceBundle;

import java.net.URI;
import java.net.URL;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Label errorMsg;
    
    @FXML
    private TextField UserNameTextField;
    @FXML
    private PasswordField passwordTextField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoginUtility.setErrorLabel(errorMsg);
        
    }
    
    @FXML
    void handleButtonAction(ActionEvent event) {
        
        if (UserNameTextField.getText().equals("") && passwordTextField.getText().equals("")) {
            
            errorMsg.setText("Please Enter username and password.");
        } else if (UserNameTextField.getText().equals("")) {
            errorMsg.setText("Please Enter username");
            
        } else if (passwordTextField.getText().equals("")) {
            errorMsg.setText("Please Enter password");
        } else {
            errorMsg.setText("");
            MainController.getRef().sendSignInRequest(UserNameTextField.getText(), passwordTextField.getText());
            System.out.println(UserNameTextField.getText() + "\n" + passwordTextField.getText());
            
        }
    }
    
}
