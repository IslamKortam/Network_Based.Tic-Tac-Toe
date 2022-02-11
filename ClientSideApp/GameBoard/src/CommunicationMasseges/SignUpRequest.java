/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;
import controllerPackage.PlayerPojo;

/**
 *
 * @author Mohamed Rashed
 */
public class SignUpRequest {
    PlayerPojo newPlayer;

    public SignUpRequest(PlayerPojo newPlayer) {
        this.newPlayer = newPlayer;
    }

    public PlayerPojo getNewPlayer() {
        return newPlayer;
    }

    public void setNewPlayer(PlayerPojo newPlayer) {
        this.newPlayer = newPlayer;
    }
    
}


