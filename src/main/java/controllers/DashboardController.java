package main.java.controllers;

import main.java.models.ModelTable;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.stage.Stage;

import main.java.database.DBConnect;

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
    
    public static Stage viewStage = new Stage();
    public static Stage editStage = new Stage();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.initTable();
        this.initNotice();
        // ### add options when right-clicking a cell
        ContextMenu cm = new ContextMenu();
        MenuItem view = new MenuItem("View");
        MenuItem edit = new MenuItem("Edit");
        MenuItem delete = new MenuItem("Delete");
        MenuItem mark = new MenuItem("Mark as Done");
        cm.getItems().add(view);
        cm.getItems().add(edit);
        cm.getItems().add(delete);
        cm.getItems().add(mark);
        
        
        // ### set menu actions
        
        view.setOnAction((ActionEvent e) -> {
            this.view();
        });
        // edit an event
        edit.setOnAction((ActionEvent e) -> {
            this.openEditMenu();
        });
        
        // delete an event
        delete.setOnAction((ActionEvent e) -> {
           this.deleteEvent();
        });
        
        // mark event as done
        mark.setOnAction((ActionEvent e) -> {
            this.markAsDone();
        });
        
        // ###
        
        // ### add the context menu feature
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
            if(t.getButton() == MouseButton.SECONDARY && !table.getSelectionModel().isEmpty()) { // only trigger right click when there is a highlighted row
                cm.show(table, t.getScreenX(), t.getScreenY());
            }
        });
        
        // ###
        
        }        
    
    private void initNotice() {
        int eventCount = 0;
        
        Connection conn = DBConnect.getConnection();
        ResultSet rs;
        
        try {
            rs = conn.createStatement().executeQuery("SELECT * FROM activeevents");
            while(rs.next()){
                eventCount += 1;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(eventCount > 0) {
            Alert notice = new Alert(AlertType.INFORMATION);
            notice.setTitle("Notice");
            notice.setHeaderText("Upcoming events");
            notice.setContentText("You have " + eventCount + " incoming events. Good luck!");
            notice.show();
        }
    }
    
    private void initTable() {
        

        Connection conn = DBConnect.getConnection();
        
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM activeevents"); // aelect all items
            // this loop deals with displaying next items 
            this.oblist.removeAll(oblist);
            while(rs.next()) {
                this.oblist.add(new ModelTable(
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
            
        } catch (SQLException eee) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, eee);
        }
        
        // ### set cell elememnts
        this.col_eventType.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        this.col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        this.col_venue.setCellValueFactory(new PropertyValueFactory<>("venue"));
        this.col_signUpDate.setCellValueFactory(new PropertyValueFactory<>("signUpDate"));
        this.col_eventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        this.col_package.setCellValueFactory(new PropertyValueFactory<>("packageInclusion"));
        this.col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.col_clientBudget.setCellValueFactory(new PropertyValueFactory<>("clientBudget"));
        this.col_notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        this.table.setItems(oblist);
        
        
        // ###
    }
    
    // method to view the event
    private void view() {
        
        // open the event in new window
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/viewEvent.fxml"));
        try {
            root = loader.load();
        } catch (IOException eee) {
             Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, eee);
        }
        main.java.controllers.ViewEventController controller = loader.getController(); // assign controller to var to enable passing values
        
        controller.setFields(
                this.table.getSelectionModel().getSelectedItem().getEventType(), 
                this.table.getSelectionModel().getSelectedItem().getName(), 
                this.table.getSelectionModel().getSelectedItem().getContact(), 
                this.table.getSelectionModel().getSelectedItem().getVenue(), 
                this.table.getSelectionModel().getSelectedItem().getSignUpDate(), 
                this.table.getSelectionModel().getSelectedItem().getEventDate(), 
                this.table.getSelectionModel().getSelectedItem().getPackageInclusion(), 
                this.table.getSelectionModel().getSelectedItem().getPrice(), 
                this.table.getSelectionModel().getSelectedItem().getClientBudget(), 
                this.table.getSelectionModel().getSelectedItem().getNotes()
        );
        
        Scene newScene = new Scene(root);
        viewStage.setScene(newScene);
        viewStage.setTitle("View event");
        viewStage.show();
        
        
        
    }
    // method to edit menu
    private void openEditMenu() {
        
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/editEvent.fxml"));
        try {
            root = loader.load();
        } catch (IOException eee) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, eee);
        }
        main.java.controllers.EditEventController controller = loader.getController();
        
        // pass the existing values of the selected row to EditEventController
        controller.setFields(
                this.table.getSelectionModel().getSelectedItem().getEventType(), 
                this.table.getSelectionModel().getSelectedItem().getName(), 
                this.table.getSelectionModel().getSelectedItem().getContact(), 
                this.table.getSelectionModel().getSelectedItem().getVenue(), 
                this.table.getSelectionModel().getSelectedItem().getSignUpDate(), 
                this.table.getSelectionModel().getSelectedItem().getEventDate(), 
                this.table.getSelectionModel().getSelectedItem().getPackageInclusion(), 
                this.table.getSelectionModel().getSelectedItem().getPrice(), 
                this.table.getSelectionModel().getSelectedItem().getClientBudget(), 
                this.table.getSelectionModel().getSelectedItem().getNotes()
        );
        
        Scene newScene = new Scene(root);
        editStage.setScene(newScene);
        editStage.setTitle("Edit Event");
        editStage.setResizable(false);
        editStage.show();
        
    }
    
    // method to delete event
    private void deleteEvent() {
         
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
                this.initTable();
            } catch (SQLException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.initTable();
    }
    
    // method to mark an event done (i.e. transfer the data to other table and delete it in the current table)
    private void markAsDone() {
        
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
            
            } catch (SQLException eee) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, eee);
            }
        }
        this.initTable(); // refresh items
        
    }
    
} // end
    
