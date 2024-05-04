/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

/**
 *
 * @author Kithmi
 */
public class Invoice {
    private String patientDetails;
    private double amountPayable; //doctor fee + hospital charges
    private String date;

    public Invoice() {
    }

    public Invoice(String patientDetails, double amountPayable, String date) {
        this.patientDetails = patientDetails;
        this.amountPayable = amountPayable;
        this.date = date;
    }

    public String getPatientDetails() {
        return patientDetails;
    }

    public void setPatientDetails(String patientDetails) {
        this.patientDetails = patientDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(double amountPayable) {
        this.amountPayable = amountPayable;
    }
}
