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
public class Medcategory {
   private int Id;
   private String DrugCategory;
   private String ShelfNo;

    public Medcategory(int Id, String DrugCategory, String ShelfNo) {
        this.Id = Id;
        this.DrugCategory = DrugCategory;
        this.ShelfNo = ShelfNo;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getDrugCategory() {
        return DrugCategory;
    }

    public void setDrugCategory(String DrugCategory) {
        this.DrugCategory = DrugCategory;
    }

    public String getShelfNo() {
        return ShelfNo;
    }

    public void setShelfNo(String ShelfNo) {
        this.ShelfNo = ShelfNo;
    }

  
}
