/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.database;

import java.sql.Connection;
import java.sql.*;
/**
 *
 * @author Windows 7
 */
public class DBConnect {
    
    public Connection conn; // initialize connection var to enable use within functions
    
    public Connection getConnection() {
        
        String url = "jdbc:mysql://localhost:3306/eventSystem";
        String username = "root";
        String password = "";
        
        
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established!"); // dev: test to connect
        }
        catch (Exception eee) {
            System.out.println("Connection error! Full error: " + eee); //dev: print the error
        }
        
        return conn;
        
    }
    
}
