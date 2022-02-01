/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverdao;

/**
 *
 * @author Salma
 */
public class GamePojo {

    private int player1Id;
    private int player2Id;
    private long timeLength;
    private String board;
    private int complete;
    private int winnerId;
    private String date_time;
    private int visible;

   

    public GamePojo(int player1Id, int player2Id, long timeLength, String board, int complete, int winnerId, String date_time,int visible) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.timeLength = timeLength;
        this.board = board;
        this.complete = complete;
        this.winnerId = winnerId;
        this.date_time = date_time;
        this.visible=visible;
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

    public int isComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

   public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
    
}
