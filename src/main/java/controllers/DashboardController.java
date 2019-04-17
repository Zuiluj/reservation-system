// controller for Dashboard
package main.java.controllers;

import java.io.IOException;
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
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import main.java.database.DBConnect;
import main.java.controllers.RootMenuController;

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
    @FXML
    private TableColumn<ModelTable, String> col_notes;
    
    ObservableList<ModelTable> oblist = FXCollections.observableArrayList(); //this is an array containing the items
    
    public static Stage newStage = new Stage();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.initTable();
        
        // ### add options when right-clicking a cell
        ContextMenu cm = new ContextMenu();
        MenuItem item1 = new MenuItem("Edit");
        MenuItem item2 = new MenuItem("Delete");
        MenuItem item3 = new MenuItem("Mark as Done");
        cm.getItems().add(item1);
        cm.getItems().add(item2);
        cm.getItems().add(item3);
        
        
        // ### set menu actions
        // edit an event
        item1.setOnAction((ActionEvent e) -> {
            this.openEditMenu();
        });
        
        // delete an event
        item2.setOnAction((ActionEvent e) -> {
           this.deleteEvent();
           
            // TODO: REFRESH THE TABLE
           this.initTable();
        });
        
        // mark event as done
        item3.setOnAction((ActionEvent e) -> {
            this.markAsDone();
        });
        
        // ###
        
        // ### add the context menu feature
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
            if(t.getButton() == MouseButton.SECONDARY && !table.getSelectionModel().isEmpty()) {
                cm.show(table, t.getScreenX(), t.getScreenY());
            }
        });
        
        // ###
        
        }        
    
    
    public void initTable() {
        
        Connection conn = DBConnect.getConnection();
        
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM activeevents"); // aelect all items
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
                        rs.getString("clientBudget"),
                        rs.getString("notes")
                ));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // ### set cell elememnts
        col_eventType.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        col_venue.setCellValueFactory(new PropertyValueFactory<>("venue"));
        col_signUpDate.setCellValueFactory(new PropertyValueFactory<>("signUpDate"));
        col_eventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        col_package.setCellValueFactory(new PropertyValueFactory<>("packageInclusion"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_clientBudget.setCellValueFactory(new PropertyValueFactory<>("clientBudget"));
        col_notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        table.setItems(oblist);
        
        // ###
    }
    
    // method to edit menu
    public void openEditMenu() {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/editEvent.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        main.java.controllers.EditEventController controller = loader.getController();
        
        // pass the existing values of the selected row to EditEventController
        controller.setFields(
                table.getSelectionModel().getSelectedItem().getEventType(), 
                table.getSelectionModel().getSelectedItem().getName(), 
                table.getSelectionModel().getSelectedItem().getContact(), 
                table.getSelectionModel().getSelectedItem().getVenue(), 
                table.getSelectionModel().getSelectedItem().getSignUpDate(), 
                table.getSelectionModel().getSelectedItem().getEventDate(), 
                table.getSelectionModel().getSelectedItem().getPackageInclusion(), 
                table.getSelectionModel().getSelectedItem().getPrice(), 
                table.getSelectionModel().getSelectedItem().getClientBudget(), 
                table.getSelectionModel().getSelectedItem().getNotes());
        
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.setTitle("Edit Event");
        newStage.show();
        
    }
    
    // method to delete event
    public void deleteEvent() {
         
        // ### initalization of confirmation dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Deleting an event...");
        alert.setContentText("Are you sure you want to delete this event? The action cannot be undone!");
        // ###
        Optional<ButtonType> result = alert.showAndWait();
            
        if(result.get() == ButtonType.OK) {
            try {
                PreparedStatement deleteStmt = null;
                    
                String query = "DELETE FROM activeevents WHERE name=?";
                deleteStmt = DBConnect.conn.prepareStatement(query);
                
                deleteStmt.setString(1, table.getSelectionModel().getSelectedItem().getName());
                deleteStmt.execute();
                System.out.println("Event deleted...");
            } catch (SQLException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }
    
    // method to mark an event done (i.e. transfer the data to other table and delete it in the current table)
    public void markAsDone() {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Mark as done?");
        alert.setContentText("This would go straight to your history window.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            try {
            // ### transfer to other table
                PreparedStatement transferStmt = null;
                String insertQuery = "INSERT INTO eventsystem.accomplishedevents(eventType, name, contact, venue, signUpDate, packageInclusion, price, clientBudget, eventDate, notes)"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
                transferStmt = DBConnect.conn.prepareStatement(insertQuery);
            
                transferStmt.setString(1, table.getSelectionModel().getSelectedItem().getEventType());
                transferStmt.setString(2, table.getSelectionModel().getSelectedItem().getName());
                transferStmt.setString(3, table.getSelectionModel().getSelectedItem().getContact());
                transferStmt.setString(4, table.getSelectionModel().getSelectedItem().getVenue());
                transferStmt.setString(5, table.getSelectionModel().getSelectedItem().getSignUpDate());
                transferStmt.setString(6, table.getSelectionModel().getSelectedItem().getPackageInclusion());
                transferStmt.setString(7, table.getSelectionModel().getSelectedItem().getPrice());
                transferStmt.setString(8, table.getSelectionModel().getSelectedItem().getClientBudget());
                transferStmt.setString(9, table.getSelectionModel().getSelectedItem().getEventDate());
                transferStmt.setString(10, table.getSelectionModel().getSelectedItem().getNotes());
            
                transferStmt.executeUpdate();
            
                // ### delete the row
                PreparedStatement deleteStmt = null;
                    
                String deleteQuery = "DELETE FROM activeevents WHERE name=?";
                deleteStmt = DBConnect.conn.prepareStatement(deleteQuery);
                
                deleteStmt.setString(1, table.getSelectionModel().getSelectedItem().getName());
                deleteStmt.execute();
                table.refresh();
            
            } catch (SQLException eee) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, eee);
            }
        }
        
    }
    
} // end
    
