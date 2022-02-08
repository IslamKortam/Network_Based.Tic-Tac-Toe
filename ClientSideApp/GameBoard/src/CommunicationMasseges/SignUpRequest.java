/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;
import controllerPackage.Player;

/**
 *
 * @author Mohamed Rashed
 */
public class SignUpRequest {
    Player newPlayer;

    public SignUpRequest(Player newPlayer) {
        this.newPlayer = newPlayer;
    }

    public Player getNewPlayer() {
        return newPlayer;
    }

    public void setNewPlayer(Player newPlayer) {
        this.newPlayer = newPlayer;
    }
    
}
