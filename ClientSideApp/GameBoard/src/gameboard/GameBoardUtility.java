/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameboard;

import controllerPackage.ClientSideGameController;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
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
    public static Parent ref;

    public static void setNodes(Label player1, Label player2, Label score1, Label score2, ImageView im1, ImageView im2) {
        GameBoardUtility.player1Name = player1;
        GameBoardUtility.player2Name = player2;
        GameBoardUtility.player1Score = score1;
        GameBoardUtility.player2Score = score2;
        GameBoardUtility.img1 = im1;
        GameBoardUtility.img2 = im2;
    }

    public static void setPlyer(String player1, String score1, int png1, String player2, String score2, int i) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                player1Name.setText(player1);
                player2Name.setText(player2);
                player1Score.setText(score1);
                player2Score.setText(score2);
                Image im1 = new Image(GameBoardController.class.getResourceAsStream("/resources/AvatarImgs/" + Integer.toString(png1) + ".png"));
                Image im2 = new Image(GameBoardController.class.getResourceAsStream("/resources/AvatarImgs/" + Integer.toString(i) + ".png"));
                img1.setImage(im1);
                img2.setImage(im2);
            }
        });
    }

    public static void setPlyer(String player1, String score1, int i) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                player1Name.setText(player1);
                player1Score.setText(score1);
                Image im1 = new Image(GameBoardController.class.getResourceAsStream("/resources/AvatarImgs/" + Integer.toString(i) + ".png"));
                img1.setImage(im1);
            }
        });
    }

    public static void setButtons(ArrayList<Button> buttons) {
        GameBoardUtility.buttons = buttons;
    }

    static void setAvatar(ImageView imv, String path, Class c) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Image img = new Image(c.getResourceAsStream("/resources/AvatarImgs" + path));
                imv.setImage(img);
            }
        });

    }

    static void setPlayerName(Label l, String name) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                l.setText(name);
            }
        });
    }

    static void setPlayerScore(Label l, String score) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                l.setText(score);
            }
        });
    }

    static void setBoxHandler(int boxNumer) {
        // event occurs here, button index = i
        Button button = buttons.get(boxNumer);
        button.setOnMouseClicked(mouseEvent -> {
            ClientSideGameController.getRef().makeAMove(boxNumer);
        });
    }

    public static void setBox(int boxNumber, String text) {
        // set the text on the buitton by String symbol and set "disable" feature of the buttons 
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (text == "X") {
                    buttons.get(boxNumber).setStyle("-fx-text-fill: #FF0000");
                } else {
                    buttons.get(boxNumber).setStyle("-fx-text-fill: #8425E1");
                }
                buttons.get(boxNumber).setText(text);
                buttons.get(boxNumber).setDisable(true);
            }
        });
    }

    static void resetBox(int boxNumber) {
        // cancels the "disable" feature of the button and clear the text on it
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                buttons.get(boxNumber).setDisable(false);
                buttons.get(boxNumber).setText("");
            }
        });
    }

    public static void resetAllBoxes() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < buttons.size(); i++) {
                    resetBox(i);
                }
            }
        });
    }

    static void changeVisibility(BorderPane ChatBox) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (ChatBox.isVisible()) {
                    ChatBox.setVisible(false);
                } else {
                    ChatBox.setVisible(true);
                }
            }
        });
    }

    public static void initScene() throws IOException {
        Parent root = FXMLLoader.load((gameboard.GameBoardUtility.class).getResource("GameBoard.fxml"));
        scene = new Scene(root);
        ref = root;
    }

    public static Scene getScene() {
        return scene;
    }
}
