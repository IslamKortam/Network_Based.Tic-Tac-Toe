/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameSession;

import java.util.ArrayList;

/**
 *
 * @author Bahaa eldin
 */
public class GameSession {

//    private static PlayerHandler player0 = new PlayerHandler(0);
//    private static PlayerHandler player1 = new PlayerHandler(1);
//    private static PlayerHandler[] players = new PlayerHandler[]{player0,player1};
//    private static int turn=0;
    private static PlayerHandler player0;
    private static PlayerHandler player1;
    private static PlayerHandler[] players;
    private static int turn;
    private static ArrayList arrayOfMoves = new ArrayList(9);
    private static char[] XOBoard = new char[]{'.','.','.','.','.','.','.','.','.'};
    private static char[] playerMark = new char[]{'X','O'};
    private enum GameResult{
        INCOMPLETE,
        TIE,
        PLAYER_1_WON,
        PLAYER_2_WON
    }

    public GameSession(PlayerHandler player_0 , PlayerHandler player_1 , int playerTurn){
        player0 = player_0;
        player1 = player_1;
        players=new PlayerHandler[]{player0,player1};
        turn = playerTurn;
    }

    public static void makeMove(int buttonId , int playerId)
    {
//System.out.println(turn);
        if(players[turn].getId() == playerId)
        {
            arrayOfMoves.add(buttonId);
            XOBoard[buttonId]=playerMark[turn];
            GameResult result = checkEndOfGame();
            if(result == GameResult.PLAYER_1_WON || result == GameResult.PLAYER_2_WON){
                declareWinner(turn);
            }
            else if(result == GameResult.TIE){
                declareTie();
            }
//            System.out.println(arrayOfMoves);
//            System.out.println(XOBoard);
            changeTurn();
        }
        else{
        System.out.println("Not your turn");
        }

    }

    public static GameResult checkEndOfGame(){
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
        System.out.println(line);
            return result;
    }

    public static void changeTurn(){
        if(turn ==0)
            turn =1;
        else if(turn ==1)
            turn =0;
    }

    public static void declareWinner(int turn){
        System.out.println("Player "+turn+" Won ^__^");
    }

    public static void declareTie(){
        System.out.println("Tie ....");
    }    
}
