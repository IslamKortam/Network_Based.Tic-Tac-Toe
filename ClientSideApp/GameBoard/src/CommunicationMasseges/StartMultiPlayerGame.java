/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;

import java.util.ArrayList;

/**
 *
 * @author imkor
 */
public class StartMultiPlayerGame {
    public enum MultiPlayerGameType{
        NEW_GAME,
        LOAD_GAME
    }
    
    int turn;
    int oponentID;
    private MultiPlayerGameType type;
    private int gameID;
    private ArrayList<Integer> arrayOfMoves;
    
    
    public StartMultiPlayerGame(int turn, int oponentID) {
        this.turn = turn;
        this.oponentID = oponentID;
    }
    
    public StartMultiPlayerGame(int turn, int oponentID, MultiPlayerGameType type, int gameID, ArrayList<Integer> arrayOfMoves) {
        this.turn = turn;
        this.oponentID = oponentID;
        this.type = type;
        this.gameID = gameID;
        this.arrayOfMoves = arrayOfMoves;
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
    
    public ArrayList<Integer> getArrayOfMoves() {
        return arrayOfMoves;
    }

    public void setArrayOfMoves(ArrayList<Integer> arrayOfMoves) {
        this.arrayOfMoves = arrayOfMoves;
    }

    public MultiPlayerGameType getType() {
        return type;
    }

    public void setType(MultiPlayerGameType type) {
        this.type = type;
    }
    
    
}
