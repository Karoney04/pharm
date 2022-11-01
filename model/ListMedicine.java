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
public class ListMedicine {
   
   private String drugName;
   private String tolprice;

    public ListMedicine(String drugName, String tolprice) {
        this.drugName = drugName;
        this.tolprice = tolprice;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getTolprice() {
        return tolprice;
    }

    public void setTolprice(String tolprice) {
        this.tolprice = tolprice;
    }
  
}
