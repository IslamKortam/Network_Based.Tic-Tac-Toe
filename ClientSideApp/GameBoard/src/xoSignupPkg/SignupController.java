/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoSignupPkg;

import CommunicationMasseges.CommunicationMassege;
import CommunicationMasseges.CommunicationMassegeType;
import CommunicationMasseges.SignUpRequest;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllerPackage.MainController;
import controllerPackage.Player;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Mohamed Rashed
 */
public class SignupController implements Initializable {
    ScheduledThreadPoolExecutor th;
    Alert a ;
    String alertMsg;
    
    @FXML
    private Label AvatarID,alertContentMsg;
    @FXML
    private JFXButton btnChooseAvatar,btnSignUp,btnMinimize;
    @FXML
    private JFXTextField txtFullName,txtUsername,txtEmail;
    @FXML
    private JFXPasswordField txtPassword,txtRePassword;
    @FXML
    private FlowPane flwAvatars;
    @FXML
    private ImageView userAvatar;
    
    
    @FXML
    private void btnChooseAvatar(ActionEvent event) {
        
        if(flwAvatars.visibleProperty().getValue()==true)
            flwAvatars.setVisible(false);
        else
            flwAvatars.setVisible(true);
    }
    
    @FXML
    private void clickedImgView(MouseEvent event) {
        
        if(flwAvatars.visibleProperty().getValue()==true)
            flwAvatars.setVisible(false);
        else
            flwAvatars.setVisible(true);
    }
    @FXML
    private void handleBtnMinimize(MouseEvent event) {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }
    
    
    @FXML
    private void btnSignUp(ActionEvent event) throws SQLException {
        if(checkError()){
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Add user information");
            alertContentMsg = new Label("Save acount information?");
            a.getDialogPane().setContent(alertContentMsg);
            a.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    System.out.println("Data submited successfully");
                   /* DataFromDB.addNewUser(txtFullName.getText(), txtUsername.getText(), txtEmail.getText(),
                            txtPassword.getText(), Integer.valueOf(AvatarID.getText()));*/
                   Player plr = new Player(txtUsername.getText().toLowerCase(),txtFullName.getText(), txtEmail.getText().toLowerCase(),
                            txtPassword.getText(), Integer.valueOf(AvatarID.getText()),0,true);
                   SignUpRequest signUp = new SignUpRequest(plr);
                   CommunicationMassege com = new CommunicationMassege(CommunicationMassegeType.SIGN_UP_REQUEST,ParserPackage.Parser.gson.toJson(signUp));
                }
            });
        }
        else
        {
            localAlert();
        }
    }
    
    @FXML
    private void btnClose(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    void handleButtonAlreadyHaveAcc(ActionEvent event) throws IOException {
        MainController.getRef().navigateToSignInPage();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SignUpUtility.setSignUpControllerReference(this);
        a = new Alert(Alert.AlertType.NONE);
        th = new ScheduledThreadPoolExecutor(1);
        
        th.schedule(new Runnable() {
            @Override
            public void run() {
                getAvatars();
                
            }
        }, 600, TimeUnit.MILLISECONDS);
    }
    
    // File representing the folder
    static final File dir = new File("src\\resources\\AvatarImgs");
    
    // array of supported extensions
    static final String[] EXTENSIONS = new String[]{"png"};
    
    // filter to identify images based on their extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    
    //Avatar ID (Pictures number start from 0)
    int imgID=0;
    
    //Get Avatars from folder
    void getAvatars(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ImageView imgView;
                Image avImg;
                flwAvatars.setVgap(5);
                flwAvatars.setHgap(5);
                flwAvatars.setPadding(new Insets(5, 5, 5, 5));
                flwAvatars.setAlignment(Pos.CENTER);
                if (dir.isDirectory()) { // make sure it's a directory
                    for (final File f : dir.listFiles(IMAGE_FILTER)) {
                        BufferedImage img = null;
                        try {
                            img = ImageIO.read(f);                            
                            avImg = SwingFXUtils.toFXImage(img, null);
                            imgView =new ImageView(avImg);
                            imgView.setFitWidth(100);
                            imgView.setFitHeight(100);
                            imgView.setPreserveRatio(true);
                            imgView.setCursor(Cursor.HAND);
                            imgView.setId(Integer.toString(imgID));
                            imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    event.consume();
                                    ImageView imview = (ImageView) event.getSource();
                                    AvatarID.setText(imview.getId());
                                    userAvatar.setImage(imview.getImage());
                                    flwAvatars.setVisible(false);
                                    System.out.println(imview.getId());
                                }
                            });
                            flwAvatars.getChildren().add(imgView);
                            imgID++;
                        } catch (final IOException e) {
                        }
                    }
                }
            }
        });
        imgID=0;
    }
    
    //Check confirm all requirments for all inputs
    boolean checkError() throws SQLException{
        int errorNum=0;
        alertMsg = "";
        if(!"".equals(txtFullName.getText())){
            errorNum--;
        }else{
            errorNum++;
            alertMsg+="You must enter full name\n";
        }
        if(!"".equals(txtUsername.getText())){
            errorNum--;
        }else{
            errorNum++;
            alertMsg+="You must enter user name\n";
        }
        if(!"".equals(txtEmail.getText())){
            errorNum--;
        }else{
            errorNum++;
            alertMsg+="You must enter email\n";
        }
        if(!"".equals(txtPassword.getText())){
            errorNum--;
        }else{
            errorNum++;
            alertMsg+="You must enter password\n";
        }
        if(!"".equals(txtRePassword.getText())){
            errorNum--;
        }else{
            errorNum++;
            alertMsg+="You must enter repassword\n";
        }
        if(txtRePassword.getText().equals(txtPassword.getText())){
            errorNum--;
        }else{
            errorNum++;
            alertMsg+="Password not match\n";
        }
        if(txtPassword.getText().length()>=8){
            errorNum--;
        }else{
            errorNum++;
            alertMsg+="You must enter strong password greater than 8 charachters\n";
        }
        if(isValidEmail(txtEmail.getText())){
            errorNum--;
        }else{
            errorNum++;
            alertMsg+="Email not valid\n";
        }
        if(isValidUsername(txtUsername.getText())==0){
            errorNum--;
        }else{
            errorNum++;
            alertMsg+="username not valid\n";
        }
        
        if(errorNum != -9)
            return false;
        else
            return true;
    }
    
    //Check confirm email requirment
    boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";              
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    
    //Check confirm username requirment
    int isValidUsername(String username) {
        int count=0;
        for (int i = 0; i < username.length(); i++) {
            if (!Character.isDigit(username.charAt(i))
                && !Character.isLetter(username.charAt(i))
                && !Character.isWhitespace(username.charAt(i))) {
                count++;
            }
        }
        return count;
    }
    
    //This method to show alert if data not enterd or dosnot confirm requirments
    void localAlert(){
        alertContentMsg = new Label(alertMsg);
        alertContentMsg.setStyle("-fx-text-fill: red;");
        a.setAlertType(Alert.AlertType.ERROR); 
        a.setHeaderText("Add user information");
        a.getDialogPane().setContent(alertContentMsg);
        a.show();
    }
    
    //This method controled by MainController to show alert and goto login screen if data valid
    public void showAlert(int state, String msg){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                a = new Alert(Alert.AlertType.NONE);
                alertMsg += msg; //Get mseeage from Controller
                alertContentMsg = new Label(alertMsg);
                if(state == 1)//1 Done validate
                {
                    alertContentMsg.setStyle("-fx-text-fill: green;");
                    a.setAlertType(Alert.AlertType.INFORMATION);
                }
                else
                {
                    alertContentMsg.setStyle("-fx-text-fill: red;");
                    a.setAlertType(Alert.AlertType.ERROR); 
                }
                a.setHeaderText("Add user information");
                a.getDialogPane().setContent(alertContentMsg);
                a.show();
            }
        });
        
    }
    
}
