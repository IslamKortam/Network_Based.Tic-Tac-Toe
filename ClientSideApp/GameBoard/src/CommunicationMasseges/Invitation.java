/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;

/**
 *
 * @author GDO
 */
public class Invitation {
    private int invitID,senderID,receiverID;

    public Invitation(int senderID, int receiverID) {
        this.senderID = senderID;
        this.receiverID = receiverID;
    }

    public Invitation(int invitID, int senderID, int receiverID) {
        this.invitID = invitID;
        this.senderID = senderID;
        this.receiverID = receiverID;
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
    
    
}
