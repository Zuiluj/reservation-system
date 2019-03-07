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

/**
 * FXML Controller class
 *
 * @author Windows 7
 */
public class RootMenuController implements Initializable {

    @FXML
    private BorderPane borderpane; // id of the borderpane

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/dashboard.fxml")); // initialize first launch by setting dashboard
        } catch (IOException ex) {
            Logger.getLogger(RootMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        borderpane.setCenter(root);
    }    
    
    @FXML
    private void addEvent(ActionEvent event) {
        changeScene("addEvent");
    }

    @FXML
    private void calculator(ActionEvent event) {
        changeScene("calculatorFXML");
    }

    @FXML
    private void calendar(ActionEvent event) {
        changeScene("calendarFXML");
    }

    @FXML
    private void history(ActionEvent event) {
        changeScene("historyFXML");
    }
     
    
    // method for changing scenes
    public void changeScene (String sceneName) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/" + sceneName + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(RootMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        borderpane.setCenter(root);
    }
}
