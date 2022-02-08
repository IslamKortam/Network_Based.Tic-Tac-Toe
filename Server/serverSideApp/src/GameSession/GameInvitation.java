/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSession;

import controllerPackage.PlayerHandler;

/**
 *
 * @author imkor
 */
public class GameInvitation {
    private static int count = 0;
    
    private int ID;
    private int senderID;
    private int recieverID;
    private PlayerHandler sender;
    private PlayerHandler reciever;
    

    public GameInvitation(int senderID, int recieverID) {
        this.senderID = senderID;
        this.recieverID = recieverID;
        this.ID = count++;
        sender = PlayerHandler.getPlayerHandlerByID(senderID);
        reciever = PlayerHandler.getPlayerHandlerByID(recieverID);
        
    }
    
    

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getRecieverID() {
        return recieverID;
    }

    public void setRecieverID(int recieverID) {
        this.recieverID = recieverID;
    }
    
    
    
}
