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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        System.out.println("Sender full name: " + sender.getFullName());
        System.out.println("Reciever full name: " + reciever.getFullName());
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
        else if(this.getType() == InvitationType.LOAD_GAME){
            if(sender.getStatus() == PlayerStatus.ONLINE && reciever.getStatus() == PlayerStatus.ONLINE){
                GameSession loadedGameSession = null;
                try {
                    loadedGameSession = GameSession.loadGame(this);
                } catch (SQLException ex) {
                    Logger.getLogger(ServerSideInvitation.class.getName()).log(Level.SEVERE, null, ex);
                }
                sender.setCurrentGame(loadedGameSession);
                reciever.setCurrentGame(loadedGameSession);
            }
        }
    }
    
    
    public void deny(){
        
    }
    
}
