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
public class Patient {
   private String RecptNo;
   private String pntNo;
   private String pntName;
   private int pntPhoneNo;
   private String pntFTreatment;
   private String pntComplain;
   private String DrugAdmistr;
   private int 	totalcost;
   private String paimentStatus;

    public Patient(String RecptNo, String pntNo, String pntName, int pntPhoneNo, String pntFTreatment, String pntComplain, String DrugAdmistr, int totalcost, String paimentStatus) {
        this.RecptNo = RecptNo;
        this.pntNo = pntNo;
        this.pntName = pntName;
        this.pntPhoneNo = pntPhoneNo;
        this.pntFTreatment = pntFTreatment;
        this.pntComplain = pntComplain;
        this.DrugAdmistr = DrugAdmistr;
        this.totalcost = totalcost;
        this.paimentStatus = paimentStatus;
    }

    public String getRecptNo() {
        return RecptNo;
    }

    public void setRecptNo(String RecptNo) {
        this.RecptNo = RecptNo;
    }

    public String getPntNo() {
        return pntNo;
    }

    public void setPntNo(String pntNo) {
        this.pntNo = pntNo;
    }

    public String getPntName() {
        return pntName;
    }

    public void setPntName(String pntName) {
        this.pntName = pntName;
    }

    public int getPntPhoneNo() {
        return pntPhoneNo;
    }

    public void setPntPhoneNo(int pntPhoneNo) {
        this.pntPhoneNo = pntPhoneNo;
    }

    public String getPntFTreatment() {
        return pntFTreatment;
    }

    public void setPntFTreatment(String pntFTreatment) {
        this.pntFTreatment = pntFTreatment;
    }

    public String getPntComplain() {
        return pntComplain;
    }

    public void setPntComplain(String pntComplain) {
        this.pntComplain = pntComplain;
    }

    public String getDrugAdmistr() {
        return DrugAdmistr;
    }

    public void setDrugAdmistr(String DrugAdmistr) {
        this.DrugAdmistr = DrugAdmistr;
    }

    public int getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(int totalcost) {
        this.totalcost = totalcost;
    }

    public String getPaimentStatus() {
        return paimentStatus;
    }

    public void setPaimentStatus(String paimentStatus) {
        this.paimentStatus = paimentStatus;
    }

  
     
}
