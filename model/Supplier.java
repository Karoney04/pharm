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
public class Supplier {
    
   private String CompNo;
   private String CompName;
   private String Product;
   private int Quanity;
   private String OrderNo;
   private int Cost;

    public Supplier(String CompNo, String CompName, String Product, int Quanity, String OrderNo, int Cost) {
        this.CompNo = CompNo;
        this.CompName = CompName;
        this.Product = Product;
        this.Quanity = Quanity;
        this.OrderNo = OrderNo;
        this.Cost = Cost;
    }

    public String getCompNo() {
        return CompNo;
    }

    public void setCompNo(String CompNo) {
        this.CompNo = CompNo;
    }

    public String getCompName() {
        return CompName;
    }

    public void setCompName(String CompName) {
        this.CompName = CompName;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String Product) {
        this.Product = Product;
    }

    public int getQuanity() {
        return Quanity;
    }

    public void setQuanity(int Quanity) {
        this.Quanity = Quanity;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int Cost) {
        this.Cost = Cost;
    }
    

}
