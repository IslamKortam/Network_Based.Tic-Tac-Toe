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
                System.out.println("Recieved from client:" + s);
                if(authrized){
                    CommunicationMassege commMsg = Parser.gson.fromJson(s, CommunicationMassege.class);
                    connectedPlayer.handle(commMsg);
                }
                else{
                    CommunicationMassege comm = Parser.gson.fromJson(s, CommunicationMassege.class);
                    if(comm.getType() == CommunicationMassegeType.SIGN_IN_REQUEST){
                        SignInRequest req = Parser.gson.fromJson(comm.getMsgBody(), SignInRequest.class);
                        SignInStatus status = NotAuthrizedUsersHandler.ref.handleSignInAttempt(req);
                        if(status.getStatus() == AcceptedDinedStatus.ACCEPTED){
                            System.out.println("Accepted Client");
                            connectedPlayer = getPlayerHandlerByID(status.getPlayerData().getId());
                            connectedPlayer.setUserHandler(this);
                            connectedPlayer.changeStatus(PlayerStatus.ONLINE);
                            authrized = true;
                            connectedPlayer.sendMeAllPlayers();
                        }else{
                            System.out.println("Denied Client");
                        }
                        comm = new CommunicationMassege(CommunicationMassegeType.SIGN_IN_REQUEST_STATUS, Parser.gson.toJson(status));
                        s = Parser.gson.toJson(comm);
                        sendToClient(s);
                    }
                    else{
                        //Handle Sign UP here
                    }
                    
                    
                    
                }
                
            } catch (IOException ex) {
                //Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Player Disconnected");
                if(authrized){
                    authrized = false;
                    connectedPlayer.changeStatus(PlayerStatus.OFFLINE);
                    connectedPlayer.setUserHandler(null);
                    //this.stop();
                    try {
                        inputStream.close();
                    } catch (IOException ex1) {
                        Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex1);
                        System.out.println("iN Catch");
                    }
                    outputStream.close();
                    UserHandler.connectedUsersHandelers.remove(this);
                    this.stop();
                }
                else{
                    try {
                        inputStream.close();
                    } catch (IOException ex1) {
                        Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex1);
                        System.out.println("iN Catch");
                    }
                    outputStream.close();
                    UserHandler.connectedUsersHandelers.remove(this);
                    this.stop();
                }
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
}
