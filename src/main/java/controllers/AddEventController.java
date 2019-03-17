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

    // establish connection to RootMenuController
    //

    @FXML
    public Label eventName;
    @FXML
    private JFXButton addEvent;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void changeLabel(String nameOfTheEvent) {
        try {
        eventName.setText(nameOfTheEvent);
        }
        catch (NullPointerException eee) {
            System.out.println("ERROR!" + eee);
        }
    }
    
    /*
    public void init(RootMenuController mainController) {
        main = mainController;
    }
    */
    
}
