/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package gameboard;

import CommHandlerPK.ClientConnectionHandler;
import CommunicationMasseges.ChatMsg;
import CommunicationMasseges.CommunicationMassege;
import CommunicationMasseges.CommunicationMassegeType;
import ParserPackage.Parser;
import com.jfoenix.controls.JFXButton;
import controllerPackage.ClientSideGameController;
import controllerPackage.MainController;
import controllerPackage.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import stagemanager.StageManager;

/**
 *
 * @author Bahaa eldin
 */
public class GameBoardController implements Initializable {
    static GameBoardController ref;
    @FXML
    private JFXButton btnSave;
    @FXML
    private ImageView imgPlayerTurn;
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
    private Button home,SendChatBtn;

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
        ref=this;
        GameBoardUtility.setChatArea(ChatArea);
        buttons = new ArrayList<>(Arrays.asList(button1 , button2 , button3 , button4 , button5 , button6 , button7 , button8 , button9));
        GameBoardUtility.setButtons(buttons);
        GameBoardUtility.setNodes(player1Name,player2Name,player1Score,player2Score,player1Img,player2Img);
        GameBoardUtility.setBtnSave(btnSave);
        GameBoardUtility.setImgPlayerTurn(imgPlayerTurn);
        for(int i=0; i < buttons.size(); i++)
        {
            GameBoardUtility.setBoxHandler(i);
            System.out.println(i);
        }        
    }
    @FXML
    void backToHome(ActionEvent event)  {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Exit Game?");
        a.setContentText("If you pressed ok you will lost this game.");
        a.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    MainController.getRef().navigateToModes();
                } catch (IOException ex) {
                    Logger.getLogger(GameBoardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    
    @FXML
    void viewChatBox(MouseEvent event) {
        GameBoardUtility.changeVisibility(ChatBox);
    }
    
    @FXML
    void sendButtonPressed(MouseEvent event){
        sendChatMsg();
    }
    
    @FXML
    void sendEnterPressed(ActionEvent ac){
        sendChatMsg();
    }
    
    void sendChatMsg(){
        System.out.println("Send Chat Msg");
        String chatMsgBody = ChatField.getText();
        if(chatMsgBody.equals("")){
            return;
        }
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ChatField.setText(""); //Empty chat field
            }
        });
       
        ChatMsg msg = new ChatMsg(ClientSideGameController.getRef().getOpponentID(), Player.getThisPlayer().getId(), chatMsgBody);
        GameBoardUtility.appendChatMsg(msg);
        CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.CHAT, Parser.gson.toJson(msg));
        ClientConnectionHandler.ref.sendCommMsgToServer(commMsg);
    }
    
    @FXML
    void requestSaveGame(MouseEvent event){
//        System.out.println("Request Save");
        CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.GameSaveRequest, "");
        ClientConnectionHandler.ref.sendCommMsgToServer(commMsg);
    }

    public static GameBoardController getRef() {
        return ref;
    }
    
    @FXML
    public void declareEndOfGame(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setHeaderText("Game Ended!");
                a.setContentText(msg);
                boolean ans = false;
                Optional<ButtonType> result = a.showAndWait();
                try {
                    stagemanager.StageManager.getStageManger().displayScene(StageManager.SceneName.GAMEMODE);
                } catch (IOException ex) {
                    Logger.getLogger(GameBoardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
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
    
    
}

