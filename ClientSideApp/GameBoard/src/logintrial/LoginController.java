package logintrial;

import controllerPackage.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ResourceBundle;

import java.net.URL;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

public class LoginController implements Initializable {

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
            System.out.println(UserNameTextField.getText().toLowerCase() + "\n" + passwordTextField.getText());

        }
            //MainController ref= MainController.getRef();
            //ref.navigateToModes();
    }

    @FXML
    void handleButtonAction2(ActionEvent event) throws IOException {
        MainController.getRef().navigateToSignUpPage();
    }
    
    @FXML
    public static void showAlert(String alertTitle, String Body,String fieldDefaultValue){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                TextInputDialog td = new TextInputDialog(fieldDefaultValue);
                td.showAndWait().ifPresent(response -> {
                    
                    td.setTitle(alertTitle);
                    td.setHeaderText(Body);

                    Optional<String> result = td.showAndWait();

                    result.ifPresent(name -> {
                        if("".equals(name)){
                            System.out.print(name);
                        }
                        else{
                            System.out.print("Exit");
                        }
                    });
                });
            }
        });
    }
}
