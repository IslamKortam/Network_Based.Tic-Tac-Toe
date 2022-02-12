/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;

import controllerPackage.PlayerStatus;

/**
 *
 * @author imkor
 */
public class StatusUpdate {
    private int playerID;
    private PlayerStatus newStatus;

    public StatusUpdate(int playerID, PlayerStatus newStatus) {
        this.playerID = playerID;
        this.newStatus = newStatus;
    }
    
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public PlayerStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(PlayerStatus newStatus) {
        this.newStatus = newStatus;
    }
}
