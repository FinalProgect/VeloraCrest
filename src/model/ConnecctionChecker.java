package model;

import gui.SplshWindow;
import java.awt.SplashScreen;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ConnecctionChecker extends Thread{
    
    
     // Database connection details 
    private static final String DB_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12760597";
    private static final String USER = "sql12760597";
    private static final String PASS = "f75Vm8XuHy";
    
    public static boolean connectionCheckerStarted;
    
     @Override
    public void run() {
        while (true) {
            
            if(!connectionCheckerStarted){
                connectionCheckerStarted = true;
            }
            
            try {
                // Attempt to establish a connection to the database
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                if (conn != null) {
                    System.out.println("Internet is connected and database is reachable.");
                    SplshWindow.connectionIsDone = true;
                    conn.close();  // Close the connection
                }
            } catch (SQLException e) {
                
                System.out.println("No internet connection or database is not reachable.");
                
            }
            
            // Sleep for a specified time (e.g., 5 seconds) before checking again
            try {
                Thread.sleep(5000);  // 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    
    
}
