/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerPackage;

import CommHandlerPK.ClientConnectionHandler;
import CommunicationMasseges.AcceptedDinedStatus;
import CommunicationMasseges.ChatMsg;
import CommunicationMasseges.CommunicationMassege;
import CommunicationMasseges.CommunicationMassegeType;
import CommunicationMasseges.GameInfo;
import CommunicationMasseges.GameMove;
import CommunicationMasseges.GameSaveResponse;
import CommunicationMasseges.GameStatusUpdate;
import CommunicationMasseges.Invitation;
import CommunicationMasseges.InvitationResponse;
import CommunicationMasseges.NewSinglePlayerGame;
import CommunicationMasseges.ScoreUpdate;
import CommunicationMasseges.SignInRequest;
import CommunicationMasseges.SignInStatus;
import CommunicationMasseges.SignUpResponse;
import CommunicationMasseges.StartMultiPlayerGame;
import CommunicationMasseges.StatusUpdate;
import ParserPackage.Parser;
import gameboard.GameBoardUtility;
import java.io.IOException;
import java.sql.Date;
import java.util.Vector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import loadGame.loadGameUtility;
import logintrial.LoginTrial;
import logintrial.LoginUtility;
import modes.AlertType;
import modes.ModesController;
import playersListScene.PlayersSceneUtility;
import stagemanager.*;
import stagemanager.StageManager.SceneName;
import xoSignupPkg.SignUpUtility;

/**
 *
 * @author imkor
 */
public class MainController extends Application{

    private static MainController ref = new MainController();
    //private static ClientConnectionHandler connectionHandler;
    private StageManager stageMagner;

    public void setStageMagner(StageManager stageMagner) {
        this.stageMagner = stageMagner;
    }

    private MainController() {
        System.out.println("Main Controller Created");
        //connectionHandler = new ClientConnectionHandler();
    }

    public void handleIncomingSignInRequsetStatus(SignInStatus status) throws IOException {
        if (status.getStatus() == AcceptedDinedStatus.ACCEPTED) {
            System.out.println("Accepted");
            System.out.println(status.getPlayerData().getFullName());
            stageMagner.displayScene(SceneName.GAMEMODE);
            Player.setThisPlayer(status.getPlayerData());
        }else if(status.getStatus() == AcceptedDinedStatus.DUPLICATED){
            System.out.println("Duplicated");
            LoginUtility.displayLoginError(2);
        }
        else {
            //Wrong email or pass
            System.out.println("Denied");
            LoginUtility.displayLoginError(0);
        }
    }

    public void sendSignInRequest(String email, String password) {
        SignInRequest req = new SignInRequest(email, password);
        String parsedReq = ParserPackage.Parser.gson.toJson(req);
        CommunicationMassege comm = new CommunicationMassege(CommunicationMassegeType.SIGN_IN_REQUEST, parsedReq);
        ClientConnectionHandler.ref.sendCommMsgToServer(comm);
    }

    public void navigateToSignUpPage() throws IOException {
        stageMagner.displayScene(SceneName.SIGNUP);
    }

    public void navigateToSignInPage() throws IOException {
        stageMagner.displayScene(SceneName.SIGNIN);
    }

    public void handle(CommunicationMassege commMsg) throws IOException {
        if (commMsg.getType() == CommunicationMassegeType.PLAYER) {
            Player p = Parser.gson.fromJson(commMsg.getMsgBody(), Player.class);
            handleRecievingNewPlayer(p);

        } else if (commMsg.getType() == CommunicationMassegeType.INVITATION) {
            System.out.println("Invitation:");
            System.out.println(commMsg.getMsgBody());
            Invitation inv = Parser.gson.fromJson(commMsg.getMsgBody(), Invitation.class);
            handleRecievedInvitation(inv);
        }
        else if(commMsg.getType() == CommunicationMassegeType.START_NEW_MULTIPLAYER_GAME){
            System.out.println("Starting Multiplayer Game");
            StartMultiPlayerGame order = Parser.gson.fromJson(commMsg.getMsgBody(), StartMultiPlayerGame.class);
            startNewMultiPlayerGame(order);
        }
        else if(commMsg.getType() == CommunicationMassegeType.GameMove){
            GameMove move = Parser.gson.fromJson(commMsg.getMsgBody(), GameMove.class);
            
            ClientSideGameController.getRef().makeOpponentMove(move.getBoxID());
        }else if(commMsg.getType() == CommunicationMassegeType.GAME_STATUS){
            GameStatusUpdate update = Parser.gson.fromJson(commMsg.getMsgBody(), GameStatusUpdate.class);
            handleGameStatus(update);
        }else if(commMsg.getType() == CommunicationMassegeType.SIGN_UP_REQUEST_STATUS){
            SignUpResponse reply = Parser.gson.fromJson(commMsg.getMsgBody(), SignUpResponse.class);
            handleSignUpReply(reply);
        }else if(commMsg.getType() == CommunicationMassegeType.STATUS_UPDATE){
            StatusUpdate update = Parser.gson.fromJson(commMsg.getMsgBody(), StatusUpdate.class);
            handleStatusUpdate(update);
        }else if(commMsg.getType() == CommunicationMassegeType.SCORE_UPDATE){
            ScoreUpdate update = Parser.gson.fromJson(commMsg.getMsgBody(), ScoreUpdate.class);
            handleScoreUpdate(update);
        }else if(commMsg.getType() == CommunicationMassegeType.CHAT){
            ChatMsg chatUpdate = Parser.gson.fromJson(commMsg.getMsgBody(), ChatMsg.class);
            handleChatUpdate(chatUpdate);
        }else if(commMsg.getType() == CommunicationMassegeType.GameSaveRequest){
            handleGameSaveRequest();
        }else if(commMsg.getType() == CommunicationMassegeType.GameInfo){
            GameInfo savedGameInfo = Parser.gson.fromJson(commMsg.getMsgBody(), GameInfo.class);
            handleNewSavedGameInfo(savedGameInfo);
        }
    }
    
    private void handleNewSavedGameInfo(GameInfo savedGameInfo){
        int opponentId = -1;
        if(savedGameInfo.getPlayer1ID() == Player.getThisPlayer().getId()){
            //Oponent is player 2
            opponentId = savedGameInfo.getPlayer2ID();
        }else{
            //Oponent is player 1
            opponentId = savedGameInfo.getPlayer1ID();
        }
        Player opponent = Player.getPlayerByID(opponentId);
        String oponnentName = opponent.getUserName();
        loadGameUtility.appendData(savedGameInfo.getGameID(), Date.valueOf(savedGameInfo.getDate()), opponent.getUserName(), "" + opponent.getStatus(), opponentId);
        if(opponent.getStatus() == PlayerStatus.ONLINE){
            loadGameUtility.enableButton(opponentId);
        }else{
            loadGameUtility.disableButton(opponentId);
        }
    }
    
    private void handleGameSaveRequest(){
        System.out.println("Recieved a game save request");
        ClientSideGameController.getRef().createAlertSaveGame();
    }
    
    
    private void handleChatUpdate(ChatMsg chatUpdate){
        GameBoardUtility.appendChatMsg(chatUpdate);
    }
    private void handleStatusUpdate(StatusUpdate update){
        Player.getPlayerByID(update.getPlayerID()).setStatus(update.getNewStatus());
        PlayersSceneUtility.updatePlayer(update.getPlayerID());
        loadGameUtility.changePlayerStatus(update.getPlayerID(), update.getNewStatus());
        if(update.getNewStatus() == PlayerStatus.ONLINE){
            loadGameUtility.enableButton(update.getPlayerID());
        }else{
            loadGameUtility.disableButton(update.getPlayerID());
        }
    }
    
    private void handleScoreUpdate(ScoreUpdate update){
        if(update.getPlayerID() == Player.getThisPlayer().getId()){
            Player.getThisPlayer().setScore(update.getScore());
        }
        else{
            Player.getPlayerByID(update.getPlayerID()).setScore(update.getScore());
            PlayersSceneUtility.updatePlayer(update.getPlayerID());
        }
    }
    
    public void handleSignUpReply(SignUpResponse reply) throws IOException{
               switch (reply.getSignup()){
                case ACCEPT:
                    SignUpUtility.displaySignUpErrorMsg(1, "Your data have been Successfully");
                    stageMagner.displayScene(SceneName.SIGNIN);
                    break;
                case EMAIL_REPEATED:
                    SignUpUtility.displaySignUpErrorMsg(0, "Error! \nRepeated Email");
                    break;
                case USERNAME_REPEATED:
                    SignUpUtility.displaySignUpErrorMsg(0, "Error! \nRepeated UserName");
                    break;
            }
    }
    
    public void handleGameStatus(GameStatusUpdate update){
        
        switch(update.getStatus()){
            case WINNER:
                ClientSideGameController.getRef().declareWinner();
                break;
            case LOSER:
                ClientSideGameController.getRef().declareLooser();
                break;
            case TIE:
                ClientSideGameController.getRef().declareTie();
                break;
            case GameSaved:
                ClientSideGameController.getRef().gameSaved();
                break;
            case OtherPlayerDisconnected:
                ClientSideGameController.getRef().otherUserDisconnected();
                break;
        }
    }
    
    public void sendMoveToServer(int boxID){
        GameMove move = new GameMove(boxID);
        CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.GameMove, Parser.gson.toJson(move));
        ClientConnectionHandler.ref.sendCommMsgToServer(commMsg);
    }
    
    private void startNewMultiPlayerGame(StartMultiPlayerGame order) throws IOException{
        stageMagner.displayScene(SceneName.GAMEBOARD);
        if(order.getType() == StartMultiPlayerGame.MultiPlayerGameType.NEW_GAME){
            new ClientSideGameController(true, order.getTurn(), order.getOponentID());    //Secont parmater is the turn
        }else{
            loadGameUtility.deleteGameFromTable(order.getGameID());
            ClientSideGameController.loadGame(order);
            //new ClientSideGameController(true, order.getTurn(), order.getOponentID()
        }
    }

    private void handleRecievingNewPlayer(Player p) throws IOException {
        System.out.println(p.getEmail() + ":" + p.getFullName() + ":" + p.getUserName() + ":" + p.getStatus());
        Player.allPlayers.add(p);
        PlayersSceneUtility.addNewPlayer(p.getId());
    }

    private void handleRecievedInvitation(Invitation inv) {
        if (stageMagner.getCurrentSceneName() != SceneName.GAMEBOARD) {
            //int invitID,senderID;
            System.out.println(inv.getInvitID());
            System.out.println(Player.allPlayers.size());
            String playerName = Player.getPlayerByID(inv.getSenderID()).getUserName();
            String alertBody = "";
            if(inv.getType() == Invitation.InvitationType.NEW_GAME){
                alertBody = playerName + " wants to challenge you\nDo you accept this challenge?";
            }else{
                //Load game
                alertBody = playerName + " wants to load a game with ID " + inv.getGameID() + "\n" + "Do you accept this challenge?";
            }
            final String fAlertBody = alertBody;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Boolean opinion = ModesController.ref.showAlert(fAlertBody);
                    InvitationResponse response = null;
                    if (opinion) {
                        response = new InvitationResponse(inv.getInvitID(), AcceptedDinedStatus.ACCEPTED);
                    } else {
                        response = new InvitationResponse(inv.getInvitID(), AcceptedDinedStatus.DENIED);
                    }
                    String s = Parser.gson.toJson(response);
                    ClientConnectionHandler.ref.sendCommMsgToServer(new CommunicationMassege(CommunicationMassegeType.INVITATION_RESPONSE, s));
                }
            });
        } else {
            System.out.println("Player in another game");
        }
    }

    public void navigateToplayerScene() throws IOException {
        stageMagner.displayScene(SceneName.PLAYERLIST);
    }
    
    public void navigeToLoadGameScene() throws IOException {
        stageMagner.displayScene(SceneName.LOADGAME);
    }
    
    

    public void navigateToModes() throws IOException {
        stageMagner.displayScene(SceneName.GAMEMODE);
    }

    public static MainController getRef() {
        return ref;
    }

    public void initSinglePlayerGame() throws IOException {
        
        NewSinglePlayerGame update = new NewSinglePlayerGame((ClientSideGameController.isIsHardGame() ? NewSinglePlayerGame.Difficulty.HARD : NewSinglePlayerGame.Difficulty.EASY));
        String s = Parser.gson.toJson(update);
        CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.NEW_SINGLE_PLAYER_GAME, s);
        ClientConnectionHandler.ref.sendCommMsgToServer(commMsg);
        stageMagner.displayScene(SceneName.GAMEBOARD);
        new ClientSideGameController(false, 0, 0); //false for single palyer
    }

    public static void main(String[] args) throws IOException {
        System.out.println("................................");


        Player.allPlayers = new Vector<Player>();

        Application.launch(LoginTrial.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }

}
