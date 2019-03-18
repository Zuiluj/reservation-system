/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;



public class AddEventController implements Initializable {

    public static String nameOfEvent; // must be static to change for the whole class not with just instance
    
    @FXML
    public Label eventName;
    @FXML
    private JFXButton addEvent;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eventName.setText(nameOfEvent);

    }    
    
    public static void store(String value) { //receives the args from RootMenuController and --
        AddEventController.nameOfEvent = value;  // assigns it to the label named 'nameOfEvent'
    }
    
    
    /*
    public void init(RootMenuController mainController) {
        main = mainController;
    }
    */
    
}
