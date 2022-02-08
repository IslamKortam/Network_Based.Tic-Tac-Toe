/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stagemanager;

import java.util.Vector;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xoSignupPkg.SignUpUtility;
import logintrial.LoginUtility;
import gameboard.GameBoardUtility;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import modes.ModesUtility;
import playersListScene.PlayersSceneUtility;

/**
 *
 * @author Salma
 */
public class StageManager {
    private static StageManager stageManger;
    //  private Vector<Scene> scenes;
    public enum SceneName {
        SIGNIN,
        SIGNUP,
        GAMEMODE,
        PLAYERLIST,
        GAMEBOARD
    }
    private final Stage stage;
    private SceneName currentSceneName;

    public StageManager(Stage stage) throws IOException {
        SignUpUtility.initScene();
        LoginUtility.initScene();
        ModesUtility.initScene();
        ModesUtility.initScene();
        PlayersSceneUtility.initScene();
        GameBoardUtility.initScene();
        this.stage = stage;
        stageManger = this;
        stage.setResizable(false);
        displayScene(SceneName.SIGNIN);
    }

    public static StageManager getStageManger() throws IOException {


        return stageManger;
    }

    public SceneName getCurrentSceneName() {
        return currentSceneName;
    }

    public void resetStage() throws IOException {
        displayScene(SceneName.SIGNIN);

    }

    public void displayScene(SceneName name) throws IOException {
        Platform.runLater(() -> {
            try {
                switch (name) {
                    case SIGNUP:
//                        SignUpUtility.initScene();
                        stage.setScene(SignUpUtility.getScene());
                        moveScreen(SignUpUtility.ref,stage);
                        stage.show();
                        break;
                    case SIGNIN:
//                        LoginUtility.initScene();
                        stage.setScene(LoginUtility.getScene());
                        moveScreen(LoginUtility.ref,stage);
                        stage.show();
                        currentSceneName = SceneName.SIGNUP;
                        break;
                    case GAMEMODE:
//                        ModesUtility.initScene();
                        stage.setScene(ModesUtility.getScene());
                        moveScreen(ModesUtility.ref,stage);
                        stage.show();
                        currentSceneName = SceneName.GAMEMODE;
                        break;
                    case PLAYERLIST:
//                        PlayersSceneUtility.initScene();
                        stage.setScene(PlayersSceneUtility.getScene());
                        moveScreen(PlayersSceneUtility.ref,stage);
                        stage.show();
                        currentSceneName = SceneName.PLAYERLIST;
                        break;
                    case GAMEBOARD:
//                        GameBoardUtility.initScene();
                        stage.setScene(GameBoardUtility.getScene());
                        moveScreen(GameBoardUtility.ref,stage);
                        stage.show();
                        currentSceneName = SceneName.GAMEBOARD;
                }
            } catch (Exception e) {
                e.printStackTrace();
            };
        });

    }
    
    private static double xOffset = 0;
    private static double yOffset = 0;
    static void moveScreen(Parent root,Stage stg){
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stg.getX() - event.getScreenX();
                yOffset = stg.getY() - event.getScreenY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stg.setX(event.getScreenX() + xOffset);
                stg.setY(event.getScreenY() + yOffset);
            }
        });
    }
}
