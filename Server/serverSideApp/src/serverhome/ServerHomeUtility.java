/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverhome;

import controllerPackage.PlayerHandler;
import java.io.IOException;
import java.util.Vector;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import playersOnServer.PlayersOnServerUtility;
import serverhome.ServerHomeController;

/**
 *
 * @author Salma
 */
public class ServerHomeUtility {

    private static Scene scene;
    static TextArea logs;
    static Button startServer;
    static Button stopServer;
    static Text status;
    static ImageView statusImg;

    public ServerHomeUtility(TextArea logs, Button startServer, Button stopServer, Text status, ImageView statusImg) {
        ServerHomeUtility.logs = logs;
        ServerHomeUtility.startServer = startServer;
        ServerHomeUtility.stopServer = stopServer;
        ServerHomeUtility.status = status;
        ServerHomeUtility.statusImg = statusImg;
    }

    public static void changeStatusToOnline() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                status.setText("server is online");
                status.setFill(Color.GREEN);
                Image online = new Image(ServerHomeController.class.getResourceAsStream("/resources/online.png"));
                statusImg.setImage(online);
                logs.appendText("\n--------- server is now live ------");
            }
        });

    }

    public static void changeStatusToOffline() {

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                status.setText("server is offline");
                status.setFill(Color.RED);
                Image offline = new Image(ServerHomeController.class.getResourceAsStream("/resources/offline.png"));
                statusImg.setImage(offline);
                logs.appendText("\n------- server is turned off ------");

            }
        });

    }

    public static void showPlayerList(Event e) throws IOException {
        //Parent root = FXMLLoader.load(serverhome.ServerHome.class.getResource("serverHome.fxml"));
        //Scene scene = new Scene(root);
        //PlayersOnServerUtility.initScene();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        
        stage.setScene(PlayersOnServerUtility.getScene());
        stage.show();
    }

    public static void updateLogs(String update) {
        logs.appendText("\n" + update);
    }

    public static void resetLogs() {
        logs.setText("");
    }

    public static void setScene(Scene scene) {
        ServerHomeUtility.scene = scene;
    }

    public static Scene getScene() {
        return scene;
    }

}
