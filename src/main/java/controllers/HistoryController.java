package main.java.controllers;

import java.io.IOException;
import main.java.models.ModelTable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import main.java.models.HistoryModelTable;
import main.java.database.DBConnect;
        
public class HistoryController implements Initializable {

    @FXML
    private PieChart chart;
    @FXML
    private TableView<HistoryModelTable> table;
    @FXML
    private TableColumn<HistoryModelTable, String> col_eventType;
    @FXML
    private TableColumn<HistoryModelTable, String> col_name;
    @FXML
    private TableColumn<HistoryModelTable, String> col_eventDate;

    Stage viewStage = new Stage();
    ObservableList<HistoryModelTable> oblist = FXCollections.observableArrayList();
    private int bdayCount, weddingCount, debutCount, othersCount;
    private String eventType, name, contact, venue, signUpDate, eventDate, packageInclusion, packagePrice, clientBudget, notes;
    private String resultKey;
    
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initTable();
        this.initValues();
        this.initChart();
    }    
    
    private void initTable() {
        // initialize context menu
        ContextMenu cm = new ContextMenu();
        MenuItem view = new MenuItem("View");
        MenuItem delete = new MenuItem("Delete");
        cm.getItems().add(view);
        cm.getItems().add(delete);
        
        view.setOnAction((ActionEvent e) -> {
            this.viewEvent();
        });
                
        delete.setOnAction((ActionEvent e) -> {
            this.deleteEvent();
        });
        
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
            if(t.getButton() == MouseButton.SECONDARY && !table.getSelectionModel().isEmpty()) {
                cm.show(table, t.getScreenX(), t.getScreenY());
            }
        });
        
        //  populate the table
        Connection conn = DBConnect.getConnection();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM accomplishedevents");
            this.oblist.removeAll(oblist); 
            while(rs.next()){
                this.oblist.add(new HistoryModelTable(
                    rs.getString("eventType"),
                    rs.getString("name"),
                    rs.getString("eventDate")
                ));
            }
                    
            
        } catch (SQLException eee) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, eee);
        }
        
        // initialize values
        
        this.col_eventType.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        this.col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.col_eventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        
        this.table.setItems(oblist);
        
    }
    
    private void initValues() {
        Connection conn = DBConnect.getConnection();
        
        // TODO: initialize values for the chart
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT eventType FROM accomplishedevents");
            while(rs.next()){
                resultKey = rs.getString("eventType");
                switch(resultKey.toLowerCase()){
                    case "birthday":
                        this.bdayCount += 1;
                        break;
                    case "wedding":
                        this.weddingCount += 1;
                        break;
                    case "debut":
                        this.debutCount += 1;
                        break;
                    default:
                        this.othersCount += 1;
                        break;
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(HistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initChart() {
        // initialize values
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Birthday", this.bdayCount),
            new PieChart.Data("Wedding", this.weddingCount),
            new PieChart.Data("Debut", this.debutCount),
            new PieChart.Data("Others", this.othersCount)
        );
        
        // display the chart
        chart.setData(pieChartData);
        chart.setLabelsVisible(true);
        chart.setTitle("Number of events");
    }
    
    private void viewEvent() {
        Connection conn = DBConnect.getConnection();
        
        // initialize scene
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/viewEvent.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(HistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        main.java.controllers.ViewEventController controller = loader.getController();
        
        //  acquire from database and initialize fields
        PreparedStatement viewStmt = null;
        
        try {
            viewStmt = conn.prepareStatement("SELECT * FROM accomplishedevents WHERE name=?");
            
            viewStmt.setString(1, table.getSelectionModel().getSelectedItem().getName());
            
            ResultSet viewEventResult = viewStmt.executeQuery();
        
            //  set fields
            while(viewEventResult.next()){
                controller.setFields(
                    viewEventResult.getString("eventType"), 
                    viewEventResult.getString("name"), 
                    viewEventResult.getString("contact"), 
                    viewEventResult.getString("venue"), 
                    viewEventResult.getString("signUpDate"), 
                    viewEventResult.getString("eventDate"), 
                    viewEventResult.getString("packageInclusion"), 
                    viewEventResult.getString("price"), 
                    viewEventResult.getString("clientBudget"), 
                    viewEventResult.getString("notes")
                );
            }
        }
        catch (SQLException eee) {
             Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, eee);
        }
        
        // launch the scene in a new window
        Scene viewScene = new Scene(root);
        viewStage.setScene(viewScene);
        viewStage.setTitle("View past event");
        viewStage.show();
    }

    private void deleteEvent() {
        // ### initalization of confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Deleting an event...");
        alert.setContentText("Are you sure you want to delete this event? The action cannot be undone!");
        // ###
        Optional<ButtonType> result = alert.showAndWait();
            
        if(result.get() == ButtonType.OK) {
            try {
                PreparedStatement deleteStmt = null;
                    
                String query = "DELETE FROM accomplishedevents WHERE name=?";
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
        this.initValues();
        this.initChart();
    }
    
    @FXML
    private void deleteAll(ActionEvent event) {
        Connection conn = DBConnect.getConnection();
    
         // ### initalization of confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Deleting all event...");
        alert.setContentText("Are you sure you want to delete all event? The action cannot be undone!");
        // ###
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            try {
                conn.createStatement().executeUpdate("DELETE FROM accomplishedevents");
                Alert notice = new Alert(Alert.AlertType.INFORMATION);
                notice.setTitle("Truncated all events");
                notice.setHeaderText("All events were deleted.");
                notice.setContentText("Fresh as new!");
            } catch (SQLException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.initTable();
        this.initValues();
        this.initChart();
    }
}
