/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;

/**
 *
 * @author Mohamed Rashed
 */
public class Invitation {
    public enum InvitationType{
        NEW_GAME,
        LOAD_GAME
    }
    private int invitID,senderID,receiverID, gameID;
    private InvitationType type;

    public Invitation(int senderID, int receiverID, InvitationType type) {
        this.type = type;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.gameID = -1;   //In case of new game
    }

   
    
    public Invitation(int senderID, int receiverID, InvitationType type, int gameID) {
        this.type = type;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.gameID = gameID;   //In case of Load game
    }
    
    public Invitation(Invitation other){
        this.type = other.type;
        this.senderID = other.senderID;
        this.receiverID = other.receiverID;
        this.gameID = other.gameID;
    }



    public int getInvitID() {
        return invitID;
    }

    public void setInvitID(int invitID) {
        this.invitID = invitID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public InvitationType getType() {
        return type;
    }

    public void setType(InvitationType type) {
        this.type = type;
    }
    
    
}
