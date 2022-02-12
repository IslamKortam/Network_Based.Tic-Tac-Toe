/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;

/**
 *
 * @author imkor
 */
public class StartMultiPlayerGame {
    public enum MultiPlayerGameType{
        NEW_GAME,
        LOAD_GAME
    }
    
    private int turn;
    private int oponentID;
    private MultiPlayerGameType type;
    private int gameID;

    public StartMultiPlayerGame(int turn, int oponentID) {
        this.turn = turn;
        this.oponentID = oponentID;
        this.gameID = -1;
        this.type = MultiPlayerGameType.NEW_GAME;
    }

    public StartMultiPlayerGame(int turn, int oponentID, MultiPlayerGameType type, int gameID) {
        this.turn = turn;
        this.oponentID = oponentID;
        this.type = type;
        this.gameID = gameID;
    }
    

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getOponentID() {
        return oponentID;
    }

    public void setOponentID(int oponentID) {
        this.oponentID = oponentID;
    }

    public MultiPlayerGameType getType() {
        return type;
    }

    public void setType(MultiPlayerGameType type) {
        this.type = type;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
    
    
}
