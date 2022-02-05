/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerPackage;

import ServerLogicClasses.ServerMulti;
import java.sql.SQLException;
import serverdao.Dao;

/**
 *
 * @author imkor
 */
public class ServerMainController {
    
    public static void main() throws SQLException{
        Dao.startConnection();
        ServerMulti server = ServerMulti.getRef();
        server.startServer();
        
    }
    
}
