/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.model.Patient;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kithmi
 */
public class PatientDAO {

    private static List<Patient> patients = new ArrayList<>();

    static {
        patients.add(new Patient(1, "Ashan Dias", "0772572800", "Kalutara", "Age: 45\n"
                + "Diagnosis: Hypertension\n"
                + "Medications: For 1 month\n"
                + "Allergies: None\n"
                + "Surgical History: None\n",
                "Recovering"));
        patients.add(new Patient(2, "Janaka Fernando", "0712359807", "Colombo-03", "Age: 62\n"
                + "Diagnosis: Type 2 Diabetes\n"
                + "Medications: For 8 years\n"
                + "Allergies: Penicillin\n"
                + "Surgical History: Knee replacement surgery at age 60\n",
                "Critical"));
    }

    public List<Patient> getAllPatients() {
        return patients;
    }

    public void deletePatient(int id) {
        patients.removeIf(patient -> patient.getId() == id);
    }

    //HELPER METHODS
    public Patient findPatientById(int id) {
        Patient patientFound = null;
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                patientFound = patient;
                break;
            }
        }

        return patientFound;
    }
}
