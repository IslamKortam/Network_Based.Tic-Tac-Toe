/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverdao;

import ServerLogicClasses.UserHandler;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverhome.ServerHomeController;

/**
 *
 * @author Salma Add new methods and modified by Mohamed Rashed // using my sql
 * queries
 */
public class Dao {

    private static String dataBaseUrl = "jdbc:mysql://uiva61xx4xcpxpjo:6oI5Bi8mbQitgbNAeT7c@bbjhbbhoug8jo9hqqoeb-mysql.services.clever-cloud.com:3306/bbjhbbhoug8jo9hqqoeb";
    private static String dataBaseName = "bbjhbbhoug8jo9hqqoeb";
    private static String dataBasePassword = "6oI5Bi8mbQitgbNAeT7c";
    private static Connection connection = null;

    public static void startConnection() {

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Dao.connection = DriverManager.getConnection(Dao.dataBaseUrl, Dao.dataBaseName, Dao.dataBasePassword);
        } catch (SQLException ex) {
            ServerHomeController.toOffline();
            ServerLogicClasses.ServerMulti.stopServer();
            ServerHomeController.showDB_ConnectionError_Alert();
            UserHandler.stopAllUsers();
        }
    }

    public static void closeConnection() throws SQLException {
        Dao.connection.close();
        Dao.connection = null;
    }

    public static void insertIntoPlayerTable(PlayerPojo p) throws SQLException {
        Dao.startConnection();
        PreparedStatement insertStatement = Dao.connection.prepareStatement("INSERT INTO Users (FullName ,UserName "
                + ",Email ,Password ,Avatar ,Score ,LastVisit,visible )\n"
                + "     VALUES (? ,? ,? ,? ,? ,? ,? ,?)");
        insertStatement.setString(1, p.getNickName());
        insertStatement.setString(2, p.getUserName());
        insertStatement.setString(3, p.getEmail());
        insertStatement.setString(4, p.getPassword());
        insertStatement.setInt(5, p.getPicture());
        insertStatement.setInt(6, p.getScore());
        insertStatement.setDate(7, p.getLastVisit());
        insertStatement.setBoolean(8, p.getVisible());
        insertStatement.execute();
        insertStatement.close();
        Dao.closeConnection();
    }

    public static void insertIntoGameTable(GamePojo g) throws SQLException {
        Dao.startConnection();
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
        insertStatement.close();
        Dao.closeConnection();
    }

    public static void updateScore(int userId, int score) throws SQLException {
        Dao.startConnection();
        PreparedStatement updateStatement = Dao.connection.prepareStatement("UPDATE Users SET Score=? WHERE ID=?");
        updateStatement.setInt(1, score);
        updateStatement.setInt(2, userId);
        updateStatement.execute();
        updateStatement.close();
        Dao.closeConnection();
    }

    public static void updateLastVisit(Date lastVisit, int userId) throws SQLException {
        Dao.startConnection();
        PreparedStatement updateStatement = Dao.connection.prepareStatement("UPDATE Users SET LastVisit=? WHERE ID=?");
        updateStatement.setDate(1, lastVisit);
        updateStatement.setInt(2, userId);
        updateStatement.execute();
        updateStatement.close();
        Dao.closeConnection();
    }

    public static void updatePlayerVisibility(Boolean vStatus, int userId) throws SQLException {
        Dao.startConnection();
        PreparedStatement updateStatement = Dao.connection.prepareStatement("UPDATE Users SET visible=? WHERE ID=?");
        updateStatement.setBoolean(1, vStatus);
        updateStatement.setInt(2, userId);
        updateStatement.execute();
        updateStatement.close();
        Dao.closeConnection();
    }

    public static void updateGameVisibility(Boolean vStatus, int gameId) throws SQLException {
        Dao.startConnection();
        PreparedStatement updateStatement = Dao.connection.prepareStatement("UPDATE Game SET Visible=? WHERE ID=?");
        updateStatement.setBoolean(1, vStatus);
        updateStatement.setInt(2, gameId);
        updateStatement.execute();
        updateStatement.close();
        Dao.closeConnection();
    }

    public static void updateGameCompletion(Boolean cStatus, int gameId) throws SQLException {
        Dao.startConnection();
        PreparedStatement updateStatement = Dao.connection.prepareStatement("UPDATE Game SET Complete=? WHERE ID=?");
        updateStatement.setBoolean(1, cStatus);
        updateStatement.setInt(2, gameId);
        updateStatement.execute();
        updateStatement.close();
        Dao.closeConnection();
    }

    public static PlayerPojo selectPlayerByID(int ID) throws SQLException {
        Dao.startConnection();
        PreparedStatement selectStatement = Dao.connection.prepareStatement("select * from Users where ID=?");
        selectStatement.setInt(1, ID);
        ResultSet query = selectStatement.executeQuery();
        PlayerPojo player = new PlayerPojo();
        if (query.next()) {
            player = new PlayerPojo(query.getInt("ID"), query.getString("UserName"), query.getString("FullName"),
                    query.getString("Email"), query.getString("Password"), query.getInt("Avatar"),
                    query.getInt("Score"), query.getDate("LastVisit"), query.getBoolean("visible"));
        }
        selectStatement.close();
        Dao.closeConnection();
        return player;
    }

    public static PlayerPojo selectPlayerByEmail(String Email) throws SQLException {
        Dao.startConnection();
        PreparedStatement selectStatement = Dao.connection.prepareStatement("select * from Users where Email=?");
        selectStatement.setString(1, Email);
        ResultSet query = selectStatement.executeQuery();
        PlayerPojo player = new PlayerPojo();
        if (query.next()) {
            player = new PlayerPojo(query.getInt("ID"), query.getString("UserName"), query.getString("FullName"),
                    query.getString("Email"), query.getString("Password"), query.getInt("Avatar"),
                    query.getInt("Score"), query.getDate("LastVisit"), query.getBoolean("visible"));
        }
        selectStatement.close();
        Dao.closeConnection();
        return player;
    }

    public static PlayerPojo selectPlayerByUsername(String username) throws SQLException {
        Dao.startConnection();
        PreparedStatement selectStatement = Dao.connection.prepareStatement("select * from Users where UserName = ? and visible = 1");
        selectStatement.setString(1, username);
        PlayerPojo player = new PlayerPojo();
        ResultSet query = selectStatement.executeQuery();
        if (query.next()) {
            player = new PlayerPojo(query.getInt("ID"), query.getString("UserName"), query.getString("FullName"),
                    query.getString("Email"), query.getString("Password"), query.getInt("Avatar"),
                    query.getInt("Score"), query.getDate("LastVisit"), query.getBoolean("visible"));
        }
        selectStatement.close();
        Dao.closeConnection();
        return player;
    }

    public static Vector<GamePojo> selectGameByPlayerID(int playerID) throws SQLException {
        Dao.startConnection();
        Vector<GamePojo> games = new Vector<GamePojo>();
        PreparedStatement selectStatement = Dao.connection.prepareStatement("Select * from Game where Player1ID = ? or Player2ID = ?");
        selectStatement.setInt(1, playerID);
        selectStatement.setInt(2, playerID);
        ResultSet query = selectStatement.executeQuery();
        while (query.next()) {
            GamePojo game = new GamePojo(query.getInt("ID"), query.getInt("Player1ID"), query.getInt("Player2ID"), query.getLong("TimeLength"), query.getString("Board"), query.getBoolean("Complete"), query.getInt("WinnerID"), query.getDate("Date"), query.getBoolean("Visible"));
            if (game.getVisible()) {
                games.add(game);
            }

        }
        selectStatement.close();
        Dao.closeConnection();
        return games;
    }

    public static GamePojo selectGameByID(int id) throws SQLException {
        Dao.startConnection();
        GamePojo game = null;
        PreparedStatement selectStatement = Dao.connection.prepareStatement("Select * from Game where ID = ?");
        selectStatement.setInt(1, id);
        ResultSet query = selectStatement.executeQuery();
        if (query.next()) {
            game = new GamePojo(query.getInt("ID"), query.getInt("Player1ID"), query.getInt("Player2ID"), query.getLong("TimeLength"), query.getString("Board"), query.getBoolean("Complete"), query.getInt("WinnerID"), query.getDate("Date"), query.getBoolean("Visible"));
        }
        selectStatement.close();
        Dao.closeConnection();
        return game;
    }

    public static Vector<PlayerPojo> selectAllPlayers() throws SQLException {
        Dao.startConnection();
        Vector<PlayerPojo> players = new Vector<PlayerPojo>();
        try {
            PreparedStatement selectStatement = Dao.connection.prepareStatement("select * from Users where visible=1");
            ResultSet query = selectStatement.executeQuery();
            while (query.next()) {
                PlayerPojo player = new PlayerPojo(query.getInt("ID"), query.getString("UserName"), query.getString("FullName"),
                        query.getString("Email"), query.getString("Password"), query.getInt("Avatar"),
                        query.getInt("Score"), query.getDate("LastVisit"), query.getBoolean("visible"));
                players.add(player);
            }
            selectStatement.close();

        } catch (SQLException ex) {
            
        }
        Dao.closeConnection();
        return players;
    }

    public static PlayerPojo selectPlayerByCredential(String email, String password) throws SQLException {
        Dao.startConnection();
        PlayerPojo player = new PlayerPojo();
        PreparedStatement selectStatement = Dao.connection.prepareStatement("select * from Users where Email = ? and Password = ? and visible = 1");
        selectStatement.setString(1, email);
        selectStatement.setString(2, password);
        ResultSet query = selectStatement.executeQuery();
        if (query.next()) {
            player = new PlayerPojo(query.getInt("ID"), query.getString("UserName"), query.getString("FullName"),
                    query.getString("Email"), query.getString("Password"), query.getInt("Avatar"),
                    query.getInt("Score"), query.getDate("LastVisit"), query.getBoolean("visible"));
        }
        selectStatement.close();
        Dao.closeConnection();
        return player;
    }
}
