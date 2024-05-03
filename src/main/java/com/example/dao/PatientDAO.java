/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.exception.ResourceNotFoundException;
import com.example.model.MedicalRecord;
import com.example.model.Patient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
public class PatientDAO {

    public static List<Patient> patients = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDAO.class);

    static {
        patients.add(new Patient(1, "Ashan Dias", "0772572800", "Kalutara", 40, List.of(
                "Medical Conditions: Type 2 Diabetes (diagnosed at age 35)",
                "Allergies: None",
                "Surgeries: None",
                "Medications: Metformin (500mg, twice daily)"
        ), "Controlled"));
        patients.add(new Patient(2, "Janaka Fernando", "0712359807", "Colombo-03", 46, List.of("Medical Conditions: Coronary artery disease (diagnosed at age 42)", "Allergies: None", "Surgeries: Coronary Artery Bypass Graft", "Medications: Medications: Aspirin (daily), Statin (daily)"), "Recovering"));

    }

    public List<Patient> getAllPatients() {
        return patients;
    }

    public void deletePatient(int id) {
        patients.removeIf(patient -> patient.getId() == id);
    }

    public void addPatient(Patient patient) {
        if (patient.getName() != null && patient.getAddress() != null && patient.getContactInfo() != null && patient.getMedicalHistory() != null && patient.getCurrentHealthStatus() != null) {
            int newUserId = getNextUserId();
            patient.setId(newUserId);
            patients.add(patient);
        } else {
            LOGGER.error("Missing mandatory field(s) in patient data. Failed to add a new Patient!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in patient data. Failed to add a new Patient!");
        }

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
