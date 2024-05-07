/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

/**
 *
 * @author Kithmi
 */
public class Doctor extends Person {

    private int doctorId;
    private String specialization;
    private double doctorFee;

    public Doctor() {
    }

    public Doctor(int id, String name, String contactInfo, String address, String specialization, double doctorFee) {
        super(id, name, contactInfo, address);
        this.specialization = specialization;
        this.doctorFee = doctorFee;
    }

    public Doctor(int id, String name, String contactInfo, String address) {
        super(id, name, contactInfo, address);
    }
        
    public Doctor(int id, String name, String contactInfo, String address, int doctorId, String specialization, double doctorFee) {
        super(id, name, contactInfo, address);
        this.doctorId = this.doctorId;
        this.specialization = specialization;
        this.doctorFee = doctorFee;
    }
//   
//    public Doctor(int id) {
//        super(id);
//    }
    
    public Doctor(int doctorId) {
        this.doctorId = this.doctorId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public double getDoctorFee() {
        return doctorFee;
    }

    public void setDoctorFee(double doctorFee) {
        this.doctorFee = doctorFee;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
    
}
