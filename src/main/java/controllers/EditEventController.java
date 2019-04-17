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
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Date;
import javafx.util.converter.DateTimeStringConverter;


import main.java.database.DBConnect;
import main.java.controllers.DashboardController;
        
public class EditEventController implements Initializable {

    @FXML
    public JFXTextField eventType;
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
    
    
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void setFields(String eventType, String name, String contact, String venue, String signUpDate, String eventDate, String packageInclusion, String price, String clientBudget, String notes) {
        
        LocalDate signUp = LocalDate.parse(signUpDate, dateFormat);
        LocalDate dateOfEvent = LocalDate.parse(eventDate, dateFormat);
        this.signUpDate.setValue(signUp);
        this.eventDate.setValue(dateOfEvent);
        
        this.eventType.setText(eventType);
        this.name.setText(name);
        this.contact.setText(contact);
        this.venue.setText(venue);
        this.packageInclusion.setText(packageInclusion);
        this.price.setText(price);
        this.clientBudget.setText(clientBudget);
        this.notes.setText(notes);
    }
    
    @FXML
    private void saveEditedEvent(ActionEvent event) {
        DBConnect.getConnection();
        PreparedStatement theEditStmt = null;
        
        try {
            String query = "UPDATE activeevents "
                    + "SET name=?, eventType=?, contact=?, venue=?, signUpDate=?, packageInclusion=?, price=?, clientBudget=?, eventDate=?, notes=?"
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
            
            theEditStmt.setString(11, name.getText()); // name column of the event
            
            theEditStmt.executeUpdate(); 
            DashboardController.newStage.close(); // closes the window
        }
        catch (SQLException eee) {
            eee.printStackTrace();
        }
        
        
    }
    
}
