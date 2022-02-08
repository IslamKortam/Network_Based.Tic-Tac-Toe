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
import CommunicationMasseges.Invitation;
import CommunicationMasseges.SignInRequest;
import CommunicationMasseges.SignInStatus;
import ParserPackage.Parser;
import java.io.IOException;
import java.util.Vector;
import javafx.application.Application;
import logintrial.LoginTrial;
import logintrial.LoginUtility;
import modes.AlertType;
import modes.ModesController;
import playersListScene.PlayersSceneUtility;
import stagemanager.*;
import stagemanager.StageManager.SceneName;


/**
 *
 * @author imkor
 */
public class MainController {
    private static MainController ref = new MainController();
    private static ClientConnectionHandler connectionHandler;
    private StageManager stageMagner;

    public void setStageMagner(StageManager stageMagner) {
        this.stageMagner = stageMagner;
    }
    
    private MainController(){
        System.out.println("Main Controller Created");
        connectionHandler = new ClientConnectionHandler();
    }
    
    public void handleIncomingSignInRequsetStatus(SignInStatus status) throws IOException{
        if(status.getStatus() == AcceptedDinedStatus.ACCEPTED){
            System.out.println("Accepted");
            System.out.println(status.getPlayerData().getFullName());
            stageMagner.displayScene(SceneName.GAMEMODE);
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
    
    public void navigateToSignUpPage() throws IOException{
        stageMagner.displayScene(SceneName.SIGNUP);
    }
    
    public void navigateToSignInPage() throws IOException{
        stageMagner.displayScene(SceneName.SIGNIN);
    }
    
    public void handle(CommunicationMassege commMsg) throws IOException{
        if(commMsg.getType() == CommunicationMassegeType.PLAYER){
            Player p = Parser.gson.fromJson(commMsg.getMsgBody(), Player.class);
            System.out.println(p.getEmail() + ":" + p.getFullName() + ":" + p.getUserName() + ":" + p.getStatus());
            PlayersSceneUtility.addPlayerToVector(p);
        }
        else if(commMsg.getType() == CommunicationMassegeType.INVITATION){
            
        }
    }
    private void receiveInvitation(Invitation inv){
        if(stageMagner.getCurrentSceneName() != SceneName.GAMEBOARD){
            int invitID,senderID;
            String playerName = Player.getPlayerByID(inv.getSenderID()).getUserName();
            ModesController.ref.showAlert(playerName+" want to challenge you\nDo you accept this challenge?",AlertType.GameLoad);
        }
        else{
            System.out.println("Player in another game");
        }
    }
    public void navigateToplayerScene() throws IOException{
        stageMagner.displayScene(SceneName.PLAYERLIST);
    }
    
    public void navigateToModes() throws IOException{
        stageMagner.displayScene(SceneName.GAMEMODE);
    }

    public static MainController getRef() {
        return ref;
    }

    public void initSinglePlayerGame() throws IOException{
        stageMagner.displayScene(SceneName.GAMEBOARD);
        new ClientSideGameController(false,0);
    }
    
    public static void main(String[] args) throws IOException{
        System.out.println("................................");
//Player p= new Player(9,"salma","Mohamed","salma@yahoo.com",4,5,PlayerStatus.IN_MULTIPLAYER_GAME);
//Vector<Player> vector =new Vector<Player>();
//vector.add(p);
        PlayersSceneUtility.setPlayers(new Vector<Player>());
//PlayersSceneUtility.addPlayerToVector(p);
//Player p= new Player(9,"salma","Mohamed","salma@yahoo.com",4,5,PlayerStatus.IN_MULTIPLAYER_GAME);
//PlayersSceneUtility.addPlayerToVector(p);
//Player p1= new Player(9,"salma","Mohamed","salma@yahoo.com",4,5,PlayerStatus.IN_SINGLE_PLAYER_GAME);
//PlayersSceneUtility.addPlayerToVector(p1);
        Application.launch(LoginTrial.class, args);
    }
    
}
