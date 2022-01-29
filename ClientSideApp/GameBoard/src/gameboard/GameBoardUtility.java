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
         static Label player1Name;
       static Label player2Name;
       static Label player1Score;
       static Label player2Score;
       static ImageView img1;
       static ImageView img2;
      
        public static void setNodes(Label player1,Label player2,Label score1,Label score2, ImageView im1 ,ImageView im2){
           GameBoardUtility.player1Name = player1;
           GameBoardUtility.player2Name = player2;
          GameBoardUtility.player1Score = score1;
          GameBoardUtility.player2Score = score2;
           GameBoardUtility.img1 = im1;
            GameBoardUtility.img2 = im2;
     
    
    }
          public static  void setPlyer(String player1, String score1, int png1,String player2,String score2 ,int i){
            player1Name.setText(player1);
            player2Name.setText(player2);
            player1Score.setText(score1);
            player2Score.setText(score2);
            
            Image im1= new Image(GameBoardController.class.getResourceAsStream("/resources/AvatarImgs/"+Integer.toString(i)+".png"));
            Image im2= new Image(GameBoardController.class.getResourceAsStream("/resources/AvatarImgs/"+Integer.toString(i)+".png"));
             img1.setImage(im1);
             img2.setImage(im2);
     }
           public static  void setPlyer(String player1, String score1, int i ){
          player1Name.setText(player1);
             player1Score.setText(score1);
              Image im1= new Image(GameBoardController.class.getResourceAsStream("/resources/AvatarImgs/"+Integer.toString(i)+".png"));
             img1.setImage(im1);
     }
        
        public static void setButtons(ArrayList<Button> buttons) {
            GameBoardUtility.buttons = buttons;
        }
    
       static  void setAvatar(ImageView imv ,String path,Class c){
        Image img =new Image(c.getResourceAsStream("/resources/AvatarImgs"+path));
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
