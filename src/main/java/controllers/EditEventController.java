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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TextField;


import main.java.controllers.DashboardController;
import main.java.database.DBConnect;
        
public class EditEventController implements Initializable {

    @FXML
    private JFXTextField eventType;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField contact;
    @FXML
    private JFXTextField venue;
    @FXML
    private DatePicker signUpDate;
    @FXML
    private JFXTextField packageInclusion;
    @FXML
    private JFXTextField price;
    @FXML
    private JFXTextField clientBudget;
    @FXML
    private DatePicker eventDate;
    @FXML
    private JFXTextArea notes;
    @FXML
    private JFXButton addEvent;
   
    private String key; // placeholder for the key of query statement; i.e. name column
    
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void setFields(String eventType, String name, String contact, String venue, String signUpDate, String eventDate, String packageInclusion, String price, String clientBudget, String notes) {
        // turn string to localDate object
        LocalDate signUp = LocalDate.parse(signUpDate, dateFormat);
        LocalDate dateOfEvent = LocalDate.parse(eventDate, dateFormat);
        //
        
        this.eventType.setText(eventType);
        this.name.setText(name);
        this.contact.setText(contact);
        this.venue.setText(venue);
        this.signUpDate.setValue(signUp);
        this.eventDate.setValue(dateOfEvent);
        this.packageInclusion.setText(packageInclusion);
        this.price.setText(price);
        this.clientBudget.setText(clientBudget);
        this.notes.setText(notes);
        
        this.key = name; // place the name into the key for sql query use
    }
    
    @FXML
    private void saveEditedEvent(ActionEvent event) {
        DBConnect.getConnection();
        PreparedStatement theEditStmt = null;
        
        try {
            String query = "UPDATE activeevents "
                    + "SET eventType=?, name=?, contact=?, venue=?, signUpDate=?, packageInclusion=?, price=?, clientBudget=?, eventDate=?, notes=?"
                    + "WHERE name=?";
        
            theEditStmt = DBConnect.conn.prepareStatement(query);
            
            
            theEditStmt.setString(1, eventType.getText());
            theEditStmt.setString(2, name.getText());
            theEditStmt.setString(3, contact.getText());
            theEditStmt.setString(4, venue.getText());
            theEditStmt.setString(5, ((TextField)signUpDate.getEditor()).getText());
            theEditStmt.setString(6, packageInclusion.getText());
            theEditStmt.setString(7, price.getText());
            theEditStmt.setString(8, clientBudget.getText());
            theEditStmt.setString(9, ((TextField)eventDate.getEditor()).getText());
            theEditStmt.setString(10, notes.getText());
            
            theEditStmt.setString(11, key); // name column of the event
            
            theEditStmt.executeUpdate(); 
            DashboardController.editStage.close(); // closes the window
            
        }
        catch (SQLException eee) {
            eee.printStackTrace();
        }
    }
    
}
