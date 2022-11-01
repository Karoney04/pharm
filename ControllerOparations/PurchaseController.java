/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerOparations;

import Database.dbconnection;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.stage.Stage;
import model.*;
import alert.AlertMaker;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javafx.scene.control.Button;


/**
 * FXML Controller class
 *
 * @author DOMINIC
 */
public class PurchaseController implements Initializable {
    

   Connection conn = dbconnection.pharm_db();
   ResultSet rs;
   PreparedStatement pst;
   Statement st;

    @FXML
    private TableView<Outofstock> orderList;
    @FXML
    private TableColumn<Outofstock, String> colmedName;
    @FXML
    private TableColumn<Outofstock, Integer> colmedqty;
    @FXML
    private TableColumn<Outofstock, String> colmedCat;
    @FXML
    private JFXTextArea DisplayOrder;
    @FXML
    private JFXTextField medName;
    @FXML
    private JFXTextField medQty;
    @FXML
    private JFXTextField ftrem_med;

    /**
     * Initializes the controller class.
     */
    
     int min = 0000000000;  
     int max = 1000000000; 
        
  //   Random rnd = new Random();
     int receiptNo =(int)(Math.random()*(max-min+1)+min);
    @FXML
    private Button btnadd;
    @FXML
    private Button btnprint;
    @FXML
    private Button btndelete;
    
   
     ArrayList<String[]> data = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
     SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
     showExpiredSoonStock();
       DisplayOrder.setText("\t\t\t\t\t  SHAVIN PHARMACY ORDER LIST          \n\n\n"
                   + "Date: "+dateFormat.format(new Date())
                   +"\t\t\t\t\t\t\tOrder No: "+  receiptNo 
                   +"\n------------------------------------------------------------------------------------"
           +"\nMedicine Name"+"\t\t\t\t\t\t\tMedicine Quantity"); 
             DisplayOrder.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick; -fx-font-size: 14px;");
         DisplayOrder.setVisible(false);
          btnadd.setDisable(true);btndelete.setDisable(true);btnprint.setDisable(true);
    }  
    
public ObservableList<Outofstock> getOutofStock(){
         //select distinct id,DrugName,DrugQuantity,category,ShelfNo,price from stock
      ObservableList<Outofstock> OutStockList = FXCollections.observableArrayList(); 
      String query = "select menName,SUM(medQty)medQty,medCat FROM  stockexpire GROUP BY menName";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          Outofstock stck;
          while(rs.next()){
              stck = new Outofstock(rs.getString("menName"),rs.getInt("medQty"),rs.getString("medCat"));
              OutStockList.add(stck);
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
      return OutStockList;
    }
    public void showExpiredSoonStock(){
     ObservableList<Outofstock>  ExpSoonList =  getOutofStock();
     colmedName.setCellValueFactory(new PropertyValueFactory<>("menName"));
     colmedqty.setCellValueFactory(new PropertyValueFactory<>("medQty"));
     colmedCat.setCellValueFactory(new PropertyValueFactory<>("medCat"));
     orderList.setItems(ExpSoonList);
    }

    @FXML
    private void makeorderlist(MouseEvent event) {
      Outofstock stck = orderList.getSelectionModel().getSelectedItem();
       medName.setText(stck.getMenName());remainMed();
       btnadd.setDisable(false);btndelete.setDisable(false);btnprint.setDisable(false);
    }
 void recipt(){
     String[] singleData = new String[2];
     singleData[0] = medName.getText();
     singleData[1] = medQty.getText();
     data.add(singleData);
     
      DisplayOrder.appendText("  \n\n" +  " " + medName.getText()
              +"\t\t\t\t\t\t\t\t\t\t"+medQty.getText());   
    }
    @FXML
    private void AddOrderList(ActionEvent event) {
           if(medQty.getText().trim().isEmpty()){
        AlertMaker.showNotification("Empty Medicine Quantity Order","Please Insert The Value",null);
    } else {
          recipt();remainMed();showExpiredSoonStock(); deletemed();
          AlertMaker.showNotification("Success","Succefully Added to the List",null);
          btnadd.setDisable(true);DisplayOrder.setVisible(true);
          clearfields();
    }
    }

    @FXML
    private void PrintorderList(ActionEvent event) throws FileNotFoundException, DocumentException, IOException { 
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       String pdfnane = ""+receiptNo+".pdf";
       Document document = new Document();
      //Create OutputStream instance.
	OutputStream outputStream = 
	    new FileOutputStream(new File("C:\\Users\\domin\\Documents\\purchaseOrderlist\\"+pdfnane+""));/*here you create the folder and name purchaseOrderlist and copy the folderpath here*/
        
        //Create PDFWriter instance.
        PdfWriter.getInstance(document, outputStream);
 
        //Open the document.
        document.open();
 
        //Create Table object, Here 4 specify the no. of columns
         PdfPTable pdfPTable2 = new PdfPTable(1);
         PdfPCell pdfPC00= new PdfPCell(new Paragraph("                                    DOMIC PHARMACY ORDER LIST"));//change the name of the heading the way you want 
           
        pdfPC00.setBorder(Rectangle.NO_BORDER);
        pdfPTable2.addCell(pdfPC00);
        
        
        PdfPTable pdfPTable3 = new PdfPTable(1);
        PdfPCell pdfPC002 = new PdfPCell(new Paragraph("                                                    "));
        pdfPC002.setBorder(Rectangle.NO_BORDER);
        pdfPTable3.addCell(pdfPC002);
        
        PdfPTable pdfPTable4 = new PdfPTable(1);
        PdfPCell pdfPC003 = new PdfPCell(new Paragraph("                                                     "));
        pdfPC003.setBorder(Rectangle.NO_BORDER);
        pdfPTable3.addCell(pdfPC003);
        
        PdfPTable pdfPTable5 = new PdfPTable(1);
        PdfPCell pdfPC3 = new PdfPCell(new Paragraph("Date:"+dateFormat.format(new Date())));
        PdfPCell pdfPC4= new PdfPCell(new Paragraph("Order receipt No. : "+receiptNo));
        pdfPC4.setBorder(Rectangle.NO_BORDER);
        pdfPC3.setBorder(Rectangle.NO_BORDER);
        pdfPTable5.addCell(pdfPC3);
        pdfPTable5.addCell(pdfPC4);
        
        PdfPTable pdfPTable6 = new PdfPTable(1);
        PdfPCell pdfPC006 = new PdfPCell(new Paragraph("                                                     "));
        pdfPC006.setBorder(Rectangle.NO_BORDER);
        pdfPTable6.addCell(pdfPC006);
        
        
        PdfPTable pdfPTable = new PdfPTable(2);
        PdfPCell pdfPC1 = new PdfPCell(new Paragraph("Medicine Name"));
        PdfPCell pdfPC2= new PdfPCell(new Paragraph("Drug Quality Order"));
        pdfPTable.addCell(pdfPC1);
        pdfPTable.addCell(pdfPC2);
     
 
        for(int i=0;i<data.size();i++){
            
        
        //Create cells
        PdfPCell pdfPCell1 = new PdfPCell(new Paragraph(data.get(i)[0]));
        PdfPCell pdfPCell2 = new PdfPCell(new Paragraph(data.get(i)[1]));
     
 
        //Add cells to table
        pdfPTable.addCell(pdfPCell1);
        pdfPTable.addCell(pdfPCell2);
        
 
        //Add content to the document using Table objects.
        }
        document.add(pdfPTable2);document.add(pdfPTable3);document.add(pdfPTable4);document.add(pdfPTable5);document.add(pdfPTable6);
        document.add(pdfPTable);
        
         AlertMaker.showNotification("Success","Succesfully print the order list",null);
         
        //Close document and outputStream.
        document.close();
        outputStream.close();
 
     
     
    }

    @FXML
    private void exitPage(MouseEvent event) throws IOException {
    Parent supplier = FXMLLoader.load(getClass().getResource("/UI/Purchase.fxml"));
    Scene supplierScene = new Scene(supplier);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(supplierScene);
    app_stage.close();
    }

    @FXML
    private void deleteMet(ActionEvent event) {
             if( medName.getText().trim().isEmpty()){
        AlertMaker.showNotification("Fail","Select the Medicine to remove from list",null);
    } else {
            deletemed();showExpiredSoonStock();
              AlertMaker.showNotification("Success !","Successfully deleted",null);
             }
    }
    
    void deletemed(){
          try {
          PreparedStatement pst = conn.prepareStatement("DELETE FROM stockexpire  WHERE menName = '"+medName.getText()+"'");
          pst.executeUpdate();
          showExpiredSoonStock();
       } catch (SQLException ex) {
           System.out.println(ex);
       }   
    }
    void remainMed(){
            try{
         String query = "SELECT SUM(DrugQuantity)DrugQuantity FROM stockmedicine WHERE DrugName = '"+medName.getText()+"'GROUP BY DrugName" ;
          st = conn.createStatement();
          rs = st.executeQuery(query);
          while(rs.next()){
          ftrem_med.setText(rs.getString(1));
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
}
    void clearfields(){
      medName.setText("");ftrem_med.setText("");medQty.setText("");
    }
}
