package main.java.controllers;

import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ViewEventController implements Initializable {

    @FXML
    private Label eventType;
    @FXML
    private Label name;
    @FXML
    private Label contact;
    @FXML
    private Label venue;
    @FXML
    private Label signUpDate;
    @FXML
    private Label eventDate;
    @FXML
    private Label packageInclusion;
    @FXML
    private Label packagePrice;
    @FXML
    private Label clientBudget;
    @FXML
    private JFXTextArea notes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setFields(String eventType, String name, String contact, String venue, String signUpDate, String eventDate, String packageInclusion, String packagePrice, String clientBudget, String notes) {
        // initialize the fields
        this.eventType.setText(eventType);
        this.name.setText(name);
        this.contact.setText(contact);
        this.venue.setText(venue);
        this.signUpDate.setText(signUpDate);
        this.eventDate.setText(eventDate);
        this.packageInclusion.setText(packageInclusion);
        this.packagePrice.setText(packagePrice);
        this.clientBudget.setText(clientBudget);
        this.notes.setText(notes);
        
    }
    
    @FXML
    private void printTheDoc(ActionEvent e) {
        // TODO: print the doc
        
    }
    
}
