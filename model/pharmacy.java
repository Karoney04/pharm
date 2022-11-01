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
public class pharmacy {
   private int batchno;
   private String DrugName;
   private String Shelfno;
   private String Expdate;
   private int Price;

    public pharmacy(int batchno, String DrugName, String Shelfno, String Expdate, int Price) {
        this.batchno = batchno;
        this.DrugName = DrugName;
        this.Shelfno = Shelfno;
        this.Expdate = Expdate;
        this.Price = Price;
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

    public String getShelfno() {
        return Shelfno;
    }

    public void setShelfno(String Shelfno) {
        this.Shelfno = Shelfno;
    }

    public String getExpdate() {
        return Expdate;
    }

    public void setExpdate(String Expdate) {
        this.Expdate = Expdate;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

   
}
