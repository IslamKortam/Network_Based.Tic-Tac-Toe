/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadGame;

import java.sql.Date;
import javafx.scene.control.Button;

/**
 *
 * @author Salma
 */
public class Game {
    int gameId;
    String player2Name;
    String player2Status;
    Date date;
    Button load;

    

    public Game(int gameId, String player2Name, String player2Status, Date date,String val) {
        this.gameId = gameId;
        this.player2Name = player2Name;
        this.player2Status = player2Status;
        this.date = date;
        this.load=new Button("reload saved game");
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getPlayer2Status() {
        return player2Status;
    }

    public void setPlayer2Status(String player2Status) {
        this.player2Status = player2Status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public Button getLoad() {
        return load;
    }

    public void setLoad(Button load) {
        this.load = load;
    }
}
