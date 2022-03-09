/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameSession;

import CommunicationMasseges.CommunicationMassege;
import CommunicationMasseges.CommunicationMassegeType;
import CommunicationMasseges.GameStatusUpdate;
import controllerPackage.Player;
import controllerPackage.PlayerHandler;
import controllerPackage.PlayerStatus;
import serverdao.Dao;
import serverdao.GamePojo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import ParserPackage.Parser;
import ServerSideInvitations.ServerSideInvitation;
import com.google.gson.reflect.TypeToken;
import java.time.LocalDate;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bahaa eldin
 */
public class GameSession {

//    private static PlayerHandler player0 = new PlayerHandler(0);
//    private static PlayerHandler player1 = new PlayerHandler(1);
//    private static PlayerHandler[] players = new PlayerHandler[]{player0,player1};
//    private static int turn=0;
    private PlayerHandler player0;
    private PlayerHandler player1;
    private PlayerHandler[] players;
    private int turn;
    private ArrayList<Integer> arrayOfMoves = new ArrayList(0);
    private char[] XOBoard = new char[]{'.','.','.','.','.','.','.','.','.'};
    private char[] playerMark = new char[]{'X','O'};

    public void requestSave(int requesterID) {
        if(requesterID == player0.getId()){
            //Player0 is the one who requested
            player1.recieceGameSaveRequest();
        }else{
            //Player1 is the one who requested
            player0.recieceGameSaveRequest();
        }
    }

    public void acceptSave(int playerId) {
        save();
        player0.updateClientGamestatus(GameStatusUpdate.GameStatus.GameSaved);
        player0.changeStatus(PlayerStatus.ONLINE);
        
        player1.updateClientGamestatus(GameStatusUpdate.GameStatus.GameSaved);
        player1.changeStatus(PlayerStatus.ONLINE);
        
        endGame();
    }
    
    public void handlePlayerDisconnection(){
        save();
        if(player0.getStatus() == PlayerStatus.OFFLINE){
            //Player 0 disconnected
            player1.updateClientGamestatus(GameStatusUpdate.GameStatus.OtherPlayerDisconnected);
            player1.changeStatus(PlayerStatus.ONLINE);
            
        }else{
            //Player 1 disconnected
            player0.updateClientGamestatus(GameStatusUpdate.GameStatus.OtherPlayerDisconnected);
            player0.changeStatus(PlayerStatus.ONLINE);
        }
        endGame();
    }

    public void quitGame(int playerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void save(){
        String gameBoard = Parser.gson.toJson(arrayOfMoves);
        LocalDate date = LocalDate.now();
        Date sqlDate = Date.valueOf(date);
        GamePojo g = new GamePojo(player0.getId(), player1.getId(), 0, gameBoard, false, -1, sqlDate, true);
        try {
            Dao.insertIntoGameTable(g);
            GamePojo savedGame = Dao.selectGameByPlayerID(player0.getId()).lastElement();
            player0.sendMeNewSavedGame(savedGame);
            player1.sendMeNewSavedGame(savedGame);
            
        } catch (SQLException ex) {
            Logger.getLogger(GameSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private enum GameResult{
        INCOMPLETE,
        TIE,
        PLAYER_1_WON,
        PLAYER_2_WON
    }
    
    public GameSession (PlayerHandler player_0 , PlayerHandler player_1){
        player0 = player_0;
        player1 = player_1;
        player0.changeStatus(PlayerStatus.IN_MULTIPLAYER_GAME);
        player1.changeStatus(PlayerStatus.IN_MULTIPLAYER_GAME);
        players=new PlayerHandler[]{player0,player1};
        turn = 0;
        player0.startNewMultiPlayerGame(0, player_1.getId());
        player1.startNewMultiPlayerGame(1, player_0.getId());
    }

    public GameSession(PlayerHandler player_0 , PlayerHandler player_1 , ArrayList arrayOfmoves, int gameID){
        player0 = player_0;
        player1 = player_1;
        players=new PlayerHandler[]{player0,player1};
        player0.changeStatus(PlayerStatus.IN_MULTIPLAYER_GAME);
        player1.changeStatus(PlayerStatus.IN_MULTIPLAYER_GAME);
        arrayOfMoves = arrayOfmoves;
        for(int i=0; i < arrayOfMoves.size(); i++)
        {
            if(i%2==0){
                XOBoard[arrayOfMoves.get(i)]='X';
            }
            else if(i%2==1){
                XOBoard[arrayOfMoves.get(i)]='O';
            }
        }
        turn = arrayOfmoves.size() % 2;
        player0.startLoadedMultiplayerGame(0, player_1.getId(), gameID, arrayOfmoves);
        player1.startLoadedMultiplayerGame(1, player_0.getId(), gameID, arrayOfmoves);
        
        
    }

    public static GameSession loadGame(ServerSideInvitation invitation) throws SQLException{
        GamePojo game = null;
        try {
            game = Dao.selectGameByID(invitation.getGameID());
        } catch (SQLException ex) {
            Logger.getLogger(GameSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        PlayerHandler player0 = PlayerHandler.getPlayerHandlerByID(game.getPlayer1Id());
        PlayerHandler player1 = PlayerHandler.getPlayerHandlerByID(game.getPlayer2Id());

        String gameMovesStringized = game.getBoard();
        ArrayList<Integer> arrayOfMoves = null;
        //arrayOfMoves = Parser.gson.fromJson(gameMovesStringized, arrayOfMoves.getClass());
        Dao.updateGameVisibility(false, game.getGameID());
        arrayOfMoves = Parser.gson.fromJson(gameMovesStringized, new TypeToken<ArrayList<Integer>>(){}.getType());
        GameSession gameSession = new GameSession(player0, player1, arrayOfMoves, game.getGameID());
        return gameSession;
    }


    public void makeMove(int buttonId , int playerId) throws SQLException
    {
//System.out.println(turn);
        if(players[turn].getId() == playerId)
        {
            arrayOfMoves.add(buttonId);
            XOBoard[buttonId]=playerMark[turn];
            GameResult result = checkEndOfGame();
            changeTurn();
            players[turn].receiveAmove(buttonId);
            if(result == GameResult.PLAYER_1_WON || result == GameResult.PLAYER_2_WON){
                declareWinner(1 - turn);
            }
            else if(result == GameResult.TIE){
                declareTie();
            }
//            System.out.println(arrayOfMoves);
//            System.out.println(XOBoard);
        }
        else{
            
        }

    }

    public GameResult checkEndOfGame(){
        GameResult result =GameResult.INCOMPLETE;
        String line = null;
        for(int i =0 ; i < 8; i++){
            switch(i){
                //horizontal cases
                case 0:
                line = String.valueOf(XOBoard[0])+String.valueOf(XOBoard[1])+String.valueOf(XOBoard[2]);
                break;
                case 1:
                line = String.valueOf(XOBoard[3])+String.valueOf(XOBoard[4])+String.valueOf(XOBoard[5]);
                break;
                case 2:
                line = String.valueOf(XOBoard[6])+String.valueOf(XOBoard[7])+String.valueOf(XOBoard[8]);
                break;
                //vertical cases
                case 3:
                line = String.valueOf(XOBoard[0])+String.valueOf(XOBoard[3])+String.valueOf(XOBoard[6]);
                break;
                case 4:
                line = String.valueOf(XOBoard[1])+String.valueOf(XOBoard[4])+String.valueOf(XOBoard[7]);
                break;
                case 5:
                line = String.valueOf(XOBoard[2])+String.valueOf(XOBoard[5])+String.valueOf(XOBoard[8]);
                break;
                //Diagonal cases
                case 6:
                line = String.valueOf(XOBoard[0])+String.valueOf(XOBoard[4])+String.valueOf(XOBoard[8]);
                break;
                case 7:
                line = String.valueOf(XOBoard[2])+String.valueOf(XOBoard[4])+String.valueOf(XOBoard[6]);
                break;
                default: line =null;
            }
            if(line.equals("XXX")){
                result =GameResult.PLAYER_1_WON;
            }
            else if(line.equals("OOO")){
                result = GameResult.PLAYER_2_WON;
            }
        }
        if(result==GameResult.INCOMPLETE && arrayOfMoves.size()==9)
        {
        result = GameResult.TIE;
        }
            return result;
    }

    public void changeTurn(){
        if(turn ==0)
            turn =1;
        else if(turn ==1)
            turn =0;
    }

    public void declareWinner(int turn) throws SQLException{
        players[turn].win();
        players[1 - turn].lose();
        endGame();
        
    }

    public void declareTie() throws SQLException{
        players[0].tie();
        players[1].tie();
        endGame();
    }    
    private void endGame(){
        players[0].setCurrentGame(null);
        players[1].setCurrentGame(null);
    }
    
    public void abort(int aborterID) throws SQLException{
        if(aborterID == player0.getId()){
            //Player0 Aborted 
            player1.opponentAbortedGame();
            player0.abortedGame();
        }else{
            player0.opponentAbortedGame();
            player1.abortedGame();
        }
        endGame();
    }
}
