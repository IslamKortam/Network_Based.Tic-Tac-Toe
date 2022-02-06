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
public class NewGamRequest {
    private int playerID;

    public NewGamRequest(int playerID) {
        this.playerID = playerID;
    }
    
    public NewGamRequest() {
        this.playerID = -1;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
    
    
    
}
