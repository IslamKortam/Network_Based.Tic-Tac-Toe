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
        GameBoardUtility.setButtons(buttons);
         GameBoardUtility.setNodes(player1Name,player2Name,player1Score,player2Score,player1Img,player2Img);
        for(int i=0; i < buttons.size(); i++)
        {
            GameBoardUtility.setBoxHandler(i);
            System.out.println(i);
        }        
    }
     @FXML
    void backToHome(ActionEvent event) {

    }

    
    @FXML
    void viewChatBox(MouseEvent event) {
        GameBoardUtility.changeVisibility(ChatBox);
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
    String[] line;
    public String[] checkIfGameOver(){
        for(int i =0 ; i < 8; i++){
            line = null;
            switch(i){
            //horizontal cases
                case 0:
                line[i] = button1.getText() + button2.getText() + button3.getText();
                break;
                case 1:
                line[i] = button4.getText() + button5.getText() + button6.getText();
                break;
                case 2:
                line[i] = button4.getText() + button5.getText() + button6.getText();
                break;
            //vertical cases
                case 3:
                line[i] = button1.getText() + button4.getText() + button7.getText();
                break;
                case 4:
                line[i] = button2.getText() + button5.getText() + button8.getText();
                break;
                case 5:
                line[i] = button3.getText() + button6.getText() + button9  .getText();
                break;
            //Diagonal cases
                case 6:
                line[i] = button1.getText() + button5.getText() + button9.getText();
                break;
                case 7:
                line[i] = button3.getText() + button5.getText() + button7.getText();
                break;
                default: line =null;
            }
        }
        return line;
    } 
    
}

