/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerPackage;

import CommunicationMasseges.NewGameRequest;
import ServerLogicClasses.UserHandler;
import java.util.Vector;
import GameSession;
/**
 *
 * @author Salma
 */
public class PlayerHandler extends Player {
    private static Vector<PlayerHandler>players=new Vector<PlayerHandler>();
    UserHandler userHandler=new UserHandler();
    GameSession currentGame= new GameSession();
      private static Vector<NewGameRequest> sentReq=new Vector<NewGameRequest>();
     private static Vector<NewGameRequest> receivedReq=new Vector<NewGameRequest>();
 
       
 
    public PlayerHandler(){
        players.add(this);
     
       
    } 
     
   public void  RequestNewGame(int playerId){
       NewGameRequest r= new NewGameRequest();
       sentReq.add(r);
       //some enhancement use hashmap instead of vectors to store request and  player id specified to this request
   }
   public void  AcceptRequest(int requestId){
       for(NewGameRequest r:receivedReq){
           if(r.getGameReqId()==requestId){
               r.accept();
           }
       }
   }
   public void makeMove(int boxId){
    //  currentGame.makeMove(boxId);
   }
   public void receiveAmove(int boxId){

   }
   
    
}
