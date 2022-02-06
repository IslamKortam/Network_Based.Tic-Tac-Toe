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
public class NewGameRequest {
    private int playerID;
    private int gameReqId;

    public NewGameRequest(int playerID, int gameReqId) {
        this.playerID = playerID;
        this.gameReqId = gameReqId;
    }

   
    public void setGameReqId(int gameReqId) {
        this.gameReqId = gameReqId;
    }

    public int getGameReqId() {
        return gameReqId;
    }  
    public NewGameRequest() {
        this.playerID = -1;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
    
    public void accept(){}
    
}
