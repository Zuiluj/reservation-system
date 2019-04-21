/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;


public class RootMenuController implements Initializable {
    
    @FXML
    public BorderPane borderpane; // id of the borderpane

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/dashboard.fxml")); // initialize first launch by setting dashboard
        } catch (IOException eee) {
            Logger.getLogger(RootMenuController.class.getName()).log(Level.SEVERE, null, eee);
        }
        borderpane.setCenter(root);
        
        
    }    
    
    @FXML
    private void dashboard(ActionEvent event) {
        changeScene("dashboard");
    }
    
    @FXML
    private void addEventBday(ActionEvent event) {
        AddEventController.changeLabel("Birthday"); // store the argument in the AddEventController, and set it as the label
        changeScene("addEvent");
    }
    
    @FXML
    private void addEventWedding(ActionEvent event) {
        AddEventController.changeLabel("Wedding");
        changeScene("addEvent");
    }
    
    @FXML
    private void addEventDebut(ActionEvent event) {
        AddEventController.changeLabel("Debut");
        changeScene("addEvent");
    }

    @FXML
    private void calculator(ActionEvent event) {
        changeScene("profitCalcFXML");
    }

    @FXML
    private void calendar(ActionEvent event) {
        changeScene("calendarFXML");
    }

    @FXML
    private void history(ActionEvent event) {
        changeScene("history");
    }
  
    
    // method for changing scenes
    private void changeScene (String sceneName) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/" + sceneName + ".fxml"));
        } catch (IOException eee) {
            System.out.println(eee);
        }
        
        borderpane.setCenter(root); 
    }
    
}
