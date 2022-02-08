/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSideInvitations;
import CommunicationMasseges.*;
import controllerPackage.PlayerHandler;

/**
 *
 * @author imkor
 */
public class ServerSideInvitation extends Invitation{
    private static int count = 0;
    PlayerHandler sender;
    PlayerHandler reciever;
    
    
    
    public ServerSideInvitation(Invitation inv){
        super(inv);
        System.out.println("SenderID: " + this.getSenderID());
        sender = PlayerHandler.getPlayerHandlerByID(this.getSenderID());
        System.out.println(PlayerHandler.getPlayers().size());
        reciever = PlayerHandler.getPlayerHandlerByID(this.getReceiverID());
        setInvitID(count++);
        System.out.println(sender.getFullName());
        sender.addASentInvitation(this);
        reciever.addARecievedInvitation(this);
    }
    
    public void accept(){
        
    }
    
    
    public void deny(){
        
    }
    
}
