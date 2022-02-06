/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverdao;

import java.sql.Date;

/**
 *
 * @author Salma
 * Modified by Mohamed Rashed
 */
public class GamePojo {

    private int gameID;
    private int player1Id;
    private int player2Id;
    private long timeLength;
    private String board;
    private Boolean complete;
    private int winnerId;
    private Date date_time;
    private Boolean visible;

   
    public  GamePojo(){
        this.gameID = -1;
        this.player1Id = -1;
        this.player2Id = -1;
        this.timeLength = -1;
        this.board = "";
        this.complete = false;
        this.winnerId = -1;
        this.date_time = new Date(1);
        this.visible=false;
    }
    public GamePojo(int player1Id, int player2Id, long timeLength, String board, Boolean complete, int winnerId, Date date_time,Boolean visible) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.timeLength = timeLength;
        this.board = board;
        this.complete = complete;
        this.winnerId = winnerId;
        this.date_time = date_time;
        this.visible=visible;
    }
    public GamePojo(int gameID,int player1Id, int player2Id, long timeLength, String board, Boolean complete, int winnerId, Date date_time,Boolean visible) {
        this.gameID = gameID;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.timeLength = timeLength;
        this.board = board;
        this.complete = complete;
        this.winnerId = winnerId;
        this.date_time = date_time;
        this.visible=visible;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(int player1Id) {
        this.player1Id = player1Id;
    }

    public int getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(int player2Id) {
        this.player2Id = player2Id;
    }

    public long getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(long timeLength) {
        this.timeLength = timeLength;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public Boolean isComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    
}
