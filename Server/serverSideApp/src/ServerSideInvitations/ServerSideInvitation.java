/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSideInvitations;
import CommunicationMasseges.*;
import CommunicationMasseges.Invitation.InvitationType;
import controllerPackage.PlayerHandler;
import GameSession.GameSession;
import controllerPackage.PlayerStatus;

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
        if(getType() == InvitationType.NEW_GAME){
            if(sender.getStatus() == PlayerStatus.ONLINE && reciever.getStatus() == PlayerStatus.ONLINE){
                GameSession newGameSession = new GameSession(sender, reciever);
                sender.setCurrentGame(newGameSession);
                reciever.setCurrentGame(newGameSession);
            }
        }
    }
    
    
    public void deny(){
        
    }
    
}
