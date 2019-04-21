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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import main.java.database.DBConnect;

public class AddEventController implements Initializable {
    
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
    private JFXButton addEvent; // button for adding the event to the database
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eventName.setText(nameOfEvent);

    }    
    
    public static void changeLabel(String value) { //receives the args from RootMenuController and --
        AddEventController.nameOfEvent = value;  // assigns it to the label named 'nameOfEvent'
    }
    
   
    public void storeEvent() {
        
        DBConnect.getConnection(); // makes sure db is connected
        PreparedStatement addStmt = null; // initalize statement
       
        // ### initialize warnings
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setHeaderText("Event added! Good Luck!");
        alert.setContentText("Click dashboard to see the events.");
        
        Alert duplicateWarning = new Alert(AlertType.WARNING);
        duplicateWarning.setTitle("Error!");
        duplicateWarning.setHeaderText("Name already exists!");
        duplicateWarning.setContentText("Please pick another name");
        
        Alert noDate = new Alert(AlertType.WARNING);
        noDate.setTitle("Error");
        noDate.setHeaderText("No date with either Sign up or Event date");
        noDate.setContentText("Please input a date. If date is still tentative, just type not yet decided and edit it later.");
        // ###
        
        // secure first if data fields have date
        if( ((TextField)signUpDate.getEditor()).getText().equals("") || ((TextField)eventDate.getEditor()).getText().equals("")) {
            System.out.println("no date");
            noDate.showAndWait();
        } 
        // if there are data within date fields proceed to adding the event
        else {
        
            try {
                String query = "INSERT INTO eventsystem.activeevents(eventType, name, contact, venue, signUpDate, packageInclusion, price, clientBudget, eventDate, notes)"
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
                addStmt = DBConnect.conn.prepareStatement(query);
            
                addStmt.setString(1, eventName.getText());
                addStmt.setString(2, name.getText());
                addStmt.setString(3, contact.getText());
                addStmt.setString(4, venue.getText());
                addStmt.setString(5, ((TextField)signUpDate.getEditor()).getText());
                addStmt.setString(6, packageInclusion.getText());
                addStmt.setString(7, price.getText());
                addStmt.setString(8, clientBudget.getText());
                addStmt.setString(9, ((TextField)eventDate.getEditor()).getText());
                addStmt.setString(10, notes.getText());
            
                addStmt.executeUpdate(); // execute the query with the updated string
            
                alert.showAndWait();
            
            } 
            catch (java.sql.SQLIntegrityConstraintViolationException duplicateKey) {
                duplicateWarning.showAndWait(); // name col is unique key
            }
            catch (SQLException eee) {
                eee.printStackTrace();
            } 
        
        }
    }
    
    
}
