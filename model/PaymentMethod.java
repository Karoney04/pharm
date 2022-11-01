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
public class PaymentMethod {
   private String DrugName;
   private int TotalAmount;
   private String paymethod;

    public PaymentMethod(String DrugName, int TotalAmount, String paymethod) {
        this.DrugName = DrugName;
        this.TotalAmount = TotalAmount;
        this.paymethod = paymethod;
    }

    public String getDrugName() {
        return DrugName;
    }

    public void setDrugName(String DrugName) {
        this.DrugName = DrugName;
    }

    public int getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(int TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

  
}
