/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package gameboard;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Bahaa eldin
 */
public class GameBoardController implements Initializable {
    
    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Label label;

    @FXML
    private Button button1;
        @FXML
    private Button changeAvatar;


    @FXML
    private ImageView player1Img;

    @FXML
    private ImageView player2Img;
       @FXML
    private Label player2Name;

    @FXML
    private Label player2Score;
      @FXML
    private Label player1Name;

    @FXML
    private Label player1Score;
    
    @FXML
    private Button home;

    @FXML
    private BorderPane ChatBox;

    @FXML
    private ImageView ChatBtn;

    @FXML
    private ImageView sendBtn;

    @FXML
    private TextArea ChatArea;

    @FXML
    private TextField ChatField;

    ArrayList<Button> buttons;

    private int playerTurn =0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttons = new ArrayList<>(Arrays.asList(button1 , button2 , button3 , button4 , button5 , button6 , button7 , button8 , button9));
        Utility.setButtons(buttons);
        for(int i=0; i < buttons.size(); i++)
        {
            Utility.setBoxHandler(i);
            System.out.println(i);
        }        
    }
     @FXML
    void backToHome(ActionEvent event) {

    }
 @FXML
            // only for testing no need 
            // to know how to use class utility
    void changeAvatar(ActionEvent event) {
        Utility.setAvatar(player1Img, "2.png", getClass());
        Utility.setAvatar(player2Img, "3.png", getClass());
        Utility.setPlayerName(player1Name, "mona");
         Utility.setPlayerName(player2Name, "ghada");
          Utility.setPlayerName(player1Score, "5");
         Utility.setPlayerName(player2Score, "6");
    }    
    
    @FXML
    void viewChatBox(MouseEvent event) {
        Utility.changeVisibility(ChatBox);
    }
    
//    public void setPlayerSymbol(Button button){
//        if(playerTurn%2==0){
//        button.setText("X");
//        playerTurn =1;
//        }
//    else{
//        button.setText("O");
//        playerTurn =0;
//        }
//    }
//    public void checkIfGameOver(){
//        for(int i =0 ; i < 8; i++){
//            String line = null;
//            switch(i){
//            //horizontal cases
//                case 0:
//                line = button1.getText() + button2.getText() + button3.getText();
//                break;
//                case 1:
//                line = button4.getText() + button5.getText() + button6.getText();
//                break;
//                case 2:
//                line = button4.getText() + button5.getText() + button6.getText();
//                break;
//            //vertical cases
//                case 3:
//                line = button1.getText() + button4.getText() + button7.getText();
//                break;
//                case 4:
//                line = button2.getText() + button5.getText() + button8.getText();
//                break;
//                case 5:
//                line = button3.getText() + button6.getText() + button9  .getText();
//                break;
//            //Diagonal cases
//                case 6:
//                line = button1.getText() + button5.getText() + button9.getText();
//                break;
//                case 7:
//                line = button3.getText() + button5.getText() + button7.getText();
//                break;
//                default: line =null;
//            }
//            if(line.equals("XXX")){
//                label.setText("The winner is X");
//            }
//            else if(line.equals("OOO")){
//                label.setText("The winner is O");
//            }
//        }
//
//    } 
    
}

