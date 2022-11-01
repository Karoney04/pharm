/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerOparations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import Database.dbconnection;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class
 *
 * @author KARONEI
 */
public class PharmacyController implements Initializable {
    
   Connection conn = dbconnection.pharm_db();
   ResultSet rs;
   PreparedStatement pst;
   Statement st;
   pharmacy pharm;
   ListMedicine listmed;
 
   
  
    @FXML
    private BorderPane borderPane2;
    @FXML
    private TableView<pharmacy> Tablemed;
    @FXML
    private TableColumn<pharmacy, String> colMdName;
    @FXML
    private TableColumn<pharmacy, String> colLocation;
    @FXML
    private TableColumn<pharmacy, String> colExpDate;
    @FXML
    private TableColumn<pharmacy, Integer> colPrice;
    @FXML
    private TableColumn<pharmacy,Integer> colBatch;
    @FXML
    private JFXTextField tfTotalPaidAmount;
    @FXML
    private JFXTextField tfUnpaidAmount;
    @FXML
    private JFXTextField tfDate;
    private JFXTextField pntName;
    private JFXTextField pntGender;
    private JFXTextField pntAddress;
    private JFXTextField pntAge;
    @FXML
    private JFXTextField tfpntNo;
    @FXML
    private JFXTextField tfpntName;
    @FXML
    private JFXTextField tfpntPhoneNo;
    @FXML
    private JFXTextArea pntComplain;   
    @FXML
    private JFXTextField tfpharmMedicine;
    private JFXTextField pntregNo;
    @FXML
    private JFXTextField MadicineName;
    @FXML
    private JFXTextField pricemedicine;
    @FXML
    private Spinner<Integer> SpnnerMedicineQuality;
    final int initialValue = 0;
    SpinnerValueFactory<Integer> Qlty = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1, initialValue);
    @FXML
    private JFXTextField tfTotal;
    @FXML
    private JFXComboBox<String> paymentMethod;
    ObservableList<String> paymentmethod = FXCollections.observableArrayList("M-pesa","Bank Deposit","Cash Deposit","PayPal");
    @FXML
    private JFXComboBox<String> PaymentStatus;
    ObservableList<String> paymentStutas = FXCollections.observableArrayList("Paid","UnPaid");
    @FXML
    private JFXTextArea receipt;
    @FXML
    private TableView<ListMedicine> tableListItems;
    @FXML
    private TableColumn<ListMedicine, String> colMedName;
    @FXML
    private TableColumn<ListMedicine, Integer> colTotal;
    @FXML
    private Label cur_date;
    @FXML
    private Label paidAmount;
    @FXML
    private Label UnpaidAmount;
    @FXML
    private Button btnList;
    @FXML
    private JFXTextField MedBatchNo;
   
    
   
     int min = 0000000000;  
     int max = 1000000000; 
       
     int receiptNo =(int)(Math.random()*(max-min+1)+min);
     
     ArrayList<String[]> data = new ArrayList<>();
     ArrayList<Integer[]> data1 = new ArrayList<>();
    @FXML
    private Text timedisplay;
    private volatile boolean stop = false;
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane pane2;
    @FXML
    private AnchorPane panemain1;
    
       @Override
       public void initialize(URL url, ResourceBundle rb) {
      
           
           showMedicine();showMedicineList(); current_Date(); paidAmountperday();UnpaidAmountperday();Timenow();acceptdigitstring();
            
           receipt.setText("\t\t\t\t SHAVIN PHARMACY           \n\n"
                   + "Date: "+cur_date.getText()
                   +"\t\t\t\tReceipt No: "+  receiptNo 
                   +"\n--------------------------------------------------------------------------------"
           +"\nMedicine Name"+"\t\t\tQty"+"\t\t\t\tAmount"); 
           receipt.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick; -fx-font-size: 12px;");
           SpnnerMedicineQuality.setValueFactory(Qlty);   
           paymentMethod.setItems(paymentmethod);
           PaymentStatus.setItems(paymentStutas );
           receipt.setVisible(false);
           
         PaymentStatus.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: yellow ; -fx-font-size: 14px; fx-font-weight:bold;");
           
       } 
 private void Timenow(){
    Thread thread = new Thread(() -> {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        while(!stop){
            try{
                Thread.sleep(1000);
            }catch(Exception e){
                System.out.println(e);
            }
            final String timenow = sdf.format(new Date());
            Platform.runLater(() -> {
                 timedisplay.setText(timenow); // This is the label
            });
        }
    });
    thread.start();
}    
    @FXML
    private void home(ActionEvent event) throws IOException {
    Parent pharmac = FXMLLoader.load(getClass().getResource("/UI/pharmacy.fxml"));
    Scene pharmacScene = new Scene(pharmac);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(pharmacScene);
    app_stage.show();
                
       }

       private void stockmedicines(ActionEvent event) throws IOException {
              borderPane2.setCenter(null);
              borderPane2.setCenter(FXMLLoader.load(getClass().getResource("/UI/Stock.fxml")));
                
       }

       private void doctor(ActionEvent event) throws IOException {
              borderPane2.setCenter(null);
              borderPane2.setCenter(FXMLLoader.load(getClass().getResource("/UI/Sales-Report.fxml")));
       }
      private void close(ActionEvent event) {
             borderPane2.setCenter(null);
    }

    private void Add_Patient(ActionEvent event) {
       String date  = cur_date.getText();
     try {
          String q = "INSERT INTO `patient`(`id`,`pntNo`,`pntName`, `pntPhoneNo`,`pntFTreatment`,`pntComplain`)"+ " VALUES (?,?,?,?,?,?)";
          pst = conn.prepareStatement(q);
          pst.setInt(1,0);
          pst.setString(2, tfpntNo.getText());  
          pst.setString(3, tfpntName.getText()); 
          pst.setString(4, tfpntPhoneNo.getText());  
          pst.setString(5, date);
          pst.setString(6, pntComplain.getText());
          pst.execute();
        } catch (SQLException ex) {
                 System.out.println(ex);
                 ex.printStackTrace();
            }
        
    }
     public ObservableList<pharmacy> getMedicine(){
         
      ObservableList<pharmacy> medicineList = FXCollections.observableArrayList(); 
      String query = "SELECT * FROM stockmedicine WHERE ExpDate > NOW() AND DrugQuantity =1";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);         
          while(rs.next()){
              pharm = new pharmacy(rs.getInt("batchno"),rs.getString("DrugName"),rs.getString("category"),rs.getString("ExpDate"),rs.getInt("price"));
              medicineList.add(pharm);
          }
      }catch(SQLException ex){
          System.out.println(ex);
      }
      return medicineList;
    }
      public void showMedicine(){
          //to observe the data from database and display to the table
      ObservableList<pharmacy> list = getMedicine();
      colBatch.setCellValueFactory(new PropertyValueFactory<>("batchno"));
      colMdName.setCellValueFactory(new PropertyValueFactory<>("DrugName"));
      colLocation.setCellValueFactory(new PropertyValueFactory<>("Shelfno"));
      colExpDate.setCellValueFactory(new PropertyValueFactory<>("Expdate"));
      colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
      Tablemed.setItems(list);
     
    //to display the searching value filtered from database and displaythe single value to the table
    
    FilteredList<pharmacy> filterMedicine = new FilteredList<>(list, b -> true); 
    tfpharmMedicine.textProperty().addListener((ObservableValue<? extends String> observable,String oldValue, String newValue)->{
    filterMedicine.setPredicate((pharmacy pharmacy) ->   {

   if(newValue.isEmpty()){
       return true;
       
   }
   String searchKeyword = newValue.toLowerCase();
        return pharmacy.getDrugName().toLowerCase().contains(searchKeyword);
       
    });
});
    SortedList<pharmacy> sortedmedicine = new SortedList<>(filterMedicine);
    sortedmedicine.comparatorProperty().bind(Tablemed.comparatorProperty());
    Tablemed.setItems(sortedmedicine);
    }

    @FXML
    private void Print_Recept(ActionEvent event) throws DocumentException, IOException {
        
            String paystata = PaymentStatus.getValue(); 
            if(paystata==null){
      alert.AlertMaker.showNotification("Failed !","Select Payment Status",null);
      
            }else if("UnPaid".equals(paystata)){
      UpdateTableSales();
      UpDatesales();deleteAllfromTableList();
      makingreceipt();UnpaidAmountperday();
      insertPatient(); 
      btnList.setDisable(false); Clear_Table_List(); 
           clearfields();
     }else if("Paid".equals(PaymentStatus.getValue()) && tfTotalPaidAmount.getText().isEmpty()){ 
          alert.AlertMaker.showNotification("Failed !","Please Enter Amount Paid",null);
      }else if("Paid".equals(PaymentStatus.getValue()) && paymentMethod.getValue()==null){
        alert.AlertMaker.showNotification("Failed !","Please Enter Payment Method",null);  
      }
         else{
     UpdateTableSales();
     UpDatesales();paidAmountperday();UnpaidAmountperday();
     insertPatient();
     UpdatePatient();Clear_Table_List();
     deleteAllfromTableList();
     Balance();
     makingreceipt();
     btnList.setDisable(false);  
           clearfields();
            }
   }
 
    
    void recipt(){
   //  Integer[] singleData1 = new Integer[1];   
     String[] singleData = new String[3];
     singleData[0] = MadicineName.getText();
     singleData[1] = (SpnnerMedicineQuality.getValue()).toString();
     singleData[2] = tfTotal.getText();
     data.add(singleData);

        receipt.appendText("  \n\n" +  " " +MadicineName.getText()
              +"\t\t\t\t\t"+SpnnerMedicineQuality.getValue()
              +"\t\t\t\t"+tfTotal.getText());
    }
    
    void makingreceipt() throws DocumentException, IOException{
      
      receipt.appendText("\n-----------------------------------------------------------------"
                   +  "\nTotal Amount:"+tfDate.getText()
                   +  "\t\t\tBalance: "+tfUnpaidAmount.getText()
                   +  "\nPayment Status: "+PaymentStatus.getValue()
                   +  "\t\tPayment Method: "+paymentMethod.getValue()
                   ); 
       alert.AlertMaker.showNotification("Success !","Take the Receipt",null);
        printercpt();clearfields();receipt.clear();SpnnerMedicineQuality.getValueFactory().setValue(0);
     
    }
    void printercpt() throws DocumentException, IOException{
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");//initialValue
       String pdfnane = ""+receiptNo+".pdf";
       Document document = new Document();
      //Create OutputStream instance.
	OutputStream outputStream = 
	    new FileOutputStream(new File("C:\\Users\\domin\\Documents\\phamacyreceipt\\"+pdfnane+""));/*YOU CREATE THE FOLDER  ON FOLDER DOCUMENT AND NAME THE phamacyreceipt
                                                                                                          and copy the path only dont delete"+pdfname*/
        
        //Create PDFWriter instance.
        PdfWriter.getInstance(document, outputStream);
 
        //Open the document.
        document.open();
 
        //Create Table object, Here 4 specify the no. of columns
         PdfPTable pdfPTable2 = new PdfPTable(1);
         PdfPCell pdfPC00= new PdfPCell(new Paragraph("                                    DOMIC PHARMACY RECEIPT"));// YOU CHANGE THE RECIPT HEADING NAME TO YOUR OWN
           
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
        
        PdfPTable pdfPTable5 = new PdfPTable(2);
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
        
        
        PdfPTable pdfPTable = new PdfPTable(3);
        PdfPCell pdfPC1 = new PdfPCell(new Paragraph("Medicine Name"));
        PdfPCell pdfPC2= new PdfPCell(new Paragraph("Drug Quality Order"));
        PdfPCell pdfPC8= new PdfPCell(new Paragraph("Total Amount"));
        pdfPTable.addCell(pdfPC1);
        pdfPTable.addCell(pdfPC2);
        pdfPTable.addCell(pdfPC8);
//        pdfPTable.addCell(pdfPC8);
     
 
        for(int i=0;i<data.size();i++){
            
        
        //Create cells
        PdfPCell pdfPCell1 = new PdfPCell(new Paragraph(data.get(i)[0]));
        PdfPCell pdfPCell2 = new PdfPCell(new Paragraph(data.get(i)[1]));
        PdfPCell pdfPCell8 = new PdfPCell(new Paragraph(data.get(i)[2]));
 
        //Add cells to table
        pdfPTable.addCell(pdfPCell1);
        pdfPTable.addCell(pdfPCell2);
        pdfPTable.addCell(pdfPCell8);
        
 
   
        //Add content to the document using Table objects.
        }
        PdfPTable pdfPTable61 = new PdfPTable(2);
        PdfPCell pdfPC0061 = new PdfPCell(new Paragraph("                                                     "));
        pdfPC0061.setBorder(Rectangle.NO_BORDER);
        pdfPTable61.addCell(pdfPC0061);
        
        PdfPTable pdfPTable15 = new PdfPTable(2);
        PdfPCell pdfPC13 = new PdfPCell(new Paragraph("Total Amount: "+tfDate.getText()));
        PdfPCell pdfPC14= new PdfPCell(new Paragraph("Balance : "+tfUnpaidAmount.getText()));
        pdfPC14.setBorder(Rectangle.NO_BORDER);
        pdfPC13.setBorder(Rectangle.NO_BORDER);
        pdfPTable15.addCell(pdfPC13);
        pdfPTable15.addCell(pdfPC14);
        
        
        PdfPTable pdfPTable21 = new PdfPTable(2);
        PdfPCell pdfPC21 = new PdfPCell(new Paragraph("                                                     "));
        pdfPC21.setBorder(Rectangle.NO_BORDER);
        pdfPTable21.addCell(pdfPC21);
        
        PdfPTable pdfPTable16 = new PdfPTable(2);
        PdfPCell pdfPC31 = new PdfPCell(new Paragraph("Payment Status: "+PaymentStatus.getValue()));
        PdfPCell pdfPC41= new PdfPCell(new Paragraph("Payment Method : "+paymentMethod.getValue()));
        pdfPC41.setBorder(Rectangle.NO_BORDER);
        pdfPC31.setBorder(Rectangle.NO_BORDER);
        pdfPTable16.addCell(pdfPC31);
        pdfPTable16.addCell(pdfPC41);
               
        
        document.add(pdfPTable2);document.add(pdfPTable3);document.add(pdfPTable4);document.add(pdfPTable5);document.add(pdfPTable6);
        document.add(pdfPTable);document.add(pdfPTable15);document.add(pdfPTable16);document.add(pdfPTable61);document.add(pdfPTable21);

        //Close document and outputStream.
        document.close();
        outputStream.close();
 
     
     
    }
    void insertPatient(){
         try {
          PreparedStatement pst = conn.prepareStatement("INSERT INTO patient (RecptNo, pntFTreatment, DrugAdmistr,totalcost,paimentStatus) SELECT pntNo, Date,DrugName,TotalAmount,paymentStatus FROM sales WHERE pntNo ='"+receiptNo+"'");
          pst.execute();
       } catch (SQLException ex) {
           System.out.println(ex);
       }
    }
    void UpdatePatient(){
         try{
       String updatePatient = ("UPDATE  patient SET pntNo='"+tfpntNo.getText()+"',pntName='"+tfpntName.getText()+"',pntPhoneNo='"+tfpntPhoneNo.getText()+"',pntComplain='"+pntComplain.getText()+"' WHERE RecptNo = '"+receiptNo+"'");
       pst.execute(updatePatient);
       }catch(SQLException ex){
          System.out.println(ex);
       }
        
    }
    
    private void Search_Patient(ActionEvent event){
       try {
           String query = "SELECT `pntName`,`pntAddress`,`pntGender`,`pntAge` FROM patient WHERE pntNo = '"+pntregNo.getText()+"'";
           
           pst = conn.prepareStatement(query);
           rs = pst.executeQuery(query);
          
           if(rs.next()){
               pntName.setText(rs.getString(1));
               pntAddress.setText(rs.getString(2));
               pntGender.setText(rs.getString(3));
               pntAge.setText(rs.getString(4));
           }
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
    }
    
    @FXML
    private void Add_to_list(MouseEvent event) throws SQLException {
       pharmacy stck = Tablemed.getSelectionModel().getSelectedItem();
       MadicineName.setText(stck.getDrugName());receipt.setVisible(true);
       MedBatchNo.setText(""+stck.getBatchno());
       pricemedicine.setText(""+stck.getPrice());
       showMedicine();
       btnList.setDisable(false);
       tfUnpaidAmount.setText("");
       tfDate.setText("");
       tfTotalPaidAmount.setText("");
      
    }
  void updateStock(){
     int countQuantity = SpnnerMedicineQuality.getValue();
        try{
       String update = ("UPDATE  stockmedicine SET DrugQuantity=DrugQuantity - '"+ countQuantity +"' WHERE batchno = '"+MedBatchNo.getText()+"'");
       pst.execute(update);
       }catch(Exception ex){
          System.out.println(ex);
       }
  }
    private void Show_Report(ActionEvent event) throws IOException {
    Parent pharmac = FXMLLoader.load(getClass().getResource("/UI/Reports.fxml"));
    Scene pharmacScene = new Scene(pharmac);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(pharmacScene);
    app_stage.show();
    }

    @FXML
    private void Show_Patient_And_Report(ActionEvent event) throws IOException {
     borderPane2.setCenter(null);
      borderPane2.setCenter(FXMLLoader.load(getClass().getResource("/UI/View-Patient-Report.fxml")));
     
    }

    @FXML
    private void Total_Amount(MouseEvent event) {
        
     //Getting the Total amount of the Purchased medicine
        int prce = Integer.parseInt(pricemedicine.getText()); 
        int  qty = SpnnerMedicineQuality.getValue();
        int TotalAmount = prce * qty;
        tfTotal.setText(""+TotalAmount);
       showMedicine();
        
    }
      /**
     *
     * @return
     */
    public ObservableList<ListMedicine> getMedicineList(){
         
      ObservableList<ListMedicine> medicineList = FXCollections.observableArrayList(); 
      String query = "SELECT * FROM medicine_list";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);         
          while(rs.next()){
              listmed = new ListMedicine(rs.getString("medicineName"),rs.getString("totalAmount"));
              medicineList.add(listmed);
          }
      }catch(SQLException ex){
          System.out.println(ex);
      }
      return medicineList;
    }
    public void showMedicineList(){
      ObservableList<ListMedicine> medlist = getMedicineList();
      colMedName.setCellValueFactory(new PropertyValueFactory<>("drugName"));
      colTotal.setCellValueFactory(new PropertyValueFactory<>("tolprice"));
      tableListItems.setItems(medlist);
    }
    @FXML
    private void Add_List(ActionEvent event) {    
        int prce = Integer.parseInt(pricemedicine.getText()); 
        if(MadicineName.getText().trim().isEmpty()){
        alert.AlertMaker.showNotification("Failed !","You have not Select Any Medicine to Sell....Please Select First",null);
        tfTotal.requestFocus();tfTotal.requestFocus();
        }else{
  try {
        String insert = "INSERT INTO `medicine_list`(`id`,`pntNo`,`medicineName`,`medQty`,`price`,`totalAmount`)"+ " VALUES (?,?,?,?,?,?)";
          pst = conn.prepareStatement(insert);
          pst.setInt(1,0);
          pst.setInt(2, receiptNo);
          pst.setString(3, MadicineName.getText());  
          pst.setInt(4, SpnnerMedicineQuality.getValue());
          pst.setInt(5,prce);
          pst.setString(6, tfTotal.getText());   
          pst.execute();
          showMedicineList();showMedicine();
          totalSum(); 
        //  sales();
          recipt();
          updateStock();
          btnList.setDisable(true);
          MadicineName.setText("");
          pricemedicine.setText("");
          tfTotal.setText("");  SpnnerMedicineQuality.getValueFactory().setValue(0);
         alert.AlertMaker.showNotification("Success !","Added To the List",null);
        } catch (SQLException ex) {
            if(MadicineName.getText().trim().isEmpty()){
        alert.AlertMaker.showNotification("Failed !","You have not Select Any Medicine to Sell....Please Select First",null);
            }else{
                System.out.println(ex);
            }
        }
        }
    }
    private void totalSum(){
    try{
         String query = "SELECT SUM(totalAmount) totalAmount FROM medicine_list";
          st = conn.createStatement();
          rs = st.executeQuery(query);
          while(rs.next()){
          tfDate.setText(rs.getString(1));
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
}
    private void Balance(){
         int Bal = (Integer.parseInt(tfTotalPaidAmount.getText()) - (Integer.parseInt(tfDate.getText()))); 
         tfUnpaidAmount.setText(""+Bal);
    }
    private void UpdateTableSales(){
        try {
         String sql= "INSERT INTO sales(pntNo, DrugName,DrugQty,price,TotalAmount) SELECT pntNo, medicineName, medQty,price,totalAmount FROM medicine_list WHERE pntNo = '"+receiptNo+"'";
                pst=conn.prepareStatement(sql);
                pst.execute();
       } catch (SQLException ex) {
           System.out.println(ex);
       }
    }
  
    private void deleteAllfromTableList(){
         try {
         String sql= "DELETE FROM `medicine_list` WHERE pntNo = '"+receiptNo+"'";
                pst=conn.prepareStatement(sql);
                pst.execute();
       } catch (SQLException ex) {
           System.out.println(ex);
       }
    }
    private void current_Date(){
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       cur_date.setText(dateFormat.format(new Date()));
       
    }

    
     private void UpDatesales(){
       try{
       String update = ("UPDATE  sales SET Date = '"+cur_date.getText()+"', paymentStatus='"+PaymentStatus.getValue()+"',paymethod='"+paymentMethod.getValue()+"' WHERE pntNo = '"+receiptNo+"'");
       pst.execute(update);
       }catch(SQLException ex){
          System.out.println(ex);
       }  
    }
    private void paidAmountperday(){
          try{
         String query = "SELECT SUM(TotalAmount) TotalAmount FROM sales WHERE Date = '"+cur_date.getText()+"' AND paymentStatus='Paid'" ;
          st = conn.createStatement();
          rs = st.executeQuery(query);
          while(rs.next()){
        paidAmount.setText(rs.getString(1));
     
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
    }
     private void UnpaidAmountperday(){
          try{
         String query = "SELECT SUM(TotalAmount) TotalAmount FROM sales WHERE Date = '"+cur_date.getText()+"' AND paymentStatus='UnPaid'" ;
          st = conn.createStatement();
          rs = st.executeQuery(query);
          while(rs.next()){
        UnpaidAmount.setText(rs.getString(1));
          }
      }catch(SQLException ex){
        System.out.println(ex);
      }
    }
     private void Clear_Table_List(){
         tableListItems.getItems().clear();
        // UnpaidAmount.setText("");
     }

    private void Report(ActionEvent event) throws IOException {
    Parent pharmac = FXMLLoader.load(getClass().getResource("/UI/Reports.fxml"));
    Scene pharmacScene = new Scene(pharmac);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(pharmacScene);
    app_stage.show(); 
    }

    @FXML
    private void Stock(ActionEvent event) throws IOException {
      borderPane2.setCenter(null);
      borderPane2.setCenter(FXMLLoader.load(getClass().getResource("/UI/Stock.fxml")));  
    }

    private void Sales(ActionEvent event) throws IOException {
      borderPane2.setCenter(null);
      borderPane2.setCenter(FXMLLoader.load(getClass().getResource("/UI/Sale-Report.fxml")));   
    }

    @FXML
    private void LogOut(ActionEvent event) throws IOException {
    Parent pharmac = FXMLLoader.load(getClass().getResource("/UI/Login.fxml"));
    Scene pharmacScene = new Scene(pharmac);  
    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    app_stage.setScene(pharmacScene);
    app_stage.show(); 
    }

    @FXML
    private void AddToList(MouseEvent event) {
    }

    @FXML
    private void validateid(KeyEvent event) {
      tfpntNo.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
            tfpntNo.setText(newValue.replaceAll("[^\\d]", ""));
        }
    });
                 }
    @FXML
    private void validateName(KeyEvent event) {
        tfpntName.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
           tfpntName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
    }

    @FXML
    private void valdatephone(KeyEvent event) {
       tfpntPhoneNo.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
           tfpntPhoneNo.setText(newValue.replaceAll("[^\\d]", ""));
        }
    });   
    }
       void acceptdigitstring(){
      final int maxLength = 9;

         tfpntPhoneNo.setOnKeyTyped(t -> {

            if ( tfpntPhoneNo.getText().length() > maxLength) {
                int pos =  tfpntPhoneNo.getCaretPosition();
                tfpntPhoneNo.setText( tfpntPhoneNo.getText(0, maxLength));
                tfpntPhoneNo.positionCaret(pos); 
            }

        });
    }

    @FXML
    private void validateCondition(KeyEvent event) {
         pntComplain.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
            pntComplain.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
    }

    @FXML
    private void validatePaidAmount(KeyEvent event) {
          tfTotalPaidAmount.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
           tfTotalPaidAmount.setText(newValue.replaceAll("[^\\d]", ""));
        }
    });
    }

    @FXML
    private void validatemedName(KeyEvent event) {
         tfpharmMedicine.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
           tfpharmMedicine.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
    }
   
    @FXML
    private void clearfield(ActionEvent event) {
           try {
          PreparedStatement pst = conn.prepareStatement("DELETE FROM medicine_list  WHERE  medicineName = '"+MadicineName.getText()+"'");
          pst.execute();     showMedicineList();
          alert.AlertMaker.showNotification("Success !","Deleted Successfully",null);
       } catch (SQLException ex) {
           System.out.println(ex);
       }  
    }
 public void clearfields(){
    tfpharmMedicine.setText(""); tfpntPhoneNo.setText("");tfpntNo.setText("");tfpntName.setText("");pntComplain.setText("");
    tfDate.setText("");tfpntNo.setText(""); tfpntName.setText("");tfTotalPaidAmount.setText("");tfUnpaidAmount.setText("");
    tfTotal.setText("");pricemedicine.setText("");MadicineName.setText("");MedBatchNo.setText("");Clear_Table_List();
    PaymentStatus.setValue("");paymentMethod.setValue("");MedBatchNo.setText("");SpnnerMedicineQuality.getValueFactory().setValue(0);
}

    @FXML
    private void tableList(MouseEvent event) {
        ListMedicine stck =  tableListItems.getSelectionModel().getSelectedItem();
      MadicineName.setText(stck.getDrugName());
    }

    @FXML
    private void Admin_pane(ActionEvent event) throws IOException {
  borderPane2.setCenter(null);
      borderPane2.setCenter(FXMLLoader.load(getClass().getResource("/UI/Purchase.fxml"))); 
    }

    
  
}
