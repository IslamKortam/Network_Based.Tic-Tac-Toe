/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;

import controllerPackage.Player;

/**
 *
 * @author imkor
 */
public class SignInStatus {
    private AcceptedDinedStatus status;
    private Player playerData;

    public SignInStatus(AcceptedDinedStatus status, Player playerData) {
        this.status = status;
        this.playerData = playerData;
    }

    public SignInStatus(AcceptedDinedStatus status) {
        this.status = status;
        this.playerData = new Player();
    }

    public void setStatus(AcceptedDinedStatus status) {
        this.status = status;
    }

    public void setPlayerData(Player playerData) {
        this.playerData = playerData;
    }

    public AcceptedDinedStatus getStatus() {
        return status;
    }

    public Player getPlayerData() {
        return playerData;
    }
    
    
}
