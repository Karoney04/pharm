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
public class Outofstock {
  
   private String menName;
   private int medQty;
   private String medCat;

    public Outofstock(String menName, int medQty, String medCat) {
        this.menName = menName;
        this.medQty = medQty;
        this.medCat = medCat;
    }

    public String getMenName() {
        return menName;
    }

    public void setMenName(String menName) {
        this.menName = menName;
    }

    public int getMedQty() {
        return medQty;
    }

    public void setMedQty(int medQty) {
        this.medQty = medQty;
    }

    public String getMedCat() {
        return medCat;
    }

    public void setMedCat(String medCat) {
        this.medCat = medCat;
    }

   
}
