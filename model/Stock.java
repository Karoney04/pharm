/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author KARONEI
 */
public class Stock {
   private int batchno;
   private String DrugName;
   private int Price;
   private int DrugQuantity;
   private String Shelfno;
   private String Category;
   private String Expdate;
   private String Supplier;

    public Stock(int batchno, String DrugName, int Price, int DrugQuantity, String Shelfno, String Category, String Expdate, String Supplier) {
        this.batchno = batchno;
        this.DrugName = DrugName;
        this.Price = Price;
        this.DrugQuantity = DrugQuantity;
        this.Shelfno = Shelfno;
        this.Category = Category;
        this.Expdate = Expdate;
        this.Supplier = Supplier;
    }

    public int getBatchno() {
        return batchno;
    }

    public void setBatchno(int batchno) {
        this.batchno = batchno;
    }

    public String getDrugName() {
        return DrugName;
    }

    public void setDrugName(String DrugName) {
        this.DrugName = DrugName;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public int getDrugQuantity() {
        return DrugQuantity;
    }

    public void setDrugQuantity(int DrugQuantity) {
        this.DrugQuantity = DrugQuantity;
    }

    public String getShelfno() {
        return Shelfno;
    }

    public void setShelfno(String Shelfno) {
        this.Shelfno = Shelfno;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getExpdate() {
        return Expdate;
    }

    public void setExpdate(String Expdate) {
        this.Expdate = Expdate;
    }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String Supplier) {
        this.Supplier = Supplier;
    }

   
}
