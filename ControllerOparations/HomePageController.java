/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerOparations;

import Database.dbconnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.HomePage;


/**
 * FXML Controller class
 *
 * @author KARONEI
 */
public class HomePageController implements Initializable {
    
   Connection conn = dbconnection.pharm_db();
   ResultSet rs;
   PreparedStatement pst;
   Statement st;
   HomePage home;
    
   private BorderPane borderPane;
   
    @FXML
    private AnchorPane BorderPaneHome;
    @FXML
    private AreaChart<String, Integer> areaChart;
    @FXML
    private BarChart<String, Integer> barchart;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    try {
           SoldDrugtabl();currentstock();totalpaid();totalunpaid();stocksold();
           PaidPerDay();UnPaidPerDay();PaidAmountPerDay();UnPaidAmountPerDay() ; weeklySales(); weeklySalesAmount() ;  
           weeklySalesAmountUnpaid() ;
       } catch (SQLException ex) {
           Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }    

    @FXML
    private void Pharmacy(MouseEvent event) throws IOException {
    Parent pharmac = FXMLLoader.load(getClass().getResource("/UI/pharmacy.fxml"));
    Scene pharmacScene = new Scene(pharmac,1600,835);  
    
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(pharmacScene);
    app_stage.centerOnScreen();
    app_stage.show();
    }
    @FXML
    private void Category(MouseEvent event) throws IOException {
     try {
         Parent parent = FXMLLoader.load(getClass().getResource("/UI/Category.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Report(MouseEvent event) throws IOException {
    Parent pharmac = FXMLLoader.load(getClass().getResource("/UI/Reports.fxml"));
    Scene pharmacScene = new Scene(pharmac);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(pharmacScene);
    app_stage.show(); 
    }
    @FXML
    private void User(MouseEvent event) {
           try {
         Parent parent = FXMLLoader.load(getClass().getResource("/UI/RegisterUsers.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void Purchase(MouseEvent event) {
         try {
         Parent parent = FXMLLoader.load(getClass().getResource("/UI/Purchase.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void Supplier(MouseEvent event) {
         try {
         Parent parent = FXMLLoader.load(getClass().getResource("/UI/Supplier.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void Patient(MouseEvent event) {
         try {
         Parent parent = FXMLLoader.load(getClass().getResource("/UI/PatientReport.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.show();
        } catch (IOException ex) {
           // Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     @FXML
    private void ExitHomepage(MouseEvent event) throws IOException {
    Parent pharmac = FXMLLoader.load(getClass().getResource("/UI/Login.fxml"));
    Scene pharmacScene = new Scene(pharmac); 
     Stage stage  = new Stage();
     stage.centerOnScreen();
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(pharmacScene);
    app_stage.show();    
    }

    @FXML
    private void ChangePassword(MouseEvent event) throws IOException {
         Parent parent = FXMLLoader.load(getClass().getResource("/UI/RestPassword.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.centerOnScreen();
         stage.setScene(scene);
         stage.show();        
    }

    @FXML
    private void tostockfc(MouseEvent event) throws IOException {
    try {
         Parent parent = FXMLLoader.load(getClass().getResource("/UI/adminstock.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Sales(MouseEvent event) {
         try {
         Parent parent = FXMLLoader.load(getClass().getResource("/UI/Sales.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void  weeklySales() throws SQLException{
     areaChart.setTitle("Monthly  Report");        
     XYChart.Series sales = new XYChart.Series();  
       sales.setName("Total Sales"); 
       String query1 = "SELECT SUM(DrugQty)DrugQty,Date FROM sales WHERE Date >= DATE(adddate(curdate(), interval -30 day)) and  Date <= DATE(curdate()) GROUP BY Date";
          st = conn.createStatement();
          rs = st.executeQuery(query1);
          while(rs.next()){
              int drugqty = rs.getInt("DrugQty");
              String date = rs.getString("Date");
              sales.getData().add(new XYChart.Data<>("" +date, drugqty));
          }
        areaChart.getData().add(sales);
}
    
    
    void  weeklySalesAmount() throws SQLException{
  
     XYChart.Series salesAmount = new XYChart.Series();  
       salesAmount.setName("Paid Amount"); 
       String query1 = "SELECT SUM(DrugQty)DrugQty,Date FROM sales WHERE Date >= DATE(adddate(curdate(), interval -30 day)) and  Date <= DATE(curdate()) and paymentStatus='Paid' GROUP BY Date";
          st = conn.createStatement();
          rs = st.executeQuery(query1);
          while(rs.next()){
              int drugqty = rs.getInt("DrugQty");
              String date = rs.getString("Date");
              salesAmount.getData().add(new XYChart.Data<>("" +date, drugqty));
          }
        areaChart.getData().add(salesAmount);
}

void  weeklySalesAmountUnpaid() throws SQLException{
  
     XYChart.Series salesAmount = new XYChart.Series();  
       salesAmount.setName("UnPaid Amount"); 
       String query1 = "SELECT SUM(DrugQty)DrugQty,Date FROM sales WHERE Date >= DATE(adddate(curdate(), interval -30 day)) and  Date <= DATE(curdate()) and paymentStatus='UnPaid' GROUP BY Date";
          st = conn.createStatement();
          rs = st.executeQuery(query1);
          while(rs.next()){
              int drugqty = rs.getInt("DrugQty");
              String date = rs.getString("Date");
              salesAmount.getData().add(new XYChart.Data<>("" +date, drugqty));
          }
        areaChart.getData().add(salesAmount);
}
    void SoldDrugtabl() throws SQLException{
       XYChart.Series<String, Integer> drugsold = new XYChart.Series<>();     
       String query1 = "SELECT COUNT(pntNo)pntNo FROM sales WHERE Date >= DATE(adddate(curdate(), interval -30 day)) and  Date <= DATE(curdate())";
          st = conn.createStatement();
          rs = st.executeQuery(query1);
          while(rs.next()){
              int drugqty = rs.getInt("pntNo");
              drugsold.getData().add(new XYChart.Data<>("No of Customers  " +drugqty, drugqty));
          }
        barchart.getData().add(drugsold);
        barchart.setAnimated(false);
    }
    public void currentstock() throws SQLException{
      
       XYChart.Series<String, Integer> currentstock = new XYChart.Series<>();
       String query1 = "SELECT SUM(DrugQuantity)DrugQuantity FROM stockmedicine WHERE DrugQuantity > 0";
          st = conn.createStatement();
          rs = st.executeQuery(query1);
          while(rs.next()){
              int drugqty = rs.getInt("DrugQuantity");
             currentstock.getData().add(new XYChart.Data<>("CurrentAvailable Stock "+drugqty,drugqty));
          }
        barchart.getData().add(currentstock);  
          barchart.setAnimated(false);
        
    }
     public void totalpaid() throws SQLException{
       XYChart.Series<String, Integer> paidAmount = new XYChart.Series<>();
       String query1 = "SELECT SUM(TotalAmount)TotalAmount FROM sales WHERE Date >= DATE(adddate(curdate(), interval -7 day)) and  Date <= DATE(curdate()) AND paymentStatus='Paid'";
          st = conn.createStatement();
          rs = st.executeQuery(query1);
          while(rs.next()){
              int drugqty = rs.getInt("TotalAmount");
             paidAmount.getData().add(new XYChart.Data<>("Last 7 days Payment "+drugqty, drugqty));
          }
        barchart.getData().add(paidAmount);  
          barchart.setAnimated(false);
        
    }
       public void totalunpaid() throws SQLException{
       XYChart.Series<String, Integer> unpaid = new XYChart.Series<>();
       String query1 = "SELECT SUM(TotalAmount)TotalAmount FROM sales WHERE Date >= DATE(adddate(curdate(), interval -7 day)) and  Date <= DATE(curdate()) AND paymentStatus='UnPaid'";
          st = conn.createStatement();
          rs = st.executeQuery(query1);
          while(rs.next()){
              int drugqty = rs.getInt("TotalAmount");
             unpaid.getData().add(new XYChart.Data<>("Last 7 days UnPaid "+drugqty, drugqty));
          }
        barchart.getData().add(unpaid); 
          barchart.setAnimated(false);
        
    }
         public void stocksold() throws SQLException{
       XYChart.Series<String, Integer> unpaid = new XYChart.Series<>();
       String query1 = "SELECT SUM(DrugQty)DrugQty FROM sales WHERE Date >= DATE(adddate(curdate(), interval -7 day)) ";
          st = conn.createStatement();
          rs = st.executeQuery(query1);
          while(rs.next()){
              int drugqty = rs.getInt("DrugQty");
             unpaid.getData().add(new XYChart.Data<>("Sold Medicine Per Week "+drugqty, drugqty));
          }
        barchart.getData().add(unpaid); 
          barchart.setAnimated(false);
        
    }
      public void UnPaidPerDay() throws SQLException{
       XYChart.Series<String, Integer> unpaid = new XYChart.Series<>();
       String query1 = "SELECT SUM(DrugQty)DrugQty FROM sales WHERE Date >=curdate() AND  paymentStatus='UnPaid'    ";
          st = conn.createStatement();
          rs = st.executeQuery(query1);
          while(rs.next()){
              int drugqty = rs.getInt("DrugQty");
             unpaid.getData().add(new XYChart.Data<>("UnPaid medicine Per Day "+drugqty, drugqty));
          }
        barchart.getData().add(unpaid);  
          barchart.setAnimated(false);
        
    }
    
       public void PaidPerDay() throws SQLException{
       XYChart.Series<String, Integer> unpaid = new XYChart.Series<>();
       String query1 = "SELECT SUM(DrugQty)DrugQty FROM sales WHERE Date =curdate() ";
          st = conn.createStatement();
          rs = st.executeQuery(query1);
          while(rs.next()){
              int drugqty = rs.getInt("DrugQty");
             unpaid.getData().add(new XYChart.Data<>("Sold Medicine Per Day "+drugqty, drugqty));
          }
        barchart.getData().add(unpaid);  
          barchart.setAnimated(false);
        
    }
     public void PaidAmountPerDay() throws SQLException{
       XYChart.Series<String, Integer> unpaid = new XYChart.Series<>();
       String query1 = "SELECT SUM(TotalAmount) TotalAmount FROM sales WHERE Date =curdate() AND  paymentStatus='Paid' ";
          st = conn.createStatement();
          rs = st.executeQuery(query1);
          while(rs.next()){
              int drugqty = rs.getInt("TotalAmount");
             unpaid.getData().add(new XYChart.Data<>("Paid Amount Per Day "+drugqty, drugqty));
          }
        barchart.getData().add(unpaid); 
        barchart.setAnimated(false);
        
    }
       public void UnPaidAmountPerDay() throws SQLException{
           barchart.setTitle("Weekly report");
       XYChart.Series<String, Integer> unpaid = new XYChart.Series<>();
       String query1 = "SELECT SUM(TotalAmount) TotalAmount FROM sales WHERE Date =curdate() AND  paymentStatus='UnPaid' ";
          st = conn.createStatement();
          rs = st.executeQuery(query1);
          while(rs.next()){
              int drugqty = rs.getInt("TotalAmount");
             unpaid.getData().add(new XYChart.Data<>("UnPaid Amount Per Day "+drugqty, drugqty));
          }
        barchart.getData().add(unpaid);  
          barchart.setAnimated(false);
        
    }    
    
  }
