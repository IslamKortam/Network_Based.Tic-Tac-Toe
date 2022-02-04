/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Salma
 * Add new methods and modified by Mohamed Rashed
 * // using my sql queries 
 */
public class Dao {

    private static String dataBaseUrl;//enter values here
    //private static String driver; // according to database used
    private static String dataBaseName;//enter values here
    private static String dataBasePassword;//enter values here
    private static Connection connection = null;

    public static void startConnection() throws SQLException {

        // DriverManager.registerDriver(new org.postgresql.Driver());  //according to database used
        Dao.connection = DriverManager.getConnection(Dao.dataBaseUrl, Dao.dataBaseName, Dao.dataBasePassword);
        System.out.println("successfully connected");
    }

    public static void insertIntoPlayerTable(PlayerPojo p) throws SQLException {
        PreparedStatement insertStatement = Dao.connection.prepareStatement("INSERT INTO Users (FullName ,UserName "
                + ",Email ,Password ,Avatar ,Score ,LastVisit,visible )\n"
                + "     VALUES (? ,? ,? ,? ,? ,? ,? ,?)");
        insertStatement.setString(1, p.getUserName());
        insertStatement.setString(2, p.getNickName());
        insertStatement.setString(3, p.getEmail());
        insertStatement.setString(4, p.getPassword());
        insertStatement.setInt(5, p.getPicture());
        insertStatement.setInt(6, p.getScore());
        insertStatement.setDate(7, p.getLastVisit());
        insertStatement.setBoolean(8, p.getVisible());
        insertStatement.execute();
    }

    public static void insertIntoGameTable(GamePojo g) throws SQLException {
        PreparedStatement insertStatement = Dao.connection.prepareStatement("INSERT INTO  Game (Player1ID ,Player2ID ,"
                + "Date ,TimeLength ,Board ,Complete ,WinnerID,visible )\n"
                + "     VALUES (? ,? ,? ,? ,? ,? ,? ,?)");
        insertStatement.setInt(1, g.getPlayer1Id());
        insertStatement.setInt(2, g.getPlayer2Id());
        insertStatement.setDate(3, g.getDate_time());
        insertStatement.setLong(4, g.getTimeLength());
        insertStatement.setString(5, g.getBoard());
        insertStatement.setBoolean(6, g.isComplete());
        insertStatement.setInt(7, g.getWinnerId());
        insertStatement.setBoolean(8, g.getVisible());
        insertStatement.execute();
    }
    
    public static PlayerPojo selectPlayerByID(int ID) throws SQLException {
        PreparedStatement selectStatement = Dao.connection.prepareStatement("select * from Users where ID=?");
        selectStatement.setInt(1, ID);
        ResultSet query = selectStatement.executeQuery();
        PlayerPojo player=new PlayerPojo();
        if(query.next()){
            player = new PlayerPojo(query.getInt("ID"), query.getString("UserName"), query.getString("FullName"),
                    query.getString("Email"), query.getString("Password"), query.getInt("Avatar"),
                    query.getInt("Score"), query.getDate("LastVisit"), query.getBoolean("visible"));
            
        }
        return player;
    }
    
    public static PlayerPojo selectPlayerByEmail(String Email) throws SQLException {
        PreparedStatement selectStatement = Dao.connection.prepareStatement("select * from Users where Email=?");
        selectStatement.setString(1, Email);
        ResultSet query = selectStatement.executeQuery();
        PlayerPojo player=new PlayerPojo();
        if(query.next()){
            player = new PlayerPojo(query.getInt("ID"), query.getString("UserName"), query.getString("FullName"),
                    query.getString("Email"), query.getString("Password"), query.getInt("Avatar"),
                    query.getInt("Score"), query.getDate("LastVisit"), query.getBoolean("visible"));
        }
        return player;
    }
}
