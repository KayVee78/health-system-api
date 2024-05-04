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
    private int appointmentId;
    private Invoice invoiceInfo;
    private double amountPaid;
    private double balance;

    public Billing() {
    }

    public Billing(int billingId, int appointmentId, Invoice invoiceInfo, double amountPaid, double balance) {
        this.billingId = billingId;
        this.appointmentId = appointmentId;
        this.invoiceInfo = invoiceInfo;
        this.amountPaid = amountPaid;
        this.balance = balance;
    }

    public int getBillingId() {
        return billingId;
    }

    public void setBillingId(int billingId) {
        this.billingId = billingId;
    }

    public Invoice getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(Invoice invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
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

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
}
