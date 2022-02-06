/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerLogicClasses;

import CommunicationMasseges.AcceptedDinedStatus;
import CommunicationMasseges.SignInRequest;
import CommunicationMasseges.SignInStatus;
import controllerPackage.Player;
import java.sql.SQLException;
import serverdao.Dao;
import serverdao.PlayerPojo;

/**
 *
 * @author imkor
 */
public class NotAuthrizedUsersHandler {
    public static NotAuthrizedUsersHandler ref = new NotAuthrizedUsersHandler();
    
    private NotAuthrizedUsersHandler(){
        
    }
    
    public SignInStatus handleSignInAttempt(SignInRequest req) throws SQLException{
        SignInStatus reply;
        PlayerPojo p = Dao.selectPlayerByCredential(req.getEmail(), req.getPassword());
        if(p.getID() == -1){    //No player with this Credenatils
            reply = new SignInStatus(AcceptedDinedStatus.DENIED);
        }
        else{
            reply = new SignInStatus(AcceptedDinedStatus.ACCEPTED, new Player(p));
        }
        return reply;
    }
    
}
