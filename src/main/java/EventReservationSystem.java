/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.database.*;
/**
 *
 * @author Windows 7
 */
public class EventReservationSystem extends Application {
    
    DBConnect connectdb = new DBConnect();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/rootMenu.fxml")); // load the first fxml file
        
        primaryStage.setTitle("EventsByRandell"); // set title of the application
        primaryStage.setScene(new Scene(root, 940, 550));  // change "root" to scene obj and set it as scene
        
        connectdb.getConnection(); // initialize database
        primaryStage.show();    // show
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
