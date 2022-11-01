/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerOparations;

import Database.dbconnection;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class
 *
 * @author KARONEI
 */
public class adminstock implements Initializable {
    
   Connection conn = dbconnection.pharm_db();
   ResultSet rs;
   PreparedStatement pst;
   Statement st;
   Stock stck;
   Outofstock Outstck;

    @FXML
    private JFXTextField ftdrugName;
    @FXML
    private JFXTextField tfprice;
    @FXML
    private JFXTextField tfQuantity;
    @FXML
    private JFXTextField tfSupplier;
    @FXML
    private JFXDatePicker tfExpDate;
    @FXML
    private TableView<Stock> Tablemed;
    @FXML
    private TableColumn<Stock, Integer> colMdId;
    @FXML
    private TableColumn<Stock, String> colMdName;
    @FXML
    private TableColumn<Stock, Integer> colPrice;
    @FXML
    private TableColumn<Stock, Integer> colQuantity;
    @FXML
    private TableColumn<Stock, String> colSupplier;
    @FXML
    private TableColumn<Stock, String> colShelfNo;
    @FXML
    private TableColumn<Stock, String> colCategory;
    @FXML
    private TableColumn<Stock, String> colExpDAte;
   
    @FXML
    private JFXTextField ftdrugId;
    @FXML
    private JFXTextField tfmedicine;
    @FXML
    private Label lblExpSoon;
    @FXML
    private Label lblOutOfStock;
    @FXML
    private Label lablEpired; 
    @FXML
    private JFXComboBox<String> ShelfNo;
    ObservableList<String> ShelfNo1 = FXCollections.observableArrayList();
    @FXML
    private JFXComboBox<String> medCategory;
    ObservableList<String> medCategory1 = FXCollections.observableArrayList();
    @FXML
    private JFXComboBox<String> medcategory ;
     ObservableList<String> medCategory12 = FXCollections.observableArrayList();
    @FXML
    private Button btnDelete;
    @FXML
    private AnchorPane lblExpired;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnclose;
  
       /**
        * Initializes the controller class.
     * @param url
     * @param rb
        */
       public void initialize(URL url, ResourceBundle rb) {
           ExpSoon();  Expired();Out_Of_stock(); OutofStock(); 
           ShelfNo.setItems(ShelfNo1);
           medCategory.setItems(medCategory1);
           medcategory.setItems(medCategory12);
           showStock();fillComboShelfNo();fillCategory();medCategory();
       }
      private void fillComboShelfNo(){
       try {
           String query = "SELECT tfCategory FROM category";
           pst = conn.prepareStatement(query);
           rs = pst.executeQuery();
           while(rs.next()){
             ShelfNo1.add(rs.getString("tfCategory"));
           }
       } catch (SQLException ex) {
           System.out.println(ex);
       }
      }
       private void fillCategory(){
       try {
           String query = "SELECT shelfNo FROM category";
           pst = conn.prepareStatement(query);
           rs = pst.executeQuery();
           while(rs.next()){
            medCategory1.add(rs.getString("shelfNo"));
           }
       } catch (SQLException ex) {
           System.out.println(ex);
       }
      }
       //to insert the  values to the combo box med category
       private void medCategory(){
        try {
           String query = "SELECT DrugName FROM stockmedicine GROUP BY DrugName";
           pst = conn.prepareStatement(query);
           rs = pst.executeQuery();
           while(rs.next()){
           medCategory12.add(rs.getString("DrugName"));
           }
       } catch (SQLException ex) {
           System.out.println(ex);
       }   
       }
  public ObservableList<Stock> getStock(){
      ObservableList<Stock> supplierList = FXCollections.observableArrayList(); 
      String query = "SELECT * FROM stockmedicine ORDER BY id DESC";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          Stock stck;
          while(rs.next()){
              stck = new Stock(rs.getInt("batchno"),rs.getString("DrugName"),rs.getInt("price"),rs.getInt("DrugQuantity"),rs.getString("category"),rs.getString("ShelfNo"),rs.getString("ExpDate"),rs.getString("Supplier"));
              supplierList.add(stck);
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
      return supplierList;
    } 
  
  public void showStock(){
     ObservableList<Stock> list =  getStock();
     colMdId.setCellValueFactory(new PropertyValueFactory<>("batchno"));
     colMdName.setCellValueFactory(new PropertyValueFactory<>("DrugName"));
     colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
     colQuantity.setCellValueFactory(new PropertyValueFactory<>("DrugQuantity"));
     colShelfNo.setCellValueFactory(new PropertyValueFactory<>("Category"));
     colCategory.setCellValueFactory(new PropertyValueFactory<>("Shelfno"));
     colSupplier.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
     colExpDAte.setCellValueFactory(new PropertyValueFactory<>("Expdate"));  
     Tablemed.setItems(list);
     
    FilteredList<Stock> filterMedicine = new FilteredList<>(list, b -> true); 
    tfmedicine.textProperty().addListener((ObservableValue<? extends String> observable,String oldValue, String newValue)->{
    filterMedicine.setPredicate((Stock stock) ->   {
    
   if(newValue.isEmpty()){
       return true;
   }
   String searchKeyword = newValue.toLowerCase();
        return stock.getDrugName().toLowerCase().contains(searchKeyword);
       
    });
});
    SortedList<Stock> sortedmedicine = new SortedList<>(filterMedicine);
    sortedmedicine.comparatorProperty().bind(Tablemed.comparatorProperty());
    Tablemed.setItems(sortedmedicine);
     
  }
    @FXML
    private void Update_Stock(ActionEvent event) {
               if (ftdrugId.getText().equals("")) {
    alert.AlertMaker.showNotification("Failed !","Empty field",null);
ftdrugId.requestFocus();
    } else if(tfprice.getText().equals("")){
    alert.AlertMaker.showNotification("Failed !","Empty field",null);
    tfprice.requestFocus();
    }
    else if(tfQuantity.getText().equals("")){
        alert.AlertMaker.showNotification("Failed !","Empty field",null);
    tfQuantity.requestFocus();
    }
    else if(ShelfNo.getValue().equals("")){
         alert.AlertMaker.showNotification("Failed !","Empty field",null);
   ShelfNo.requestFocus(); 
    }
    else if(medCategory.getValue().equals("")){
          alert.AlertMaker.showNotification("Failed !","Empty field",null);
     medCategory.requestFocus();   
    }
    else if(tfSupplier.getText().equals("")){
     alert.AlertMaker.showNotification("Failed !","Empty field",null);
     tfSupplier.requestFocus();
    }else if(tfExpDate.getValue().equals("")){
      alert.AlertMaker.showNotification("Failed !","Empty field",null);
    tfExpDate.requestFocus();  
    }else if(ftdrugName.getText().equals("")){
     alert.AlertMaker.showNotification("Failed !","Empty field",null);
  ftdrugName.requestFocus();    
    }else if(ShelfNo.getValue()== null){
     alert.AlertMaker.showNotification("Failed !","Select Drug Category",null);
  ShelfNo.requestFocus();    
    }else if(medCategory.getValue()== null){
   alert.AlertMaker.showNotification("Failed !","Select Drug Category",null);
  medCategory.requestFocus(); 
    }
    
    else{
    updateFieldstock();
    }     
    }
    public void updateFieldstock(){
      String ExpDate = tfExpDate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
         
       try{
       String update = ("UPDATE  stockmedicine SET DrugName = '"+ftdrugName.getText()+"',price='"+tfprice.getText()+"',DrugQuantity='"+tfQuantity.getText()+"',ShelfNo='"+ShelfNo.getValue()+"',category='"+medCategory.getValue()+"', Supplier='"+tfSupplier.getText()+"',ExpDate= '"+tfExpDate.getValue()+"' WHERE  batchno='"+ftdrugId.getText()+"'");
       pst.execute(update);
       showStock();
       ExpSoon();  Expired();Out_Of_stock();
      alert.AlertMaker.showNotification("Success","Successfully Update",null);
       }catch(Exception ex){
            alert.AlertMaker.showNotification("Fail Update!","Unsuccessfully Update",null);
            System.out.println(ex);
       }   
  }
    
    @FXML
    private void Delete_Medicine(ActionEvent event) {
                 if (ftdrugId.getText().equals("")) {
    alert.AlertMaker.showNotification("Failed !","Empty field",null);
                     ftdrugId.requestFocus();
    } else{
       try {
          PreparedStatement pst1 = conn.prepareStatement("DELETE FROM stockmedicine  WHERE  batchno = '"+ftdrugId.getText()+"'");
          pst1.execute();
          showStock();Out_Of_stock();
            alert.AlertMaker.showNotification("Success !","Deleted Successfully",null);
       } catch (SQLException ex) {
           System.out.println(ex);
       }   }
    }

    private void Exit_Stock(ActionEvent event) throws IOException {
     Parent supplier = FXMLLoader.load(getClass().getResource("/UI/Stock.fxml"));
    Scene supplierScene = new Scene(supplier);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(supplierScene);
    app_stage.close();
    }

    private void Display_data(MouseEvent event) {
       String ExpDate = String.valueOf(tfExpDate.getValue());
       Stock stck = Tablemed.getSelectionModel().getSelectedItem();
       ftdrugId.setText(""+stck.getBatchno());
       ftdrugName.setText(stck.getDrugName());
       tfprice.setText(""+stck.getPrice());
       tfSupplier.setText(""+stck.getSupplier());
       tfQuantity.setText(""+stck.getDrugQuantity());   
      
    }
    private void ExpSoon() {
        
       Calendar calFromDate = Calendar.getInstance();
       Calendar calTDate = Calendar.getInstance();
       
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       
       calFromDate.add(Calendar.DATE, +1);
       calTDate.add(Calendar.DATE, +7);
       String datefrom =dateFormat.format(calFromDate.getTime());
       String dateto =dateFormat.format(calTDate.getTime());
      
        
      try{
        String query = "select COUNT(*) from stockmedicine WHERE ExpDate BETWEEN '"+datefrom+"' AND '"+dateto+"'";
          st = conn.createStatement();
          rs = st.executeQuery(query);
          Stock stock;
          while(rs.next()){
      lblExpSoon.setText(rs.getString(1));
          }
      }catch(SQLException ex){
        System.out.println(ex);  
      }
}
    @FXML
    private void Expired(){
        try{
         String query = "SELECT COUNT(*) AS Expired FROM stockmedicine WHERE ExpDate <= CURDATE()";
          st = conn.createStatement();
          rs = st.executeQuery(query);
        
          Stock stock;
          while(rs.next()){
        lablEpired.setText(rs.getString(1));
          }
      }catch(SQLException ex){
        System.out.println(ex);  
      }  
        
    }
    private void Out_Of_stock(){
         try{
         String query = "SELECT COUNT(*) Out_of_stock FROM stockmedicine WHERE DrugQuantity = 0";
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          Stock stock;
          while(rs.next()){
        lblOutOfStock.setText(rs.getString(1));
          }
      }catch(SQLException ex){
        System.out.println(ex);  
      } 
    }

    @FXML
    private void Add_Medicine(ActionEvent event) {
      int medqty = Integer.parseInt(tfQuantity.getText()); 
         if (ftdrugId.getText().equals("")) {
    alert.AlertMaker.showNotification("Failed !","Empty field",null);
ftdrugId.requestFocus();
    } else if(tfprice.getText().equals("")){
    alert.AlertMaker.showNotification("Failed !","Empty field",null);
    tfprice.requestFocus();
    }
    else if(tfQuantity.getText().equals("")){
        alert.AlertMaker.showNotification("Failed !","Empty field",null);
    tfQuantity.requestFocus();
    }
    else if(ShelfNo.getValue().equals("")){
         alert.AlertMaker.showNotification("Failed !","Empty field",null);
   ShelfNo.requestFocus(); 
    }
    else if(medCategory.getValue().equals("")){
          alert.AlertMaker.showNotification("Failed !","Empty field",null);
     medCategory.requestFocus();   
    }
    else if(tfSupplier.getText().equals("")){
     alert.AlertMaker.showNotification("Failed !","Empty field",null);
     tfSupplier.requestFocus();
    }else if(tfExpDate.getValue().equals("")){
      alert.AlertMaker.showNotification("Failed !","Empty field",null);
    tfExpDate.requestFocus();  
    }else if(ftdrugName.getText().equals("")){
     alert.AlertMaker.showNotification("Failed !","Empty field",null);
  ftdrugName.requestFocus();    
    }else if(medqty<=0){
             alert.AlertMaker.showNotification("Failed !","Invalid Medicine value",null);
    }else if(medqty>=2){
          alert.AlertMaker.showNotification("Failed !","Invalid Medicine value can only be",null);   
    }
    
    else{
    addmedicine ();
    }
    }
    public void addmedicine (){
      String ExpDate = tfExpDate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); 
    try {
       
          String q = "INSERT INTO `stockmedicine`(`id`,`batchno`,`DrugName`,`price`,`DrugQuantity`,`category`,`shelfNo`,`Supplier`,`ExpDate`)"+ " VALUES (?,?,?,?,?,?,?,?,?)";
          pst = conn.prepareStatement(q);
          pst.setInt(1,0);
          pst.setString(2,ftdrugId.getText());
          pst.setString(3, ftdrugName.getText()); 
          pst.setString(4, tfprice.getText());
          pst.setString(5, tfQuantity.getText()); 
          pst.setString(6, medCategory.getValue());
          pst.setString(7, ShelfNo.getValue());  
          pst.setString(8, tfSupplier.getText());
          pst.setString(9, ExpDate);        
          pst.execute();
          showStock();
          ExpSoon();  Expired();Out_Of_stock();
         alert.AlertMaker.showNotification("Success!","Successfuly add the Medicine",null);
        } catch (SQLException ex) {
                alert.AlertMaker.showNotification("Fail To Add Medicine!","",null);
            }  
}
    
     
//shows the medicine category to the table with unique drug batch number
     public ObservableList<Stock> getStockCat(){
         //select distinct id,DrugName,DrugQuantity,category,ShelfNo,price from stock
      ObservableList<Stock> supplierList = FXCollections.observableArrayList(); 
      String query = "select distinct batchno,DrugName,DrugQuantity,price,category,ShelfNo,ExpDate,Supplier from stockmedicine where DrugName= '"+ medcategory.getValue()+"'";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          Stock stck;
          while(rs.next()){
              stck = new Stock(rs.getInt("batchno"),rs.getString("DrugName"),rs.getInt("price"),rs.getInt("DrugQuantity"),rs.getString("category"),rs.getString("ShelfNo"),rs.getString("ExpDate"),rs.getString("Supplier"));
              supplierList.add(stck);
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
      return supplierList;
    } ////shows the medicine category to the table with unique drug batch number
    public void showStockCat(){
     ObservableList<Stock> list =  getStockCat();
     colMdId.setCellValueFactory(new PropertyValueFactory<>("batchno"));
     colMdName.setCellValueFactory(new PropertyValueFactory<>("DrugName"));
     colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
     colQuantity.setCellValueFactory(new PropertyValueFactory<>("DrugQuantity"));
     colShelfNo.setCellValueFactory(new PropertyValueFactory<>("Category"));
     colCategory.setCellValueFactory(new PropertyValueFactory<>("Shelfno"));
     colSupplier.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
     colExpDAte.setCellValueFactory(new PropertyValueFactory<>("Expdate"));  
     Tablemed.setItems(list);
    }
    //shows the medicine category to the table with unique drug batch number
    @FXML
    private void To_FilterDrugName(ActionEvent event) {
     showStockCat();
    }
    
    void RemoveExpiredMed(){
         try {
          PreparedStatement pst = conn.prepareStatement("INSERT INTO stockexpire (BatchNo, menName, medQty,medCat,expdate) SELECT batchno, DrugName, DrugQuantity,category,ExpDate FROM stockmedicine WHERE ExpDate <= CURDATE()");
          pst.execute();
          showStock();
       } catch (SQLException ex) {
           System.out.println(ex);
       }
    }
    
     void OutofStock(){
         try {
          PreparedStatement pst = conn.prepareStatement("INSERT INTO stockexpire (BatchNo, menName, medQty,medCat,expdate) SELECT batchno, DrugName, DrugQuantity,category,ExpDate FROM stockmedicine WHERE DrugQuantity <= 0");
          pst.execute();
          showStock();
       } catch (SQLException ex) {
           System.out.println(ex);
       }
     }
      void ExprSoon(){
         try {
         
          PreparedStatement pst = conn.prepareStatement("INSERT INTO stockexpire (BatchNo, menName, medQty,medCat,expdate) SELECT batchno, DrugName, DrugQuantity,category,ExpDate FROM stockmedicine WHERE  ExpDate > now() - INTERVAL 1 DAY");
          pst.execute();
          PreparedStatement pst1 = conn.prepareStatement("DELETE FROM stockmedicine WHERE ExpDate > now() - INTERVAL 7 DAY");
          pst1.execute();
          showStock();
       } catch (SQLException ex) {
           System.out.println(ex);
       }
     }
      
      
     
    @FXML
    private void OutStock(MouseEvent event) throws SQLException {
    showOutStock();
    btnDelete.setDisable(true);
    }
    
   public ObservableList<Stock> getOutofStock(){
         //select distinct id,DrugName,DrugQuantity,category,ShelfNo,price from stock
      ObservableList<Stock> OutStockList = FXCollections.observableArrayList(); 
      String query = "select batchno,DrugName,DrugQuantity,price,category,ShelfNo,ExpDate,Supplier from stockmedicine where DrugQuantity <= 0";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          Stock stck;
          while(rs.next()){
              stck = new Stock(rs.getInt("batchno"),rs.getString("DrugName"),rs.getInt("price"),rs.getInt("DrugQuantity"),rs.getString("category"),rs.getString("ShelfNo"),rs.getString("ExpDate"),rs.getString("Supplier"));
              OutStockList.add(stck);
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
      return OutStockList;
    }
      public void showOutStock(){
     ObservableList<Stock> OutStocklist =  getOutofStock();
     colMdId.setCellValueFactory(new PropertyValueFactory<>("batchno"));
     colMdName.setCellValueFactory(new PropertyValueFactory<>("DrugName"));
     colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
     colQuantity.setCellValueFactory(new PropertyValueFactory<>("DrugQuantity"));
     colShelfNo.setCellValueFactory(new PropertyValueFactory<>("Category"));
     colCategory.setCellValueFactory(new PropertyValueFactory<>("Shelfno"));
     colSupplier.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
     colExpDAte.setCellValueFactory(new PropertyValueFactory<>("Expdate"));  
     Tablemed.setItems(OutStocklist);
    }

      public ObservableList<Stock> getExpiredStock(){
         //select distinct id,DrugName,DrugQuantity,category,ShelfNo,price from stock
      ObservableList<Stock> ExpList = FXCollections.observableArrayList(); 
      String query = "select batchno,DrugName,DrugQuantity,price,category,ShelfNo,ExpDate,Supplier from stockmedicine where ExpDate <= CURDATE()";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          Stock stck;
          while(rs.next()){
              stck = new Stock(rs.getInt("batchno"),rs.getString("DrugName"),rs.getInt("price"),rs.getInt("DrugQuantity"),rs.getString("category"),rs.getString("ShelfNo"),rs.getString("ExpDate"),rs.getString("Supplier"));
               ExpList.add(stck);
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
      return  ExpList;
    }
      public void showExpiredStock(){
     ObservableList<Stock>  ExpList =  getExpiredStock();
     colMdId.setCellValueFactory(new PropertyValueFactory<>("batchno"));
     colMdName.setCellValueFactory(new PropertyValueFactory<>("DrugName"));
     colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
     colQuantity.setCellValueFactory(new PropertyValueFactory<>("DrugQuantity"));
     colShelfNo.setCellValueFactory(new PropertyValueFactory<>("Category"));
     colCategory.setCellValueFactory(new PropertyValueFactory<>("Shelfno"));
     colSupplier.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
     colExpDAte.setCellValueFactory(new PropertyValueFactory<>("Expdate"));  
     Tablemed.setItems(ExpList);
    } 
    
      public ObservableList<Stock> getExpiredSoonStock(){
          
       Calendar calFromDate = Calendar.getInstance();
       Calendar calTDate = Calendar.getInstance();
       
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       
       calFromDate.add(Calendar.DATE, +1);
       calTDate.add(Calendar.DATE, +7);
       String datefrom =dateFormat.format(calFromDate.getTime());
       String dateto =dateFormat.format(calTDate.getTime());
          
         // select distinct id,DrugName,DrugQuantity,category,ShelfNo,price from stock
      ObservableList<Stock> ExpSoonList = FXCollections.observableArrayList(); 
        String query = "select batchno,DrugName,DrugQuantity,price,category,ShelfNo,ExpDate,Supplier from stockmedicine WHERE ExpDate BETWEEN '"+datefrom+"' AND '"+dateto+"'";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          Stock stck;
          while(rs.next()){
              stck = new Stock(rs.getInt("batchno"),rs.getString("DrugName"),rs.getInt("price"),rs.getInt("DrugQuantity"),rs.getString("category"),rs.getString("ShelfNo"),rs.getString("ExpDate"),rs.getString("Supplier"));
               ExpSoonList.add(stck);
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
      return  ExpSoonList;
    }
       public void showExpiredSoonStock(){
     ObservableList<Stock>  ExpSoonList =  getExpiredSoonStock();
     colMdId.setCellValueFactory(new PropertyValueFactory<>("batchno"));
     colMdName.setCellValueFactory(new PropertyValueFactory<>("DrugName"));
     colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
     colQuantity.setCellValueFactory(new PropertyValueFactory<>("DrugQuantity"));
     colShelfNo.setCellValueFactory(new PropertyValueFactory<>("Category"));
     colCategory.setCellValueFactory(new PropertyValueFactory<>("Shelfno"));
     colSupplier.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
     colExpDAte.setCellValueFactory(new PropertyValueFactory<>("Expdate"));  
     Tablemed.setItems(ExpSoonList);
    } 
      
    @FXML
    private void Expired1(MouseEvent event) {
        showExpiredStock(); 
        
    }
 
    @FXML
    private void Expiresoon(MouseEvent event) {
        showExpiredSoonStock();
    }

    @FXML
    private void Expiresoon1(MouseEvent event) {
        showExpiredSoonStock();
    }
    @FXML
    private void closePane(ActionEvent event) throws IOException {
         Parent supplier = FXMLLoader.load(getClass().getResource("/UI/adminstock.fxml"));
    Scene supplierScene = new Scene(supplier);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(supplierScene);
    app_stage.close();
    }

    @FXML
    private void medicineVali(KeyEvent event) {
               tfmedicine.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
           tfmedicine.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
    }

    @FXML
    private void medvalidate(KeyEvent event) {
               tfmedicine.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
           tfmedicine.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
    }

    @FXML
    private void DrugName(KeyEvent event) {
               ftdrugName.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
          ftdrugName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
    }

    @FXML
    private void price(KeyEvent event) {
              tfprice.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
          tfprice.setText(newValue.replaceAll("[^\\d]", ""));
        }
    });
    }

    @FXML
    private void qty(KeyEvent event) {
               tfQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
           tfQuantity.setText(newValue.replaceAll("[^\\d]", ""));
        }
    });
    }

    @FXML
    private void Supplier(KeyEvent event) {
             tfSupplier.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
          tfSupplier.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
                       });
    }

    @FXML
    private void batchNo(KeyEvent event) {
              ftdrugId.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
           ftdrugId.setText(newValue.replaceAll("[^\\d]", ""));
        }
    });
    }

    @FXML
    private void Display_data1(MouseEvent event) {
       String ExpDate = String.valueOf(tfExpDate.getValue());
       Stock stck = Tablemed.getSelectionModel().getSelectedItem();
       ftdrugId.setText(""+stck.getBatchno());
       ftdrugName.setText(stck.getDrugName());
       tfprice.setText(""+stck.getPrice());
       tfSupplier.setText(""+stck.getSupplier());
       tfQuantity.setText(""+stck.getDrugQuantity());   
       ShelfNo.setValue(""+stck.getCategory());
       medCategory.setValue(""+stck.getShelfno());
       tfExpDate.setValue(LocalDate.now());
    }
}
