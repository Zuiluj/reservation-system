package main.java.database;

import java.sql.Connection;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnect {
    
    private static final String JDBC_DRIVER = "jdbc:mysql://localhost:3306/eventSystem";
    public static Connection conn = null; // initialize connection var to enable use within functions
    
    public static Connection getConnection() {
        String username = "root";
        String password = "";
        
        
        try {
            conn = DriverManager.getConnection(JDBC_DRIVER, username, password); // connect url, provide with username and with password
            System.out.println("Connection established!"); // dev: test to connect
        }
        catch (SQLException eee) {
            System.out.println("Connection error! Full error: " + eee); //dev: print the error
        }
        
        return conn;
        
    }

}
