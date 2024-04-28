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

    public void addPatient(Patient patient) {
        int newUserId = getNextUserId();
        patient.setId(newUserId);
        patients.add(patient);
    }

    public void updatePatient(Patient updatePatient) {
        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            if (patient.getId() == updatePatient.getId()) {
                patients.set(i, updatePatient);
                return;
            }
        }
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

    public int getNextUserId() {
        //Initialize the maxUserId with a value lower than any possible userId
        int maxUserId = Integer.MIN_VALUE;

        //Iterage through the list and finding the maximum userId
        for (Patient patient : patients) {
            int userId = patient.getId();
            if (userId > maxUserId) {
                maxUserId = userId;
            }
        }

        //Increment the mxUserId to get the next available userId
        return maxUserId + 1;
    }
}
