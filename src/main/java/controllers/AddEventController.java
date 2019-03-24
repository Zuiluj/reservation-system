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
import javafx.scene.control.Label;



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
    private JFXButton addEvent; // button for adding the event to th database
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eventName.setText(nameOfEvent);

    }    
    
    public static void changeLabel(String value) { //receives the args from RootMenuController and --
        AddEventController.nameOfEvent = value;  // assigns it to the label named 'nameOfEvent'
    }
    
    
    private void storeEvent (ActionEvent event) {
        // stores the event to database
    }
    
}
