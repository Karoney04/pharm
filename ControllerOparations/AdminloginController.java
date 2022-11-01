/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerOparations;

import Database.dbconnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DOMINIC
 */
public class AdminloginController implements Initializable {

   Connection conn = dbconnection.pharm_db();
   ResultSet rs;
   PreparedStatement pst;
   Statement st;
    
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton signin;
    @FXML
    private JFXButton user;
    
    Stage dialogStage = new Stage();
    Scene scene;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void LoginAdmin(ActionEvent event) throws IOException {
       String usernam = username.getText();
        String pass = password.getText();
    Parent pharmac = FXMLLoader.load(getClass().getResource("/UI/Login.fxml"));
    Scene pharmacScene = new Scene(pharmac);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(pharmacScene);
    app_stage.show(); 
          
    }
     public static void infoBox(String infoMessage, String titleBar, String headerMessage)
{
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle(titleBar);
       alert.setHeaderText(headerMessage);
       alert.setContentText(infoMessage);
       alert.showAndWait();
}

    @FXML
    private void BackLogin(ActionEvent event) throws IOException {
    Parent supplier = FXMLLoader.load(getClass().getResource("/UI/RegisterUsers.fxml"));
    Scene supplierScene = new Scene(supplier);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(supplierScene);
    app_stage.close();
    }
    
}
