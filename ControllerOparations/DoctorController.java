/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerOparations;

import Database.dbconnection;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Medcategory;
import model.pharmacy;

/**
 * FXML Controller class
 *
 * @author KARONEI
 */
public class DoctorController implements Initializable {
    
   Connection conn = dbconnection.pharm_db();
   ResultSet rs;
   PreparedStatement pst;
   Statement st;
   Medcategory cat;

    @FXML
    private JFXTextField tfshelfNo;
    @FXML
    private JFXTextField tfCategory;
    @FXML
    private AnchorPane doctor;
    @FXML
    private JFXTextField tfcatId;
    @FXML
    private TableView<Medcategory> tableCategory;
    @FXML
    private TableColumn<Medcategory, Integer> colId;
    @FXML
    private TableColumn<Medcategory, String> colshlfNo;
    @FXML
    private TableColumn<Medcategory, String> colCategory;

       /**
        * Initializes the controller class.
        */
       @Override
       public void initialize(URL url, ResourceBundle rb) {
               showMedCategory();
       }       
    @FXML
    private void Close_Pane(MouseEvent event) throws IOException {
    Parent supplier = FXMLLoader.load(getClass().getResource("/UI/Category.fxml"));
    Scene supplierScene = new Scene(supplier);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(supplierScene);
    app_stage.close();
    }

    @FXML
    private void Delete_Category(ActionEvent event) {
     try {
          PreparedStatement pst = conn.prepareStatement("DELETE FROM category  WHERE id = '"+tfcatId.getText()+"'");
          pst.executeUpdate();
         alert.AlertMaker.showNotification("Success !","Successfully Delete Category",null);
          showMedCategory();
       } catch (SQLException ex) {
               alert.AlertMaker.showNotification("Failed !","Failed to delete category",null);
           System.out.println(ex);
       }    
    }

    @FXML
    private void Add_Category(ActionEvent event) {
        if (tfshelfNo.getText().equals("")) {
    alert.AlertMaker.showNotification("Failed !","Shelf Number Field is empty",null);
     tfshelfNo.requestFocus();
    } else if(tfCategory.getText().equals("")){
    alert.AlertMaker.showNotification("Failed !","Category Field is empty",null);
     tfCategory.requestFocus();
    }else{
     try {
          String q = "INSERT INTO `category`(`id`,`shelfNo`,`tfCategory`)"+ " VALUES (?,?,?)";
          pst = conn.prepareStatement(q);
          pst.setInt(1,0);
          pst.setString(2, tfshelfNo.getText());  
          pst.setString(3, tfCategory.getText()); 
          pst.execute();
          showMedCategory();
         alert.AlertMaker.showNotification("Success!","Succefully add new Category",null);  
        } catch (SQLException ex) {
         System.out.println(ex);
            }}   
    }
     public ObservableList<Medcategory> getcategory(){
      ObservableList<Medcategory> categoryList = FXCollections.observableArrayList(); 
      String query = "SELECT * FROM category ORDER BY id DESC";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          while(rs.next()){
              cat = new Medcategory(rs.getInt("Id"),rs.getString("tfCategory"),rs.getString("ShelfNo"));
             categoryList.add(cat);
             
          }
        
      }catch(SQLException ex){
        System.out.println(ex);
      }
      return categoryList;
    } 
     
     public void showMedCategory(){
     ObservableList<Medcategory> list =  getcategory();
     colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
     colCategory.setCellValueFactory(new PropertyValueFactory<>("DrugCategory"));
     colshlfNo.setCellValueFactory(new PropertyValueFactory<>("ShelfNo")); 
     tableCategory.setItems(list);
     
      }

    @FXML
    private void ShowDetails(MouseEvent event) {
         Medcategory cat = tableCategory.getSelectionModel().getSelectedItem();
         tfcatId.setText(""+cat.getId());
         tfCategory.setText(cat.getDrugCategory());
         tfshelfNo.setText(cat.getShelfNo());
    }

    @FXML
    private void UpdateCategory(ActionEvent event) {
        try{
      PreparedStatement pst = conn.prepareStatement("UPDATE category SET shelfNo = '"+tfshelfNo.getText()+"', tfCategory = '"+tfCategory.getText()+"' WHERE id = '"+tfcatId.getText()+"'");
            pst.executeUpdate();
             alert.AlertMaker.showNotification("Success !","Successfully Update Category",null);
               showMedCategory();
       }catch(Exception ex){
          System.out.println(ex);
       }
    }
}
