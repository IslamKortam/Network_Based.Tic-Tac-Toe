/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerLogicClasses;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author imkor
 */
public class ServerMulti extends Thread{
    private static ServerMulti ref;
    private static Boolean runningStatus = false;
    private ServerSocket serverSocket;
    private ServerMulti(){
        try {
            serverSocket = new ServerSocket(8083);
            ref = this;
            runningStatus = true;
        } catch (IOException ex) {
            Logger.getLogger(ServerMulti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void run(){
        while(true){
            try {
                Socket internalSocket = serverSocket.accept();
                new UserHandler(internalSocket);
            } catch (IOException ex) {
                ref = null;
                runningStatus = false;
                Logger.getLogger(ServerMulti.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void startServer(){
        if(getRunningStatus()){
            return;
        }
        ref = new ServerMulti();
        ref.runningStatus = true;
        ref.start();
    }
    public static void stopServer(){
        if(!getRunningStatus()){
            return;
        }
        ref.stop();
        ref.runningStatus = false;
        try {
            ref.serverSocket.close();
            ref = null;
        } catch (IOException ex) {
            ref = null;
            Logger.getLogger(ServerMulti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Boolean getRunningStatus() {
        return runningStatus;
    }

    public static ServerMulti getRef() {
        return ref;
    }
    
    
}

