/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerLogicClasses;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import ParserPackage.Parser;
import CommunicationMasseges.*;
import controllerPackage.PlayerHandler;
import static controllerPackage.PlayerHandler.getPlayerHandlerByID;
import controllerPackage.PlayerStatus;
import java.sql.SQLException;
/**
 *
 * @author imkor
 */
public class UserHandler extends Thread{
    public static Vector<UserHandler> connectedUsersHandelers = new Vector<UserHandler>();
    private Socket socket;
    private DataInputStream inputStream;
    private PrintStream outputStream;
    private Boolean authrized = false;
    private PlayerHandler connectedPlayer;
    public UserHandler(Socket sc) throws IOException{
        this.socket = sc;
        inputStream = new DataInputStream(sc.getInputStream());
        outputStream = new PrintStream(sc.getOutputStream());
        UserHandler.connectedUsersHandelers.add(this);
        startListening();
        //outputStream.println("Your are Connected");
    }

    public UserHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void run(){
        while(true){
            try {
                String s = inputStream.readLine();
                if(authrized){
                    CommunicationMassege commMsg = Parser.gson.fromJson(s, CommunicationMassege.class);
                    if(commMsg.getType() == CommunicationMassegeType.SIGN_OUT_REQUEST){
                        authrized = false;
                        connectedPlayer.changeStatus(PlayerStatus.OFFLINE);
                        connectedPlayer.setUserHandler(null);
                        connectedPlayer = null;
                    }else{
                        connectedPlayer.handle(commMsg);
                    }
                }
                else{
                    CommunicationMassege comm = Parser.gson.fromJson(s, CommunicationMassege.class);
                    if(comm.getType() == CommunicationMassegeType.SIGN_IN_REQUEST){
                        SignInRequest req = Parser.gson.fromJson(comm.getMsgBody(), SignInRequest.class);
                        SignInStatus status = NotAuthrizedUsersHandler.ref.handleSignInAttempt(req);
                        comm = new CommunicationMassege(CommunicationMassegeType.SIGN_IN_REQUEST_STATUS, Parser.gson.toJson(status));
                        if(status.getStatus() == AcceptedDinedStatus.ACCEPTED){
                            connectedPlayer = getPlayerHandlerByID(status.getPlayerData().getId());
                            connectedPlayer.setUserHandler(this);
                            connectedPlayer.changeStatus(PlayerStatus.ONLINE);
                            authrized = true;
                            sendCommMsgToClient(comm);
                            connectedPlayer.sendMeAllPlayers();
                            connectedPlayer.sendMeMySavedGames();
                        }else{
                            sendCommMsgToClient(comm);
                        }
                        
                    }
                    else if(comm.getType() == CommunicationMassegeType.SIGN_UP_REQUEST){
                        SignUpRequest req = Parser.gson.fromJson(comm.getMsgBody(), SignUpRequest.class);
                        SignUpResponse status = NotAuthrizedUsersHandler.ref.handleSignUpAttempt(req);
                        String res = Parser.gson.toJson(status);
                        CommunicationMassege replyComm = new CommunicationMassege(CommunicationMassegeType.SIGN_UP_REQUEST_STATUS, res);
                        sendCommMsgToClient(replyComm);
                    }
                    
                    
                    
                }
                
            } catch (IOException ex) {
                //Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
                UserHandler.connectedUsersHandelers.remove(this);
                stopThisUser();
            } catch (SQLException ex) {
                //Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void sendToClient(String s){
        outputStream.println(s);
    }
    
    public void sendCommMsgToClient(CommunicationMassege commMsg){
        String s = Parser.gson.toJson(commMsg);
        sendToClient(s);
    }
    
    public void startListening(){
        this.start();
    }
    public void stopListening() throws IOException{
        this.stop();
        socket.close();
        UserHandler.connectedUsersHandelers.remove(this);
    }
    
    private void stopThisUser(){
        if(authrized){
                    connectedPlayer.changeStatus(PlayerStatus.OFFLINE);
                    if(connectedPlayer.getCurrentGame() != null){
                        connectedPlayer.getCurrentGame().handlePlayerDisconnection();
                    }
                    authrized = false;
                    connectedPlayer.setUserHandler(null);
                    try {
                        inputStream.close();
                        outputStream.close();
                        socket.close();
                        
                    } catch (IOException ex1) {
                        Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    this.stop();
                }
                else{
                    
                    //outputStream.close();
                    
                    this.stop();
                    try {
                        socket.shutdownOutput();
                        socket.shutdownInput();
                        
                    } catch (IOException ex) {
                        Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    finally{
                        try {
                            socket.close();
                        } catch (IOException ex) {
                            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
        }
    }
    
    
    
    public static void stopAllUsers(){
        for(UserHandler userHandler : connectedUsersHandelers){
            userHandler.stopThisUser();
        }
        connectedUsersHandelers.removeAllElements();
    }
}
