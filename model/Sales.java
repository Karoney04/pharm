/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author DOMINIC
 */
public class Sales {
   private String DrugName;
   private int DrugQty;
   private int Price;
   private int TotalAmount;

    public Sales(String DrugName, int DrugQty, int Price, int TotalAmount) {
        this.DrugName = DrugName;
        this.DrugQty = DrugQty;
        this.Price = Price;
        this.TotalAmount = TotalAmount;
    }

    public String getDrugName() {
        return DrugName;
    }

    public void setDrugName(String DrugName) {
        this.DrugName = DrugName;
    }

    public int getDrugQty() {
        return DrugQty;
    }

    public void setDrugQty(int DrugQty) {
        this.DrugQty = DrugQty;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public int getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(int TotalAmount) {
        this.TotalAmount = TotalAmount;
    }
   
   
}
