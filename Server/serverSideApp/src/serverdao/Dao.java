/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Salma
 */
public class Dao {

    private static String dataBaseUrl;//enter values here
    //private static String driver; // according to database used
    private static String dataBaseName;//enter values here
    private static String dataBasePassword;//enter values here
    private static Connection connection = null;
    
    
    
    
    
    private static void startConnection( ) throws SQLException{
          
     // DriverManager.registerDriver(new org.postgresql.Driver());  //according to database used
          Dao.connection = DriverManager.getConnection(Dao.dataBaseUrl, Dao.dataBaseName, Dao.dataBasePassword);
           System.out.println("successfully connected");
    }

}
