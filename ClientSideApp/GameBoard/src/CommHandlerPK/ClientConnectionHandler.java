/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommHandlerPK;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ParserPackage.Parser;
import CommunicationMasseges.*;
import controllerPackage.MainController;

/**
 *
 * @author imkor
 */
public class ClientConnectionHandler extends Thread{
    public static ClientConnectionHandler ref;
    private Socket clientSocket;
    private String serverIP = "127.0.0.1";
    private int serverPort = 8083;
    private DataInputStream inputStream;
    private PrintStream outputStream;
    private Boolean listening;
    public ClientConnectionHandler(){
        listening = false;
        try {
            clientSocket = new Socket(serverIP, serverPort);
            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new PrintStream(clientSocket.getOutputStream());
            startListening();
            ref = this;
            
        } catch (IOException ex) {
            handleBeginConnectionProblem();
            //Logger.getLogger(ClientConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void handleBeginConnectionProblem(){
        
        try {
            stagemanager.StageManager.getStageManger().resetStage();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        String title = "Connection problem(Server not found)";
        String body = "Re-enter Server IP Address and press ok to reconnet.\nOr cancel to Exit";
        logintrial.LoginController.showConnectionProblemAlert(title, body, getServerIP());
    }
    
    private void handleConnectionBroken(){
        try {
            stagemanager.StageManager.getStageManger().resetStage();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        String title = "Connetcion to Server is Lost!";
        String body = "Re-enter Server IP Address and press ok to reconnet.\nOr cancel to Exit";
        logintrial.LoginController.showConnectionProblemAlert(title, body, getServerIP());
    }
    
    @Override
    public void run(){
        while(true){
            try {
                String s = inputStream.readLine();
                System.out.println("Recieved From Server:" + s);
                if(s == null){
                    System.err.println("Server disconnected!!!");
                    continue;
                }
                CommunicationMassege msg = Parser.gson.fromJson(s, CommunicationMassege.class);
                switch(msg.getType()){
                    case SIGN_IN_REQUEST_STATUS:
                        SignInStatus parsedStatus = Parser.gson.fromJson(msg.getMsgBody(), SignInStatus.class);
                        MainController.getRef().handleIncomingSignInRequsetStatus(parsedStatus);
                        break;
                        
                }
                if(msg.getType() != CommunicationMassegeType.SIGN_IN_REQUEST_STATUS){
                    MainController.getRef().handle(msg);
                }
                System.out.println("CommHandler:"+s);
            } catch (IOException ex) {
                Logger.getLogger(ClientConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void sendCommMsgToServer(CommunicationMassege msg){
        String s = Parser.gson.toJson(msg, CommunicationMassege.class);
        sendToServer(s);
    }
    
    private void sendToServer(String s){
        System.out.println("Sending To Server:" + s);
        outputStream.println(s);
    }
    
    public void startListening(){
        if(!listening){
            listening = true;
            this.start();
        }
    }
    
    public void stopListening(){
        if(listening){
            listening = false;
            this.stop();
        }
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    
    
    
}
