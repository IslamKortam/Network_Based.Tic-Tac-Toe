/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerPackage;

import CommHandlerPK.ClientConnectionHandler;
import CommunicationMasseges.AcceptedDinedStatus;
import CommunicationMasseges.CommunicationMassege;
import CommunicationMasseges.CommunicationMassegeType;
import CommunicationMasseges.SignInRequest;
import CommunicationMasseges.SignInStatus;
import ParserPackage.Parser;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import logintrial.LoginTrial;
import logintrial.LoginUtility;
import stagemanager.*;
import stagemanager.StageManager.SceneName;


/**
 *
 * @author imkor
 */
public class MainController {
    private static MainController ref = new MainController();
    private static ClientConnectionHandler connectionHandler;
    
    private MainController(){
        System.out.println("Main Controller Created");
        connectionHandler = new ClientConnectionHandler();
    }
    
    public void handleIncomingSignInRequsetStatus(SignInStatus status) throws IOException{
        if(status.getStatus() == AcceptedDinedStatus.ACCEPTED){
            System.out.println("Accepted");
            System.out.println(status.getPlayerData().getFullName());
            StageManager.getStageManger().displayScene(SceneName.GAMEMODE);
        }
        else{
            System.out.println("Denied");
            LoginUtility.displayLoginError(0);
        }
    }
    
    public void sendSignInRequest(String email, String password){
        SignInRequest req = new SignInRequest(email, password);
        String parsedReq = ParserPackage.Parser.gson.toJson(req);
        CommunicationMassege comm = new CommunicationMassege(CommunicationMassegeType.SIGN_IN_REQUEST, parsedReq);
        connectionHandler.sendCommMsgToServer(comm);
    }

    public static MainController getRef() {
        return ref;
    }
    
    public static void main(String[] args){
        Application.launch(LoginTrial.class, args);
    }
    
}
