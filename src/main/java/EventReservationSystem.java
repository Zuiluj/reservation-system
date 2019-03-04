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

/**
 *
 * @author Windows 7
 */
public class EventReservationSystem extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/dashboard/dashboard.fxml")); // load the first fxml file
        
        primaryStage.setTitle("EventsBRandell"); // set title of the application
        primaryStage.setScene(new Scene(root));  // change "root" to scene obj and set it as scene
        primaryStage.show();    // show
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
