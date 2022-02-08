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
    int turn;
    int oponentID;

    public StartMultiPlayerGame(int turn, int oponentID) {
        this.turn = turn;
        this.oponentID = oponentID;
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
    
    
}
