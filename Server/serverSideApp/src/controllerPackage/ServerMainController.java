/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerPackage;

import ServerLogicClasses.ServerMulti;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import serverdao.Dao;
import serverdao.GamePojo;
import serverdao.PlayerPojo;

/**
 *
 * @author imkor
 */
public class ServerMainController extends Application {
    
    public static void main(String[] args) throws SQLException, IOException{
        
        launch(serverhome.ServerHome.class ,args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
    }
    
}
