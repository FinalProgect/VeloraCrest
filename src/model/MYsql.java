package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MYsql{
    
    private static Connection connection;
    
    private static void createConnection(){
    
        if(connection == null){
            
            try{
            
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                connection = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12755589", "sql12755589", "MuT9hwZ9Dc");
                
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }catch(SQLException e){
                e.printStackTrace();    
            }  
        }   
    }
    
    public static ResultSet execute(String query) throws SQLException{
    
        createConnection();
        
        if(query.startsWith("SELECT")){
            
            return connection.createStatement().executeQuery(query);
        
        }else{
            
            connection.createStatement().executeUpdate(query);
            return null;
        
        }
    
    }
    
}