/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerOparations;

import Database.dbconnection;
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
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Patient;


/**
 * FXML Controller class
 *
 * @author KARONEI
 */
public class PatientReportController implements Initializable {
    
   Connection conn = dbconnection.pharm_db();
   ResultSet rs;
   PreparedStatement pst;
   Statement st;
   Patient pnt;
   
  
    
    @FXML
    private TableView<Patient> tablePatient;
   @FXML
    private TableColumn<Patient, Integer> colRcptNo;
    @FXML
    private TableColumn<Patient, Integer> colpntNo;
    @FXML
    private TableColumn<Patient, Integer> colpntPhoneNo;
    @FXML
    private TableColumn<Patient, String> colpntName;
    @FXML
    private TableColumn<Patient, String> colpntTrmntDate;
    @FXML
    private TableColumn<Patient, String> colpntCpmln;
    @FXML
    private TableColumn<Patient, String> colpntDrug;
    @FXML
    private TableColumn<Patient, Integer> colTotalCost;
    @FXML
    private TableColumn<Patient, String> colStatus;
    @FXML
    private JFXDatePicker DateFrom;
    @FXML
    private JFXDatePicker DateTo;
    @FXML
    private Label lblTotalPatient;
    @FXML
    private LineChart<String, Integer> LinegraphPatient;
    @FXML
    private JFXTextField tfpnt;
    @FXML
    private Pane ViewAllDetails;
    @FXML
    private Button btnSearchDetails1;
    @FXML
    private Button btnSearchDetails;
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Calendar cal = Calendar.getInstance();
    Calendar cal1 = Calendar.getInstance();
     Calendar cal3 = Calendar.getInstance();
    @FXML
    private AreaChart<String, Integer> testpatient;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
             LinegraphPatient.setVisible(false);
     showPatientList();TotalPatientGraph();Count_Patients(); 
       weeklyPatientVisit();
       } catch (SQLException ex) {
           Logger.getLogger(PatientReportController.class.getName()).log(Level.SEVERE, null, ex);
       }
         
    }    
void  weeklyPatientVisit() throws SQLException{
  
     XYChart.Series weeklyVisit = new XYChart.Series();  
       weeklyVisit.setName("Patient Visit"); 
            String query3 = "SELECT pntFTreatment, COUNT(RecptNo)RecptNo FROM patient WHERE pntFTreatment >= DATE(adddate(curdate(), interval -7 day)) and pntFTreatment <= DATE(curdate()) GROUP BY pntFTreatment";
           st = conn.createStatement();
           rs = st.executeQuery(query3);
            while(rs.next()){
               cal3.add(Calendar.DATE, -7);
               int rcptNo3 = rs.getInt("RecptNo");
               String date = rs.getString("pntFTreatment");
            weeklyVisit.getData().add(new XYChart.Data<>(""+date, rcptNo3)); 
          }
        testpatient.getData().add(weeklyVisit);
}
   @FXML
    private void CloseScene(MouseEvent event) throws IOException {
    Parent supplier = FXMLLoader.load(getClass().getResource("/UI/PatientReport.fxml"));
    Scene supplierScene = new Scene(supplier);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(supplierScene);
    app_stage.close();
    }

  public ObservableList<Patient> getpatientList(){
        
      ObservableList<Patient> patientlist = FXCollections.observableArrayList(); 
      String query = "select RecptNo,pntNo,pntName,pntPhoneNo,pntFTreatment,pntComplain,DrugAdmistr,totalcost,paimentStatus from patient GROUP BY RecptNo";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          Patient pntlst;
          while(rs.next()){
              pntlst = new Patient(rs.getString("RecptNo"),rs.getString("pntNo"),rs.getString("pntName"),rs.getInt("pntPhoneNo"),rs.getString("pntFTreatment"),rs.getString("pntComplain"),rs.getString("DrugAdmistr"),rs.getInt("totalcost"),rs.getString("paimentStatus"));
             patientlist.add(pntlst);
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
      return  patientlist;
  }
    
      public void showPatientList(){
     ObservableList<Patient> pantientlist =  getpatientList();
     colRcptNo.setCellValueFactory(new PropertyValueFactory<>("RecptNo"));
     colpntNo.setCellValueFactory(new PropertyValueFactory<>("pntNo"));
     colpntPhoneNo.setCellValueFactory(new PropertyValueFactory<>("pntPhoneNo"));
     colpntName.setCellValueFactory(new PropertyValueFactory<>("pntName"));
     colpntTrmntDate.setCellValueFactory(new PropertyValueFactory<>("pntFTreatment"));
     colpntCpmln.setCellValueFactory(new PropertyValueFactory<>("pntComplain"));
     colpntDrug.setCellValueFactory(new PropertyValueFactory<>("DrugAdmistr"));
     colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalcost"));
     colStatus.setCellValueFactory(new PropertyValueFactory<>("paimentStatus")); 
     tablePatient.setItems(pantientlist);
    
   
     colpntCpmln.setVisible(false);
     colpntDrug.setVisible(false);
     
    FilteredList<Patient> filterPatient = new FilteredList<>(pantientlist, b -> true); 
    tfpnt.textProperty().addListener((ObservableValue<? extends String> observable,String oldValue, String newValue)->{
    filterPatient.setPredicate((Patient patient) ->   {
   if(newValue.isEmpty()){
       return true;
   }
   String searchKeyword = newValue.toLowerCase();
        return patient.getRecptNo().toLowerCase().contains(searchKeyword);
       
    });
});
    SortedList<Patient> sortedpantient = new SortedList<>(filterPatient);
    sortedpantient.comparatorProperty().bind(tablePatient.comparatorProperty());
    tablePatient.setItems(sortedpantient);
     
    }
 public ObservableList<Patient> getpatientDetails(){
      ObservableList<Patient> patientDetails = FXCollections.observableArrayList(); 
      String query = "select RecptNo,pntNo,pntName,pntPhoneNo,pntFTreatment,pntComplain,DrugAdmistr,totalcost,paimentStatus from patient WHERE RecptNo='"+ tfpnt.getText()+"' OR pntNo='"+ tfpnt.getText()+"'";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          Patient pntlst;
          while(rs.next()){
              pntlst = new Patient(rs.getString("RecptNo"),rs.getString("pntNo"),rs.getString("pntName"),rs.getInt("pntPhoneNo"),rs.getString("pntFTreatment"),rs.getString("pntComplain"),rs.getString("DrugAdmistr"),rs.getInt("totalcost"),rs.getString("paimentStatus"));
             patientDetails.add(pntlst);
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
       return patientDetails;
  
}
 
  public ObservableList<Patient> getpatientDetailsByDates(){
      ObservableList<Patient> patientDetails = FXCollections.observableArrayList(); 
      String query = "select RecptNo,pntNo,pntName,pntPhoneNo,pntFTreatment,pntComplain,DrugAdmistr,totalcost,paimentStatus from patient WHERE pntFTreatment BETWEEN '"+DateFrom.getValue()+"' AND '"+DateTo.getValue()+"'";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          Patient pntlst;
          while(rs.next()){
              pntlst = new Patient(rs.getString("RecptNo"),rs.getString("pntNo"),rs.getString("pntName"),rs.getInt("pntPhoneNo"),rs.getString("pntFTreatment"),rs.getString("pntComplain"),rs.getString("DrugAdmistr"),rs.getInt("totalcost"),rs.getString("paimentStatus"));
             patientDetails.add(pntlst);
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
       return patientDetails;
  
}
 
  void pantientDetailsByDates(){
     ObservableList<Patient> pantientdtails = getpatientDetailsByDates();
     colRcptNo.setCellValueFactory(new PropertyValueFactory<>("RecptNo"));
     colpntNo.setCellValueFactory(new PropertyValueFactory<>("pntNo"));
     colpntPhoneNo.setCellValueFactory(new PropertyValueFactory<>("pntPhoneNo"));
     colpntName.setCellValueFactory(new PropertyValueFactory<>("pntName"));
     colpntTrmntDate.setCellValueFactory(new PropertyValueFactory<>("pntFTreatment"));
     colpntCpmln.setCellValueFactory(new PropertyValueFactory<>("pntComplain"));
     colpntDrug.setCellValueFactory(new PropertyValueFactory<>("DrugAdmistr"));
     colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalcost"));
     colStatus.setCellValueFactory(new PropertyValueFactory<>("paimentStatus")); 
     colpntCpmln.setVisible(true);
     colpntDrug.setVisible(true);
     tablePatient.setItems(pantientdtails );
 }
  
 void pantientDetails(){
     ObservableList<Patient> pantientdtails = getpatientDetails();
     colRcptNo.setCellValueFactory(new PropertyValueFactory<>("RecptNo"));
     colpntNo.setCellValueFactory(new PropertyValueFactory<>("pntNo"));
     colpntPhoneNo.setCellValueFactory(new PropertyValueFactory<>("pntPhoneNo"));
     colpntName.setCellValueFactory(new PropertyValueFactory<>("pntName"));
     colpntTrmntDate.setCellValueFactory(new PropertyValueFactory<>("pntFTreatment"));
     colpntCpmln.setCellValueFactory(new PropertyValueFactory<>("pntComplain"));
     colpntDrug.setCellValueFactory(new PropertyValueFactory<>("DrugAdmistr"));
     colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalcost"));
     colStatus.setCellValueFactory(new PropertyValueFactory<>("paimentStatus")); 
     colpntCpmln.setVisible(true);
     colpntDrug.setVisible(true);
     tablePatient.setItems(pantientdtails );
 }
     private void Total_patient(){
      try{
         String query = ("SELECT COUNT(*) AS TotalPatients FROM patient WHERE pntFTreatment BETWEEN '"+DateFrom.getValue()+"' AND '"+DateTo.getValue()+"' ");
          st = conn.createStatement();
          rs = st.executeQuery(query);
          Patient pntRcord ;
          while(rs.next()){
          lblTotalPatient.setText(rs.getString(1));
          }
      }catch(SQLException ex){
        System.out.println(ex);  
      }
}
    private void TotalPatientGraph(){
       try {
         //  XYChart.Series series = new XYChart.Series<>();
           XYChart.Series<String, Integer> numberofPatient = new XYChart.Series<>();
          numberofPatient.setName("Patients Visit As Per Date");
           String query3 = "SELECT pntFTreatment, COUNT(RecptNo)RecptNo FROM patient WHERE pntFTreatment >= DATE(adddate(curdate(), interval -7 day)) and pntFTreatment <= DATE(curdate()) GROUP BY pntFTreatment";
           st = conn.createStatement();
           rs = st.executeQuery(query3);
           
         
           while(rs.next()){
               cal3.add(Calendar.DATE, -7);
               int rcptNo3 = rs.getInt("RecptNo");
               String date = rs.getString("pntFTreatment");
            numberofPatient.getData().add(new XYChart.Data<>(""+date, rcptNo3));     
           }
           LinegraphPatient.getData().add(numberofPatient);
       }catch (SQLException ex) {
           Logger.getLogger(PatientReportController.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
    
     private void TotalPatientGraphByDates(){
       try {
         //  XYChart.Series series = new XYChart.Series<>();
           XYChart.Series<String, Integer> numberofPatient = new XYChart.Series<>();
        //  numberofPatient.setName("Patients Visit As Per Date");
           String query3 = "SELECT pntFTreatment, COUNT(RecptNo)RecptNo FROM patient WHERE pntFTreatment BETWEEN '"+DateFrom.getValue()+"' AND '"+DateTo.getValue()+"' GROUP BY pntFTreatment";
           st = conn.createStatement();
           rs = st.executeQuery(query3);
           
         
           while(rs.next()){
               cal3.add(Calendar.DATE, -7);
               int rcptNo3 = rs.getInt("RecptNo");
               String date = rs.getString("pntFTreatment");
            numberofPatient.getData().add(new XYChart.Data<>(""+date, rcptNo3));     
           }
           testpatient.getData().add(numberofPatient);
       }catch (SQLException ex) {
           Logger.getLogger(PatientReportController.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
    
     private void Count_Patients(){
         try{
         String query = "SELECT  COUNT(*)RecptNo FROM patient WHERE pntFTreatment >= DATE(adddate(curdate(), interval -7 day)) and pntFTreatment <= DATE(curdate())";
          st = conn.createStatement();
          rs = st.executeQuery(query);
          while(rs.next()){
        lblTotalPatient.setText(rs.getString(1));
          }
      }catch(SQLException ex){
        System.out.println(ex);  
      } 
    }
       private void Count_PatientsByDates(){
         try{
         String query = "SELECT  COUNT(*)RecptNo FROM patient WHERE pntFTreatment BETWEEN '"+DateFrom.getValue()+"' AND '"+DateTo.getValue()+"'";
          st = conn.createStatement();
          rs = st.executeQuery(query);
          while(rs.next()){
        lblTotalPatient.setText(rs.getString(1));
          }
      }catch(SQLException ex){
        System.out.println(ex);  
      } 
    }
    @FXML
    private void ViewAllDetails(ActionEvent event) { 
       pantientDetails();
    }
    @FXML
    private void DisplayPantientNo(MouseEvent event) {
         Patient stck = tablePatient.getSelectionModel().getSelectedItem();
        tfpnt.setText(""+stck.getPntNo());
    }  
    @FXML
    private void SearchByDates(ActionEvent event) throws SQLException {
        pantientDetailsByDates();TotalPatientGraphByDates();Count_PatientsByDates(); weeklyPatientVisit();
    }
    @FXML
    private void RefreshTable(MouseEvent event) throws SQLException {
         showPatientList();TotalPatientGraph();Count_Patients(); weeklyPatientVisit();
    }

   @FXML
    private void validatePnt(KeyEvent event) {
                  tfpnt.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
         tfpnt.setText(newValue.replaceAll("[^\\d]", ""));
        }
    });
    }
  
}
