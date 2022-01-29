/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modes;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author imkor
 */
public class ModesUtility {
    private static Scene scene;
    
    
    public static void initScene() throws IOException{
        Parent root = FXMLLoader.load((modes.ModesUtility.class).getResource("Modes.fxml"));
        scene = new Scene(root);
    }
    
    public static Scene getScene(){
        return scene;
    }
    
}
