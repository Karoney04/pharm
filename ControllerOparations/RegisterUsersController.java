/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerOparations;

import Database.dbconnection;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Users;

/**
 * FXML Controller class
 *
 * @author DOMINIC
 */
public class RegisterUsersController implements Initializable {

   Connection conn = dbconnection.pharm_db();
   ResultSet rs;
   PreparedStatement pst;
   Statement st;
   
    
    @FXML
    private JFXTextField tfname;
    @FXML
    private JFXTextField tfusername;
    @FXML
    private JFXPasswordField tfpassword;
    @FXML
    private JFXPasswordField tfpassword2;
    @FXML
    private ComboBox<String> SelectRole;
    ObservableList<String> SelectRoleuser = FXCollections.observableArrayList("User","Admin");
    @FXML
    private TableView<Users> tableusers;
    @FXML
    private TableColumn<Users, String> coluser;
 
    /**
     * Initializes the controller class.
     */

    
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SelectRole.setItems(SelectRoleuser);showuser();
    }    

    @FXML
    private void BackLogin(ActionEvent event) throws IOException {  
    Parent supplier = FXMLLoader.load(getClass().getResource("/UI/RegisterUsers.fxml"));
    Scene supplierScene = new Scene(supplier);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(supplierScene);
    app_stage.close();
  
    }
public ObservableList<Users> getOutofStock(){
         //select distinct id,DrugName,DrugQuantity,category,ShelfNo,price from stock
      ObservableList<Users> OutStockList = FXCollections.observableArrayList(); 
      String query = "select name FROM  users";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          Users stck;
          while(rs.next()){
              stck = new Users(rs.getString("name"));
              OutStockList.add(stck);
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
      return OutStockList;
    }
  public void showuser(){
     ObservableList<Users>  ExpSoonList =  getOutofStock();
     coluser.setCellValueFactory(new PropertyValueFactory<>("name"));
     tableusers.setItems(ExpSoonList);
    }

    @FXML
    private void Register(ActionEvent event) throws NoSuchAlgorithmException {
        String UserRole = SelectRole.getValue();
        if(tfname.getText().isEmpty()){
         alert.AlertMaker.showNotification("Fail !","Empty field",null);  
         tfname.requestFocus();
        }
         else if(tfusername.getText().isEmpty()){
          alert.AlertMaker.showNotification("Fail !","Empty field",null);  
       tfusername.requestFocus();       
        }else if(tfpassword.getText().isEmpty()){
             alert.AlertMaker.showNotification("Fail !","Empty field",null);  
       tfpassword.requestFocus();  
        }else if(!(tfpassword.getText().matches(tfpassword2.getText()))){
         alert.AlertMaker.showNotification("Fail !","Password does not Match !!",null);  
       tfpassword.requestFocus();  tfpassword2.requestFocus();     
        
    }else if(UserRole=="User"){
        Userregitered();  showuser();
        
    }else if(UserRole=="Admin"){
        AdminReg();
    }else{
        alert.AlertMaker.showNotification("Failed!","Not Registed",null);
    }
    }
void Userregitered(){
    
        String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
        for (int i=0; i < tfpassword.getText().length() ; i++)
        {
            char ch = tfpassword.getText().charAt(i);
            if(specialCharactersString.contains(Character.toString(ch))) {
          try {
          String q = "INSERT INTO `users`(`id`,`name`,`username`,`password`)"+ " VALUES (?,?,?,?)";
          pst = conn.prepareStatement(q);
          pst.setInt(1,0);
          pst.setString(2, tfname.getText());
          pst.setString(3, tfusername.getText());  
          pst.setString(4, tfpassword.getText()); 
          pst.execute();
         alert.AlertMaker.showNotification("Success!","You have Succefuly registered new user",null);
         clearfield();
        } catch (SQLException ex) {
               alert.AlertMaker.showNotification("Failed!","Choose different Username",null);
            } 
                break;
            }    
            else if(i == tfpassword.getText().length()-1)     
               alert.AlertMaker.showNotification("Failed!","Insert Special Characters like"+specialCharactersString,null);
        }
    
    
   
}
void AdminReg(){
          String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
        for (int i=0; i < tfpassword.getText().length() ; i++)
        {
            char ch = tfpassword.getText().charAt(i);
            if(specialCharactersString.contains(Character.toString(ch))) {
          try {
           String q1 = "INSERT INTO `admin`(`name`,`username`,`password`)"+ " VALUES (?,?,?)";
          pst = conn.prepareStatement(q1);
          pst.setString(1, tfname.getText());
          pst.setString(2, tfusername.getText());  
          pst.setString(3, tfpassword.getText()); 
          pst.execute();
         alert.AlertMaker.showNotification("Success!","You have Succefuly registered new user",null);
         clearfield();
        } catch (SQLException ex) {
               alert.AlertMaker.showNotification("Failed!","Choose different Username",null);
            } 
                break;
            }    
            else if(i == tfpassword.getText().length()-1)     
               alert.AlertMaker.showNotification("Failed!","Insert Special Characters like"+specialCharactersString,null);
        }
}
    
    @FXML
    private void vlname(KeyEvent event) {
            tfname.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
         tfname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
                       });
    }

    @FXML
    private void name(KeyEvent event) {
    }
public void clearfield(){
       tfname.setText("");tfusername.setText("");tfpassword.setText("");tfpassword2.setText("");
}

    @FXML
    private void display(MouseEvent event) {
        Users stck = tableusers.getSelectionModel().getSelectedItem();
       tfname.setText(stck.getName());
    }

    @FXML
    private void RemoveUser(ActionEvent event) {
          try {
         String sql= "DELETE FROM `users` WHERE name = '"+tfname.getText()+"'";
                pst=conn.prepareStatement(sql);
                pst.execute();
                alert.AlertMaker.showNotification("Success!","You have Succefuly remove user",null);
               showuser(); clearfield();
       } catch (SQLException ex) {
           System.out.println(ex);
       }
    }
}

