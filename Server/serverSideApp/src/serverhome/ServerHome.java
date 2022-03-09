/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverhome;

import controllerPackage.PlayerHandler;
import java.io.IOException;
import java.util.Vector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import playersOnServer.PlayersOnServerUtility;
import serverdao.Dao;
import serverdao.GamePojo;
import serverdao.PlayerPojo;

/**
 *
 * @author Salma
 */
public class ServerHome extends Application {
   private static double xOffset = 0;
    private static double yOffset = 0;
   

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("serverHome.fxml"));
       
        Scene scene = new Scene(root);
        ServerHomeUtility.setScene(scene);
        PlayersOnServerUtility.setMainStage(stage);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("XO-Server");
        Image img = new Image(ServerHome.class.getResourceAsStream("/resources/TitleLogo.png"));
        stage.getIcons().add(img);
        stage.show();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
        
        try{
            Vector<PlayerPojo> players = Dao.selectAllPlayers();
            playersOnServer.PlayersOnServerUtility.initScene();
            for(PlayerPojo player : players){
                System.out.println(player.getID() + ":" + player.getEmail() + ":" + player.getPassword() + ":" + player.getNickName() + ":" + player.getUserName() + ":" + player.getPicture());
                new PlayerHandler(player);
                //serverhome.ServerHomeUtility.updateLogs("+Added Player from DB: " + player.getUserName());
            }

            Vector<GamePojo> gameVector = Dao.selectGameByPlayerID(1);

            for(GamePojo game : gameVector){
                System.out.println("Saved Game: " + game.getGameID() + ":" + game.getPlayer1Id() + ":" + game.getPlayer2Id() + ":" + game.getBoard());
            }
        }catch(IOException e){
            
        }
        
        logPlayers();
    }
    
    private void logPlayers(){
        for(PlayerHandler player : PlayerHandler.getPlayers()){
            ServerHomeUtility.updateLogs("+ Added: " + player.getUserName() + ":" + player.getStatus());
        }
    }
}
