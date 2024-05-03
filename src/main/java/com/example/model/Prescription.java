/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

import java.util.List;

/**
 *
 * @author Kithmi
 */
public class Prescription {
    private int prescriptionId;
    private String patientId;
    private List<String> medication; //Includes medication name, dosage, instructions and duration

    public Prescription() {
    }

    public Prescription(int prescriptionId, String patientId, List<String> medication) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.medication = medication;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public List<String> getMedication() {
        return medication;
    }

    public void setMedication(List<String> medication) {
        this.medication = medication;
    }
}
