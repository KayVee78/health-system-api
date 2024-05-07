/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Patient;
import com.example.model.Person;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
public class PatientDAO extends PersonDAO {

    public static List<Patient> patients = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDAO.class);

//    static {
//        patients.add(new Patient(1, "Ashan Dias", "0772572800", "Kalutara", 1, 40, List.of(
//                "Medical Conditions: Type 2 Diabetes (diagnosed at age 35)",
//                "Allergies: None",
//                "Surgeries: None",
//                "Medications: Metformin (500mg, twice daily)"
//        ), "Controlled"));
//        patients.add(new Patient(2, "Janaka Fernando", "0712359807", "Colombo-03", 2, 46, List.of("Medical Conditions: Coronary artery disease (diagnosed at age 42)", "Allergies: None", "Surgeries: Coronary Artery Bypass Graft", "Medications: Medications: Aspirin (daily), Statin (daily)"), "Recovering"));
//
//    }

    public List<Patient> getAllPatients() {
        return patients;
    }

    public void deletePatient(int id) {
        Patient findPatient = findPatientById(id);
        deletePerson(findPatient.getId());
        patients.removeIf(patient -> patient.getPatientId() == id);
    }

    public void addPatient(Patient patient) {
        if (patient.getAge() > 0 && (patient.getName() != null && patient.getName() instanceof String && !patient.getName().isEmpty()) && (patient.getAddress() != null && patient.getAddress() instanceof String && !patient.getAddress().isEmpty()) && (patient.getContactInfo() != null && patient.getContactInfo() instanceof String && !patient.getContactInfo().isEmpty()) && (patient.getMedicalHistory() != null && patient.getMedicalHistory() instanceof List) && (patient.getCurrentHealthStatus() != null && patient.getCurrentHealthStatus() instanceof String && !patient.getCurrentHealthStatus().isEmpty())) {
            int newPersonId = getNextUserId();
            int newPatientId = getNewPatientId();
            patient.setId(newPersonId);
            patient.setPatientId(newPatientId);
            addPerson(new Person(patient.getId(), patient.getName(), patient.getContactInfo(), patient.getAddress()));
            patients.add(patient);

        } else {
            LOGGER.error("Missing mandatory field(s) in patient data. Failed to add a new Patient!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in patient data. Failed to add a new Patient!", Response.Status.BAD_REQUEST);
        }

    }

    public void updatePatient(Patient updatePatient) {
        if (updatePatient.getAge() > 0 && (updatePatient.getName() != null && updatePatient.getName() instanceof String && !updatePatient.getName().isEmpty()) && (updatePatient.getAddress() != null && updatePatient.getAddress() instanceof String && !updatePatient.getAddress().isEmpty()) && (updatePatient.getContactInfo() != null && updatePatient.getContactInfo() instanceof String && !updatePatient.getContactInfo().isEmpty()) && (updatePatient.getMedicalHistory() != null && updatePatient.getMedicalHistory() instanceof List) && (updatePatient.getCurrentHealthStatus() != null && updatePatient.getCurrentHealthStatus() instanceof String && !updatePatient.getCurrentHealthStatus().isEmpty())) {
            for (int i = 0; i < patients.size(); i++) {
                Patient patient = patients.get(i);
                if (patient.getPatientId() == updatePatient.getPatientId()) {
                    Patient updatingPatient = findPatientById(updatePatient.getPatientId());
                    updatePerson(new Person(updatingPatient.getId(), updatePatient.getName(), updatePatient.getContactInfo(), updatePatient.getAddress()));
                    updatePatient.setId(updatingPatient.getId());
                    patients.set(i, updatePatient);
                    return;
                }
            }
        } else {
            LOGGER.error("Missing mandatory field(s) in patient data. Failed to update Patient!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in patient data. Failed to update Patient!", Response.Status.BAD_REQUEST);
        }
    }

    public void updatePatientFromPerson(Patient updatePatient) {
        if (updatePatient.getAge() > 0 && (updatePatient.getName() != null && updatePatient.getName() instanceof String && !updatePatient.getName().isEmpty()) && (updatePatient.getAddress() != null && updatePatient.getAddress() instanceof String && !updatePatient.getAddress().isEmpty()) && (updatePatient.getContactInfo() != null && updatePatient.getContactInfo() instanceof String && !updatePatient.getContactInfo().isEmpty()) && (updatePatient.getMedicalHistory() != null && updatePatient.getMedicalHistory() instanceof List) && (updatePatient.getCurrentHealthStatus() != null && updatePatient.getCurrentHealthStatus() instanceof String && !updatePatient.getCurrentHealthStatus().isEmpty())) {
            Patient updatingPatient = findPatientByPersonId(updatePatient.getId());
            updatePatient.setPatientId(updatingPatient.getPatientId());
            for (int i = 0; i < patients.size(); i++) {
                Patient patient = patients.get(i);
                if (patient.getPatientId() == updatePatient.getPatientId()) {
                    patients.set(i, updatePatient);
                    return;
                }
            }
        } else {
            LOGGER.error("Missing mandatory field(s) in patient data. Failed to update Patient!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in patient data. Failed to update Patient!", Response.Status.BAD_REQUEST);
        }
    }

    //HELPER METHODS
    public Patient findPatientById(int id) {
        Patient patientFound = null;
        for (Patient patient : patients) {
            if (patient.getPatientId() == id) {
                patientFound = patient;
                break;
            }
        }
        return patientFound;
    }

    public Patient findPatientByPersonId(int id) {
        Patient patientFound = null;
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                patientFound = patient;
                break;
            }
        }
        return patientFound;
    }

    public int getNewPatientId() {
        int maxUserId = 0;
        if (patients.size() > 0) {
            maxUserId = Integer.MIN_VALUE;

            for (Patient patient : patients) {
                int userId = patient.getPatientId();
                if (userId > maxUserId) {
                    maxUserId = userId;
                }
            }
            return maxUserId + 1;
        } else {
            maxUserId = 1;
        }
        return maxUserId;
    }

    private boolean isValidNumber(String age) {
        try {
            Integer.parseInt(age);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
