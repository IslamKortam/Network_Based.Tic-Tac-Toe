/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameboard;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Salma
 */
public class Utility {

        static ArrayList<Button> buttons;
        public static void setButtons(ArrayList<Button> buttons) {
            Utility.buttons = buttons;
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
        static void setUpButton(int i){
            Button button = buttons.get(i);
            button.setOnMouseClicked(mouseEvent ->{
            setPlayerSymbol(i  , "x");
            button.setDisable(true);
            });
        }
        static void setPlayerSymbol(int i  , String symbol){
            buttons.get(i).setText(symbol);
            System.out.println(buttons.get(i).getText());
        }

        static void resetButton(int i){
            buttons.get(i).setDisable(false);
            buttons.get(i).setText("");
        }
        static void resetAllButtons(){
            for(int i =0; i<buttons.size();i++)
            {
            resetButton(i);
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
}
