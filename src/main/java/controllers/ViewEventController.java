package main.java.controllers;

import com.jfoenix.controls.JFXTextArea;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
        Alert notice = new Alert(AlertType.INFORMATION);

        notice.setTitle("Notice");
        notice.setHeaderText("Contract created!");
        notice.setContentText("The contract was saved in your Documents folder.");
        //  print the doc
        String dir = System.getProperty("user.dir") + "\\src\\main\\python\\printContract.py";
        try {
            ProcessBuilder pb = new ProcessBuilder(
                "python", 
                dir,
                this.eventType.getText(),
                this.name.getText(),
                this.contact.getText(),
                this.venue.getText(),
                this.signUpDate.getText(),
                this.eventDate.getText(),
                this.packageInclusion.getText(),
                this.packagePrice.getText(),
                this.clientBudget.getText(),
                this.notes.getText()
                );
            Process p = pb.start();
            
            // assign outputs from the script to be able to print it within java
            BufferedReader br1 = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            
            // displays whatever print() returned from python
            String line;
            while((line = br1.readLine()) != null){
                System.out.println(line);
            }
            br1.close();
            
            // display errors given by python
            while((line = brError.readLine()) != null){
                System.out.println(line);
            }
            brError.close();
            

        } catch (IOException ex) {
            Logger.getLogger(ViewEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        notice.showAndWait();
        
    }
    
}
