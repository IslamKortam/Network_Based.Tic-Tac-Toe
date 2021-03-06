package logintrial;

import CommHandlerPK.ClientConnectionHandler;
import controllerPackage.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ResourceBundle;

import java.net.URL;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

public class LoginController implements Initializable {
    private static LoginController ref;

    @FXML
    private Label errorMsg;

    @FXML
    private TextField UserNameTextField;
    @FXML
    private PasswordField passwordTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoginUtility.setErrorLabel(errorMsg);
        //showAlert("aa","bb","cc");
    }

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {

        if (UserNameTextField.getText().equals("") && passwordTextField.getText().equals("")) {

            errorMsg.setText("Please Enter username and password.");
        } else if (UserNameTextField.getText().equals("")) {
            errorMsg.setText("Please Enter username");

        } else if (passwordTextField.getText().equals("")) {
            errorMsg.setText("Please Enter password");
        } else {
            errorMsg.setText("");
            MainController.getRef().sendSignInRequest(UserNameTextField.getText().toLowerCase(), passwordTextField.getText());

        }
    }

    @FXML
    void handleButtonAction2(ActionEvent event) throws IOException {
        MainController.getRef().navigateToSignUpPage();
    }
    
    
    public static void showConnectionProblemAlert(String alertTitle, String Body,String fieldDefaultValue){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                TextInputDialog td = new TextInputDialog(fieldDefaultValue);
                td.setTitle(alertTitle);
                td.setHeaderText(Body);
                Optional<String> result = td.showAndWait();
                
                result.ifPresent(name -> {
                    ClientConnectionHandler.setServerIP(name);
                    new ClientConnectionHandler();
                });
                
                if(!result.isPresent())
                {
                    Platform.exit();
                    System.exit(0);
                }
            }
        });
    }
    
    private void setEmailField(String s){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                UserNameTextField.setText(s);
            }
        });
    }
    
    private void setPasswordField(String s){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                passwordTextField.setText(s);
            }
        });
    }
    
    public static void resetScene(){
        ref.UserNameTextField.setText("");
        ref.passwordTextField.setText("");
        //ref.setEmailField("");
        //ref.setPasswordField("");
    }
}
