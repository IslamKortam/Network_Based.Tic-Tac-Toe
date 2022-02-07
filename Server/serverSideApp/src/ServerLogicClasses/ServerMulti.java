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
    private static ServerMulti ref = new ServerMulti();
    private static Boolean runningStatus = true;
    private ServerSocket serverSocket;
    private ServerMulti(){
        try {
            serverSocket = new ServerSocket(5005);
            ref = this;
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
                Logger.getLogger(ServerMulti.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void startServer(){
        this.runningStatus = true;
        this.start();
    }
    public void stopServer(){
        this.runningStatus = false;
        this.stop();
    }

    public static Boolean getRunningStatus() {
        return runningStatus;
    }

    public static ServerMulti getRef() {
        return ref;
    }
    
    
}

