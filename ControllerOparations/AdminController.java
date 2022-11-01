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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DOMINIC
 */
public class AdminController implements Initializable {
   Connection conn = dbconnection.pharm_db();
   ResultSet rs;
   PreparedStatement pst;
   Statement st;
   
    @FXML
    private JFXTextField tfusername;
    @FXML
    private JFXTextField tfpassword;
    /**
     * Initializes the controller class.
     */
    Stage dialogStage = new Stage();
    Scene scene;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void admin(ActionEvent event) throws IOException {
        String username = tfusername.getText();
        String pass = tfpassword.getText();
   try{
      
     String q = "SELECT * FROM `admin` WHERE username = MD5(?) AND password = MD5(?) ";
          pst = conn.prepareStatement(q);
          pst.setString(1,  username);  
          pst.setString(2, pass); 
          ResultSet rs = pst.executeQuery();
          if(rs.next()){
          infoBox("Login Successfull", "Success",null);
          Node source = (Node) event.getSource();
          dialogStage = (Stage) source.getScene().getWindow();
          dialogStage.close();
          scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/HomePage.fxml")));
          dialogStage.setScene(scene);
          dialogStage.show();
          }
          else if(username.equals("") && pass.equals("")){
          infoBox("Please Enter Your Creditials", "Failed", null);      
          }else if(username.equals("")){
              infoBox("Please Enter Username","Failed",null);
              
          }else if(pass.equals("")){
              infoBox("Please Enter Password","Failed",null);
              
          }
            else{
           infoBox("Enter Correct Creditals", "Failed", null);           
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
 
    }
    public static void infoBox(String infoMessage, String titleBar, String headerMessage)
{
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle(titleBar);
       alert.setHeaderText(headerMessage);
       alert.setContentText(infoMessage);
       alert.showAndWait();
}
 
}
