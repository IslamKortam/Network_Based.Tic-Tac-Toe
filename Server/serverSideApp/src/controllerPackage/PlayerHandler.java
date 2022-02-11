/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerPackage;

import CommunicationMasseges.CommunicationMassege;
import CommunicationMasseges.CommunicationMassegeType;
import CommunicationMasseges.GameMove;
import CommunicationMasseges.*;
import CommunicationMasseges.GameStatusUpdate;
import CommunicationMasseges.NewGameRequest;
import ServerLogicClasses.UserHandler;
import java.util.Vector;
import GameSession.*;
import ParserPackage.Parser;
import serverdao.PlayerPojo;
import serverhome.ServerHomeUtility;
import ServerSideInvitations.*;

/**
 *
 * @author Salma
 */
public class PlayerHandler extends Player {

    private static Vector<PlayerHandler> players = new Vector<PlayerHandler>();
    UserHandler userHandler;
    GameSession currentGame;
    private Vector<ServerSideInvitation> sentInvReq;
    private Vector<ServerSideInvitation> receivedReq;
    
    private ServerSideInvitation getRecievedInvitationByID(int id){
        ServerSideInvitation invitation = null;
        for(ServerSideInvitation inv : receivedReq){
            if(inv.getInvitID() == id){
                invitation = inv;
                break;
            }
        }
        return invitation;
    }
        

    public void addARecievedInvitation(ServerSideInvitation invitation){
        receivedReq.add(invitation);
        String s = Parser.gson.toJson(new Invitation(invitation));
        CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.INVITATION, s); 
        if(getStatus() == PlayerStatus.ONLINE){
            sendMeCommMsg(commMsg);
        }
    }
    
    public void addASentInvitation(ServerSideInvitation invitation){
        sentInvReq.add(invitation);
    }
     
     public static PlayerHandler getPlayerHandlerByID(int id) {
        PlayerHandler p = null;
        for (PlayerHandler player : players) {
            if (player.getId() == id) {
                p = player;
                break;
            }
        }
        return p;
    }

    public void sendMeCommMsg(CommunicationMassege commMsg) {
        if (getStatus() != PlayerStatus.OFFLINE) {
            userHandler.sendCommMsgToClient(commMsg);
        }
    }

    public void sendMeAllPlayers() {
        for (PlayerHandler playerHandler : players) {
            if(playerHandler.getId() != getId()){
                CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.PLAYER, Parser.gson.toJson(new Player(playerHandler)));
                sendMeCommMsg(commMsg);
            }
        }
    }
    
    public void handle(CommunicationMassege commMsg){
        if(commMsg.getType() == CommunicationMassegeType.INVITATION){
            new ServerSideInvitation(Parser.gson.fromJson(commMsg.getMsgBody(), Invitation.class));
        }else if(commMsg.getType() == CommunicationMassegeType.INVITATION_RESPONSE){
            InvitationResponse response = Parser.gson.fromJson(commMsg.getMsgBody(), InvitationResponse.class);
            handleInvitationResponse(response);
        }else if(commMsg.getType() == CommunicationMassegeType.GameMove){
            GameMove move = Parser.gson.fromJson(commMsg.getMsgBody(), GameMove.class);
            makeAMove(move);
        }
    }
    
    private void handleInvitationResponse(InvitationResponse response){
        ServerSideInvitation inv = getRecievedInvitationByID(response.getInvitationID());
        if(inv == null){
            return;
        }
        if(response.getStatus() == AcceptedDinedStatus.ACCEPTED){
            inv.accept();
        }else{
            inv.deny();
        }
    }

    public static Vector<PlayerHandler> getPlayers() {
        return players;
    }

    public void setUserHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    public PlayerHandler(PlayerPojo p) {

        super(p);
        players.add(this);
        sentInvReq = new Vector<ServerSideInvitation>();
        receivedReq = new Vector<ServerSideInvitation>();

    }

    public void RequestNewGame(int playerId) {
        NewGameRequest r = new NewGameRequest();
        //sentReq.add(r);

    }

    public void AcceptRequest(int requestId) {
        /*for (NewGameRequest r : receivedReq) {
            if (r.getGameReqId() == requestId) {
                r.accept();
            }
        }*/
    }

    public void makeAMove(GameMove move) {
        currentGame.makeMove(move.getBoxID(), getId());
    }

    public void receiveAmove(int boxId) {
        GameMove move = new GameMove(boxId);
        CommunicationMassege moveMsg = new CommunicationMassege(CommunicationMassegeType.GameMove, Parser.gson.toJson(move));
        userHandler.sendCommMsgToClient(moveMsg);
    }

    public void RequsetGameSave(int playerId) {
        GameSession.requestSave(playerId);
    }

    public void acceptGameSave(int playerId) {
        GameSession.acceptSave(playerId);
    }

    public void quitGame(int playerId) {
        GameSession.quitGame(playerId);
    }

    public void status(GameStatusUpdate.GameStatus s) {
        GameStatusUpdate status = new GameStatusUpdate(s);
        CommunicationMassege statusMsg = new CommunicationMassege(CommunicationMassegeType.GAME_STATUS, Parser.gson.toJson(status));
        userHandler.sendCommMsgToClient(statusMsg);
    }

    public void win() {
        status(GameStatusUpdate.GameStatus.WINNER);
        changeStatus(PlayerStatus.ONLINE);

    }

    public void lose() {
        status(GameStatusUpdate.GameStatus.LOSER);
        changeStatus(PlayerStatus.ONLINE);

    }

    public void tie() {
        status(GameStatusUpdate.GameStatus.TIE);
        changeStatus(PlayerStatus.ONLINE);

    }

    public void changeStatus(PlayerStatus st) {
        this.setStatus(st);
        System.err.println("Notify The Others");
        ServerHomeUtility.updateLogs("Player: " + getUserName() + "'s Status changed to: " + st);
    }

    public void sendMsg(int player2Id, String message) {
        Chatting chat = new Chatting(player2Id, this.getId(), message);
        CommunicationMassege chatMsg = new CommunicationMassege(CommunicationMassegeType.CHAT, Parser.gson.toJson(chat));
        userHandler.sendCommMsgToClient(chatMsg);
    }
    public void startNewMultiPlayerGame(int turn, int oponentID){
        StartMultiPlayerGame order = new StartMultiPlayerGame(turn, oponentID);
        String s = Parser.gson.toJson(order);
        CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.START_NEW_MULTIPLAYER_GAME, s);
        sendMeCommMsg(commMsg);
    }

    public void setCurrentGame(GameSession currentGame) {
        this.currentGame = currentGame;
    }
    
    
    
}
