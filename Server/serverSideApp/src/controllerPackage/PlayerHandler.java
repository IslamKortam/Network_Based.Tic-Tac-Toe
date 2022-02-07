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
/**
 *
 * @author Salma
 */
public class PlayerHandler extends Player {
    private static Vector<PlayerHandler>players=new Vector<PlayerHandler>();
    UserHandler userHandler;
    GameSession currentGame;
      private static Vector<NewGameRequest> sentReq=new Vector<NewGameRequest>();
     private static Vector<NewGameRequest> receivedReq=new Vector<NewGameRequest>();
     
     
     public void sendMeCommMsg(CommunicationMassege commMsg){
         if(getStatus() == PlayerStatus.ONLINE){
             userHandler.sendCommMsgToClient(commMsg);
         }
     }
     
     public void sendMeAllPlayers(){
         for(PlayerHandler playerHandler : players){
             CommunicationMassege commMsg = new CommunicationMassege(CommunicationMassegeType.PLAYER, Parser.gson.toJson(new Player(playerHandler)));
             sendMeCommMsg(commMsg);
         }
     }

    public static Vector<PlayerHandler> getPlayers() {
        return players;
    }

    public void setUserHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }
    
    public static PlayerHandler getPlayerHandlerByID(int id){
        PlayerHandler p = null;
        for(PlayerHandler player : players){
            if(player.getId() == id){
                p = player;
            }
        }
        return p;
    }
 
       
 
    public PlayerHandler(PlayerPojo p) {
  
        super(p);
        players.add(this); 
        
       
    } 
     
   public void  RequestNewGame(int playerId){
       NewGameRequest r= new NewGameRequest();
       sentReq.add(r);
   
   }
   public void  AcceptRequest(int requestId){
       for(NewGameRequest r:receivedReq){
           if(r.getGameReqId()==requestId){
               r.accept();
           }
       }
   }
   public void makeAMove(int boxId,int playerId){
     currentGame.makeMove(boxId,playerId);
   }
   
   public void receiveAmove(int boxId){
       GameMove move=new GameMove(boxId);
         CommunicationMassege moveMsg = new CommunicationMassege(CommunicationMassegeType.GameMove, Parser.gson.toJson(move));
      userHandler.sendCommMsgToClient(moveMsg);
   }
   
   public void RequsetGameSave(int playerId){
         GameSession.requestSave(playerId);
   }  
    public void acceptGameSave(int playerId){
         GameSession.acceptSave(playerId);
   }  
    public void quitGame(int playerId){
         GameSession.quitGame(playerId);
   }  
    
    
    public void status(GameStatusUpdate.GameStatus s){
          GameStatusUpdate status = new GameStatusUpdate(s);
      CommunicationMassege statusMsg = new CommunicationMassege(CommunicationMassegeType.GAME_STATUS, Parser.gson.toJson(status));
      userHandler.sendCommMsgToClient(statusMsg);
    }
  public void win(){
    status(GameStatusUpdate.GameStatus.WINNER);
   
  }
  public void lose(){
     status(GameStatusUpdate.GameStatus.LOSER);
   
  }
  public void Tie(){
     status(GameStatusUpdate.GameStatus.TIE);
   
  }
  
  public void changeStatus(PlayerStatus st){
      this.setStatus(st);
      System.err.println("Notify The Others");
      ServerHomeUtility.updateLogs("Player: " + getUserName() + "'s Status changed to: " + st);
  }
  

 public void sendMsg(int player2Id,String message){
        Chatting chat = new Chatting(player2Id,message);
         CommunicationMassege chatMsg = new CommunicationMassege(CommunicationMassegeType.CHAT, Parser.gson.toJson(chat));
      userHandler.sendCommMsgToClient(chatMsg);
  }

    
}