/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadGame;

import CommHandlerPK.ClientConnectionHandler;
import CommunicationMasseges.CommunicationMassege;
import CommunicationMasseges.CommunicationMassegeType;
import CommunicationMasseges.Invitation;
import ParserPackage.Parser;
import controllerPackage.Player;
import controllerPackage.PlayerStatus;
import java.io.IOException;
import java.sql.Date;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

/**
 *
 * @author Salma
 */
public class loadGameUtility {
     private static Scene scene;
    private static TableColumn<Game, Date> date;
    private static TableColumn<Game, Integer> gameId;
    private static TableColumn<Game, String> player2Name;
    private static TableColumn<Game, String> player2Status;
    private static TableView<Game> table;
    private static TableColumn load;
    private static ObservableList<Game> data = FXCollections.observableArrayList();
    private static Game g;
    public static Parent ref;
    public static void setNodes(TableView<Game> table, TableColumn<Game, Integer> gameId, TableColumn<Game, Date> date, TableColumn<Game, String> player2Name, TableColumn<Game, String> player2Status, ObservableList<Game> data, TableColumn<Game, Button> load) {
        loadGameUtility.date = date;
        loadGameUtility.gameId = gameId;
        loadGameUtility.player2Name = player2Name;
        loadGameUtility.player2Status = player2Status;
        loadGameUtility.table = table;
        loadGameUtility.data = data;
        loadGameUtility.load = load;
    }
    
    public static void appendData(int gameId, Date date, String p2name, String p2status, int p2id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loadGameUtility.g = new Game(gameId, p2id, p2name, p2status, date, "");
                data.add(g);
                table.setItems(data);
                loadGameUtility.clickOnButton();
            }
        });
    }

    public static void clickOnButton() {
        
        for (Game g : table.getItems()) {
            Button load = g.getLoad();
            load.setOnAction((event) -> {
                System.out.println(g.getPlayer2Name());
                Invitation inv = new Invitation(Player.getThisPlayer().getId(), g.player2Id, Invitation.InvitationType.LOAD_GAME, g.getGameId());
                String s = Parser.gson.toJson(inv);
                CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.INVITATION, s);
                ClientConnectionHandler.ref.sendCommMsgToServer(commMsg);
            });
            
        }
    }
    
    public static void reset() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                table.getItems().clear();
            }
        });
    }
    
    public static void deleteGameFromTable(int gameId) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Game x = null;
                for (Game g : table.getItems()) {
                    if (g.getGameId() == gameId) {
                        x = g;
                        break;
                    }
                }
                if(x != null){
                    table.getSelectionModel().select(x);
                    Game loaded = table.getSelectionModel().getSelectedItem();
                    table.getItems().remove(loaded);
                }
            }
        });
    }
    
    public static void disableButton(int p2id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Game g : table.getItems()) {
                    if (g.getPlayer2Id() == p2id) {
                        Button load = g.getLoad();
                        load.visibleProperty().setValue(Boolean.FALSE);
                    }
                }
            }
        });
    }
    
    public static void changePlayerStatus(int p2id, PlayerStatus newStatus) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Game g : table.getItems()) {
                    if (g.getPlayer2Id() == p2id) {
                                g.setPlayer2Status("" + newStatus);
                    }
                }
            }
        });
    }
  public static void enableButton(int p2id) {
      Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Game g : table.getItems()) {
                    if (g.getPlayer2Id() == p2id) {
                        Button load = g.getLoad();
                        load.visibleProperty().setValue(Boolean.TRUE);
                    }
                }
            }
        });
    }
  public static void initScene() throws IOException {
        Parent root = FXMLLoader.load((loadGameUtility.class).getResource("loadGame.fxml"));
                     ref=root;
         scene = new Scene(root);
      
       
    }

    public static Scene getScene() {
        return scene;
    }
    
}
