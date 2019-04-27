/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import static com.sun.media.jfxmediaimpl.MediaUtils.warning;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Denver
 */
public class ProfitCalcController implements Initializable {

    @FXML
    private TextField tf1;
  

    @FXML
    private TextField tf3;

    @FXML
    private TextField tf2;

    @FXML
    private TextField tf4;// budget

    @FXML
    private Label display;
    
       @FXML
    private Label display1;

    @FXML
    private Label display11;

    @FXML
    private Label display111;

    @FXML
    private Label display1111;

    @FXML
    private Label display2;

    @FXML
    private Label display12;

    @FXML
    private Label display112;

    @FXML
    private Label display1112;

    @FXML
    private Label display11111;

    @FXML
    private Label display11112;

    
       @FXML
    private TextField tf11;//tf4 for expenses

    @FXML
    private TextField tf111;//tf5 for expenses

    @FXML
    private TextField tf12;// NOE tf1

    @FXML
    private TextField tf121; //NOEof tf2

    @FXML
    private TextField tf1211;//NOEof tf3

    @FXML
    private TextField tf12111;// NOE of tf11

    @FXML
    private TextField tf121111;//NOE of tf 111

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        tf1.setText("0.00");
        tf2.setText("0.00");
        tf3.setText("0.00");
        tf11.setText("0.00");
        tf111.setText("0.00");
        tf4.setText("0.00");
    }  
    
    @FXML
    public void handleButtonAction(ActionEvent event) {
        // ### initialize warning 
        Alert warning = new Alert(AlertType.WARNING);
        warning.setTitle("Warning");
        warning.setHeaderText("Empty value");
        warning.setContentText("There is a field that has an empty value. If it is unneeded, initialize it to zero.");
        // ###
        
        
    try {
     float num1= Float.parseFloat(tf1.getText());
 
     float num2= Float.parseFloat(tf2.getText());
       
     float num3= Float.parseFloat(tf3.getText());
    
     float num44= Float.parseFloat(tf11.getText());
     
     float num5=Float.parseFloat(tf111.getText());
     
     float num4=Float.parseFloat(tf4.getText());

     
     tf12.getText();//tf1
     
     tf121.getText();//tf2
     
     tf1211.getText();//tf3
     
     tf12111.getText();//tf44
     
     tf121111.getText();//tf5
  
     float answer1= num1 + num2 + num3+ num44+ num5;
     float fanswer=num4-answer1;
     
     display2.setText(tf1.getText());
     display.setText(tf12.getText());
     display12.setText(tf2.getText());
     display1.setText(tf121.getText());
     display112.setText(tf3.getText());
     display11.setText(tf1211.getText());
     display1112.setText(tf11.getText());
     display111.setText(tf12111.getText());
     display11111.setText(tf111.getText());
     display1111.setText(tf121111.getText());
     display11112.setText(Float.toString(fanswer));
    }
    catch(java.lang.RuntimeException eee) {
        warning.showAndWait();
    }
      
    }

    
}
