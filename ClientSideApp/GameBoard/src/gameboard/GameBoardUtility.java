/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameboard;

import java.io.IOException;
import java.util.ArrayList;
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
 * @author Salma
 */
public class GameBoardUtility {
        private static Scene scene;
        static ArrayList<Button> buttons;
        
        public static void setButtons(ArrayList<Button> buttons) {
            GameBoardUtility.buttons = buttons;
        }
    
       static  void setAvatar(ImageView imv ,String path,Class c){
        Image img =new Image(c.getResourceAsStream("/resources/"+path));
        imv.setImage(img);
       }
       static void setPlayerName(Label l,String name){
           l.setText(name);
       }
        static void setPlayerScore(Label l,String score){
           l.setText(score);
       }
        static void setBoxHandler(int boxNumer){
        // event occurs here, button index = i
            Button button = buttons.get(boxNumer);
            button.setOnMouseClicked(mouseEvent ->{
//            setBox(boxNumer  , "x");
            System.out.println("button pressed " +boxNumer);
            });
        }
        static void setBox(int boxNumber  , String text){
        // set the text on the buitton by String symbol and set "disable" feature of the buttons 
            buttons.get(boxNumber).setText(text);
            buttons.get(boxNumber).setDisable(true);
        }

        static void resetBox(int boxNumber){
        // cancels the "disable" feature of the button and clear the text on it
            buttons.get(boxNumber).setDisable(false);
            buttons.get(boxNumber).setText("");
        }
        static void resetAllBoxes(){
            for(int i =0; i<buttons.size();i++)
            {
            resetBox(i);
            }
        }
        static void changeVisibility(BorderPane ChatBox){
            if(ChatBox.isVisible()){
                ChatBox.setVisible(false);
            }
            else{
                ChatBox.setVisible(true);
            }
        }
        
    public static void initScene()throws IOException{
        Parent root = FXMLLoader.load((gameboard.GameBoard.class).getResource("GameBoard.fxml"));
        scene = new Scene(root);
    }    
    public static Scene getScene(){
        return scene;
    }
}
