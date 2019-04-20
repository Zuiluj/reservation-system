/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import main.java.models.ModelTable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

import main.java.database.DBConnect;
        
public class HistoryController implements Initializable {

    @FXML
    private PieChart chart;
    @FXML
    private TableView<ModelTable> table;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void populateTable() {
        // TODO: populate the table
        Connection conn = DBConnect.getConnection();
        /*
        try {
            
            
        } catch (SQLException eee) {
            
        }
        */
    }
    
    public void showChart() {
        // TODO: display the chart
    }
    
}
