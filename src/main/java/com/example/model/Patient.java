/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kithmi
 */
public class Patient extends Person {
    
    private int patientId;
    private int age;
    private List<String> medicalHistory;
    private String currentHealthStatus;

    public Patient() {
    }

//    public Patient(int id) {
//        super(id);
//    }

    public Patient(int patientId) {
        this.patientId = patientId;
    }   

    public Patient(int id, String name, String contactInfo, String address, int age, List<String> medicalHistory, String currentHealthStatus) {
        super(id, name, contactInfo, address);
        this.age = age;
        this.medicalHistory = medicalHistory;
        this.currentHealthStatus = currentHealthStatus;
    }
    
     public Patient(int id, String name, String contactInfo, String address, int patientId, int age, List<String> medicalHistory, String currentHealthStatus) {
        super(id, name, contactInfo, address);
        this.age = age;
        this.medicalHistory = medicalHistory;
        this.currentHealthStatus = currentHealthStatus;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getCurrentHealthStatus() {
        return currentHealthStatus;
    }

    public void setCurrentHealthStatus(String currentHealthStatus) {
        this.currentHealthStatus = currentHealthStatus;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
