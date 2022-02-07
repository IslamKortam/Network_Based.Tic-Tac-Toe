/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoSignupPkg;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author imkor
 */
public class SignUpUtility {
    private static Scene scene;
    static SignupController signUpControllerReference;
    public static Parent ref;
    
    static void setSignUpControllerReference(SignupController ref){
        signUpControllerReference = ref;
    }
    
    public static void displaySignUpErrorMsg(int state, String msg){
        signUpControllerReference.showAlert(state, msg);
    }
    
    
    
    public static void initScene() throws IOException{
        Parent root = FXMLLoader.load((xoSignupPkg.SignUpUtility.class).getResource("FXMLSignup.fxml"));
        scene = new Scene(root);
        ref=root;
    }
    public static Scene getScene(){
        return scene;
    }
}
