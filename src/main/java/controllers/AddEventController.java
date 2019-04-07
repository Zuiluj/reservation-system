/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import main.java.controllers.RootMenuController.*;
import main.java.database.DBConnect;

public class AddEventController implements Initializable {
    
    public DBConnect dbInstance = new DBConnect();
    
    public static String nameOfEvent; // must be static to change for the whole class not with just instance
    
    @FXML
    public Label eventName;
    @FXML
    private JFXTextField name; // client's name
    @FXML
    private JFXTextField contact; // client's contact number
    @FXML
    private JFXTextField venue; // venue of the event
    @FXML
    private DatePicker signUpDate; // exact date of sign up
    @FXML
    private JFXTextField packageInclusion; // what package inclusion
    @FXML
    private JFXTextField price; // price of the package
    @FXML
    private JFXTextField clientBudget; // budget of the client in peso
    @FXML
    private DatePicker eventDate; // date of the event
    @FXML
    private JFXTextArea notes; //notes of the event
    @FXML
    private JFXButton addEvent; // button for adding the event to th database
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eventName.setText(nameOfEvent);

    }    
    
    public static void changeLabel(String value) { //receives the args from RootMenuController and --
        AddEventController.nameOfEvent = value;  // assigns it to the label named 'nameOfEvent'
    }
    
   
    public void storeEvent() {
        
        DBConnect.getConnection(); // makes sure db is connected
        PreparedStatement theRealStmt = null; // initalize statement
       
        try {
            String query = "INSERT INTO eventsystem.activeevents(eventType, name, contact, venue, signUpDate, packageInclusion, price, clientBudget, eventDate, notes)"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            theRealStmt = DBConnect.conn.prepareStatement(query);
            
            theRealStmt.setString(1, eventName.getText());
            theRealStmt.setString(2, name.getText());
            theRealStmt.setString(3, contact.getText());
            theRealStmt.setString(4, venue.getText());
            theRealStmt.setString(5, ((TextField)signUpDate.getEditor()).getText());
            theRealStmt.setString(6, packageInclusion.getText());
            theRealStmt.setString(7, price.getText());
            theRealStmt.setString(8, clientBudget.getText());
            theRealStmt.setString(9, ((TextField)eventDate.getEditor()).getText());
            theRealStmt.setString(10, notes.getText());
            
            theRealStmt.executeUpdate(); // execute the query with the updated string
            
        } catch (SQLException eee) {
            eee.printStackTrace();
        }
        
        
        // go back to dashboard after adding event 
        
        //
        
    }
    
    
}
