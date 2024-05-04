/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

/**
 *
 * @author Kithmi
 */
public class Billing {

    private int billingId;
    private int patientId;
    private Invoice invoiveInfo;
    private double amountPaid;
    private double balance;

    public Billing() {
    }

    public Billing(int billingId, int patientId, Invoice invoiveInfo, double amountPaid, double balance) {
        this.billingId = billingId;
        this.patientId = patientId;
        this.invoiveInfo = invoiveInfo;
        this.amountPaid = amountPaid;
        this.balance = balance;
    }

    public int getBillingId() {
        return billingId;
    }

    public void setBillingId(int billingId) {
        this.billingId = billingId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Invoice getInvoiveInfo() {
        return invoiveInfo;
    }

    public void setInvoiveInfo(Invoice invoiveInfo) {
        this.invoiveInfo = invoiveInfo;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
