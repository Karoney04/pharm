/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerOparations;

import Database.dbconnection;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author DOMINIC
 */
public class LoginController implements Initializable {
    
   Connection conn = dbconnection.pharm_db();
   ResultSet rs;
   PreparedStatement pst;
   Statement st;
   
   
   @FXML
    private JFXPasswordField tfpasword;
    @FXML
    private JFXTextField tfusername;
    Stage dialogStage = new Stage();
    Scene scene;
    
    private Stage stage;
    private Parent root;
    
   
   

     
     
   @Override
    public void initialize(URL url, ResourceBundle rb) {   
      RequiredFieldValidator validatorusername = new RequiredFieldValidator();
      tfusername.getValidators().add(validatorusername);
      validatorusername.setMessage("Please Username");
      tfusername.focusedProperty().addListener(new ChangeListener<Boolean>() {
          @Override
          public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
              if(!newValue){
                  tfusername.validate();
              }
          }
      });
        RequiredFieldValidator validatorpassword = new RequiredFieldValidator();
      tfpasword.getValidators().add(validatorpassword);
      validatorpassword.setMessage("Please Your Password");
      tfpasword.focusedProperty().addListener(new ChangeListener<Boolean>() {
          @Override
          public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
              if(!newValue){
                  tfpasword.validate();
              }
          }
      });
    
              }    
    
   
    @FXML
    private void login(ActionEvent event) throws NoSuchAlgorithmException, IOException {
        String username = tfusername.getText();
        String pass = tfpasword.getText();
       
         try{
         String users = "SELECT `name`,`username`, `password` FROM `users`" ;
          st = conn.createStatement();
          rs = st.executeQuery(users);
          while(rs.next()){
         String Usersname = rs.getString(1);
         String Userpassword = rs.getString(2); 
         
          }
         String admin = "SELECT `name`,`username`, `password` FROM `admin`  " ;
         st = conn.createStatement();
         rs = st.executeQuery(admin);
          while(rs.next()){
         String Adminname = rs.getString(1);
         String Adminusername = rs.getString(2);
         String Adminpass = rs.getString(3);
             if(Adminusername.equals(username) && Adminpass.equals(Adminpass)){
          Node source = (Node) event.getSource();
          dialogStage = (Stage) source.getScene().getWindow();
          dialogStage.close();
          scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/HomePage.fxml")),1600,835);
          dialogStage.setScene(scene);
          dialogStage.show();
             }else{
          Node source = (Node) event.getSource();
          dialogStage = (Stage) source.getScene().getWindow();
          dialogStage.close();
          scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/pharmacy.fxml")),1600,835);
          
          dialogStage.setScene(scene);
          dialogStage.show();   
             }
          }
      }catch(SQLException ex){
        System.out.println("");
      }
        
        
        
        /*
        if("User".equals(UserRole)){
            
        try{
    
     String q = "SELECT * FROM `users` WHERE username = MD5(?) AND password = MD5(?) ";
          pst = conn.prepareStatement(q);
          pst.setString(1,  username);  
          pst.setString(2, pass); 
          ResultSet rs = pst.executeQuery();
          if(rs.next()){
          Node source = (Node) event.getSource();
          dialogStage = (Stage) source.getScene().getWindow();
          dialogStage.close();
          scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/pharmacy.fxml")),1600,835);
          
          dialogStage.setScene(scene);
          dialogStage.show();
          }
          else if(username.equals("") && pass.equals("")){
          infoBox("Please Enter Your Creditials", "Failed", null);      
          }else if(username.equals("")){
              infoBox("Please Enter Username","Failed",null);
              
          }else if(pass.equals("")){
              infoBox("Please Enter Password","Failed",null);
              
          }else if(UserRole.equals("")){
                  infoBox("Select User Role Before Login","Failed",null);
          }
            else{
           infoBox("Enter Correct Creditals", "Failed", null);           
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
     }else if("Admin".equals(UserRole)){
          
           try{
    
     String q = "SELECT * FROM `admin` WHERE username = MD5(?) AND password = MD5(?) ";
          pst = conn.prepareStatement(q);
          pst.setString(1,  username);  
          pst.setString(2, pass); 
          ResultSet rs = pst.executeQuery();
          if(rs.next()){
         // infoBox("Login Successfull", "Success",null);
          Node source = (Node) event.getSource();
          dialogStage = (Stage) source.getScene().getWindow();
          dialogStage.close();
          scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/HomePage.fxml")),1600,835);
     
          dialogStage.setScene(scene);
          dialogStage.show();
          }
          else if(username.equals("") && pass.equals("")){
           alert.AlertMaker.showNotification("Fail !","Enter Detail to login",null);       
          }else if(username.equals("")){
                alert.AlertMaker.showNotification("Fail !","Enter Username",null); 
              
          }else if(pass.equals("")){
           alert.AlertMaker.showNotification("Fail !","Enter password",null); 
              
          }else if(UserRole.isEmpty()){
              alert.AlertMaker.showNotification("Fail !","Select User Role Before Login",null); 
          }
            else{ 
           alert.AlertMaker.showNotification("Fail !","Enter Correct Creditals",null); 
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }  
      }else if(UserRole==null){
             alert.AlertMaker.showNotification("Fail !","To Login Select User",null);
          } 
       */
    }
    @FXML
    private void restpassword(MouseEvent event) {
  try {
         Parent parent = FXMLLoader.load(getClass().getResource("/UI/RestPassword.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // to validate and allow the user to input only the character value
    @FXML
  private  void usernamevalidator(KeyEvent event) {
        tfusername.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
            tfusername.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
    }
  // to validate and allow user to input only integer values
      @FXML
    void passwordvalidat(KeyEvent event) {

    }
   
    //to limit the maximum character in textfield input to accept 4 digit numbers only
    void acceptdigitstring(){
      final int maxLength = 10;

         tfpasword.setOnKeyTyped(t -> {

            if ( tfpasword.getText().length() > maxLength) {
                int pos =  tfpasword.getCaretPosition();
                tfpasword.setText( tfpasword.getText(0, maxLength));
                tfpasword.positionCaret(pos); 
            }

        });
    }

    @FXML
    private void SignUp(MouseEvent event) {
      System.exit(0);
    }
   public static void infoBox(String infoMessage, String titleBar, String headerMessage)
{
       Alert alert = new Alert(AlertType.INFORMATION);
       alert.setTitle(titleBar);
       alert.setHeaderText(headerMessage);
       alert.setContentText(infoMessage);
       alert.showAndWait();
}

  

 

    @FXML
    private void Signup(MouseEvent event) {
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

}
