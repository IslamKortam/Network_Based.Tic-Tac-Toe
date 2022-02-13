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
public class ChatMsg {
    private int receiver;
    private int senderID;
    private String message;

    public ChatMsg(int receiver, int senderID, String message) {
        this.receiver = receiver;
        this.senderID = senderID;
        this.message = message;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
