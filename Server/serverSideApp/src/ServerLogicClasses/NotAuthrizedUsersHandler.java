/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerLogicClasses;

import CommunicationMasseges.AcceptedDinedStatus;
import CommunicationMasseges.SignInRequest;
import CommunicationMasseges.SignInStatus;
import CommunicationMasseges.SignUpRequest;
import CommunicationMasseges.SignUpResponse;
import CommunicationMasseges.SignUpStatusEnum;
import controllerPackage.Player;
import controllerPackage.PlayerHandler;
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
    
    
     public SignUpResponse handleSignUpAttempt(SignUpRequest req) throws SQLException{
        SignUpResponse reply = null;
        Boolean emailValid = true;
        Boolean userNameValid = true;
        String email = req.getNewPlayer().getEmail();
        String userName = req.getNewPlayer().getUserName();
         System.out.println(email);
         System.out.println(userName);
        for(PlayerHandler player : PlayerHandler.getPlayers()){
            System.out.println(player.getEmail());
            System.out.println(player.getUserName());
            if(email.equals(player.getEmail())){
                emailValid = false;
            }
            if(userName.equals(player.getUserName())){
                userNameValid = false;
            }
        }
        if(userNameValid && emailValid){
            Dao.insertIntoPlayerTable(req.getNewPlayer());
            reply = new SignUpResponse(SignUpStatusEnum.ACCEPT);
            new PlayerHandler(Dao.selectPlayerByEmail(email));
        }else{
            if(!emailValid){
                reply = new SignUpResponse(SignUpStatusEnum.EMAIL_REPEATED);
            }else{
                reply = new SignUpResponse(SignUpStatusEnum.USERNAME_REPEATED);
            }
        }
        return reply;
    }
    
}
