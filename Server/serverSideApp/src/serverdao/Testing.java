/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverdao;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.sql.Date;
import java.sql.SQLException;
import static javafx.application.Application.launch;

/**
 *
 * @author Salma
 */
public class Testing {
    public static void main(String[] args) throws SQLException {
       Dao.startConnection();
       PlayerPojo p1= new PlayerPojo("salma", "mohamed", "salma@gmail.com", "1234567891", 4, 3,  new Date(2020, 15, 1), TRUE);
       PlayerPojo p2= new PlayerPojo("ihab", "ibraheem", "ibraheem@gmail.com", "11111111", 4, 3,  new Date(2020, 5, 1), TRUE);
        Dao.insertIntoPlayerTable(p1);
          Dao.insertIntoPlayerTable(p2);
         GamePojo g=new GamePojo(1, 2, 0, "12345", FALSE, 0,new Date(2020, 5, 1), TRUE);
        Dao.insertIntoGameTable(g);
     
        
    }
}
