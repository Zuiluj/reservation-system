/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.database.DBConnect;
/**
 * FXML Controller class
 *
 * @author Windows 7
 */
public class DashboardController implements Initializable {

    @FXML
    private TableView<ModelTable> table;
    @FXML
    private TableColumn<ModelTable, String> col_eventType;
    @FXML
    private TableColumn<ModelTable, String> col_name;
    @FXML
    private TableColumn<ModelTable, String> col_contact;
    @FXML
    private TableColumn<ModelTable, String> col_venue;
    @FXML
    private TableColumn<ModelTable, String> col_signUpDate;
    @FXML
    private TableColumn<ModelTable, String> col_eventDate;
    @FXML
    private TableColumn<ModelTable, String> col_package;
    @FXML
    private TableColumn<ModelTable, String> col_price;
    @FXML
    private TableColumn<ModelTable, String> col_clientBudget;
    
    ObservableList<ModelTable> oblist = FXCollections.observableArrayList(); // create observable list 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Connection conn = DBConnect.getConnection();
        
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM activeevents"); // sql statement
            
            // this loop deals with displaying next items
            while(rs.next()) {
                oblist.add(new ModelTable(
                        rs.getString("eventType"),
                        rs.getString("name"),
                        rs.getString("contact"),
                        rs.getString("venue"),
                        rs.getString("signUpDate"),
                        rs.getString("eventDate"),
                        rs.getString("packageInclusion"),
                        rs.getString("price"),
                        rs.getString("clientBudget")
                ));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        col_eventType.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        col_venue.setCellValueFactory(new PropertyValueFactory<>("venue"));
        col_signUpDate.setCellValueFactory(new PropertyValueFactory<>("signUpDate"));
        col_eventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        col_package.setCellValueFactory(new PropertyValueFactory<>("packageInclusion"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_clientBudget.setCellValueFactory(new PropertyValueFactory<>("clientBudget"));
        
        table.setItems(oblist);
    }    
    
}
