/**************************************
 * DEAL OR NO DEAL
 * 
 * DIINKANT RAVICHANDRAN 1385134
 * 
 **************************************/

import java.sql.*;
import javax.swing.JOptionPane;

public class DatabaseConnect {
    
      static Connection myCoc = null;

    public static Connection dbconnect() {
        try {
            //1. Get a connect to the database
            myCoc = DriverManager.getConnection("jdbc:derby://localhost:1527/HighScore", "Harambae", "harambase");

            return myCoc;

        } catch (Exception e) {//Catch exception if it fail to connect to Derby Database
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }
}
