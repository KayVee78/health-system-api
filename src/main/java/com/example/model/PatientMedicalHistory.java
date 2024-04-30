/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

/**
 *
 * @author Kithmi
 */
public class PatientMedicalHistory {

    private int age;
    private String diagnosis;
    private String medications;
    private String allergies;
    private String surgicalHistory;

    public PatientMedicalHistory() {
    }

    public PatientMedicalHistory(int age, String diagnosis, String medications, String allergies, String surgicalHistory) {
        this.age = age;
        this.diagnosis = diagnosis;
        this.medications = medications;
        this.allergies = allergies;
        this.surgicalHistory = surgicalHistory;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getSurgicalHistory() {
        return surgicalHistory;
    }

    public void setSurgicalHistory(String surgicalHistory) {
        this.surgicalHistory = surgicalHistory;
    }
}
