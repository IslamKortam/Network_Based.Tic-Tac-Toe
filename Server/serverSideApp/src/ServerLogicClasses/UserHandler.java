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
    public UserHandler(Socket sc) throws IOException{
        this.socket = sc;
        inputStream = new DataInputStream(sc.getInputStream());
        outputStream = new PrintStream(sc.getOutputStream());
        UserHandler.connectedUsersHandelers.add(this);
        startListening();
        //outputStream.println("Your are Connected");
    }
    
    @Override
    public void run(){
        while(true){
            try {
                String s = inputStream.readLine();
                if(authrized){
                    
                }
                else{
                    CommunicationMassege comm = Parser.gson.fromJson(s, CommunicationMassege.class);
                    if(comm.getType() == CommunicationMassegeType.SIGN_IN_REQUEST){
                        SignInRequest req = Parser.gson.fromJson(comm.getMsgBody(), SignInRequest.class);
                        SignInStatus status = NotAuthrizedUsersHandler.ref.handleSignInAttempt(req);
                        if(status.getStatus() == AcceptedDinedStatus.ACCEPTED){
                            System.out.println("Accepted Client");
                            authrized = true;
                        }else{
                            System.out.println("Denied Client");
                        }
                        comm = new CommunicationMassege(CommunicationMassegeType.SIGN_IN_REQUEST_STATUS, Parser.gson.toJson(status));
                        s = Parser.gson.toJson(comm);
                        sendToClient(s);
                    }
                    //else if(comm.getType() == CommunicationMassegeType)
                    
                }
                
            } catch (IOException ex) {
                Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
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
