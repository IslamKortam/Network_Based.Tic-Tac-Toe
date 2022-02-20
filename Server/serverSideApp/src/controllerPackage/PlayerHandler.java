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
import CommunicationMasseges.GameStatusUpdate.GameStatus;
import CommunicationMasseges.NewGameRequest;
import ServerLogicClasses.UserHandler;
import java.util.Vector;
import GameSession.*;
import ParserPackage.Parser;
import serverdao.PlayerPojo;
import serverhome.ServerHomeUtility;
import ServerSideInvitations.*;
import java.io.IOException;
import java.sql.SQLException;
import playersOnServer.PlayersOnServerUtility;
import serverdao.Dao;

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
    
    public static void notifyAllExcept(CommunicationMassege msg, int id){
        for(PlayerHandler player : players){
            if(player.getId() != id)
                player.sendMeCommMsg(msg);
        }
    }
    
    
    
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
    
    public void handle(CommunicationMassege commMsg) throws SQLException{
        if(commMsg.getType() == CommunicationMassegeType.INVITATION){
            Invitation inv = Parser.gson.fromJson(commMsg.getMsgBody(), Invitation.class);
            System.out.println("Sending Invetation to another player handler " + inv.getReceiverID());
            new ServerSideInvitation(inv);
        }else if(commMsg.getType() == CommunicationMassegeType.INVITATION_RESPONSE){
            InvitationResponse response = Parser.gson.fromJson(commMsg.getMsgBody(), InvitationResponse.class);
            handleInvitationResponse(response);
        }else if(commMsg.getType() == CommunicationMassegeType.GameMove){
            GameMove move = Parser.gson.fromJson(commMsg.getMsgBody(), GameMove.class);
            makeAMove(move);
        }else if(commMsg.getType() == CommunicationMassegeType.GAME_STATUS){
            GameStatusUpdate update = Parser.gson.fromJson(commMsg.getMsgBody(), GameStatusUpdate.class);
            handleSinglePlayerGameEnd(update);
        }else if(commMsg.getType() == CommunicationMassegeType.NEW_SINGLE_PLAYER_GAME){
            changeStatus(PlayerStatus.IN_SINGLE_PLAYER_GAME);
        }else if(commMsg.getType() == CommunicationMassegeType.CHAT){
            ChatMsg msg = Parser.gson.fromJson(commMsg.getMsgBody(), ChatMsg.class);
            sendChatMsg(msg);
        }else if(commMsg.getType() == CommunicationMassegeType.GameSaveRequest){
            RequsetGameSave();
        }else if(commMsg.getType() == CommunicationMassegeType.GameSaveResponse){
            GameSaveResponse response = Parser.gson.fromJson(commMsg.getMsgBody(), GameSaveResponse.class);
            handleGameSaveResponse(response);
        }
    }

    private void handleGameSaveResponse(GameSaveResponse response){
        switch(response.getStatus()){
            case ACCEPTED:
                acceptGameSave();
                break;
            case DENIED:
                //Do Nothing
                break;
        }
    }

    
    private void handleSinglePlayerGameEnd(GameStatusUpdate update) throws SQLException{
        updateScore(update.getStatus());
        changeStatus(PlayerStatus.ONLINE);
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

    public PlayerHandler(PlayerPojo p) throws IOException {

        super(p);
        players.add(this);
        sentInvReq = new Vector<ServerSideInvitation>();
        receivedReq = new Vector<ServerSideInvitation>();
        try{
            PlayersOnServerUtility.addNewPlayer(getId());
        }
        catch(IOException ex){
            System.out.println("Error on Application GUI");
        }
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

    public void makeAMove(GameMove move) throws SQLException {
        currentGame.makeMove(move.getBoxID(), getId());
    }

    public void receiveAmove(int boxId) {
        GameMove move = new GameMove(boxId);
        CommunicationMassege moveMsg = new CommunicationMassege(CommunicationMassegeType.GameMove, Parser.gson.toJson(move));
        userHandler.sendCommMsgToClient(moveMsg);
    }

    public void RequsetGameSave() {
        if(currentGame != null){
            currentGame.requestSave(this.getId());
        }
    }

    public void recieceGameSaveRequest(){
        CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.GameSaveRequest, "");
        sendMeCommMsg(commMsg);
    }

    public void acceptGameSave() {
        if(currentGame != null){
            currentGame.acceptSave(this.getId());
        }
    }

    public void quitGame() {
        if(currentGame != null){
            currentGame.quitGame(this.getId());
        }
    }

    public void updateClientGamestatus(GameStatusUpdate.GameStatus s) {
        GameStatusUpdate status = new GameStatusUpdate(s);
        CommunicationMassege statusMsg = new CommunicationMassege(CommunicationMassegeType.GAME_STATUS, Parser.gson.toJson(status));
        userHandler.sendCommMsgToClient(statusMsg);
    }

    public void win() throws SQLException {
        updateClientGamestatus(GameStatusUpdate.GameStatus.WINNER);
        changeStatus(PlayerStatus.ONLINE);
        updateScore(GameStatus.WINNER);
    }

    public void lose() throws SQLException {
        updateClientGamestatus(GameStatusUpdate.GameStatus.LOSER);
        changeStatus(PlayerStatus.ONLINE);
        updateScore(GameStatus.LOSER);
    }

    public void tie() throws SQLException {
        updateClientGamestatus(GameStatusUpdate.GameStatus.TIE);
        changeStatus(PlayerStatus.ONLINE);
        updateScore(GameStatus.TIE);
    }
    
    public void updateScore(GameStatus gameStatus) throws SQLException{
        final int WINNING_UPDATE = 50;
        final int LOOSING_UPDATE = -50;
        final int TIE_UPDATE = 20;
        switch(gameStatus){
            case TIE:
                setScore(getScore() + TIE_UPDATE);
                break;
            case WINNER:
                setScore(getScore() + WINNING_UPDATE);
                break;
                
            case LOSER:
                setScore(Math.max(getScore() + LOOSING_UPDATE, 0)); //Neglicting negative numbers
                break;
        }
        Dao.updateScore(getId(), getScore());
        
        ScoreUpdate update = new ScoreUpdate(getId(), getScore());
        String s = Parser.gson.toJson(update);
        CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.SCORE_UPDATE, s);
        notifyAllExcept(commMsg, -1);   //Notify all with me
    }

    public void changeStatus(PlayerStatus st) {
        this.setStatus(st);
        //System.err.println("Notify The Others");
        
        StatusUpdate update = new StatusUpdate(getId(), st);
        String s = Parser.gson.toJson(update);
        CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.STATUS_UPDATE, s);
        notifyAllExcept(commMsg, getId());  //Notify all except me
        
        PlayersOnServerUtility.updatePlayer(getId());
        ServerHomeUtility.updateLogs("Player: " + getUserName() + "'s Status changed to: " + st);
    }

    private void sendChatMsg(ChatMsg chatMsg) {
        PlayerHandler.getPlayerHandlerByID(chatMsg.getReceiver()).recieveChatMsg(chatMsg);
    }
    
    private void recieveChatMsg(ChatMsg chatMsg){
        String s = Parser.gson.toJson(chatMsg);
        CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.CHAT, s);
        sendMeCommMsg(commMsg);
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
