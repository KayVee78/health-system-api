/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import static com.example.dao.MedicalRecordDAO.medicalRecords;
import com.example.exception.ResourceNotFoundException;
import com.example.model.MedicalRecord;
import com.example.model.Patient;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
public class MedicalRecordDAO {

    public static List<MedicalRecord> medicalRecords = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordDAO.class);
    private PatientDAO patientDAO = new PatientDAO();

    static {
        medicalRecords.add(new MedicalRecord(1, "1", "Management of Type 2 Diabetes", "Type 2 Diabetes", "Monitor blood sugar levels regularly, Continue current medications", "None"));
        medicalRecords.add(new MedicalRecord(2, "2", "Acute upper respiratory infection (URI)", "Acute URI, viral", "Rest, fluids, over-the-counter pain relievers, recommend follow-up if symptoms worsen or persist", "None"));
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        List<MedicalRecord> formattedMedicalRecordList = new ArrayList<>();
        if (!medicalRecords.isEmpty()) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                int recordId = medicalRecord.getRecordId();
                int patientId = Integer.parseInt(medicalRecord.getPatientId());
                Patient patient = patientDAO.findPatientById(patientId);
                if (patient != null) {
                    String patientDetails = String.format("%d (%s)",
                            patientId,
                            patient.getName());

                    //Created a new Appointment Object with formatted data
                    MedicalRecord formattedMedicalRecordObj = new MedicalRecord(recordId, patientDetails, medicalRecord.getReasonToVisit(), medicalRecord.getDiagnosis(), medicalRecord.getMedications(), medicalRecord.getAllergies());
                    formattedMedicalRecordList.add(formattedMedicalRecordObj);
                } else {
                    throw new ResourceNotFoundException("Error occurred while finding a medicsl record for patient with ID: " + patientId);
                }
            }
        }
        return formattedMedicalRecordList;
    }

    public MedicalRecord getMedicalRecordById(int id) {
        MedicalRecord formattedMedicalRecord = null;
        MedicalRecord medicalRecord = findMedicalRecordById(id);
        int patientId = Integer.parseInt(medicalRecord.getPatientId());
        Patient patient = patientDAO.findPatientById(patientId);
        if (patient != null) {
            String patientDetails = String.format("%d (%s)",
                    patientId,
                    patient.getName());
            formattedMedicalRecord = new MedicalRecord(medicalRecord.getRecordId(), patientDetails, medicalRecord.getReasonToVisit(), medicalRecord.getDiagnosis(), medicalRecord.getMedications(), medicalRecord.getAllergies());
        } else {
            throw new ResourceNotFoundException("Error occurred while finding a medicsl record for patient with ID: " + patientId);
        }
        return formattedMedicalRecord;
    }

    public void deleteMedicalRecord(int id) {
        medicalRecords.removeIf(medicalRecord -> medicalRecord.getRecordId() == id);
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        if (medicalRecord.getPatientId() != null && medicalRecord.getReasonToVisit() != null && medicalRecord.getDiagnosis() != null && medicalRecord.getMedications() != null && medicalRecord.getAllergies() != null) {

            if (!isNumeric(medicalRecord.getPatientId())) {
                throw new ResourceNotFoundException("Patient ID must be numeric. Failed to add a new medical record!");
            }
            boolean isValidPatient = false;
            for (Patient patient : PatientDAO.patients) {
                if (patient.getId() == Integer.parseInt(medicalRecord.getPatientId())) {
                    isValidPatient = true;
                    break;
                }
            }
            if (!isValidPatient) {
                throw new ResourceNotFoundException("Invalid patient ID. Failed to add a new medical record!");
            }

            int newRecordId = getNextRecordId();
            medicalRecord.setRecordId(newRecordId);
            medicalRecords.add(medicalRecord);
        } else {
            LOGGER.error("Missing mandatory field(s) in medical record data. Failed to add a new medical record!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in medical record data. Failed to add a new medical record!");
        }

    }

    public void updateMedicalRecord(MedicalRecord updateMedicalRecord) {
        if (!isNumeric(updateMedicalRecord.getPatientId())) {
            throw new ResourceNotFoundException("Patient ID must be numeric. Failed to add a new medical record!");
        }
        boolean isValidPatient = false;
        for (Patient patient : PatientDAO.patients) {
            if (patient.getId() == Integer.parseInt(updateMedicalRecord.getPatientId())) {
                isValidPatient = true;
                break;
            }
        }
        if (isValidPatient) {
            for (int i = 0; i < medicalRecords.size(); i++) {
                MedicalRecord medicalRecord = medicalRecords.get(i);
                if (medicalRecord.getRecordId() == updateMedicalRecord.getRecordId()) {
                    medicalRecords.set(i, updateMedicalRecord);
                    return;
                }
            }
        } else {
            throw new ResourceNotFoundException("Error occured while updating a medical record. No patient found with ID: " + updateMedicalRecord.getPatientId());
        }

    }

    public MedicalRecord getMedicalRecordByPatientId(int id) {
        MedicalRecord medicalRecordFound = null;
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (Integer.parseInt(medicalRecord.getPatientId()) == id) {
                Patient patient = patientDAO.findPatientById(id);
                String patientDetails = String.format("%d (%s)",
                        id,
                        patient.getName());
                medicalRecord.setPatientId(patientDetails);
                medicalRecordFound = medicalRecord;
                break;
            }
        }
        return medicalRecordFound;
    }

    //HELPER METHODS
    public MedicalRecord findMedicalRecordById(int id) {
        MedicalRecord medicalRecordFound = null;
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getRecordId() == id) {
                medicalRecordFound = medicalRecord;
                break;
            }
        }
        return medicalRecordFound;
    }

    public int getNextRecordId() {
        int maxUserId = Integer.MIN_VALUE;

        for (MedicalRecord medicalRecord : medicalRecords) {
            int userId = medicalRecord.getRecordId();
            if (userId > maxUserId) {
                maxUserId = userId;
            }
        }

        return maxUserId + 1;

    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
}
