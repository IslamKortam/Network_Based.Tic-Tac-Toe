/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;

import java.time.LocalDate;
import serverdao.GamePojo;

/**
 *
 * @author imkor
 */
public class GameInfo {
    private int gameID;
    private int player1ID;
    private int player2ID;
    private LocalDate date;

    public GameInfo(int gameID, int player1ID, int player2ID, LocalDate date) {
        this.gameID = gameID;
        this.player1ID = player1ID;
        this.player2ID = player2ID;
        this.date = date;
    }
    
    public GameInfo(GamePojo gamePojo){
        this.gameID = gamePojo.getGameID();
        this.player1ID = gamePojo.getPlayer1Id();
        this.player2ID = gamePojo.getPlayer2Id();
        this.date = gamePojo.getDate_time().toLocalDate();
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getPlayer1ID() {
        return player1ID;
    }

    public void setPlayer1ID(int player1ID) {
        this.player1ID = player1ID;
    }

    public int getPlayer2ID() {
        return player2ID;
    }

    public void setPlayer2ID(int player2ID) {
        this.player2ID = player2ID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    
}
